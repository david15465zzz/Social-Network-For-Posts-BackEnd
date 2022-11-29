package InstagramProject.InstagramProject.repositories;


import InstagramProject.InstagramProject.beans.Comment;
import InstagramProject.InstagramProject.beans.Like;
import InstagramProject.InstagramProject.beans.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface LikeRepository  extends JpaRepository<Like, Integer> {
    @Query(value ="select * from likes where post_id = ?1 and user_id = ?2",nativeQuery = true)
    Like findByUserAndPost(int postId,int userId );

    @Query(value ="select * from likes where post_id = ?1",nativeQuery = true)
    List<Like> findByPost(int postId );




    @Modifying
    @Transactional
    @Query(value = "delete from likes where post_id = ?1 and user_id = ?2", nativeQuery = true)
    void deleteUserLike(int postId,int UserId);

}
