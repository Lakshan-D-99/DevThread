package com.sp.devthread.services.serviceImp;

import com.sp.devthread.Utils.AuthUtils;
import com.sp.devthread.Utils.Mapper;
import com.sp.devthread.daos.RequestDaos.PostRequests.PostRequestDao;
import com.sp.devthread.daos.ResponsetDaos.CategoryResponses.CategoryResponseDao;
import com.sp.devthread.daos.ResponsetDaos.CommentResponses.CommentResponseDao;
import com.sp.devthread.daos.ResponsetDaos.PostResponses.PostResponseDao;
import com.sp.devthread.daos.ResponsetDaos.UserResponses.UserResponseDao;
import com.sp.devthread.models.Category;
import com.sp.devthread.models.Post;
import com.sp.devthread.models.PostCategory;
import com.sp.devthread.models.User;
import com.sp.devthread.repositories.PostRepository;
import com.sp.devthread.services.CategoryService;
import com.sp.devthread.services.CommentService;
import com.sp.devthread.services.PostService;
import com.sp.devthread.services.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PostServiceImpl implements PostService {

    private final AuthUtils authUtils;
    private final PostRepository postRepository;
    private final Mapper mapper;
    private final UserService userService;
    private final CategoryService categoryService;
    private final CommentService commentService;

    public PostServiceImpl(AuthUtils authUtils, PostRepository postRepository, Mapper mapper, UserService userService, CategoryService categoryService, CommentService commentService) {
        this.authUtils = authUtils;
        this.postRepository = postRepository;
        this.mapper = mapper;
        this.userService = userService;
        this.categoryService = categoryService;
        this.commentService = commentService;
    }

    @Override
    public List<PostResponseDao> getAllThePosts() {

        List<PostResponseDao> postResponseDaoList = new ArrayList<>();

        List<CommentResponseDao> commentResponseDaoList = new ArrayList<>();

        Set<CategoryResponseDao> categoryResponseDaoList = new HashSet<>();

        List<Post> allPosts = postRepository.findAll();

        // Get all the Comments and the Categories of a specific Post
        allPosts.forEach(post -> {

            post.getCommentList().forEach(comment -> {
                CommentResponseDao commentResponseDao = mapper.conEntityToDao(comment);
                commentResponseDaoList.add(commentResponseDao);
            });

            post.getCategorySet().forEach(category -> {
                CategoryResponseDao categoryResponseDao = mapper.conEntityToDao(category);
                categoryResponseDaoList.add(categoryResponseDao);
            });

            PostResponseDao postResponseDao = mapper.conEntityToDao(post);
            postResponseDao.setCommentResponseDaoList(commentResponseDaoList);
            postResponseDao.setCategoryResponseDaoSet(categoryResponseDaoList);
            postResponseDaoList.add(postResponseDao);
        });

        return postResponseDaoList;
    }

    @Override
    public List<PostResponseDao> getAllThePostsOfUser(long userId) {

        List<Post> allUsersPost = postRepository.findAllPostsByUserId(userId).orElseThrow(()->
                new RuntimeException("The User with the UserId: " + userId + " does not have any Posts"));

        List<PostResponseDao> postResponseDaoList = new ArrayList<>();

        List<CommentResponseDao> commentResponseDaoList = new ArrayList<>();

        allUsersPost.forEach(post -> {

            post.getCommentList().forEach(comment -> {
                CommentResponseDao commentResponseDao = mapper.conEntityToDao(comment);
                commentResponseDaoList.add(commentResponseDao);
            });

            PostResponseDao postResponseDao = mapper.conEntityToDao(post);
            postResponseDao.setCommentResponseDaoList(commentResponseDaoList);
            postResponseDaoList.add(postResponseDao);
        });

        return postResponseDaoList;
    }

    @Override
    public PostResponseDao getSinglePost(long postId) {

        // Get the Post from the Database, if it exists
        Post post = postRepository.findById(postId).orElseThrow(()->
                new RuntimeException("The Post with the PostId: " + postId + " does not exists")
        );

        // Now we can convert the Post into a Post Response Object and send it to Controller Layer
        return mapper.conEntityToDao(post);
    }

    @Override
    public PostResponseDao getPostByTitle(String postTitle) {

        Post post = postRepository.findPostByPostTitle(postTitle).orElseThrow(()->
                new RuntimeException("A Post containing the following PostTitle: " + postTitle + " does not exists"));

        return mapper.conEntityToDao(post);
    }

    @Override
    @Transactional
    public void addNewPost(PostRequestDao postRequestDao) {

        // First of all we have to check if the User is Logged-In to create a new Post
        if (authUtils.getLoggedInUserId() == -1){
            throw new RuntimeException("Only a Logged in User can make a new Post");
        }

        // Check if the User has passed in all the Fields required to create a new Post
        if (postRequestDao.getPostTitle().isEmpty()){
            throw new RuntimeException("PostServiceImpl - Post Title is required to create a new Post");
        }

        // Now we can construct a new Post Object
        Post post = new Post();

        post.setPostTitle(postRequestDao.getPostTitle());

        if (!postRequestDao.getPostImage().isEmpty()){
            post.setPostImage(postRequestDao.getPostImage());
        }else {
            post.setPostImage("");
        }

        if (!postRequestDao.getPostBody().isEmpty()){
            post.setPostBody(postRequestDao.getPostBody());
        }else {
            post.setPostBody("");
        }

        LocalDate currentDateTime = LocalDate.now();
        post.setCreatedDate(currentDateTime);

        // Get the Currently Logged-in User and set it into our Post Object.
        User loggedInUser = userService.findUserByPassedInUserId(authUtils.getLoggedInUserId());
        post.setUser(loggedInUser);

        // Check if the Categories are Empty or not
        if (!postRequestDao.getCategoriesList().isEmpty()){

            // Now we have to assign the Categories of our Post Object.
            postRequestDao.getCategoriesList().forEach(category -> {
                System.out.println("Category:"+category);
                Category postCategory = categoryService.getCategoryByNameService(PostCategory.valueOf(category.toUpperCase()));
                System.out.println("PostCategory:"+postCategory);
                post.getCategorySet().add(postCategory);
            });
        } else {
         throw new RuntimeException("Post Category must be selected to continue");
        }

        // Lastly, we can save the Post Object into the Database.
        postRepository.save(post);
    }

    @Override
    public void updatePost(long postId,PostRequestDao postRequestDao) {

        // Check if a Post exists with the passed in post id
        Post post = postRepository.findById(postId).orElseThrow(()->
                new RuntimeException("A Post does not exists with the passed in PostId: " + postId + " !"));

        // Check if the currently logged-in user owns the post
        if (post.getUser().getId() != authUtils.getLoggedInUserId()){
            throw new RuntimeException("A User can only edit their own Posts");
        }

        // Now we have to update our post based on the passed in Values.
        if (!(postRequestDao.getPostTitle().isEmpty())){
            post.setPostTitle(postRequestDao.getPostTitle());
        }

        if (!(postRequestDao.getPostImage().isEmpty())){
            post.setPostImage(postRequestDao.getPostImage());
        }

        if (!(postRequestDao.getPostBody().isEmpty())){
            post.setPostBody(postRequestDao.getPostBody());
        }

        //TODO: Add editDate Variable to store edited date and Time

        // Now we can save the Updated post into the Database.
        postRepository.save(post);
    }

    @Override
    public void deletePost(long postId) {

        // Get the specific Post through the postId
        Post post = postRepository.findById(postId).orElseThrow(()->
                new RuntimeException("A Post with the PostId: " + postId + " does not exists !"));

        // Now we have to check if the Post owner is the currently logged-in user
        if (post.getUser().getId()!=authUtils.getLoggedInUserId()){
            throw new RuntimeException("A User can only delete, their own Posts");
        }

        postRepository.delete(post);
    }
}
