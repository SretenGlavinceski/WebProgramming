package mk.ukim.finki.wp.jan2024g2.repository;

import mk.ukim.finki.wp.jan2024g2.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
