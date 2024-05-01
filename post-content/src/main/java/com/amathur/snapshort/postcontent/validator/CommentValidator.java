package com.amathur.snapshort.postcontent.validator;

import com.amathur.snapshort.postcontent.dto.SaveCommentDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
public class CommentValidator
{
    public void validate(@Valid SaveCommentDTO comment)
    {
    }
}
