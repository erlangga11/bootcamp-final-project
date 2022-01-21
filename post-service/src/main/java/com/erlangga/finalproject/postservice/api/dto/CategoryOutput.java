package com.erlangga.finalproject.postservice.api.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryOutput {
    private Integer id;
    private String name;
}
