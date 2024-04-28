package com.amathur.snapshort.postcontent.controller;

import com.amathur.snapshort.postcontent.dto.SavePostDTO;
import com.amathur.snapshort.postcontent.service.PostService;
import com.amathur.snapshort.postcontent.validator.PostValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post")
public class SavePostController
{
    @Autowired
    PostService service;

    @Autowired
    PostValidator validator;

    @PostMapping("/save")
    public void savePost(@RequestBody SavePostDTO post)
    {
        validator.validate(post);
        String username = "user1";
        service.save(post, username);
    }
}
