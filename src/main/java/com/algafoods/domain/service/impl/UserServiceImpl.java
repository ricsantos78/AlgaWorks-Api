package com.algafoods.domain.service.impl;

import com.algafoods.domain.exception.BusinessException;
import com.algafoods.domain.model.UserModel;
import com.algafoods.domain.service.UserService;
import com.algafoods.infra.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserModel> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<UserModel> findByCdUser(Long cdUser) {
        return userRepository.findByCdUser(cdUser);
    }

    @Override
    public UserModel save(UserModel userModel) {
        var optionalUserModel = findByEmail(userModel.getDsEmail());

        if(optionalUserModel.isPresent() && !optionalUserModel.get().equals(userModel)){
            throw new BusinessException
                    (String.format("Já existe um usuario cadastrado com o e-mail %s", userModel.getDsEmail()));
        }
        if (userModel.getCdUser() == null){
            userModel.setCdUser(findMaxCdUser());
        }
        return userRepository.save(userModel);
    }

    @Override
    public void delete(UserModel userModel) {
        userRepository.delete(userModel);
    }

    @Override
    public Optional<UserModel> findByEmail(String email) {
        return userRepository.findByDsEmail(email);
    }

    public Long findMaxCdUser(){
        var maxCdUser = userRepository.findMaxCdUser();
        return maxCdUser != null ? maxCdUser + 1 : 1;
    }
}
