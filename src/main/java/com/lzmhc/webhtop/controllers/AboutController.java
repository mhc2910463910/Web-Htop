package com.lzmhc.webhtop.controllers;

import com.lzmhc.webhtop.dto.InfoDto;
import com.lzmhc.webhtop.services.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;
@Controller
@RequestMapping(value = "/about")
public class AboutController {
    @Autowired
    private InfoService infoService;
    @GetMapping
    public String getIndex(Map<String, InfoDto> map){
        map.put("info",infoService.getInfo());
        return "about";
    }
}