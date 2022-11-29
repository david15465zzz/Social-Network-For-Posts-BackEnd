package InstagramProject.InstagramProject.repositories;


import InstagramProject.InstagramProject.beans.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CommentRepository  extends JpaRepository<Comment, Integer> {

    @Query(value ="select * from comments where post_id = ?1 and user_id = ?2",nativeQuery = true)
    List<Comment> findByUserAndPost(int postId, int userId );

    @Query(value ="select * from comments where post_id = ?1 and text = ?2",nativeQuery = true)
    Comment findByTextAndPost(int postId, String text );

    @Query(value ="select * from comments where post_id = ?1",nativeQuery = true)
    List<Comment> findByPost(int postId );


    @Modifying
    @Transactional
    @Query(value = "delete from comments where post_id = ?1 and text = ?2", nativeQuery = true)
    void deleteUserComment(int postId,String text);
}
