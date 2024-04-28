package com.amathur.snapshort.postcontent.repository;

import com.amathur.snapshort.postcontent.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends MongoRepository<Post, String>
{
    List<Post> findAllByAuthorId(String username);
}
