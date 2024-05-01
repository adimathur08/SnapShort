package com.amathur.snapshort.postcontent.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SaveCommentDTO
{
    @Valid

    @NotBlank(message = "Comment cannot be blank")
    @NotNull(message = "Comment cannot be null")
    @Size(min = 1, max = 50, message = "Comment should be in the range 1-50")
    private String comment;
}
