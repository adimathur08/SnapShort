package com.amathur.snapshort.postcontent.model;

import lombok.Data;
import java.util.Date;

@Data
public class Comment
{
    private String author;
    private String content;
    private Date createdAt;
}