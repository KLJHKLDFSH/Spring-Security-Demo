package com.example.demo.common;

import com.example.demo.model.LoginUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 模拟下数据库
 */
public class UserList {

    public static ConcurrentHashMap<String,UserDetails> database = new ConcurrentHashMap<>();

    static {
        List<String> role_admin = new ArrayList<>();
        role_admin.add("ADMIN");
        role_admin.add("USER");
        UserDetails hxb = new LoginUser("hxb","111","豪小宝",role_admin);
        database.put("hxb",hxb);

        List<String> role_user = new ArrayList<>();
        role_user.add("USER");
        UserDetails ng = new LoginUser("ng","222","暖宝宝",role_user);
        database.put("ng",ng);
    }

    public static UserDetails getUserByUserName(String userName){
        return database.get(userName);
    }
}
