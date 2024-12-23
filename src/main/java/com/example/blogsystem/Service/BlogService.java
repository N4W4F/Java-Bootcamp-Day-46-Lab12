package com.example.blogsystem.Service;

import com.example.blogsystem.ApiResponse.ApiException;
import com.example.blogsystem.Model.Blog;
import com.example.blogsystem.Model.MyUser;
import com.example.blogsystem.Repository.AuthRepository;
import com.example.blogsystem.Repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;
    private final AuthRepository authRepository;

    public List<Blog> getAllBlogs(Integer userId) {
        MyUser myUser = authRepository.findMyUserById(userId);
        if (myUser == null)
            throw new ApiException("User with ID: " + userId + " was not found");

        return blogRepository.findAll();
    }

    public List<Blog> getMyBlogs(Integer userId) {
        MyUser myUser = authRepository.findMyUserById(userId);
        if (myUser == null)
            throw new ApiException("User with ID: " + userId + " was not found");

        return blogRepository.findBlogsByUser(myUser);
    }

    public void addBlog(Integer userId, Blog blog) {
        MyUser myUser = authRepository.findMyUserById(userId);
        if (myUser == null)
            throw new ApiException("User with ID: " + userId + " was not found");

        blog.setUser(myUser);
        blogRepository.save(blog);
    }

    public void updateBlog(Integer userId, Integer blogId, Blog blog) {
        MyUser myUser = authRepository.findMyUserById(userId);
        if (myUser == null)
            throw new ApiException("User with ID: " + userId + " was not found");

        Blog oldBlog = blogRepository.findBlogById(blogId);
        if (oldBlog == null)
            throw new ApiException("Blog with ID: " + blogId + " was not found");

        Blog blogByUser = blogRepository.findBlogByIdAndUser(blogId, myUser);
        if (blogByUser != null || myUser.getRole().equals("ADMIN")) {
            oldBlog.setTitle(blog.getTitle());
            oldBlog.setBody(blog.getBody());
            blogRepository.save(oldBlog);
        }
        else throw new ApiException("You don't have the permission to update this blog");
    }

    public void deleteBlog(Integer userId, Integer blogId) {
        MyUser myUser = authRepository.findMyUserById(userId);
        if (myUser == null)
            throw new ApiException("User with ID: " + userId + " was not found");

        Blog blog = blogRepository.findBlogById(blogId);
        if (blog == null)
            throw new ApiException("Blog with ID: " + blogId + " was not found");

        Blog blogByUser = blogRepository.findBlogByIdAndUser(blogId, myUser);
        if (blogByUser != null || myUser.getRole().equals("ADMIN")) {
            blogRepository.delete(blog);
        }
        else throw new ApiException("You don't have the permission to update this blog");
    }

    public Blog getBlogById(Integer userId, Integer blogId) {
        MyUser myUser = authRepository.findMyUserById(userId);
        if (myUser == null)
            throw new ApiException("User with ID: " + userId + " was not found");

        Blog blog = blogRepository.findBlogById(blogId);
        if (blog == null)
            throw new ApiException("Blog with ID: " + blogId + " was not found");

        return blog;
    }

    public Blog getBlogByTitle(Integer userId, String title) {
        MyUser myUser = authRepository.findMyUserById(userId);
        if (myUser == null)
            throw new ApiException("User with ID: " + userId + " was not found");

        Blog blog = blogRepository.findBlogByTitle(title);
        if (blog == null)
            throw new ApiException("Blog with Title: " + title + " was not found");

        return blog;
    }
}
