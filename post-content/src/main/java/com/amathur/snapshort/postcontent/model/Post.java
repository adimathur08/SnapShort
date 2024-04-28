package com.amathur.snapshort.postcontent.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "post")
public class Post
{
    @Id
    private String id;
    private String authorId;
    private String title;
    private String content;
    private int likes;
    private List<Comment> comments;
    private Date createdAt;

}
