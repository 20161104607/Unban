package com.lly.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.lly.entity.User;

public interface UserDao {

	@Select("select role from user where username=#{username} and password=#{password}")
	Integer login(@Param("username") String username, @Param("password") String password);

	@Select("select * from user where username=#{username} and password=#{password}")
	User login_user(@Param("username") String username, @Param("password") String password);

	@Select("select * from user where id = #{id}")
	User findByid(@Param("id")Integer id);

	@Update("update user set password = #{password} where id = #{id}")
	void updatePassword(@Param("password") String password, @Param("id") Integer id);

	@Insert("insert into user values(#{id},#{username},#{password},#{role},#{lasttime},#{address})")
	void addUser(User u);

	@Update("update user set lasttime = #{lasttime} where id = #{id}")
	void updateLasttime(@Param("lasttime") String lasttime, @Param("id") Integer id);
		
	@Select("select * from user order by id desc")
	List<User> findOreder();
	
	
}
