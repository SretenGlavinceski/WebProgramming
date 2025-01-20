package mk.ukim.finki.wp.jan2024g2.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Post {

    public Post(String title, String content, LocalDate dateCreated, PostType postType, List<Tag> tags) {
        this.title = title;
        this.content = content;
        this.dateCreated = dateCreated;
        this.postType = postType;
        this.tags = tags;
        this.likes = 0;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private LocalDate dateCreated;

    @Enumerated(EnumType.STRING)
    private PostType postType;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Tag> tags;

    private Integer likes = 0;
}
