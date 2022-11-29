package InstagramProject.InstagramProject.auth;

import InstagramProject.InstagramProject.beans.Credentials;
import InstagramProject.InstagramProject.beans.User;
import InstagramProject.InstagramProject.facades.UserFacade;
 import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;

@Service
public class AuthManager{

    private static ApplicationContext applicationContext;


    public AuthManager(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public UserFacade login(Credentials credentials) throws LoginException {

                UserFacade userFacade = applicationContext.getBean(UserFacade.class);
                if(!userFacade.login(credentials.getEmail(), credentials.getPassword()))
                    throw new LoginException();
                return userFacade;

        }

    public UserFacade register(User user) throws Exception {

        UserFacade userFacade = applicationContext.getBean(UserFacade.class);
        if(!userFacade.register(user))
            throw new Exception("user already exists");
        return userFacade;

    }

    }

