package com.anjoy.frozen.seller.dao;

import com.anjoy.frozen.seller.entity.Users;
/**
 * UserDAO
 * @author ldh
 */
public interface UsersDao {

	/**
	 * 查询用户对象根据Uid
	 * @param users
	 */
	public Users getUsersById(Long userId);
	
	/**
	 * 根据账号获取users对象
	 * @param account
	 */
	public Users findUserByAccount(String account);
}