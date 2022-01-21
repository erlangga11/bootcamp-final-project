package com.erlangga.finalproject.postservice.data.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="likes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Long user_id;
    private Integer post_id;
}
