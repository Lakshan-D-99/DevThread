package com.sp.devthread.services.serviceImp;

import com.sp.devthread.Utils.AuthUtils;
import com.sp.devthread.Utils.Mapper;
import com.sp.devthread.daos.RequestDaos.CommentRequests.CommentRequestDao;
import com.sp.devthread.daos.ResponsetDaos.CommentResponses.CommentResponseDao;
import com.sp.devthread.daos.ResponsetDaos.UserResponses.UserResponseDao;
import com.sp.devthread.models.Comment;
import com.sp.devthread.models.Post;
import com.sp.devthread.models.User;
import com.sp.devthread.repositories.CommentRepository;
import com.sp.devthread.repositories.PostRepository;
import com.sp.devthread.services.CommentService;
import com.sp.devthread.services.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserService userService;
    private final AuthUtils authUtils;
    private final Mapper mapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, UserService userService, AuthUtils authUtils, Mapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userService = userService;
        this.authUtils = authUtils;
        this.mapper = mapper;
    }

    @Override
    public List<CommentResponseDao> getAllCommentsOfPost(long postId) {

        // First get the Post based on the passed in PostId
        Post post = postRepository.findById(postId).orElseThrow(()->
                new RuntimeException("The Post with the PostId: " + postId + " does not exists"));

        // Get all the comments of that post
        List<Comment> allPostComments = commentRepository.findAllByPostId(post.getId());

        // Create a CommentResponseDao List to store Comment Daos
        List<CommentResponseDao> commentResponseDaoList = new ArrayList<>();

        // Check if this Post has any Posts, if not we can return an empty list to the user.
        if (allPostComments.isEmpty()){
            return commentResponseDaoList;
        }

        // If the List is not Empty, then we can convert all the Comments into Daos and return them.
        allPostComments.forEach(comment -> {
            CommentResponseDao commentResponseDao = mapper.conEntityToDao(comment);
            commentResponseDaoList.add(commentResponseDao);
        });

        return commentResponseDaoList;
    }

    @Override
    public CommentResponseDao getCommentById(long commentId) {

        // Get the specific Comment based on the passed in comment id and return it.
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->
                new RuntimeException("The Comment with the CommentId: " + commentId + " does not exists."));

        return mapper.conEntityToDao(comment);
    }

    @Override
    public void addComment(CommentRequestDao commentRequestDao) {

        // Get the current logged-in user.
        User user = userService.findUserByPassedInUserId(authUtils.getLoggedInUserId());

        if (user == null){
            throw new RuntimeException("Log-in to make a comment");
        }

        // Get the Post through the passed in PostId
        Post post = postRepository.findById(commentRequestDao.getPostId()).orElseThrow(()->
                new RuntimeException("This Post does not exists"));

        // Check if the required fields have been passed to create a Comment.
        if (commentRequestDao.getCommentBody().isEmpty()){
            throw new RuntimeException("Please provide a Comment Body to create a Comment");
        }

        // Now we can create a new Comment Object and add it into the Post.
        Comment comment = new Comment();
        comment.setCommentBody(commentRequestDao.getCommentBody());
        comment.setPost(post);
        comment.setUser(user);

        // We also have to assign the published date and time of the comment
        LocalDate currentDate = LocalDate.now();
        comment.setPublishedDate(currentDate);

        // Lastly, save the Comment into the Database
        commentRepository.save(comment);
    }

    @Override
    public void updateComment(long commentId, CommentRequestDao commentRequestDao) {

        // Get the Comment through the passed in Comment id
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->
                new RuntimeException("A Comment with the CommentId:" + commentId + " does not exists"));

        // Get the Post through the postId, which was passed inside the commentRequestDao.
        Post post = postRepository.findById(commentRequestDao.getPostId()).orElseThrow(()->
                new RuntimeException("A Post with the PostId:" + commentRequestDao.getPostId() + " does not exists"));

        // Check if the currently logged-in user is the owner of the comment, that will be get updated.
        if (!(comment.getUser().getId() == authUtils.getLoggedInUserId())){
            throw new RuntimeException("A User can only update a Comment, made by him");
        }

        // Check if the Comment really belongs to the passed in Post
        if (!(comment.getPost().getId().equals(post.getId()))){
            throw new RuntimeException("This Comment does not belong to this Post");
        }

        // Now we can check and updated all the Fields of the original Comment, that should get updated.
        if (!(commentRequestDao.getCommentBody().isEmpty())){
            comment.setCommentBody(commentRequestDao.getCommentBody());
        }

        // Add current Date as the Updated Date
        LocalDate currentDate = LocalDate.now();
        comment.setUpdatedDate(currentDate);

        // Lastly, save the comment
        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(long commentId) {

        // Get the Comment through passed in comment id.
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->
                new RuntimeException("The Comment with the CommentId: " + commentId + " does not exists"));

        // A User can only delete a Comment, if this Comment belongs to the User, who wants to delete the comment.
        if (comment.getUser().getId() != authUtils.getLoggedInUserId()){
            throw new RuntimeException("A Comment can only be deleted from its owner");
        }

        commentRepository.delete(comment);
    }
}
