package com.sp.devthread.services;

import com.sp.devthread.daos.RequestDaos.CommentRequests.CommentRequestDao;
import com.sp.devthread.daos.ResponsetDaos.CommentResponses.CommentResponseDao;

import java.util.List;

public interface CommentService {

    // Get all the Comments of a specific Post based on the passed in Post id.
    List<CommentResponseDao> getAllCommentsOfPost(long postId);

    // Get a Single Comment based on the passed in Comment id.
    CommentResponseDao getCommentById(long commentId);

    // Add a new Comment to an existing Post.
    void addComment(CommentRequestDao commentRequestDao);

    // Update an existing Comment.
    void updateComment(long commentId, CommentRequestDao commentRequestDao);

    // Delete an existing Comment of a Post.
    void deleteComment(long commentId);
}
