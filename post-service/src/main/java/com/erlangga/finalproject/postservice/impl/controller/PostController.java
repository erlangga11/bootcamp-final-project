package com.erlangga.finalproject.postservice.impl.controller;

import com.erlangga.finalproject.postservice.api.dto.BaseResponse;
import com.erlangga.finalproject.postservice.api.dto.PostInput;
import com.erlangga.finalproject.postservice.api.dto.PostOutput;
import com.erlangga.finalproject.postservice.api.dto.SinglePostOutput;
import com.erlangga.finalproject.postservice.api.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    @Qualifier("postServiceImpl")
    private PostService postService;

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<SinglePostOutput>> getOne(@PathVariable Integer id) {
        try {
            SinglePostOutput postOutput = postService.getOne(id);
            return ResponseEntity.ok(new BaseResponse<>(postOutput));
        }catch (Exception e){
            if(e.getMessage().equalsIgnoreCase("Not Found")){
                return new ResponseEntity(new BaseResponse(Boolean.FALSE,
                        "No post found"), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity(new BaseResponse(Boolean.FALSE,
                    "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/postByCategory/{id}")
    public ResponseEntity<BaseResponse<List<PostOutput>>> getByCategory(@PathVariable Integer id){
        try {
            List<PostOutput> postOutputs = postService.getByCategory(id);
            return ResponseEntity.ok(new BaseResponse<>(postOutputs));
        }catch (Exception e){
            return new ResponseEntity(new BaseResponse(Boolean.FALSE,
                    "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<PostOutput>>> getAll(){
        try {
            List<PostOutput> postOutputs = postService.getAll();
            return ResponseEntity.ok(new BaseResponse<>(postOutputs));
        }catch (Exception e){
            return new ResponseEntity(new BaseResponse(Boolean.FALSE,
                    "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<BaseResponse<PostInput>> addOne(@RequestBody PostInput postInput){
        if (postInput.getTitle() == null){
            return new ResponseEntity(new BaseResponse(Boolean.FALSE,
                    "Bad Request"), HttpStatus.BAD_REQUEST);
        }
        try {
            postService.addOne(postInput);
            return ResponseEntity.ok(new BaseResponse<>(postInput));
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
            postService.delete(id);
            return new ResponseEntity(new BaseResponse(Boolean.TRUE,
                    "Success deleting item"), HttpStatus.OK);
        }catch (Exception e){
            if(e.getMessage().equalsIgnoreCase("Not Found")){
                return new ResponseEntity(new BaseResponse(Boolean.FALSE,
                        "No post found"), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity(new BaseResponse(Boolean.FALSE,
                    "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BaseResponse<PostInput>> update(@PathVariable Integer id,@RequestBody PostInput postInput){
        try{
            if (postInput.getTitle() == null){
                return new ResponseEntity(new BaseResponse(Boolean.FALSE,
                        "Bad Request"), HttpStatus.BAD_REQUEST);
            }
            postService.update(id, postInput);
            return ResponseEntity.ok(new BaseResponse<>(postInput));
        }catch (Exception e){
            if(e.getMessage().equalsIgnoreCase("Not Found")){
                return new ResponseEntity(new BaseResponse(Boolean.FALSE,
                        "No post found"), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity(new BaseResponse(Boolean.FALSE,
                    "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
