package com.josh.security.services;

import com.josh.security.dao.UserRepository;
import com.josh.security.entity.UserCredentials;
import com.josh.security.services.impl.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserCredentials user=userRepository.findByUsername( username);
        if(user==null)
            throw new UsernameNotFoundException("User Not Match in database.");
        return new UserPrinciple(user);
    }


    public void addUser(UserCredentials userCredentials){
        this.userRepository.save(userCredentials);
    }
}
