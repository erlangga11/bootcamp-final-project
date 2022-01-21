package com.erlangga.finalproject.postservice.impl.service;

import com.erlangga.finalproject.postservice.api.dto.CommentInput;
import com.erlangga.finalproject.postservice.api.dto.CommentOutput;
import com.erlangga.finalproject.postservice.api.service.CommentService;
import com.erlangga.finalproject.postservice.data.model.Comment;
import com.erlangga.finalproject.postservice.impl.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public CommentOutput getOneComment(Integer id, Integer post_id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isEmpty()||comment.get().getPost_id()!=post_id){
            throw new RuntimeException("Not Found");
        }
        return CommentOutput.builder()
                .id(comment.get().getId())
                .user_id(comment.get().getUser_id())
                .post_id(comment.get().getPost_id())
                .content(comment.get().getContent())
                .createdAt(comment.get().getCreatedAt())
                .createdAt(comment.get().getUpdatedAt())
                .build();
    }

    @Override
    public List<CommentOutput> getAllComment() {
        Iterable<Comment> comments = commentRepository.findAll();
        List<CommentOutput> commentOutputs = new ArrayList<>();
        for (Comment comment : comments){
            CommentOutput commentOutput = CommentOutput.builder()
                    .id(comment.getId())
                    .user_id(comment.getUser_id())
                    .post_id(comment.getPost_id())
                    .content(comment.getContent())
                    .createdAt(comment.getCreatedAt())
                    .createdAt(comment.getUpdatedAt())
                    .build();
            commentOutputs.add(commentOutput);
        }
        return commentOutputs;
    }

    @Override
    public List<CommentOutput> getCommentByPost(Integer post_id) {
        Iterable<Comment> comments = commentRepository.findAll();
        List<CommentOutput> commentOutputs = new ArrayList<>();
        for (Comment comment : comments){
            if (comment.getPost_id() == post_id){
                CommentOutput commentOutput = CommentOutput.builder()
                        .id(comment.getId())
                        .user_id(comment.getUser_id())
                        .post_id(comment.getPost_id())
                        .content(comment.getContent())
                        .createdAt(comment.getCreatedAt())
                        .createdAt(comment.getUpdatedAt())
                        .build();
                commentOutputs.add(commentOutput);
            }
        }
        return commentOutputs;
    }

    @Override
    public void addOneComment(CommentInput commentInput) {
        Comment comment = Comment.builder()
                .post_id(commentInput.getPost_id())
                .user_id(commentInput.getUser_id())
                .content(commentInput.getContent())
                .build();
        try {
            commentRepository.save(comment);
        }catch (Exception e){
            throw new RuntimeException("Duplicated");
        }
    }

    @Override
    public void deleteComment(Integer id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isEmpty()){
            throw new RuntimeException("Not Found");
        }
        commentRepository.deleteById(id);
    }

    @Override
    public Comment updateComment(Integer id, CommentInput commentInput) {
        Optional<Comment> commentUpdated = commentRepository.findById(id);
        if (commentUpdated.isEmpty()){
            throw new RuntimeException("Not Found");
        }
        commentUpdated.get().setContent(commentInput.getContent());
        return  commentRepository.save(commentUpdated.get());
    }
}
