package com.lms.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lms.app.entity.AppUsers;
import com.lms.app.repository.AppUsersRepository;
import com.lms.app.service.IAppUsersService;
import com.lms.app.to.AppUsersTo;
import com.lms.app.utils.DozerUtils;
import com.lms.app.value.IncorrectEmailIdOrPasswordException;
import com.lms.app.value.UserNotFoundException;

@Service
public class AppUsersServiceImpl implements IAppUsersService {

	private AppUsersRepository appUsersRepository;
	private DozerUtils dozerutils;

	@Autowired
	public AppUsersServiceImpl(AppUsersRepository appUsersRepository, DozerUtils dozerUtils) {
		this.appUsersRepository = appUsersRepository;
		this.dozerutils = dozerUtils;

	}

	/**
	 *Method takes Email Id and Password for Login Authentication.
	 *@param email User Email ID
	 *@param password User Password
	 *@return {@link AppUsersTo}
	 */
	@Override
	public AppUsersTo login(String email, String password) {
		AppUsers appUsers = appUsersRepository.getUserLoginByEmailAndPassword(email, password);
		if (appUsers != null) {
			return dozerutils.convert(appUsers, AppUsersTo.class);
		}
		throw new UserNotFoundException();
	}

	/**
	 * Method takes current password and email as input and sets 
	 * new password if the given arguments matches.
	 * @param currentPassword AppUser Current Password
	 * @param email AppUser Email ID
	 * @param newPassword New Password
	 * @return {@link AppUsersTo}
	 */
	@Override
	public AppUsersTo resetPassword(String currentPassword, String newPassword, String email) {
		AppUsers appUsers = appUsersRepository.findByPasswordAndEmail(currentPassword, email);
		if (appUsers != null && currentPassword.equals(appUsers.getPassword())
				&& email.equals(appUsers.getEmail())) {
			appUsers.setPassword(newPassword);
			appUsersRepository.save(appUsers);
			AppUsers findByPasswordAndEmail = appUsersRepository.findByPasswordAndEmail(appUsers.getPassword(),
					appUsers.getEmail());
			return dozerutils.convert(findByPasswordAndEmail, AppUsersTo.class);
		}
		throw new IncorrectEmailIdOrPasswordException();
	}
}
