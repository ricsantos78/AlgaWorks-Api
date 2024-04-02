package com.algafoods.domain.service;

import com.algafoods.domain.model.UserModel;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserModel> findAll();
    Optional<UserModel> findByCdUser(Long cdUser);
    UserModel save(UserModel userModel);
    void delete(UserModel userModel);

    Optional<UserModel>findByEmail(String email);

}
