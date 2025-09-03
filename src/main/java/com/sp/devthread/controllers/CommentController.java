package com.sp.devthread.controllers;

import com.sp.devthread.daos.RequestDaos.CommentRequests.CommentRequestDao;
import com.sp.devthread.daos.ResponsetDaos.CommentResponses.CommentResponseDao;
import com.sp.devthread.daos.ResponsetDaos.GlobalResponses.APIResponse;
import com.sp.devthread.daos.ResponsetDaos.GlobalResponses.MessageResponse;
import com.sp.devthread.services.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // Get all the Comments of a specific Post
    @GetMapping("/postsId={postId}/all-comments")
    public ResponseEntity<?> getAllThePostComments(@PathVariable long postId){
        try {

            List<CommentResponseDao> allCommentsOfPost = commentService.getAllCommentsOfPost(postId);

            if (allCommentsOfPost.isEmpty()){
                return ResponseEntity.ok(new APIResponse("This Post does not have any comments"));
            }

            return ResponseEntity.ok(allCommentsOfPost);

        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse("Error getting the comments of the Post"));
        }
    }

    // Add a Comment to a Post
    @PostMapping("/add-comment")
    public ResponseEntity<?> addCommentToPost(@RequestBody CommentRequestDao commentRequestDao){
        try {
            commentService.addComment(commentRequestDao);

            return ResponseEntity.ok(new APIResponse("A new Comment has been added to this Post"));
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse("Error adding a Comment to the Post"));
        }
    }

    // Update an existing Comment

    // Delete an existing Comment
}
