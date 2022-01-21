package com.erlangga.finalproject.postservice.impl.controller;

import com.erlangga.finalproject.postservice.api.dto.BaseResponse;
import com.erlangga.finalproject.postservice.api.service.LikeService;
import com.erlangga.finalproject.postservice.data.model.Like;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post/{id_post}/like")
public class LikeController {
    @Autowired
    @Qualifier("likeServiceImpl")
    private LikeService likeService;

    @PostMapping
    public ResponseEntity<BaseResponse<Like>> doLike(@RequestBody Like like, @PathVariable Integer id_post){
        if (like.getUser_id() == null){
            return new ResponseEntity(new BaseResponse(Boolean.FALSE,
                    "Bad Request"), HttpStatus.BAD_REQUEST);
        }
        try {
            like.setPost_id(id_post);
            likeService.doLike(like);
            return ResponseEntity.ok(new BaseResponse<>(like));
        }catch (Exception e){
            return new ResponseEntity(new BaseResponse(Boolean.FALSE,
                    "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
