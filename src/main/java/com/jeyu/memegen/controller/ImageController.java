package com.jeyu.memegen.controller;

import com.jeyu.memegen.service.ImageProcessService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.net.URL;

@RestController
public class ImageController {

    private static final String LENNA_PNG = "https://upload.wikimedia.org/wikipedia/en/7/7d/Lenna_%28test_image%29.png";
    //private ImageProcessService ip = new ImageProcessService();

    @GetMapping("/hello")
    public String helloWorld(){
        ImageProcessService ip = new ImageProcessService(LENNA_PNG, "To nie jest mem, tak naprawdę chciałem to wszystko tylko przetestować");
        return ip.processImage();

    }
}
