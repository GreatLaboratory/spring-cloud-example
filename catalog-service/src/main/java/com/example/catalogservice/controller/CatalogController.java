package com.example.catalogservice.controller;

import com.example.catalogservice.dto.CatalogReadResponseDto;
import com.example.catalogservice.service.CatalogServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/catalog-service")
public class CatalogController {

    private final CatalogServiceImpl catalogService;

    @GetMapping("/catalogs")
    public ResponseEntity<List<CatalogReadResponseDto>> getCatalogList() {
        return ResponseEntity.status(HttpStatus.OK).body(catalogService.getCatalogList());
    }

}
