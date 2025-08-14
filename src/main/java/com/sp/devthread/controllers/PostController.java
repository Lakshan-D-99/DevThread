package com.sp.devthread.controllers;

import com.sp.devthread.daos.RequestDaos.PostRequests.PostRequestDao;
import com.sp.devthread.daos.ResponsetDaos.GlobalResponses.APIResponse;
import com.sp.devthread.daos.ResponsetDaos.GlobalResponses.MessageResponse;
import com.sp.devthread.daos.ResponsetDaos.PostResponses.PostResponseDao;
import com.sp.devthread.services.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // Get all the Posts.
    @GetMapping("/all-posts")
    public ResponseEntity<?> getAllPosts(){

        try {
            List<PostResponseDao> allPosts = postService.getAllThePosts();

            if (allPosts.isEmpty()){
                return ResponseEntity.ok(new APIResponse("There are no Posts to display"));
            }

            return ResponseEntity.ok(allPosts);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse("Error getting all the Posts"));
        }
    }

    // Get a Single Post based on the passed in Post id
    @GetMapping("/single-post/postId={postsId}")
    public ResponseEntity<?> getSinglePostById(@PathVariable long postsId){
        try {

            PostResponseDao postResponseDao = postService.getSinglePost(postsId);

            if (postResponseDao == null){
                return ResponseEntity.ok(new APIResponse("The Post with the PostId: " + postsId + " does not exists."));
            }

            return ResponseEntity.ok(postResponseDao);

        } catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse("Error getting the Post Data of Post:" + postsId));

        }
    }

    // Get a Single Post based on the passed in Post Title
    @GetMapping("/single-post/postTitle={postsTitle}")
    public ResponseEntity<?> getSinglePostByPostTitle(@PathVariable String postsTitle){
        try {

            PostResponseDao postResponseDao = postService.getPostByTitle(postsTitle);

            if (postResponseDao == null){
                return ResponseEntity.ok(new APIResponse("The Post with the PostTitle: " + postsTitle + " does not exists."));
            }

            return ResponseEntity.ok(postResponseDao);

        } catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse("Error getting the Post Data of PostTitle:" + postsTitle));
        }
    }

    // Get all the Posts of a User based on the passed in User id
    @GetMapping("/all-posts/user-posts/userId={usersId}")
    public ResponseEntity<?> getAllPostsOfUser(@PathVariable long usersId){

        try {
            List<PostResponseDao> allPosts = postService.getAllThePostsOfUser(usersId);

            if (allPosts.isEmpty()){
                return ResponseEntity.ok(new APIResponse("The User with the UserId: " + usersId + " does not have any Posts"));
            }

            return ResponseEntity.ok(allPosts);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse("Error getting all the Posts of the User with the UserId: "+usersId));
        }
    }

    // Create a new Post
    @PostMapping("/add-post")
    public ResponseEntity<?> addNewPost(@RequestBody PostRequestDao postRequestDao){
        try {

            postService.addNewPost(postRequestDao);

            return ResponseEntity.ok(new APIResponse("A new Post with the PostTitle: " + postRequestDao.getPostTitle() + " has been made successfully"));

        } catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse("Error creating a new Post with PostTitle: " + postRequestDao.getPostTitle() + "."));
        }
    }

    // Update an existing Post
    @PutMapping("/update-post/postId={postsId}")
    public ResponseEntity<?> updateExistingPost(@PathVariable long postsId, @RequestBody PostRequestDao postRequestDao){
        try {

            postService.updatePost(postsId, postRequestDao);

            return ResponseEntity.ok(new APIResponse("The Post with the PostId: " + postsId + " has been successfully updated."));

        } catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse("Error Updating the Post with the PostId: " + postsId + " ."));
        }
    }


    // Delete an existing Post
    @DeleteMapping("/delete-post/postId={postsId}")
    public ResponseEntity<?> deletePost(@PathVariable long postsId){
        try {

            postService.deletePost(postsId);

            return ResponseEntity.ok("The Post with the PostId: " + postsId + " has been deleted successfully!");

        } catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse("Error Deleting the Post with the PostId:" + postsId));
        }
    }


}
