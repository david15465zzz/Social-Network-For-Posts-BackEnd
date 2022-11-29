package InstagramProject.InstagramProject.controllers;

import InstagramProject.InstagramProject.beans.Facades;
import InstagramProject.InstagramProject.beans.Post;
import InstagramProject.InstagramProject.beans.User;
import InstagramProject.InstagramProject.facades.UserFacade;
import com.auth0.jwt.JWT;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin
public class UserController {

    private HttpServletRequest request;

    private HashMap<Long, Facades> facades;

    public UserController(HttpServletRequest request, HashMap<Long, Facades> facades) {
        this.request = request;
        this.facades = facades;
    }

    @PostMapping(path = "/uploadPost")
    public ResponseEntity<?> uploadPost(@RequestBody Post post) throws Exception {
        return ResponseEntity.ok(userFacade().uploadPost(post));
    }

    @PostMapping(path = "/like/{id}")
    public ResponseEntity<?> like(@PathVariable int id) throws Exception {
        userFacade().like(id);
        return ResponseEntity.ok("post liked");
    }
    @DeleteMapping(path = "/removeLike/{id}")
    public ResponseEntity<?> removeLike(@PathVariable int id) throws Exception {
        userFacade().removeLike(id);
        return ResponseEntity.ok("like removed");
    }
    @PostMapping(path = "/comment/{postId}/{text}")
    public ResponseEntity<?>  comment(@PathVariable int postId,@PathVariable String text) throws Exception {
        return ResponseEntity.ok(userFacade().comment(postId,text));
    }
    @DeleteMapping(path = "/removeComment/{postId}/{text}")
    public ResponseEntity<?>  removeComment(@PathVariable int postId,@PathVariable String text) throws Exception {
        userFacade().removeComment(postId,text);
        return ResponseEntity.ok("comment removed");
    }
    @PutMapping(path = "/updateUser")
    public ResponseEntity<?>   updateUser(@RequestBody User user) throws Exception {
        userFacade().updateUser(user);
        return ResponseEntity.ok("user updated");
    }

    @PutMapping(path = "/updatePost")
    public ResponseEntity<?>    updatePost(@RequestBody Post post) throws Exception {
        userFacade().updatePost(post);
        return ResponseEntity.ok("post updated");
    }

    @GetMapping(path = "/userDetails")
    public User getUserDetails(){
        return  userFacade().userDetails();
    }
    @GetMapping(path = "/allPosts")
    public List<Post> getAllPosts(){
        return  userFacade().allPosts();
    }
    @GetMapping(path = "/userPosts")
    public List<Post> getMyPosts(){
        return  userFacade().userPosts();
    }




    public UserFacade userFacade() {
        String token = request.getHeader("authorization").replace("Bearer ", "");
        long id = JWT.decode(token).getClaim("id").asLong();
        Facades facade = facades.get(id);
        if (facade != null) {
            facade.setLastActive(System.currentTimeMillis());
            UserFacade userFacade = (UserFacade) facade.getFacade();
            return userFacade;
        }
        return null;
    }
}
