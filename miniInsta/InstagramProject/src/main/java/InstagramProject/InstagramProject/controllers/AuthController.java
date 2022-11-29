package InstagramProject.InstagramProject.controllers;

import InstagramProject.InstagramProject.auth.AuthManager;
import InstagramProject.InstagramProject.beans.Credentials;
import InstagramProject.InstagramProject.beans.Facades;
import InstagramProject.InstagramProject.beans.User;
import InstagramProject.InstagramProject.facades.UserFacade;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.LoginException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

@RestController
@CrossOrigin
public class AuthController {

    private AuthManager authManager;
    private HashMap<Long, Facades> facades;


    public AuthController(AuthManager authManager, HashMap<Long, Facades> facades) {
        this.authManager = authManager;
        this.facades = facades;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Credentials credentials) throws LoginException {
        UserFacade facade;
        facade = authManager.login(credentials);
        long id = facade.getUserId();
        String token = createToken(facade.userDetails());
        facades.put(id, new Facades(facade, System.currentTimeMillis()));
        return ResponseEntity.ok(token);

    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) throws Exception {
        UserFacade facade;
        facade = authManager.register(user);
        long id = facade.getUserId();
        String token = createToken(facade.userDetails());
        facades.put(id, new Facades(facade, System.currentTimeMillis()));
        return ResponseEntity.ok(token);



    }

    protected String createToken(User user){
        Calendar expiresAt = Calendar.getInstance();
        expiresAt.add(Calendar.MINUTE, 30);
        Date expires = expiresAt.getTime();
        String token = JWT.create()
                .withIssuer("DavidTech")
                .withIssuedAt(new Date())
                .withClaim("id", user.getId())
                .withClaim("first_mame",  user.getFirst_name())
                .withClaim("last_name", user.getLast_name())
                .withClaim("email", user.getEmail())
                .withClaim("password", user.getPassword())
                .withExpiresAt(expires)
                .sign(Algorithm.HMAC256("topSecret"));
        return token;
    }
}









