package com.alchemist.yoru.repo;

import com.alchemist.yoru.entity.ProbabilityString;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProbabilityArrayRepository extends JpaRepository<ProbabilityString,Long> {

    Optional<ProbabilityString> findByTenantId(String tenantId);
}
