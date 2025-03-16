package com.practice.springjpaformock.controller;


import com.practice.springjpaformock.model.Attribute;
import com.practice.springjpaformock.model.Category;
import com.practice.springjpaformock.repository.CategoryRepository;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CategoryRepository categoryRepository;

    @Test
    void whenCreateCategory_withoutAttributes() throws Exception {
//        Category category = new Category();
//        category.setId(1);
//        category.setName("Наушники");
//        category.setAttributes(new ArrayList<>());

        Mockito.when(categoryRepository.save(Mockito.any(Category.class))).thenAnswer(invocation -> {
            Category savedCategory = invocation.getArgument(0);
            savedCategory.setId(1);
            savedCategory.setName("Наушники");
            return savedCategory;
        });

        mockMvc.perform(post("/categories").contentType(MediaType.APPLICATION_JSON).content("{\"name\": \"Наушники\"}")).andExpect(status().isCreated()).andExpect(jsonPath("$.id").value(1)).andExpect(jsonPath("$.name").value("Наушники")).andExpect(jsonPath("$.attributes").isEmpty());
        Mockito.verify(categoryRepository, Mockito.times(1)).save(Mockito.any(Category.class));
    }



    @Test
    void whenCreateCategory_withAttributes() throws Exception {


        String textJson = """
            {
             "name": "Наушники",
              "attributes": [ "Производитель", "Аккумулятор", "Цвет"]
            }
            """;

        Mockito.when(categoryRepository.save(Mockito.any(Category.class))).thenAnswer(invocation -> {
            Category savedCategory = invocation.getArgument(0);
            savedCategory.setId(1);
            savedCategory.setName("Наушники");
            savedCategory.setAttributes(List.of(
                    new Attribute(1, "Производитель"),
                    new Attribute(2, "Аккумулятор"),
                    new Attribute(3, "Цвет")
            ));
            return savedCategory;
        });

        mockMvc.perform(post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(textJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Наушники"))
                .andExpect(jsonPath("$.attributes").isArray())
                .andExpect(jsonPath("$.attributes[0].id").value(1))
                .andExpect(jsonPath("$.attributes[0].name").value("Производитель"))
                .andExpect(jsonPath("$.attributes[1].id").value(2))
                .andExpect(jsonPath("$.attributes[1].name").value("Аккумулятор"))
                .andExpect(jsonPath("$.attributes[2].id").value(3))
                .andExpect(jsonPath("$.attributes[2].name").value("Цвет"))
        ;
        Mockito.verify(categoryRepository, Mockito.times(1)).save(Mockito.any(Category.class));
    }

    @Test
    void getCategoryById_withAttributes() throws Exception {

        Category category = new Category();
        category.setId(1);
        category.setName("Наушники");
        category.setAttributes(List.of(
                new Attribute(1, "Производитель"),
                new Attribute(2, "Аккумулятор"),
                new Attribute(3, "Цвет")));

        Mockito.when(categoryRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(category));

        mockMvc.perform(get("/categories/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Наушники"))
                .andExpect(jsonPath("$.attributes").isArray())
                .andExpect(jsonPath("$.attributes[0].id").value(1))
                .andExpect(jsonPath("$.attributes[0].name").value("Производитель"))
                .andExpect(jsonPath("$.attributes[1].id").value(2))
                .andExpect(jsonPath("$.attributes[1].name").value("Аккумулятор"))
                .andExpect(jsonPath("$.attributes[2].id").value(3))
                .andExpect(jsonPath("$.attributes[2].name").value("Цвет"));
        Mockito.verify(categoryRepository, Mockito.times(1)).findById(Mockito.anyInt());
    }

    @Test
    void getCategories_withoutAttributes() throws Exception {

        Category category = new Category();
        category.setId(1);
        category.setName("Наушники");

        Category category2 = new Category();
        category2.setId(2);
        category2.setName("Смартфоны");

        Mockito.when(categoryRepository.findAll()).thenReturn(List.of(category, category2));

        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray()) // Ожидаем массив в корне JSON
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Наушники"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Смартфоны"));

        Mockito.verify(categoryRepository, Mockito.times(1)).findAll();
    }







}