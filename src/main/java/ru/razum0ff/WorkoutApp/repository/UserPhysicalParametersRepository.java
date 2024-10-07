package ru.razum0ff.WorkoutApp.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import ru.razum0ff.WorkoutApp.entity.UserEntity;
import ru.razum0ff.WorkoutApp.entity.UserPhysicalParameters;

import java.util.UUID;

@Repository
public interface UserPhysicalParametersRepository extends JpaRepository<UserPhysicalParameters, UUID> {
//    @Modifying
//    @Transactional
//    void deleteByUser(UserEntity user);
}
