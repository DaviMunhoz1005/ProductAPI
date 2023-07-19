package com.api.product.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProductController {

    //Request Start API
    @GetMapping(path = "start")
    public @ResponseBody String start() {
        return "Welcome to products API!";
    }

    //Request About API
    @GetMapping(path = "about")
    public @ResponseBody String about() {
        return "About the API...";
    }

    //Request Contact API
    @GetMapping(path = "contact")
    public @ResponseBody String contact() {
        return "Contact us :)";
    }
}
