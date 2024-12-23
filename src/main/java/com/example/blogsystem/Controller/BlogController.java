package com.example.blogsystem.Controller;

import com.example.blogsystem.ApiResponse.ApiException;
import com.example.blogsystem.Model.Blog;
import com.example.blogsystem.Model.MyUser;
import com.example.blogsystem.Service.BlogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/blog")
public class BlogController {
    private final BlogService blogService;

    @GetMapping("/get-all")
    public ResponseEntity getAllBlogs(@AuthenticationPrincipal MyUser myUser) {
        return ResponseEntity.status(200).body(blogService.getAllBlogs(myUser.getId()));
    }

    @GetMapping("/get-my")
    public ResponseEntity getMyBlogs(@AuthenticationPrincipal MyUser myUser) {
        return ResponseEntity.status(200).body(blogService.getMyBlogs(myUser.getId()));
    }

    @PostMapping("/add")
    public ResponseEntity addBlog(@AuthenticationPrincipal MyUser myUser,
                                  @RequestBody @Valid Blog blog) {
        blogService.addBlog(myUser.getId(), blog);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiException("Blog has been added successfully"));
    }

    @PutMapping("/update/{blogId}")
    public ResponseEntity updateBlog(@AuthenticationPrincipal MyUser myUser,
                                     @PathVariable Integer blogId,
                                     @RequestBody @Valid Blog blog) {
        blogService.updateBlog(myUser.getId(), blogId, blog);
        return ResponseEntity.status(200).body(new ApiException("Blog with ID: " + blogId + " has been updated successfully"));
    }

    @DeleteMapping("/delete/{blogId}")
    public ResponseEntity deleteBlog(@AuthenticationPrincipal MyUser myUser,
                                     @PathVariable Integer blogId) {
        blogService.deleteBlog(myUser.getId(), blogId);
        return ResponseEntity.status(200).body(new ApiException("Blog with ID: " + blogId + " has been deleted successfully"));
    }

    @GetMapping("/get/by-id/{blogId}")
    public ResponseEntity getBlogById(@AuthenticationPrincipal MyUser myUser,
                                      @PathVariable Integer blogId) {
        return ResponseEntity.status(200).body(blogService.getBlogById(myUser.getId(), blogId));
    }

    @GetMapping("/get/by-title/{title}")
    public ResponseEntity getBlogByTitle(@AuthenticationPrincipal MyUser myUser,
                                         @PathVariable String title) {
        return ResponseEntity.status(200).body(blogService.getBlogByTitle(myUser.getId(), title));
    }
}
