package com.example.comeonplayerserviceassignment.utils;

import com.example.comeonplayerserviceassignment.palyer.PlayerEntity;
import com.example.comeonplayerserviceassignment.palyer.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PlayerDetailsService implements UserDetailsService
{
    @Autowired
    PlayerRepository playerRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
    {
        PlayerEntity player = playerRepository.findByEmail(email).orElse(null);
        if (player == null) {
            throw new UsernameNotFoundException("Player not found");
        }
        return new PlayerPrincipal(player);
    }
}
