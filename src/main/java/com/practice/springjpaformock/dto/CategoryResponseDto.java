package com.practice.springjpaformock.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * Категория без характеристик
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "DTO для отображения краткой информации о категории")
public class CategoryResponseDto {
    @Schema(description = "Идентификатор категории", example = "1")
    Integer id;

    @Schema(description = "Название категории", example = "Электроника")
    String name;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
