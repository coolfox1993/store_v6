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

	@Override
	public boolean userActive(String code) throws SQLException {
		//用户激活
		UserDao userDao = new UserDaoImp();
		User user=userDao.userActive(code);
		if (user!=null) {
			user.setCode(null);
			user.setState(1);
			// 对数据执行一次更新操作
			// update user set username=?, password=?, name=?,email=?,telephone=?,birthday=?,sex=?,code=?state=?;
			userDao.updateUser(user);
			
			return true;
		}else {

			return false;
		}
		
	}

	@Override
	public User userLogin(User user) throws SQLException {
		UserDao userDao = new UserDaoImp();
		User uu = userDao.userLogin(user);
		if (null==uu) {
			throw new RuntimeException("密码有误! ");
		}else if(uu.getState()!=1){
			throw new RuntimeException("用户未激活! ");
		}else {
			return uu;
		}
	}

}
