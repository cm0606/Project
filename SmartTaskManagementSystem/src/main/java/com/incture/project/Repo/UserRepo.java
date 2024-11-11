package com.incture.project.Repo;

import org.springframework.stereotype.Repository;
import com.incture.project.Entity.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface UserRepo extends JpaRepository<User,Long>{

	User findByUserName(String userName);
	@Query("SELECT u FROM User u WHERE u.userName = ?1 AND u.password = ?2")
	Optional<User> findOneByUserNameAndPassword(String userName, String password);
	


}
