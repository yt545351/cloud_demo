package com.example.system.controller;

import com.example.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping
public class SystemController {
    @Autowired
    public RestTemplate restTemplate;

//    @Autowired
    public SystemService systemService;

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public String query() {
        String result = "";
        String url = "http://Log/query/111111";
        //RestTemplate
//        result = restTemplate.getForObject(url, String.class);
        //Feign
        result = systemService.query("222222");
        return result;
    }
}