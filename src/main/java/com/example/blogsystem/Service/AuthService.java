package com.example.blogsystem.Service;

import com.example.blogsystem.ApiResponse.ApiException;
import com.example.blogsystem.Model.MyUser;
import com.example.blogsystem.Repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository authRepository;

    public List<MyUser> getAllUsers(Integer adminId) {
        MyUser admin = authRepository.findMyUserById(adminId);
        if (admin == null)
            throw new ApiException("Admin with ID: " + adminId + " was not found");

        if (admin.getRole().equals("ADMIN"))
            return authRepository.findAll();

        throw new ApiException("You don't have the permission to access this endpoint");
    }

    public void register(MyUser myUser) {
        MyUser oldUser = authRepository.findMyUserByUsername(myUser.getUsername());
        if (oldUser != null)
            throw new ApiException("Username is already taken");

        myUser.setRole("USER");
        myUser.setPassword(new BCryptPasswordEncoder().encode(myUser.getPassword()));
        authRepository.save(myUser);
    }

    public void updateUser(Integer authId, Integer userId, MyUser user) {
        MyUser auth = authRepository.findMyUserById(authId);
        if (auth == null)
            throw new ApiException("User with ID: " + authId + " was not found");

        MyUser oldUser = authRepository.findMyUserById(userId);
        if (oldUser == null)
            throw new ApiException("User with ID: "+ userId + " was not found");

        if (authId.equals(userId) || auth.getRole().equals("ADMIN")) {
            MyUser byUsername = authRepository.findMyUserByUsername(user.getUsername());
            if (byUsername != null)
                throw new ApiException("Username is already taken");
            oldUser.setUsername(new BCryptPasswordEncoder().encode(user.getPassword()));
            oldUser.setPassword(user.getPassword());
            authRepository.save(oldUser);
        }
        else throw new ApiException("You don't have the permission to update this user");
    }

    public void deleteUser(Integer authId, Integer userId) {
        MyUser admin = authRepository.findMyUserById(authId);
        if (admin == null)
            throw new ApiException("User with ID: " + authId + " was not found");

        MyUser oldUser = authRepository.findMyUserById(userId);
        if (oldUser == null)
            throw new ApiException("User with ID: " + userId + " was not found");

        if (admin.getRole().equals("ADMIN"))
            authRepository.delete(oldUser);

        else throw new ApiException("You don't have the permission to delete a user");
    }
}
