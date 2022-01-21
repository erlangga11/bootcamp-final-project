package com.erlangga.finalproject.postservice.impl.service;
import com.erlangga.finalproject.postservice.api.service.LikeService;
import com.erlangga.finalproject.postservice.data.model.Like;
import com.erlangga.finalproject.postservice.impl.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Override
    public void doLike(Like likeRequest) {
        Iterable<Like> likes = likeRepository.findAll();
        List<Like> likesList = new ArrayList<>();
        for (Like like : likes){
            if (like.getPost_id()==likeRequest.getPost_id()&&like.getUser_id()==likeRequest.getUser_id()){
                likeRepository.deleteById(like.getId());
                return;
            }
        }
        likeRepository.save(likeRequest);
    }
}
