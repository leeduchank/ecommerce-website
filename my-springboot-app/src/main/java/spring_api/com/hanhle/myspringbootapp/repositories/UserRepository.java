package spring_api.com.hanhle.myspringbootapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import spring_api.com.hanhle.myspringbootapp.entities.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
    boolean existsByUsername(String username) ;
}
