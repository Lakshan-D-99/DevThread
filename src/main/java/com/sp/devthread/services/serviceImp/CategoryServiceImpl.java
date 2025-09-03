package com.sp.devthread.services.serviceImp;

import com.sp.devthread.models.Category;
import com.sp.devthread.models.PostCategory;
import com.sp.devthread.repositories.CategoryRepository;
import com.sp.devthread.services.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category getCategoryByNameService(PostCategory postCategory) {
        return categoryRepository.findByPostCategory(postCategory).orElseThrow(
                ()-> new RuntimeException("The Category Type: " + postCategory + " does not exists")
        );
    }


}
