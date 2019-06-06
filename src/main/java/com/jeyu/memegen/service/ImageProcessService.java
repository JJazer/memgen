package com.jeyu.memegen.service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;


public class ImageProcessService {

    private URL url;
    private String textToOverlay;


    public ImageProcessService(String url, String textToOverlay) {
        this.textToOverlay = textToOverlay;
        createUrl(url).ifPresent(url1 -> this.url = url1);

    }

    Optional<URL> createUrl(String url) {
        try {
            return Optional.of(new URL(url));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return Optional.empty();

    }

    public String processImage(){
        try {
            final BufferedImage image = ImageIO
                    .read(url);
            Size imageSize= getImageSize(image);
            Graphics2D graph = (Graphics2D) image.getGraphics();

            overlayStringOnImage(graph, imageSize);
            return encodeToString(image);
        }catch(final IOException ioe){
            throw new UncheckedIOException(ioe);
        }
    }

    private void overlayStringOnImage(Graphics2D graph, Size size) {
        //graph.setFont(graph.getFont().deriveFont(30f));
        Font impact = new Font("Impact", Font.BOLD, 30);

        graph.setFont(impact);
        graph.setFont(graph.getFont().deriveFont(30f));
        graph.setStroke(new BasicStroke(5f));

        List<String> textToOverlay = getTextToOverlay(this.textToOverlay, graph, size);
        for (int i = 0; i < textToOverlay.size(); i++) {

            Size position = calculatePosition(textToOverlay.get(i), i, graph, size);

            graph.drawString(textToOverlay.get(i), position.getX(), position.getY());
        }

        graph.dispose();
    }




    String encodeToString(BufferedImage image){
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        try{
            ImageIO.write(image, "png", os);
            return Base64.getEncoder().encodeToString(os.toByteArray());
        }catch(final IOException ioe){
            throw new UncheckedIOException(ioe);
        }
    }

    Size getImageSize(BufferedImage image){
        return new Size(image.getWidth(), image.getHeight());
    }

    private List<String> getTextToOverlay(String text, Graphics g, Size size){
        List<String> stringList = new ArrayList<>();
        int x = g.getFontMetrics().stringWidth(text);
        if(x>size.getX()){
            String[] parts = cutInHalf(text);
            stringList.addAll(getTextToOverlay(parts[0], g, size));
            stringList.addAll(getTextToOverlay(parts[1], g, size));

        }else{
            stringList.add(text);
        }
        return stringList;
    }

    String[] cutInHalf(String text){
        int middle = text.length() / 2;
        int before = text.lastIndexOf(" ", middle);
        String[] str = new String[2];
        str[0] = text.substring(0, before);
        str[1] = text.substring(before+1);
        return str;
    }

    private Size calculatePosition(String text, int index, Graphics g, Size size){
        Size position =new Size();
        position.setX(getPosX(text, g, size));
        position.setY(getPosY(index, g, size));
        return position;
    }

    private int getPosY(int index, Graphics g, Size size) {
        int posY = 0;
        switch (index){
            case 0:
                posY = 30;
                break;
            case 1:
                posY = g.getFontMetrics().getHeight() + 40;
                break;
            case 2:
                posY = size.getY() - g.getFontMetrics().getHeight() - 20;
                break;
            case 3:
                posY = size.getY() - 10;
                break;
                default:
                    break;
        }
        return posY;
    }

    private int getPosX(String text, Graphics g, Size size) {
        int halfWidth = size.getX() /2;
        int halfStringWidth = g.getFontMetrics().stringWidth(text)/2;
        return halfWidth - halfStringWidth;
    }

    static class Size{
        private int x;
        private int y;

        Size() {
        }

        Size(int x, int y) {
            this.x = x;
            this.y = y;
        }

        int getX() {
            return x;
        }

        void setX(int x) {
            this.x = x;
        }

        int getY() {
            return y;
        }

        void setY(int y) {
            this.y = y;
        }

    }
}
