package com.sp.devthread.services.serviceImp.joinTableServiceImpl;

import com.sp.devthread.Utils.AuthUtils;
import com.sp.devthread.repositories.CategoryRepository;
import com.sp.devthread.repositories.PostRepository;
import com.sp.devthread.services.JoinTableServices.PostCategoryService;
import org.springframework.stereotype.Service;

@Service
public class PostCategoryServiceImpl implements PostCategoryService {

    private final AuthUtils authUtils;
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;

    public PostCategoryServiceImpl(AuthUtils authUtils, PostRepository postRepository, CategoryRepository categoryRepository) {
        this.authUtils = authUtils;
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
    }
}
