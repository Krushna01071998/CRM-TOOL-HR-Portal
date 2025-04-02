package com.crm.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crm.userservice.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	public boolean existsByUserEmail(String email);

	public boolean existsByUserName(String userName);

	public boolean existsByNumber(String number);

	public  User findByUserName(String username);
}
