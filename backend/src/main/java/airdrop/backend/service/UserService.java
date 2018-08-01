package airdrop.backend.service;


import airdrop.backend.entity.User;

import javax.jws.soap.SOAPBinding;

public interface UserService {
    User findUserByName(String name);

    User save(User user);
}
