package com.zorvyn.finance.backend.data.accessor;

import com.zorvyn.finance.backend.data.constants.RoleType;
import com.zorvyn.finance.backend.service.model.common.User;

import java.util.List;
import java.util.Optional;

public interface IUserDataAccessor {

    void createUser(User user);

    Optional<User> findUserById(Long id);

    Optional<User> findUserByEmail(String email);

    List<User> findUsers(String email, List<RoleType> roles, int pageSize, Long lastAccessedKey);

    void updateUser(User user);

    void deleteUser(Long id);

    boolean existsByEmail(String email);

}