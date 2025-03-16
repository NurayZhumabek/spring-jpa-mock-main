package com.practice.springjpaformock.controller;

import com.practice.springjpaformock.dto.CategoryCreateDto;
import com.practice.springjpaformock.dto.CategoryFullResponseDto;
import com.practice.springjpaformock.dto.CategoryResponseDto;
import com.practice.springjpaformock.model.Category;
import com.practice.springjpaformock.repository.CategoryRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
@Tag(name = "Категории", description = "Управление категориями")
public class CategoryController {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryController(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    @Operation(summary = "Создать категорию", description = "Создает новую категорию и возвращает полную информацию о ней")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryFullResponseDto create(
            @RequestBody @Parameter(description = "Данные для создания категории") CategoryCreateDto categoryCreateDto) {
        Category category = modelMapper.map(categoryCreateDto, Category.class);
        categoryRepository.save(category);
        return  modelMapper.map(category, CategoryFullResponseDto.class);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Найти категорию по ID", description = "Возвращает полную информацию о категории по её ID")
    public CategoryFullResponseDto findById(
            @PathVariable @Parameter(description = "ID категории") int id) {
        return modelMapper.map(categoryRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND)),
                CategoryFullResponseDto.class);

    }

    @GetMapping
    @Operation(summary = "Получить список всех категорий", description = "Возвращает список всех категорий в кратком формате")
    public List<CategoryResponseDto> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(category -> modelMapper.map(category,CategoryResponseDto.class))
                .collect(Collectors.toList());
    }
}
