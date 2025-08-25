package com.sp.devthread.Utils;

import com.sp.devthread.daos.RequestDaos.PostRequests.PostRequestDao;
import com.sp.devthread.daos.ResponsetDaos.CategoryResponses.CategoryResponseDao;
import com.sp.devthread.daos.ResponsetDaos.CommentResponses.CommentResponseDao;
import com.sp.devthread.daos.ResponsetDaos.PostResponses.PostResponseDao;
import com.sp.devthread.models.Category;
import com.sp.devthread.models.Comment;
import com.sp.devthread.models.Post;
import org.springframework.stereotype.Component;

/**
 * This class will act as a Mapper Class that converts Entities into Request and Response DAOs and vice versa.
 */
@Component
public class Mapper {

    // Convert a Post Entity into a PostResponseDao
    public PostResponseDao conEntityToDao(Post post){
        PostResponseDao postResponseDao = new PostResponseDao();
        postResponseDao.setId(post.getId());
        postResponseDao.setPostTitle(post.getPostTitle());
        postResponseDao.setPostImage(post.getPostImage());
        postResponseDao.setPostBody(post.getPostBody());
        postResponseDao.setCreatedDate(post.getCreatedDate());
        postResponseDao.setUserId(post.getUser().getId());
        return postResponseDao;
    }

    // Convert a Comment Entity into a CommentResponseDao
    public CommentResponseDao conEntityToDao(Comment comment){
        CommentResponseDao commentResponseDao = new CommentResponseDao();
        commentResponseDao.setCommentId(comment.getId());
        commentResponseDao.setCommentBody(comment.getCommentBody());
        commentResponseDao.setPublishedDate(comment.getPublishedDate());
        commentResponseDao.setPostId(comment.getPost().getId());
        commentResponseDao.setUserId(comment.getUser().getId());
        return commentResponseDao;
    }

    // Convert a Category Entity into a CategoryResponseDao
    public CategoryResponseDao conEntityToDao(Category category){
        CategoryResponseDao categoryResponseDao = new CategoryResponseDao();
        categoryResponseDao.setCategoryId(category.getId());
        categoryResponseDao.setCategoryName(category.getPostCategory().name());
        return categoryResponseDao;
    }


}
