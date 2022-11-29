package InstagramProject.InstagramProject.beans;

import InstagramProject.InstagramProject.facades.UserFacade;

public class Facades {
    private UserFacade facade;
    private long lastActive;

    public Facades(UserFacade facade, long lastActive) {
        this.facade = facade;
        this.lastActive = lastActive;
    }

    public UserFacade getFacade() {
        return facade;
    }

    public void setFacade(UserFacade facade) {
        this.facade = facade;
    }

    public long getLastActive() {
        return lastActive;
    }

    public void setLastActive(long lastActive) {
        this.lastActive = lastActive;
    }
}


