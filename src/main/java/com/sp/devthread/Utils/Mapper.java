package com.sp.devthread.Utils;

import com.sp.devthread.daos.RequestDaos.PostRequests.PostRequestDao;
import com.sp.devthread.daos.ResponsetDaos.PostResponses.PostResponseDao;
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


}
