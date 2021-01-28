package com.example.demo.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.db.model.User;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {

    Page<User> selectByName(IPage<User> page , String name);
}
