package com.example.log.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.GET;

@RequestMapping
@RestController
@Slf4j
public class LogController {

    @RequestMapping(value = "/query/{ss}", method = RequestMethod.GET)
    private String query(@PathVariable("ss") String ss) {
        log.info("{}",111);
        return ss;
    }

}
