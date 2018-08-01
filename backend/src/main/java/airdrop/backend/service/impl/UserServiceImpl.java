package airdrop.backend.service.impl;

import airdrop.backend.Repository.UserRepository;
import airdrop.backend.entity.User;
import airdrop.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User findUserByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}
