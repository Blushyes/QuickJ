package xyz.blushyes.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import xyz.blushyes.mapper.UserMapper;
import xyz.blushyes.service.UserService;
import xyz.blushyes.po.User;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}