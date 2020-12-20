package cn.itcast.store.service.serciceImp;

import java.sql.SQLException;

import cn.itcast.store.dao.UserDao;
import cn.itcast.store.dao.daoImp.UserDaoImp;
import cn.itcast.store.domain.User;
import cn.itcast.store.service.UserService;

public class UserServiceImp implements UserService {

	public void userRegist(User user) throws SQLException {
		// 实现注册
		UserDao userDao = new UserDaoImp();
		userDao.userRgsit(user);
		
	}

}
