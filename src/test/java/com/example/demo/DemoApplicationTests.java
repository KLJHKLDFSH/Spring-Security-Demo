package com.example.demo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.db.mapper.NameMapper;
import com.example.demo.db.mapper.UserInfoMapper;
import com.example.demo.db.mapper.UserMapper;
import com.example.demo.db.model.User;
import com.example.demo.db.model.Name;
import com.example.demo.db.model.UserInfo;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Random;
import java.util.UUID;

@SpringBootTest
class DemoApplicationTests {

	private static final Logger log = LoggerFactory.getLogger(DemoApplicationTests.class);
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserInfoMapper userInfoMapper;
	@Autowired
	private NameMapper nameMapper;
	@Autowired
	private PlatformTransactionManager transactionManager;
	@Test
	public void testSelect() {
		System.out.println(("----- selectAll method test ------"));
		Page<User> userPage = userMapper.selectByName(new Page<User>(1,1),"Billie");
		userPage.getRecords().stream().forEach(user ->  System.out.println(user.toString()));

		userMapper.selectPage(new Page<>(0,1), null)
				.getRecords().forEach(user-> System.out.println(user.toString()) );

	}

	@Test
	public void test(){
		DefaultTransactionDefinition a = new DefaultTransactionDefinition();
		a.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus aStatus = transactionManager.getTransaction(a);
		String userId = UUID.randomUUID().toString().substring(1,10);
		UserInfo user = new UserInfo(userId,"userName");
		userInfoMapper.insert(user);
		transactionManager.commit(aStatus);
		DefaultTransactionDefinition b = new DefaultTransactionDefinition();
		b.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		TransactionStatus bStatus = transactionManager.getTransaction(b);
		try {
			if (bStatus.isNewTransaction()){
				int nameId = new Random().nextInt();
				Name name = new Name(nameId, "a");
				nameMapper.insert(name);
				nameMapper.insert(new Name(new Random().nextInt(),"b"));
				int g = 1/0;
				transactionManager.commit(bStatus);
			}else {
				log.error("不是一个新的事务");
			}
		}catch (Exception e){
			transactionManager.rollback(bStatus);
		}
	}
}
