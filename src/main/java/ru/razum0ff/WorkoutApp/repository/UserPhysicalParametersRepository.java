package ru.razum0ff.WorkoutApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.razum0ff.WorkoutApp.entity.UserEntity;
import ru.razum0ff.WorkoutApp.entity.UserPhysicalParametersEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserPhysicalParametersRepository extends JpaRepository<UserPhysicalParametersEntity, UUID> {
//    @Modifying
//    @Transactional
//    void deleteByUser(UserEntity user);

    List<UserPhysicalParametersEntity> findAllByUserOrderByDateDesc(UserEntity user);
}
