package com.jeyu.memegen.controller;

import com.jeyu.memegen.service.ImageProcessService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.net.URL;

@RestController
public class ImageController {

    //private ImageProcessService ip = new ImageProcessService();

    /*@GetMapping("/hello")
    public String helloWorld(){
        try {
            return ip.processImage(new URL("http://upload.wikimedia.org/wikipedia/en/2/24/Lenna.png"));
        } catch (final MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }*/
}
