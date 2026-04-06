package com.zorvyn.finance.backend.data.repository;

import com.zorvyn.finance.backend.data.constants.RoleType;
import com.zorvyn.finance.backend.data.constants.UserStatus;
import com.zorvyn.finance.backend.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailAndStatus(String email, UserStatus status);

    boolean existsByEmail(String email);

    @Query(value = """
        SELECT *
        FROM users u
        WHERE u.status = 'ACTIVE'
          AND (:email IS NULL OR u.email = :email)
          AND (:lastKey IS NULL OR u.id > :lastKey)
          AND (COALESCE(:roles) IS NULL OR u.role_type = ANY(:roles))
        ORDER BY u.id ASC
        LIMIT :pageSize
    """, nativeQuery = true)
    List<User> findWithFilters(
            @Param("email") String email,
            @Param("roles") List<RoleType> roles,
            @Param("pageSize") int pageSize,
            @Param("lastKey") Long lastAccessedKey
    );
}