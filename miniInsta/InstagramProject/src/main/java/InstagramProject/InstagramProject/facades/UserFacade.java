package InstagramProject.InstagramProject.facades;

import InstagramProject.InstagramProject.beans.Comment;
import InstagramProject.InstagramProject.beans.Like;
import InstagramProject.InstagramProject.beans.Post;
import InstagramProject.InstagramProject.beans.User;
import InstagramProject.InstagramProject.repositories.CommentRepository;
import InstagramProject.InstagramProject.repositories.LikeRepository;
import InstagramProject.InstagramProject.repositories.PostRepository;
import InstagramProject.InstagramProject.repositories.UserRepository;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Scope("prototype")
public class UserFacade {

    private int userId;

    private UserRepository userRepository;

    private PostRepository postRepository;

    private LikeRepository likeRepository;

    private CommentRepository commentRepository;

    public UserFacade(int userId, UserRepository userRepository, PostRepository postRepository, LikeRepository likeRepository, CommentRepository commentRepository) {
        this.userId = userId;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.likeRepository = likeRepository;
        this.commentRepository = commentRepository;
    }

    public int getUserId() {
        return userId;
    }

    public boolean register(User user) {
        if(userRepository.findByEmail(user.getEmail())!=null)
            return false;
        else {
            userRepository.save(user);
            this.userId = userRepository.findByEmail(user.getEmail()).getId();
            return true;
        }
    }

    public boolean login(String email, String password) {
        if (userRepository.findByEmail(email) != null && userRepository.findByEmail(email).getPassword().equals(password)) {
            this.userId = userRepository.findByEmail(email).getId();
            return true;
        }
        return false;
    }

   public Post uploadPost(Post post){
        Date date=new Date();
        post.setDate(date);
        post.setUser(userRepository.findById(this.userId).orElseThrow(() -> new RuntimeException("No data!")));
        return postRepository.save(post);
}

   public void like(int postId) throws Exception {
       if (likeRepository.findByUserAndPost(postId,this.userId)!=null)
           throw new Exception("you already liked this post");
       else {
           Like like=new Like();
           like.setUser(userRepository.findById(this.userId).orElseThrow(() -> new RuntimeException("No data!")));
           like.setPost(postRepository.findById(postId).orElseThrow(() -> new RuntimeException("No data!")));
           likeRepository.save(like);
       }
   }
    public void removeLike(int postId) throws Exception {
        if (likeRepository.findByUserAndPost(postId,this.userId)==null)
            throw new Exception("you didn't liked this post");
        else
            likeRepository.deleteUserLike(postId,this.userId);
    }

    public Comment comment(int postId,String text) throws Exception {
        List<Comment> comments = commentRepository.findByUserAndPost(postId, this.userId);
        if (comments.size() >= 3)
            throw new Exception("you commented too much on that post");
        else {
            Comment comment = new Comment();
            Date date = new Date();
            comment.setDate(date);
            comment.setText(text);
            comment.setUser(userRepository.findById(this.userId).orElseThrow(() -> new RuntimeException("No data!")));
            comment.setPost(postRepository.findById(postId).orElseThrow(() -> new RuntimeException("No data!")));
            return commentRepository.save(comment);
        }
    }
        public void removeComment(int postId,String text) throws Exception {
            if (commentRepository.findByTextAndPost(postId,text)==null)
                throw new Exception("comment don't exists");
            else if(commentRepository.findByTextAndPost(postId,text).getUser().getId()!=this.userId
                    &&postRepository.findById(postId).orElseThrow(() -> new RuntimeException("No data!")).getUser().getId()!=this.userId)
                throw new Exception("this comment is not yours,no right to delete");
            else
                commentRepository.deleteUserComment(postId,text);

    }
    public void updateUser(User user) throws Exception {
        if(user.getId()!=this.userId)
            throw new Exception("you cant update other user details");
        User user1=userRepository.findById(user.getId()).orElseThrow(() -> new RuntimeException("No data!"));
        if(user1!=null){
             user1.setFirst_name(user.getFirst_name());
             user1.setLast_name(user.getLast_name());
             user1.setPassword(user.getPassword());
             userRepository.save(user1);
         }
        else
           throw new Exception("update failed");
    }
    public void updatePost(Post post) throws Exception {
        if(!postRepository.existsById(post.getId()) )
            throw new Exception("update failed");
        else if(postRepository.findById(post.getId()).orElseThrow(() -> new RuntimeException("No data!")).getUser().getId()!=this.userId)
            throw new Exception("you cant update other users posts ");
        else
        postRepository.save(post);
    }
    public User userDetails(){
        return userRepository.findById(this.userId).orElseThrow(() -> new RuntimeException("No data!"));
    }
    public List<Post> userPosts(){
        return postRepository.findByUser(this.userId);
    }
    public List<Post> allPosts(){
        return postRepository.findAll();
    }









}





