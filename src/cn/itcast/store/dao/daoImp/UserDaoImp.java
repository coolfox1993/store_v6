package cn.itcast.store.dao.daoImp;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import cn.itcast.store.dao.UserDao;
import cn.itcast.store.domain.User;
import cn.itcast.store.utils.JDBCUtils;

public class UserDaoImp implements UserDao {

	public void userRgsit(User user) throws SQLException {
		String sql = "INSERT INTO `user` VALUES(?,?,?,?,?,?,?,?,?,?)";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		queryRunner.update(sql, user.getUid(),user.getUsername(),user.getPassword(),user.getName(),user.getEmail(),user.getTelephone(),user.getBirthday(),user.getSex(),user.getState(),user.getCode());
		
		
	}

	@Override
	public User userActive(String code) throws SQLException {
		String sql = "SELECT * FROM `user` WHERE `code` = ?";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		User user = queryRunner.query(sql, new BeanHandler<>(User.class),code);
		return user;
	}

	@Override
	public void updateUser(User user) throws SQLException {
		// 用户激活状态更新
		String sql = "update user set username=?, password=?, name=?,email=?,telephone=?,birthday=?,sex=?,state=?,code=? where uid=?";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		Object[] params = {user.getUsername(),user.getPassword(),user.getName(),user.getEmail(),user.getTelephone(),user.getBirthday(),user.getSex(),user.getState(),user.getCode(),user.getUid()};
		queryRunner.update(sql, params);
	}

	@Override
	public User userLogin(User user) throws SQLException {
		String sql = "SELECT * FROM `user` WHERE `username` = ? and password=?";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		return queryRunner.query(sql, new BeanHandler<User>(User.class), user.getUsername(),user.getPassword());
	}

}
