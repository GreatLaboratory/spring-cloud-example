package com.example.catalogservice.service;

import com.example.catalogservice.dto.CatalogReadResponseDto;

import java.util.List;

public interface CatalogService {
    List<CatalogReadResponseDto> getCatalogList();
}
