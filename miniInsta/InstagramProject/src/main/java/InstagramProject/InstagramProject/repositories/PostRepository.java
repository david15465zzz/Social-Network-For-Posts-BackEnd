package InstagramProject.InstagramProject.repositories;

import InstagramProject.InstagramProject.beans.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository  extends JpaRepository<Post, Integer> {

    @Query(value ="select * from posts  user_id = ?1",nativeQuery = true)
    List<Post> findByUser(int userId );
}
