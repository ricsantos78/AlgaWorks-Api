package com.algafoods.infra.repository;

import com.algafoods.domain.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {
    Optional<UserModel>findByDsEmail(String email);

    Optional<UserModel> findByCdUser(Long cdUser);

    @Query("select max(u.cdUser) from UserModel u")
    Long findMaxCdUser();
}
