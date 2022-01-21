package com.erlangga.finalproject.postservice.impl;

import com.erlangga.finalproject.postservice.api.dto.PostOutput;
import com.erlangga.finalproject.postservice.api.dto.SinglePostOutput;
import com.erlangga.finalproject.postservice.data.model.Post;
import com.erlangga.finalproject.postservice.impl.repository.PostRepository;
import com.erlangga.finalproject.postservice.impl.service.PostServiceImpl;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TestPostServiceImpl {
    private static EasyRandom EASY_RANDOM = new EasyRandom();
    private static ModelMapper modelMapper = new ModelMapper();
    private Integer RANDOM_ID;

    @InjectMocks
    private PostServiceImpl postService;

    @Mock
    private PostRepository postRepository;
    @Spy
    private ModelMapper mapper = new ModelMapper();

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        RANDOM_ID = EASY_RANDOM.nextInt();
    }

    @Test
    public void getAll_WillReturnListPostOutput() {
        Iterable<Post> posts = EASY_RANDOM.objects(Post.class, 2)
                .collect(Collectors.toList());
        when(postRepository.findAll()).thenReturn(posts);

        var result = postService.getAll();

        List<PostOutput> outputs = new ArrayList<>();
        for (Post post: posts) {
            outputs.add(modelMapper.map(post, PostOutput.class));
        }
        verify(postRepository, times(1)).findAll();
        assertEquals(outputs, result);
    }

    @Test
    public void getOne_WillReturnPostOutput() {
        // Given
        Post post = EASY_RANDOM.nextObject(Post.class);
        when(postRepository.findById(6)).thenReturn(Optional.of(post));  // Manipulasi return mock

        // When
        var result = postService.getOne(6);

        // Then
        SinglePostOutput expectedOutput = modelMapper.map(post, SinglePostOutput.class);
        assertEquals(expectedOutput, result);
        verify(postRepository, times(1)).findById(6);
    }

}
