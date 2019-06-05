package com.jeyu.memegen.service;

import org.junit.jupiter.api.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Optional;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ImageProcessServiceTest {

    public static final String WHITE_SQUARE = "iVBORw0KGgoAAAANSUhEUgAAAV8AAAFfCAYAAADptc+BAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAA2NpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMC1jMDYxIDY0LjE0MDk0OSwgMjAxMC8xMi8wNy0xMDo1NzowMSAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wTU09Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9tbS8iIHhtbG5zOnN0UmVmPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VSZWYjIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtcE1NOk9yaWdpbmFsRG9jdW1lbnRJRD0ieG1wLmRpZDowMjgwMTE3NDA3MjA2ODExQjc4QUFGOTUzREJCNTBCOCIgeG1wTU06RG9jdW1lbnRJRD0ieG1wLmRpZDozRUVFNkFCMkJDNzgxMUUxQUY5OUFEQzg0RTEyRjFENiIgeG1wTU06SW5zdGFuY2VJRD0ieG1wLmlpZDozRUVFNkFCMUJDNzgxMUUxQUY5OUFEQzg0RTEyRjFENiIgeG1wOkNyZWF0b3JUb29sPSJBZG9iZSBQaG90b3Nob3AgQ1M0IE1hY2ludG9zaCI+IDx4bXBNTTpEZXJpdmVkRnJvbSBzdFJlZjppbnN0YW5jZUlEPSJ4bXAuaWlkOjUxNTM1OTdGM0IyMDY4MTE5QjE2ODE4NkVGQjRDMzdCIiBzdFJlZjpkb2N1bWVudElEPSJ1dWlkOkNCMDU2MzZCNkVBREUxMTE5NUM0OTBGQ0FBRUNBNEQwIi8+IDwvcmRmOkRlc2NyaXB0aW9uPiA8L3JkZjpSREY+IDwveDp4bXBtZXRhPiA8P3hwYWNrZXQgZW5kPSJyIj8+e3rDXAAABcZJREFUeNrs3e9K21AYwOGc5FQcgoiKX3cvu55dwe5hlyqyQTtsm5zlaFJibLHVLtjxPFCSxv+n8PPlfEhDsV0oADiW9FZkX0V3vV5/CyHctacr6wfwMWVZVu1hGXeEN/TPm6Y5b+P7pT2Nlg3g4Nim0XluaRlH4Q3D8ObjarUqYoxbx2YA3pS68OZhNrXHJl8bT7N9eMv++XK5LNvJN6SU7AMDHDb15uCGbuLNj3z+9LG4I7ybCTh/YV3XOb6lpQTYf7uhbWeRZ9d+NyFfy03t4xtGWw/FMMKr1SqcnZ3leltNgP31wd1sPbyI844veLH325cagP0MB9Ztw+u2Pd/xNxBggAN1WwxPXa2qaq/JF4B/HWdLACC+AOILgPgCiC8A4gsgvgCIL4D4AjDhDdLv7++/X1xc3PW3UwM4JSmlYj6f/7q+vv55UvFtw/v15ubmtqoqryJwcvJ9GkIItyc3+bYT758cXvEFTlEb3tyxxdGaaEkB9pO3HsQX4IRNEt/8/m/H/I8BIL57hNcyA0wY3z683gUDYOLJFwDxBRBfAPEFQHwBxBcA8QUQXwDEF0B8ARBfAPEFEF8AxBdAfAEQXwDxBUB8AcQXAPEFEF8A8QVAfAHEFwDxBRBfAMQXQHwBEF8A8QUQX0sAIL4A4guA+AKILwDiCyC+AIgvgPgCIL4A4gsgvgCIL4D4AiC+AOILgPgCiC8A4gsgvgDiC4D4AogvAOILIL4AiC+A+AIgvgDiCyC+AIgvgPgCIL4A4guA+AKILwDiCyC+AOILgPgCiC8A4gsgvgCIL4D4AiC+AOILIL4AiC+A+AIgvgDiC4D4AogvAOILIL4A4guA+AKILwDiCyC+AIgvgPgCIL4A4gsgvgCIL4D4AiC+AOILgPgCiC8A4gsgvgDiC4D4AogvAOILIL4AiC+A+AIgvgDiCyC+AIgvgPgCIL4A4guA+AKILwDiCyC+AIgvgPgCiC8A4gsgvgCIL4D4AiC+AOILgPgCiC+A+AIgvgDiC4D4AogvAOILIL4AiC+A+AKILwDiCyC+AIgvgPgCIL4A4guA+AKIL4D4AiC+AOILgPgCiC8A4gsgvgCIL4D4AogvAOILIL4AiC+A+AIgvgDiC4D4AogvgPgCIL4A4guA+AKILwDiCyC+AIgvgPgCiC8A4gsgvgCIL4D4AiC+AOILgPgCiC+A+AIgvgDiC4D4AogvAOILIL4AiC+A+AKILwDiCyC+AIgvgPgCIL4A4guA+AKILwDiCyC+AOILgPgCiC8A4gsgvgCIL4D4AiC+AOILIL4AiC+A+AIgvgDiC4D4AogvAOILIL4A4guA+AKILwDiCyC+AIgvgPgCIL4A4gsgvgCIL4D4AiC+AOILgPgCiC8A4gsgvgDiC4D4AogvAOILIL4AiC+A+AIgvgDiCyC+AIgvgPgCIL4A4guA+AKILwDiCyC+AOILgPgCiC8A4gsgvgCIL4D4AiC+AOILIL4AiC+A+AIgvgDiC4D4AogvAOILIL4A4guA+AKILwDiCyC+AIgvgPgCIL4A4gsgvpYAQHwBxBcA8QUQXwDEF0B8ARBfAPEFYNr4BssN8CxO9YMWi8Xs4eGhKEvDNnB6UkrFfD6fXV1dff74hhDa3zeFNrjp8vLyR77WNI1XEfh82wBtp/pu7ehZcazwTjL5DgPs5QXoYj/FD9n1nwTgs8idmrJVcco/zMsLMOHkC4D4AogvgPgCIL4A4guA+AKILwDiCyC+AIgvgPgCiC8A4gvwH3vzlpIxxlkI4byqKu/BBnDIdFuWm/sEd7fVzY/YXg/j+L665+7j4+PvNryzuq7XlhLggOk2xiYHuHsnn2Y2m+Vj1TTNMk+z/URbdufl4GHaBXi/p+h2x3rwvKi6TwiDo+ACHCe82x7PU/GWT2y6qTeJMcCH41sMpt/Nx3bt+TajiRiA9wW42Db5DuM6PhdegOMGeHPtrwADAAEMxsaJGWIgAAAAAElFTkSuQmCC";
    private static final String WHITE_SQUARE_URL = "https://www.pngkey.com/png/full/416-4160040_box-jo-baer-white-square-lavender.png";
    private static final String LENNA_PNG = "https://upload.wikimedia.org/wikipedia/en/7/7d/Lenna_%28test_image%29.png";
    private static final String OVERLAY_TEXT = "Hello World";
    private ImageProcessService ips;
    private BufferedImage image;
    private ImageProcessService.Size size = new ImageProcessService.Size(351, 351);

    @BeforeAll
    void createService() {
        ips = new ImageProcessService(LENNA_PNG, OVERLAY_TEXT);
        image = getRead();
    }

    private BufferedImage getRead() {
        try {
            return ImageIO
                    .read(new URL(WHITE_SQUARE_URL));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    @Disabled
    void processImage() {
        //URL url = new URL("http://upload.wikimedia.org/wikipedia/en/2/24/Lenna.png");
    }

    @Test
    void testCreateUrlCorrectData(){
        try {
            URL expectedUrl = new URL(LENNA_PNG);
            URL createdUrl = ips.createUrl(LENNA_PNG).orElse(null);
            Assertions.assertEquals(expectedUrl, createdUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testCreateUrlIncorrectData(){
        Assertions.assertEquals(ips.createUrl("cos"), Optional.empty());
    }

    @Test
    @Disabled
    void testEncodeToString(){
        Assertions.assertEquals(WHITE_SQUARE, ips.encodeToString(image));
    }

    @Test
    void testGetImageSize(){
        ImageProcessService.Size realSize = ips.getImageSize(image);
        Assertions.assertTrue(realSize.getX() == size.getX());
        Assertions.assertTrue(realSize.getY() == size.getY());

    }

    @Test
    void testCutInHalf(){
        String hello = "Hello fucking world";
        String[] expected = {"Hello", "fucking world"};
        Assertions.assertEquals(Arrays.toString(expected), Arrays.toString(ips.cutInHalf(hello)));
    }

}