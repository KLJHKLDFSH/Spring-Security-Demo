package com.example.demo;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.db.mapper.UserMapper;
import com.example.demo.db.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private UserMapper userMapper;

	@Test
	public void testSelect() {
		System.out.println(("----- selectAll method test ------"));
		Page<User> userPage = userMapper.selectByName(new Page<User>(1,1),"Billie");
		userPage.getRecords().stream().forEach(user ->  System.out.println(user.toString()));

		userMapper.selectPage(new Page<>(0,1), null)
				.getRecords().forEach(user-> System.out.println(user.toString()) );

	}

}
