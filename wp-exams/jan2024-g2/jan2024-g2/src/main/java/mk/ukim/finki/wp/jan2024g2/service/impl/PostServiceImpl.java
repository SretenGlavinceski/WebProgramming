package mk.ukim.finki.wp.jan2024g2.service.impl;

import mk.ukim.finki.wp.jan2024g2.model.Post;
import mk.ukim.finki.wp.jan2024g2.model.PostType;
import mk.ukim.finki.wp.jan2024g2.model.exceptions.InvalidPostIdException;
import mk.ukim.finki.wp.jan2024g2.repository.PostRepository;
import mk.ukim.finki.wp.jan2024g2.service.PostService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final TagServiceImpl tagService;

    public PostServiceImpl(PostRepository postRepository, TagServiceImpl tagService) {
        this.postRepository = postRepository;
        this.tagService = tagService;
    }

    @Override
    public List<Post> listAll() {
        return postRepository.findAll();
    }

    @Override
    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(InvalidPostIdException::new);
    }

    @Override
    public Post create(String title, String content, LocalDate dateCreated, PostType postType, List<Long> tags) {
        Post post = new Post(
                title,
                content,
                dateCreated,
                postType,
                tags.stream().map(tagService::findById).collect(Collectors.toList())
        );

        postRepository.save(post);
        return post;
    }

    @Override
    public Post update(Long id, String title, String content, LocalDate dateCreated, PostType postType, List<Long> tags) {
        Post post = findById(id);

        post.setTitle(title);
        post.setContent(content);
        post.setDateCreated(dateCreated);
        post.setPostType(postType);
        post.setTags(tags.stream().map(tagService::findById).collect(Collectors.toList()));

        postRepository.save(post);
        return post;
    }

    @Override
    public Post delete(Long id) {
        Post post = findById(id);
        postRepository.delete(post);
        return post;
    }

    @Override
    public Post like(Long id) {
        Post post = findById(id);
        post.setLikes(post.getLikes() + 1);
        postRepository.save(post);
        return post;
    }

    @Override
    public List<Post> filterPosts(Long tagId, PostType postType) {
        List<Post> posts = listAll();

        if (tagId != null) {
            posts = posts.stream()
                    .filter(post -> post.getTags().contains(tagService.findById(tagId)))
                    .collect(Collectors.toList());
        }

        if (postType != null) {
            posts = posts.stream()
                    .filter(post -> post.getPostType() == postType)
                    .collect(Collectors.toList());
        }

        return posts;
    }
}
