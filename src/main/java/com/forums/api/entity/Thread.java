package com.forums.api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "thread")
@Builder
public class Thread {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "thread_generator")
    @SequenceGenerator(name = "thread_generator", sequenceName = "thread_seq", allocationSize = 1, initialValue = 1)
    private Long id;

    private String title;

    private Instant createdAt;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonManagedReference
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "username")
    @JsonManagedReference
    private User user;
}
