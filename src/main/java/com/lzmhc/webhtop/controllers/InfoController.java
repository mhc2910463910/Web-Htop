package com.lzmhc.webhtop.controllers;

import com.lzmhc.webhtop.services.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/info")
public class InfoController {
    @Autowired
    private InfoService infoService;
    @GetMapping
    public ResponseEntity<?> getInfo(){
        return new ResponseEntity<>(infoService.getInfo(), HttpStatus.OK);
    }
}
