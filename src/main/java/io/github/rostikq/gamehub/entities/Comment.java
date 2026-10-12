package io.github.rostikq.gamehub.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.IdGeneratorType;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String comment;

    @Column(name = "publication_date_time")
    private LocalDateTime publicationDateTime;

    public Comment(String username, String comment, LocalDateTime time){
        this.username = username;
        this.comment = comment;
        this.publicationDateTime = time;
    }

    public Comment(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getPublicationDateTime() {
        return publicationDateTime;
    }

    public void setPublicationDateTime(LocalDateTime publicationDateTime) {
        this.publicationDateTime = publicationDateTime;
    }
}
