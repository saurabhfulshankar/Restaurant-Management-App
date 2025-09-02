package com.alchemist.yoru.repo;

import com.alchemist.yoru.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
*
* @author Atul Mundaware
* @since 17 04 2023
*/

@Repository
public interface UserDetailRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String name);
    Optional<User> findByEmail(String email);

    User findByConfirmationToken(String confirmationToken);

    User findByToken(String token);
}
