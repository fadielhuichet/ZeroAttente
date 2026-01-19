package com.fedicode.getticket.security;

import com.fedicode.getticket.repository.AdminRepository;
import com.fedicode.getticket.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private ClientRepository clientRepository;
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return adminRepository.findByEmail(login)
                .<UserDetails>map(a -> User.builder()
                        .username(a.getEmail())          // use email as principal
                        .password(a.getPasswordHash())   // BCrypt hash
                        .roles("ADMIN")
                        .build())
                .orElseGet(()-> clientRepository.findByUsername(login)
                        .map(c->User.builder()
                                .username(c.getUsername())
                                .password(c.getPasswordHash())
                                .roles("CLIENT")
                                .build())
                        .orElseThrow(()->new UsernameNotFoundException("User Not Found")));


    }

}
