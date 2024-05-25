package com.example.foody_android.Model;
import java.io.Serializable;

public class User implements Serializable {

    private LoginResult loginResult;

    private Object userProfile;

    public LoginResult getLoginResult(){
        return loginResult;
    }

    public void setLoginResult(LoginResult loginResult) {
        this.loginResult = loginResult;
    }

    public Object getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(Object userProfile) {
        this.userProfile = userProfile;
    }


}
