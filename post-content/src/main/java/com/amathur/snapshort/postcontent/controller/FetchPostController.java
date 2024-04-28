package com.amathur.snapshort.postcontent.controller;

import com.amathur.snapshort.postcontent.model.Post;
import com.amathur.snapshort.postcontent.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/post/fetch")
public class FetchPostController
{
    @Autowired
    PostService service;

    @GetMapping("/user/{username}")
    public void getAllPostOfAuthor(@PathVariable("username") String username)
    {
        List<Post> posts = service.findAllByAuthorId(username);
        System.out.println("POSTS : " + posts.size());
    }
}
