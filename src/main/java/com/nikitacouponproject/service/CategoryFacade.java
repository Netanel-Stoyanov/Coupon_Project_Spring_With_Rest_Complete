package com.nikitacouponproject.service;

import com.nikitacouponproject.bean.Category;
import com.nikitacouponproject.bean.CategoryEnum;
import com.nikitacouponproject.reposiory.CategoryRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@NoArgsConstructor
public class CategoryFacade {

    @Autowired
    private CategoryRepository categoryRepository;

    public void addCategories(){
        categoryRepository.save(new Category(CategoryEnum.FOOD));
        categoryRepository.save(new Category(CategoryEnum.ELECTRICITY));
        categoryRepository.save(new Category(CategoryEnum.VACATION));
        categoryRepository.save(new Category(CategoryEnum.RESTAURANT));
    }

    public Optional<Category> getCategory(CategoryEnum name){
        return categoryRepository.findByName(name);
    }
}
