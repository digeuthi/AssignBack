package com.example.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.board.domain.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer>{

    public boolean existsByUserEmail(String email);
    public boolean existsByUserName(String name);

    public UserEntity findByUserEmail(String email);
    public UserEntity findByUserCode(int userCode);
    
}
