package com.erlangga.finalproject.postservice.api.service;

import com.erlangga.finalproject.postservice.api.dto.PostInput;
import com.erlangga.finalproject.postservice.api.dto.PostOutput;
import com.erlangga.finalproject.postservice.api.dto.SinglePostOutput;
import com.erlangga.finalproject.postservice.data.model.Post;

import java.util.List;

public interface PostService {
    SinglePostOutput getOne(Integer id);
    List<PostOutput> getAll();
    List<PostOutput> getByCategory(Integer category_id);
    void addOne(PostInput postInput);
    void delete(Integer id);
    Post update(Integer id, PostInput postInput);
}
