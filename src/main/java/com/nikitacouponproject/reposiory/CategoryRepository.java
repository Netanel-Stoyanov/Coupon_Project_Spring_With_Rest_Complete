package com.nikitacouponproject.reposiory;

import com.nikitacouponproject.bean.Category;
import com.nikitacouponproject.bean.CategoryEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findByName(CategoryEnum string);
}
