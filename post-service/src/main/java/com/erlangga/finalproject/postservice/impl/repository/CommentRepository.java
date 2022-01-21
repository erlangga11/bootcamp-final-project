package com.erlangga.finalproject.postservice.impl.repository;

import com.erlangga.finalproject.postservice.data.model.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment,Integer> {
}
