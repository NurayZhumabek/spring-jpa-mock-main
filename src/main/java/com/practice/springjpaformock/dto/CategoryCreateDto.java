package com.practice.springjpaformock.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "DTO для создания категории")
public class CategoryCreateDto {
    @Schema(description = "Название категории", example = "Электроника")
    String name;

    @Schema(description = "Список характеристик категории", example = "[\"Цвет\", \"Размер\"]")
    List<String> attributes;


    }

