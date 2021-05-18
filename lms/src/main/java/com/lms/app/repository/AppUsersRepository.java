package com.lms.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.lms.app.entity.AppUsers;

@Repository
public interface AppUsersRepository extends JpaRepository<AppUsers, Long> {
	/**
	 * Authenticates AppUser By Email ID And Password
	 * @param email User Email ID
	 * @param password User Password
	 * @return User Details {@link AppUsers}
	 */
	@Query(value="select * from appusers where email=:email and password=:password and status='ACTIVE'",nativeQuery=true)
	public AppUsers getUserLoginByEmailAndPassword(@Param("email")String email, @Param("password")String password);

	/**
	 * Finds AppUser By Password And Email ID
	 * @param password User Password
	 * @param email    User Email ID
	 * @return AppUser Details {@link AppUsers}
	 */
	public AppUsers findByPasswordAndEmail(String password, String email);

	/**
	 * Finds AppUser By Id.
	 * @return {@link AppUsers}
	 */
	public AppUsers findAppUsersById(Long id);

}
