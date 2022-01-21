package com.erlangga.finalproject.postservice.impl.controller;

import com.erlangga.finalproject.postservice.api.dto.*;
import com.erlangga.finalproject.postservice.api.dto.BaseResponse;
import com.erlangga.finalproject.postservice.api.dto.CommentInput;
import com.erlangga.finalproject.postservice.api.dto.CommentOutput;
import com.erlangga.finalproject.postservice.api.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post/{id_post}/comment")
public class CommentController {
    @Autowired
    @Qualifier("commentServiceImpl")
    private CommentService commentService;

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<CommentOutput>> getOneComment(@PathVariable Integer id, @PathVariable Integer id_post) {
        try {
            CommentOutput commentOutput = commentService.getOneComment(id,id_post);
            return ResponseEntity.ok(new BaseResponse<>(commentOutput));
        }catch (Exception e){
            if(e.getMessage().equalsIgnoreCase("Not Found")){
                return new ResponseEntity(new BaseResponse(Boolean.FALSE,
                        "No comment found"), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity(new BaseResponse(Boolean.FALSE,
                    "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<CommentOutput>>> getCommentByPost(@PathVariable Integer id_post){
        try{
            List<CommentOutput> commentOutputs = commentService.getCommentByPost(id_post);
            System.out.println(id_post);
            return ResponseEntity.ok(new BaseResponse<>(commentOutputs));
        }catch (Exception e){
            return new ResponseEntity(new BaseResponse(Boolean.FALSE,
                    "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<BaseResponse<CommentInput>> addOne(@RequestBody CommentInput commentInput, @PathVariable Integer id_post){
        if (commentInput.getContent() == null){
            return new ResponseEntity(new BaseResponse(Boolean.FALSE,
                    "Bad Request"), HttpStatus.BAD_REQUEST);
        }
        try {
            commentInput.setPost_id(id_post);
            commentService.addOneComment(commentInput);
            return ResponseEntity.ok(new BaseResponse<>(commentInput));
        }catch (Exception e){
            if(e.getMessage().equalsIgnoreCase("Duplicated")){
                return new ResponseEntity(new BaseResponse(Boolean.FALSE,
                        "Duplicated"), HttpStatus.CONFLICT);
            }
            return new ResponseEntity(new BaseResponse(Boolean.FALSE,
                    "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> delete(@PathVariable Integer id){
        try {
            commentService.deleteComment(id);
            return new ResponseEntity(new BaseResponse(Boolean.TRUE,
                    "Success deleting item"), HttpStatus.OK);
        }catch (Exception e){
            if(e.getMessage().equalsIgnoreCase("Not Found")){
                return new ResponseEntity(new BaseResponse(Boolean.FALSE,
                        "No comment found"), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity(new BaseResponse(Boolean.FALSE,
                    "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BaseResponse<CommentInput>> update(@PathVariable Integer id,@RequestBody CommentInput commentInput){
        try{
            if (commentInput.getContent() == null){
                return new ResponseEntity(new BaseResponse(Boolean.FALSE,
                        "Bad Request"), HttpStatus.BAD_REQUEST);
            }
            commentService.updateComment(id, commentInput);
            return ResponseEntity.ok(new BaseResponse<>(commentInput));
        }catch (Exception e){
            if(e.getMessage().equalsIgnoreCase("Not Found")){
                return new ResponseEntity(new BaseResponse(Boolean.FALSE,
                        "No comment found"), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity(new BaseResponse(Boolean.FALSE,
                    "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
