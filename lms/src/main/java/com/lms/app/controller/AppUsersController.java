package com.lms.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lms.app.service.IAppUsersService;
import com.lms.app.to.AppUsersTo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/v2")
@Api(value = "LMS App User Login And Password Update APIs")
public class AppUsersController {
	private IAppUsersService iAppUsersService;

	@Autowired
	public AppUsersController(IAppUsersService iAppUsersService) {
		this.iAppUsersService = iAppUsersService;
	}

	@GetMapping("/login")
	@ApiOperation(value = "loginLms", notes = "API to login to LMS.")
	public ResponseEntity<?> login(@ApiParam(value = "Email Id", required = true) @RequestHeader("email") String email,
			@ApiParam(value = "Password", required = true) @RequestHeader("password") String password) {
		return ResponseEntity.ok(iAppUsersService.login(email, password));

	}

	@PutMapping("/reset/password")
	@ApiOperation(value = "updateAppUserPassword", notes = "API to update App User Password.")
	public ResponseEntity<?> resetPassword(
			@ApiParam(value = "Current Password", required = true) @RequestHeader("currentPassword") String currentPassword,
			@ApiParam(value = "New Password", required = true) @RequestHeader("newPassword") String newPassword,
			@ApiParam(value = "Email Id", required = true) @RequestHeader("email") String email) {
		AppUsersTo appUsersTo = iAppUsersService.resetPassword(currentPassword, newPassword, email);
		return ResponseEntity.ok(appUsersTo);
	}
}
