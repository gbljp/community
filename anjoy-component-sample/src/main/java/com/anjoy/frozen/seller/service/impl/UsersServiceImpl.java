package com.anjoy.frozen.seller.service.impl;

import com.anjoy.frozen.seller.dao.UsersDao;
import com.anjoy.frozen.seller.entity.Users;
import com.anjoy.frozen.seller.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 用户服务
 * 
 * @author wangfucai
 */
@Service
public class UsersServiceImpl implements UsersService {

	@Autowired
	private UsersDao usersDao;

	@Override
	public Users getUsersById(Long userId) {
		return usersDao.getUsersById(userId);
	}
	@Override
	public Users findUserByAccount(String account) {
		return usersDao.findUserByAccount(account);
	}
}