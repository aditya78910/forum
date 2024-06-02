package com.forums.api.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "post")
@Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_generator")
    @SequenceGenerator(name = "post_generator", sequenceName = "post_seq", allocationSize = 1, initialValue = 1)
    private Long id;

    private String username;

//    @ManyToOne
//    @JoinColumn(name = "username")
//    @JsonManagedReference
//    private User user;

    @Column(columnDefinition = "text")
    private String text;

    private Instant createdAt;

    @ManyToOne
    @JoinColumn(name = "thread_id")
    @JsonManagedReference
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Thread thread;



}
