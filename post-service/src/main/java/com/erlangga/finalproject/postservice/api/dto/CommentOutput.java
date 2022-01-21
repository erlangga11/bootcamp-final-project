package com.erlangga.finalproject.postservice.api.dto;

import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentOutput {
    private Integer id;
    private Long user_id;
    private Integer post_id;
    private String content;

    private Date updatedAt;
    private Date createdAt;
}
