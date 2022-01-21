package com.erlangga.finalproject.postservice.data.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="comments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Long user_id;
    private Integer post_id;
    private String content;

    @Column
    @UpdateTimestamp
    private Date updatedAt;
    @Column
    @CreationTimestamp
    private Date createdAt;
}
