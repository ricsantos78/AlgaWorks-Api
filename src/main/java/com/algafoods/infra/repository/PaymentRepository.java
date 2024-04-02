package com.algafoods.infra.repository;

import com.algafoods.domain.model.PaymentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentModel, UUID> {


    //@Query("SELECT p FROM PaymentModel p WHERE p.cdPayment = :cdPayment")
    Optional<PaymentModel> findByCdPayment(Long cdPayment);

    @Query("select max(p.cdPayment) from PaymentModel p")
    Long findMaxCdPayment();
}
