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

    public Optional<URL> createUrl(String url) {
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
            Graphics graph = image.getGraphics();
            //overlayStringOnImage(graph, textToOverlay);
            String imageInBase64 = encodeToString(image);
            return imageInBase64;
        }catch(final IOException ioe){
            throw new UncheckedIOException(ioe);
        }
    }

    private void overlayStringOnImage(Graphics graph, List<String> textToOverlay, Size size) {
        graph.setFont(graph.getFont().deriveFont(30f));
        for (int i = 0; i < textToOverlay.size(); i++) {
            Size position = calculatePosition(textToOverlay.get(i), i, graph, size);
            graph.drawString(textToOverlay.get(i), position.getX(), position.getY());
        }

        graph.dispose();
    }


    public String encodeToString(BufferedImage image){
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        try{
            ImageIO.write(image, "png", os);
            return Base64.getEncoder().encodeToString(os.toByteArray());
        }catch(final IOException ioe){
            throw new UncheckedIOException(ioe);
        }
    }

    public Size getImageSize(BufferedImage image){
        return new Size(image.getWidth(), image.getHeight());
    }

    public List<String> getTextToOverlay(String text, Graphics g, Size size){
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

    public String[] cutInHalf(String text){
        int middle = text.length() / 2;
        int before = text.lastIndexOf(" ", middle);
        String[] str = new String[2];
        str[0] = text.substring(0, before);
        str[1] = text.substring(before+1);
        return str;
    }

    public Size calculatePosition(String text, int index, Graphics g, Size size){
        Size position =new Size();
        position.setX(getPosX(text, g, size));
        position.setY(getPosY(index, g, size));
        return position;
    }

    private int getPosY(int index, Graphics g, Size size) {
        int posY = 0;
        switch (index){
            case 0:
                posY = 10;
                break;
            case 1:
                posY = g.getFontMetrics().getHeight() + 20;
                break;
            case 2:
                posY = size.getY() - g.getFontMetrics().getHeight() - 20;
                break;
            case 3:
                posY = size.getY() - 10;
                break;
                default:
                    posY = 0;
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

        public Size() {
        }

        public Size(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

    }
}
