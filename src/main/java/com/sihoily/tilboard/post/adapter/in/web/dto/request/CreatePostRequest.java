package com.sihoily.tilboard.post.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostRequest {
    @NotBlank
    @Size(max = 200)
    private String title;
    @NotBlank
    private String content;
    @Size(max = 10)
    private List<String> tags;
}
