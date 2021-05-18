package com.lms.app.service;

import com.lms.app.to.AppUsersTo;

public interface IAppUsersService {
	public AppUsersTo login(String email, String password);
	public AppUsersTo resetPassword(String currentPassword, String newPassword,String email);
}
