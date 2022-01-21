package com.erlangga.finalproject.postservice.impl.service;

import com.erlangga.finalproject.postservice.api.dto.CategoryOutput;
import com.erlangga.finalproject.postservice.api.dto.PostInput;
import com.erlangga.finalproject.postservice.api.dto.PostOutput;
import com.erlangga.finalproject.postservice.api.dto.SinglePostOutput;
import com.erlangga.finalproject.postservice.api.service.CommentService;
import com.erlangga.finalproject.postservice.api.service.KafkaPost;
import com.erlangga.finalproject.postservice.api.service.PostService;
import com.erlangga.finalproject.postservice.data.model.Post;
import com.erlangga.finalproject.postservice.impl.repository.PostRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    @Qualifier("commentServiceImpl")
    private CommentService commentService;

    @Autowired
    private KafkaPost kafkaPost;

    @Override
    public SinglePostOutput getOne(Integer id) {

        Optional<Post> post = postRepository.findById(id);

        if (post.isEmpty()){
            throw new RuntimeException("Not Found");
        }

        return SinglePostOutput.builder()
                .id(post.get().getId())
                .user_id(post.get().getUser_id())
                .category_id(post.get().getCategory_id())
                .title(post.get().getTitle())
                .content(post.get().getContent())
                .comments(commentService.getCommentByPost(id))
                .createdAt(post.get().getCreatedAt())
                .updatedAt(post.get().getUpdatedAt())
                .build();
    }

    @Override
    public List<PostOutput> getAll() {
        Iterable<Post> posts = postRepository.findAll();
        List<PostOutput> postOutputs = new ArrayList<>();

        for (Post post : posts){
            PostOutput postOutput = PostOutput.builder()
                    .id(post.getId())
                    .user_id(post.getUser_id())
                    .category_id(post.getCategory_id())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .createdAt(post.getCreatedAt())
                    .updatedAt(post.getUpdatedAt())
                    .build();
            postOutputs.add(postOutput);
        }
        return postOutputs;
    }

    @Override
    public List<PostOutput> getByCategory(Integer category_id) {
        Iterable<Post> posts = postRepository.findAll();
        List<PostOutput> postOutputs = new ArrayList<>();
        for (Post post : posts){
            if (post.getCategory_id()==category_id){
                PostOutput postOutput = PostOutput.builder()
                        .id(post.getId())
                        .user_id(post.getUser_id())
                        .category_id(post.getCategory_id())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .updatedAt(post.getCreatedAt())
                        .createdAt(post.getUpdatedAt())
                        .build();
                postOutputs.add(postOutput);
            }
        }
        return postOutputs;
    }

    @Override
    public void addOne(PostInput postInput) {
        Post post = Post.builder()
                .title(postInput.getTitle())
                .content(postInput.getContent())
                .user_id(postInput.getUser_id())
                .category_id(postInput.getCategory_id())
                .build();
        try {
            postRepository.save(post);
        }catch (Exception e){
            throw new RuntimeException("Duplicated");
        }
    }

    @Override
    public void delete(Integer id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isEmpty()) {
            throw new RuntimeException("Not Found");
        }
        String oldPost = null;
        try {
            oldPost = new JSONObject()
                    .put("data", post)
                    .toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        kafkaPost.sendLog(oldPost);
        postRepository.deleteById(id);
    }

    @Override
    public Post update(Integer id, PostInput postInput) {
        Optional<Post> postUpdated = postRepository.findById(id);
        if (postUpdated.isEmpty()){
            throw new RuntimeException("Not Found");
        }
        postUpdated.get().setTitle(postInput.getTitle());
        postUpdated.get().setContent(postInput.getContent());
        return  postRepository.save(postUpdated.get());
    }

    private CategoryOutput checkCategory(Integer id){
        String result;
        try{
            final String uri = "http://192.168.43.15:8701/category/"+id;
            RestTemplate restTemplate = new RestTemplate();
            result = restTemplate.getForObject(uri, String.class);

            Gson gsonCategory= new Gson();
            CategoryOutput categoryOutput = gsonCategory.fromJson(result, CategoryOutput.class);

            System.out.println(categoryOutput);
            System.out.println(result);
            return categoryOutput;
        }catch (Exception e){
           return null;
        }
    }
}
