package com.forums.api.service;

import com.forums.api.dto.request.post.PostCreateRequestListDTO;
import com.forums.api.dto.request.thread.ThreadCreateRequestDTO;
import com.forums.api.dto.request.thread.ThreadGetRequest;
import com.forums.api.dto.response.post.PostResponseDTO;
import com.forums.api.dto.response.post.ThreadPostResponseDTO;
import com.forums.api.dto.response.thread.ThreadDTO;
import com.forums.api.dto.response.thread.ThreadListResponseDTO;
import com.forums.api.entity.Category;
import com.forums.api.entity.Post;
import com.forums.api.entity.Thread;
import com.forums.api.entity.User;
import com.forums.api.exception.NotFoundException;
import com.forums.api.exception.ErrorResponse;
import com.forums.api.mapper.PostMapper;
import com.forums.api.mapper.ThreadMapper;
import com.forums.api.repository.CategoryRepository;
import com.forums.api.repository.PostRepository;
import com.forums.api.repository.ThreadRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ThreadService {

    CategoryRepository categoryRepository;

    ThreadMapper threadMapper;

    ThreadRepository threadRepository;

    PostRepository postRepository;

    PostMapper postMapper;

    public void createThreadWithInitialPost(ThreadCreateRequestDTO request, String username) {
        Category category = null;
        if (!ObjectUtils.isEmpty(request.getCategory())) {
            category = categoryRepository.findByName(request.getCategory())
                    .orElseThrow();
        }
        User user = User.builder()
                .username(username)
                .build();

        Thread thread = threadMapper.thread_threadCreateRequestDTO(request, username);
        thread.setUsername(user.getUsername());
        thread.setCategory(category);
        thread.setText(request.getOpeningPost().getText());
        threadRepository.save(thread);
    }

    private static Post postDto_post_mapper(String text, Thread savedThread, User user) {
        return Post.builder()
                .text(text)
                .thread(savedThread)
                .username(user.getUsername())
                .createdAt(Instant.now())
                .build();
    }

    public ThreadListResponseDTO getThreads(ThreadGetRequest request, String username) {

        //VALIDATION

        List<Long> categoryIds = categoryRepository.findAllByNameIn(request.getCategories())
                .stream().map(Category::getId).toList();

        if (categoryIds.size() < request.getCategories().size()) {
            throw new NotFoundException(ErrorResponse
                    .<List<String>>builder()
                    .data(request.getCategories())
                    .message("Category invalid")
                    .build());
        }


        List<ThreadDTO> threadDTOS = threadRepository.findThreadsByCriteria(request, categoryIds)
                .stream().map(threadMapper::thread_threadDTO_mapper)
                .toList();

        return ThreadListResponseDTO.builder()
                .threads(threadDTOS)
                .build();
    }
    public ThreadListResponseDTO getThreadsBk(ThreadGetRequest request, String username) {

        //VALIDATION

        List<Long> categoryIds = categoryRepository.findAllByNameIn(request.getCategories())
                .stream().map(Category::getId).toList();

        if (categoryIds.size() < request.getCategories().size()) {
            throw new NotFoundException(ErrorResponse
                    .<List<String>>builder()
                    .data(request.getCategories())
                    .message("Category invalid")
                    .build());
        }



        PageRequest pageRequest = null;


        if (!ObjectUtils.isEmpty(request.getPage()) && !ObjectUtils.isEmpty(request.getPage_size())) {
            pageRequest = PageRequest.of(request.getPage(), request.getPage_size());
        }

        List<ThreadDTO> threads;

        Sort sortOrder;

        if(request.isNewest_first()){
            sortOrder = Sort.by(Sort.Direction.DESC, "createdAt");
        }else{
            sortOrder = Sort.by(Sort.Direction.ASC, "createdAt");
        }

        if (pageRequest != null) {
            pageRequest = pageRequest.withSort(sortOrder);
            threads = threadRepository.findAllByCategoryIdIn(pageRequest, categoryIds)
                    .getContent()
                    .stream().map(threadMapper::thread_threadDTO_mapper)
                    .toList();
        }else{
            threads = threadRepository.findAllByCategoryIdIn(categoryIds, sortOrder)
                    .stream().map(threadMapper::thread_threadDTO_mapper)
                    .toList();
        }
        return ThreadListResponseDTO.builder()
                .threads(threads)
                .build();

    }

    public void createPosts(PostCreateRequestListDTO request, String username) {
        Thread thread = threadRepository.findById(request.getThreadId())
                .orElseThrow();

        User user = User.builder()
                .username(username)
                .build();

        List<Post> posts = request.getPosts()
                .stream()
                .map(postCreateDTO -> postDto_post_mapper(postCreateDTO.getText(), thread, user))
                .toList();

        postRepository.saveAll(posts);
    }

    public ThreadPostResponseDTO getPosts(Long threadId) {
        Thread thread = threadRepository.findById(threadId)
                .orElseThrow(() -> new NotFoundException(ErrorResponse
                        .<List<String>>builder()
                        .data(Arrays.asList(String.valueOf(threadId)))
                        .message("Category invalid")
                        .build()));

        List<PostResponseDTO> posts = postRepository.findByThreadId(threadId, Sort.by(Sort.Direction.ASC, "createdAt"))
                .stream()
                .map(postMapper::post_postresponseDTO_mapper)
                .toList();

        return ThreadPostResponseDTO.builder()
                .posts(posts)
                .title(thread.getTitle())
                .id(thread.getId())
                .author(thread.getUsername())
                .createdAt(thread.getCreatedAt())
                .category(thread.getCategory().getName())
                .build();
    }

    public void deleteThread(Long threadId) {
        if(!threadRepository.findById(threadId).isPresent()){
            throw new NotFoundException(ErrorResponse
                    .<List<String>>builder()
                    .data(Arrays.asList(String.valueOf(threadId)))
                    .message("Thread not found")
                    .build());
        }
        threadRepository.deleteById(threadId);
    }
}
