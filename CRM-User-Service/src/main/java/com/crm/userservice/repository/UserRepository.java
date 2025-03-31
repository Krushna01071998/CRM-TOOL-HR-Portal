package com.crm.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crm.userservice.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	public User findByUserEmail(String email);

	public User findByUserName(String userName);

	public User findByNumber(String number);
}
