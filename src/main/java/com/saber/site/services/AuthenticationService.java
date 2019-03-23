package com.saber.site.services;

import com.saber.site.model.UserPrincipal;

public interface AuthenticationService {
    UserPrincipal authenticate(String username,String password);
    void saveUser(UserPrincipal userPrincipal);
}
