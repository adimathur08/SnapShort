package com.amathur.snapshort.postcontent.validator;

import com.amathur.snapshort.postcontent.dto.SavePostDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
public class PostValidator
{
    public void validate(@Valid SavePostDTO postDTO)
    {}
}
