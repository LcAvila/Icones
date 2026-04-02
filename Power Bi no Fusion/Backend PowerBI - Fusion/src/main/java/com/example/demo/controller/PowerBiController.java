package com.example.demo.controller;

import com.example.demo.dto.EmbedConfigResponse;
import com.example.demo.service.PowerBiEmbedService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PowerBiController {

    private final PowerBiEmbedService service;

    public PowerBiController(PowerBiEmbedService service) {
        this.service = service;
    }

    @CrossOrigin(origins = "https://fdev.compactortech.com.br")
    @GetMapping("/api/powerbi/embed-config")
    public EmbedConfigResponse embedConfig() {
        return service.getEmbedConfig();
    }
}
