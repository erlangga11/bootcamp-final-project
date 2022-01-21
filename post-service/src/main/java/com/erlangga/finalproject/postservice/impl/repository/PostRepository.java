package com.erlangga.finalproject.postservice.impl.repository;

import com.erlangga.finalproject.postservice.data.model.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post,Integer> {

}
