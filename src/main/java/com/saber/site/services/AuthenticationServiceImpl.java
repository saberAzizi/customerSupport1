package com.saber.site.services;

import com.saber.site.model.UserPrincipal;
import com.saber.site.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private UserRepository  userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserPrincipal authenticate(String username, String password) {
        UserPrincipal userPrincipal = this.userRepository.findByUsername(username);
        if (userPrincipal==null){
            return null;
        }
        if (!userPrincipal.getPassword().equals(password)){
            return null;
        }
        return userPrincipal;
    }

    @Override
    @Transactional
    public void saveUser(UserPrincipal userPrincipal) {
        this.userRepository.save(userPrincipal);
    }
}
