package com.democom.news.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@ToString
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_news")
public class News {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Title must be not null")
    @Size(min = 5, message = "Title must be at least 3 character")
    private String title;

    @NotBlank(message = "Category should not be null")
    private String category;

    @NotBlank(message = "Description must be not null")
    @Lob
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date publicationDate;

    //da fare
    /*@UpdateTimestamp
    private Timestamp updatedAt;*/

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private  Author author;



    @PrePersist
    private void onCreate() {
        publicationDate = new Date();
    }
}
