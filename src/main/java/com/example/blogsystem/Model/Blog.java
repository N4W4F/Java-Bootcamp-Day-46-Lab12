package com.example.blogsystem.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "VARCHAR(50) NOT NULL UNIQUE")
    @NotEmpty(message = "Blog Title cannot be empty")
    @Size(min = 2, max = 50, message = "Blog Title must be between 2 and 50 characters")
    private String title;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    @NotEmpty(message = "Blog body cannot be empty")
    @Size(min = 1, max = 255, message = "Blog Body must be between 1 and 255 characters")
    private String body;

    // Relations
    @ManyToOne
    @JsonIgnore
    private MyUser user;
}
