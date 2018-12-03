package com.anjoy.cloud.component.service;


import com.anjoy.cloud.component.entity.Users;

public interface UsersService {

	/**
	 * 根据用户的ID获取用户对象
	 * @param userId
	 * @return
	 */
	public Users getUsersById(Long userId);
	
	/**
	 * 根据用户的account获取用户对象
	 * @param account
	 * @return
	 */
	public Users findUserByAccount(String account);
}
