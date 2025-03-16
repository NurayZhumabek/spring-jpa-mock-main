package com.practice.springjpaformock;

import com.practice.springjpaformock.dto.CategoryFullResponseDto;
import com.practice.springjpaformock.dto.CategoryResponseDto;
import com.practice.springjpaformock.model.Category;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringJpaForMockApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringJpaForMockApplication.class, args);



    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();



        modelMapper.typeMap(Category.class, CategoryResponseDto.class)
                .addMappings(mapper -> {
                    mapper.map(Category::getId, CategoryResponseDto::setId);
                    mapper.map(Category::getName,CategoryResponseDto::setName);
                });


        return modelMapper;
    }
}
