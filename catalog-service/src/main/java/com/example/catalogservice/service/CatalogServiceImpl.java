package com.example.catalogservice.service;

import com.example.catalogservice.dto.CatalogReadResponseDto;
import com.example.catalogservice.entity.CatalogEntity;
import com.example.catalogservice.repository.CatalogRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CatalogServiceImpl implements CatalogService {

    private final CatalogRepository catalogRepository;

    @Override
    public List<CatalogReadResponseDto> getCatalogList() {
        List<CatalogReadResponseDto> returnList = new ArrayList<>();
        List<CatalogEntity> catalogEntityList = catalogRepository.findAll();

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        catalogEntityList.forEach(catalogEntity -> {
            CatalogReadResponseDto catalogReadResponseDto = modelMapper.map(catalogEntity, CatalogReadResponseDto.class);
            returnList.add(catalogReadResponseDto);
        });

        return returnList;
    }
}
