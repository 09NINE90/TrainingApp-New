package ru.razum0ff.WorkoutApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.razum0ff.WorkoutApp.entity.UserEntity;

import java.util.Set;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    UserEntity findByUsername(String username);
    Set<UserEntity> findByRoles(String role);

    Set<UserEntity> findByTrainerId(UUID id);
}
