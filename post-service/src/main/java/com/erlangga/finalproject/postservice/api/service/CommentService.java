package com.erlangga.finalproject.postservice.api.service;

import com.erlangga.finalproject.postservice.api.dto.CommentInput;
import com.erlangga.finalproject.postservice.api.dto.CommentOutput;
import com.erlangga.finalproject.postservice.data.model.Comment;

import java.util.List;

public interface CommentService {
    CommentOutput getOneComment(Integer id, Integer post_id);
    List<CommentOutput> getAllComment();
    List<CommentOutput> getCommentByPost(Integer post_id);
    void addOneComment(CommentInput commentInput);
    void deleteComment(Integer id);
    Comment updateComment(Integer id, CommentInput commentInput);
}
