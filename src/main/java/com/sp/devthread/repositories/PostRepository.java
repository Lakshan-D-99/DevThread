package com.sp.devthread.repositories;

import com.sp.devthread.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

    Optional<List<Post>> findAllPostsByUserId(long userId);

    Optional<Post> findPostByPostTitle(String postTitle);
}
