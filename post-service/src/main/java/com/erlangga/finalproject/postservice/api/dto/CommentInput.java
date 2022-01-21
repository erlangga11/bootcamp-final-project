package com.erlangga.finalproject.postservice.api.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentInput {
    private String content;
    private Long user_id;
    private Integer post_id;
}
