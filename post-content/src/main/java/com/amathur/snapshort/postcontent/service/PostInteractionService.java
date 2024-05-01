package com.amathur.snapshort.postcontent.service;

import com.amathur.snapshort.postcontent.exception.PostNotFoundException;
import com.amathur.snapshort.postcontent.model.Comment;
import com.amathur.snapshort.postcontent.model.Post;
import com.amathur.snapshort.postcontent.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostInteractionService
{

    @Autowired
    PostRepository repository;

    public void likePost(String postId) throws PostNotFoundException
    {
        Post post = getPost(postId);
        post.setLikes(post.getLikes() + 1);
        repository.save(post);
    }

    public void save(Comment comment, String postID) throws PostNotFoundException
    {
        Post post = getPost(postID);
        List<Comment> commentList = post.getComments();
        commentList.add(comment);
        post.setComments(commentList);
        repository.save(post);
    }

    private Post getPost(String postID) throws PostNotFoundException
    {
        Optional<Post> optionalPost = repository.findById(postID);
        if (!optionalPost.isPresent() || optionalPost.isEmpty())
            throw new PostNotFoundException("Unable to find post with ID : " + postID);
        Post post = optionalPost.get();
        return post;
    }


}
