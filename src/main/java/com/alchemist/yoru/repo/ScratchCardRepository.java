package com.alchemist.yoru.repo;

import com.alchemist.yoru.entity.OrderItem;
import com.alchemist.yoru.entity.ScratchCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScratchCardRepository extends JpaRepository<ScratchCard,Long> {

    Optional<ScratchCard> findByCodeAndTenantId(long code,String tenantId);

    List<ScratchCard> findByTenantId(String id);

    Optional<ScratchCard> findByIdAndTenantId(long id, String tenantId);

}
