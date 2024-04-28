package com.amathur.snapshort.postcontent.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SavePostDTO
{
    @Valid

    @NotBlank(message = "Title cannot be blank")
    @NotNull(message = "Title cannot be null")
    @Size(min = 3, max = 15, message = "Title should be in the range 3-15")
    private String title;

    @NotBlank(message = "Post content cannot be blank")
    @NotNull(message = "Post content cannot be null")
    @Size(min = 5, max = 100, message = "Post content should be in the range 5-100")
    private String content;

}
