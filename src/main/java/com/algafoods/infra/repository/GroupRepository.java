package com.algafoods.infra.repository;

import com.algafoods.domain.model.GroupModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GroupRepository extends JpaRepository<GroupModel, UUID> {


    //@Query("SELECT g FROM GroupModel g WHERE g.cdGroup = :cdGroup")
    Optional<GroupModel> findByCdGroup(Long cdGroup);

    @Query("SELECT MAX(g.cdGroup) from GroupModel g")
    Long findMaxCdGroup();
}
