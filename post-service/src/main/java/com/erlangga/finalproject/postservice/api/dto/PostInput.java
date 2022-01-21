package com.erlangga.finalproject.postservice.api.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostInput {
    private Long user_id;
    private Integer category_id;
    private String title;
    private String content;
}
