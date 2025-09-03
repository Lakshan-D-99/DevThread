package com.sp.devthread.services;

import com.sp.devthread.daos.RequestDaos.PostRequests.PostRequestDao;
import com.sp.devthread.daos.ResponsetDaos.PostResponses.PostResponseDao;

import java.util.List;

public interface PostService {

    // Get all the Posts from the Database.
    List<PostResponseDao> getAllThePosts();

    // Get all the Posts of a specific User based on the passed in user id.
    List<PostResponseDao> getAllThePostsOfUser(long userId);

    // Get a single Post based on the passed in post id.
    PostResponseDao getSinglePost(long postId);

    // Find a Post based on the passed in post title.
    PostResponseDao getPostByTitle(String postTitle);

    // Add a new Post in to the Database.
    void addNewPost(PostRequestDao postRequestDao);

    // Update an existing Post in the Database.
    void updatePost(long postId,PostRequestDao postRequestDao);

    // Delete an existing Post from the Database.
    void deletePost(long postId);


}
