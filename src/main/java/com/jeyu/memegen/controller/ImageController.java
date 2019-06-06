package com.jeyu.memegen.controller;

import com.jeyu.memegen.service.ImageProcessService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.net.URL;

@RestController
public class ImageController {

    private static final String LENNA_PNG = "https://upload.wikimedia.org/wikipedia/en/7/7d/Lenna_%28test_image%29.png";
    private static final String MALFORMED_URL = "elo";
    //private ImageProcessService ip = new ImageProcessService();

    @GetMapping("/hello")
    public String helloWorld(){
        ImageProcessService ip = new ImageProcessService(MALFORMED_URL, "To nie jest mem, tak naprawdę chciałem to wszystko tylko przetestować");
        return ip.processImage();

    }

    @GetMapping("/fancymeme")
    public ResponseEntity<String> getYourFancyMeme(@RequestParam String url, @RequestParam String textToOverlay){
        ImageProcessService ip = new ImageProcessService(url, textToOverlay);
        return new ResponseEntity<>(ip.processImage(), HttpStatus.OK);
    }
}
