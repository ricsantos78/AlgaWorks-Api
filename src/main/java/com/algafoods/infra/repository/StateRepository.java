package com.algafoods.infra.repository;

import com.algafoods.domain.model.StateModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StateRepository extends JpaRepository<StateModel, UUID> {


    Optional<StateModel> findByCdState(Long cdState);

    @Query("select max(s.cdState) from StateModel s")
    Long findMaxCdState();
}
