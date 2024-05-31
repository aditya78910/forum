package com.forums.api.rest;

import com.forums.api.dto.request.post.PostCreateRequestListDTO;
import com.forums.api.dto.request.thread.ThreadCreateRequestDTO;
import com.forums.api.dto.request.thread.ThreadGetRequest;
import com.forums.api.dto.response.authentication.UserDTO;
import com.forums.api.dto.response.post.ThreadPostResponseDTO;
import com.forums.api.exception.AuthenticationFailedException;
import com.forums.api.exception.ErrorResponse;
import com.forums.api.service.ThreadService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/thread")
@AllArgsConstructor
public class ThreadController {

    ThreadService threadService;
    @PostMapping
    public ResponseEntity<?> createThread(@Valid @RequestBody ThreadCreateRequestDTO request,
                                          @AuthenticationPrincipal UserDTO userDTO){
        threadService.createThreadWithInitialPost(request, userDTO.getUsername());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getThreads(@Valid @ModelAttribute ThreadGetRequest request,
                                          @AuthenticationPrincipal UserDTO userDTO){
         ;
        return ResponseEntity.ok(threadService.getThreads(request, userDTO.getUsername()));
    }

    @PostMapping("/post")
    public ResponseEntity<?> createPost(@Valid @RequestBody PostCreateRequestListDTO request,
                                          @AuthenticationPrincipal UserDTO userDTO){
        threadService.createPosts(request, userDTO.getUsername());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/post")
    public ResponseEntity<?> getPosts(@RequestParam Long thread_id,
                                        @AuthenticationPrincipal UserDTO userDTO){
        ThreadPostResponseDTO threadPostResponseDTO = threadService.getPosts(thread_id);
        return ResponseEntity.ok(threadPostResponseDTO);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteThread(@RequestParam Long id,
                                      @AuthenticationPrincipal UserDTO userDTO){

        if(ObjectUtils.isEmpty(userDTO.getRoles()) || !userDTO.getRoles().contains("admin")){
            throw new AuthenticationFailedException(ErrorResponse.<String>builder()
                    .message("Operation not Allowed")
                    .build());
        }

        threadService.deleteThread(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
