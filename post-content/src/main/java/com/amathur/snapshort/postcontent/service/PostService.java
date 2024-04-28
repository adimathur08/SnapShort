package com.amathur.snapshort.postcontent.service;

import com.amathur.snapshort.postcontent.dto.SavePostDTO;
import com.amathur.snapshort.postcontent.model.Post;
import com.amathur.snapshort.postcontent.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PostService
{
    @Autowired
    PostRepository repository;

    public void save(SavePostDTO postDTO, String username)
    {
        Post post = new Post();
        post.setAuthorId(username);
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setLikes(0);
        post.setComments(new ArrayList<>());
        post.setCreatedAt(new Date());

        repository.save(post);
    }

    public List<Post> findAllByAuthorId(String username)
    {
        return repository.findAllByAuthorId(username);
    }
}
