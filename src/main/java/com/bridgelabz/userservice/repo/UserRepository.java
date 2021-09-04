package com.bridgelabz.userservice.repo;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.userservice.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByEmailId(String emailId);
	
	@Transactional
	@Modifying
	@Query(value = "update user u set u.password = ?1 where u.id = ?2",nativeQuery = true)
	public int updatePassword(String password,Long userId);


}
