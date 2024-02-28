package xyz.blushyes.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import xyz.blushyes.po.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}