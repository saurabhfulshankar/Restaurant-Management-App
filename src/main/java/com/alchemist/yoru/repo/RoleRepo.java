package com.alchemist.yoru.repo;

import com.alchemist.yoru.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.Column;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {

	public Role findByName(String roleName);

}