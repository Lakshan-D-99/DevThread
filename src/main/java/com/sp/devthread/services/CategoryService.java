package com.sp.devthread.services;

import com.sp.devthread.models.Category;
import com.sp.devthread.models.PostCategory;

public interface CategoryService {

    // Find the Category based on the passed in Category Name
    Category getCategoryByNameService(PostCategory postCategory);
}
