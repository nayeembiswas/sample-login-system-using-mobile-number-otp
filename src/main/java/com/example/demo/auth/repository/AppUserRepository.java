/**
 * 
 */
package com.example.demo.auth.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.AppUser;


/**
 * @Project   ibcs-bof-erp
 * @Author    Md. Nayeemul Islam - 596
 * @Since     Jun 9, 2021
 * @version   1.0.0
 */
public interface AppUserRepository extends JpaRepository<AppUser, Integer>{
	Optional<AppUser> findByUsername(String username);
	List<AppUser> findByActive(boolean active);
//	Boolean existsByUsername(String username);
//	Boolean existsByEmail(String email);
	List<AppUser> findByEmployeeCodeAndActive(String code, boolean active);
}
