package com.zorvyn.finance.backend.data.accessor.impl;

import com.zorvyn.finance.backend.data.accessor.IUserDataAccessor;
import com.zorvyn.finance.backend.data.accessor.exception.DataAccessException;
import com.zorvyn.finance.backend.data.accessor.transformer.ServiceToDataModelTransformer;
import com.zorvyn.finance.backend.data.constants.RoleType;
import com.zorvyn.finance.backend.data.constants.UserStatus;
import com.zorvyn.finance.backend.data.repository.UserRepository;
import com.zorvyn.finance.backend.service.model.common.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class UserDataSqlAccessor implements IUserDataAccessor {

    private final UserRepository userRepository;
    private final ServiceToDataModelTransformer transformer;

    @Override
    public void createUser(User user) {
        try {
            userRepository.save(transformer.toUserEntity(user));
        } catch (Exception e) {
            throw new DataAccessException("Failed to create user: " + e.getMessage());
        }
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id)
                .filter(u -> u.getStatus() == UserStatus.ACTIVE)
                .map(transformer::toServiceUser);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmailAndStatus(email, UserStatus.ACTIVE)
                .map(transformer::toServiceUser);
    }

    @Override
    public List<User> findUsers(String email, List<RoleType> roles, int pageSize, Long lastKey) {
        return userRepository.findWithFilters(email, roles, pageSize, lastKey)
                .stream().map(transformer::toServiceUser).toList();
    }

    @Override
    public void updateUser(User user) {
        var existing = userRepository.findById(user.getId())
                .orElseThrow(() -> new DataAccessException("User not found: " + user.getId()));
        existing.setFirstName(user.getFirstName());
        existing.setLastName(user.getLastName());
        existing.setPassword(user.getPassword());
        existing.setRoleType(user.getRoleType());
        existing.setStatus(user.getStatus());
        existing.setUpdatedByUserId(user.getId());
        userRepository.save(existing);
    }

    @Override
    public void deleteUser(Long id) {
        var existing = userRepository.findById(id)
                .orElseThrow(() -> new DataAccessException("User not found: " + id));
        existing.setStatus(UserStatus.INACTIVE);
        userRepository.save(existing);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}