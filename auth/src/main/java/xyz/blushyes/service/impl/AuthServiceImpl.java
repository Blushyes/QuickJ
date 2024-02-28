package xyz.blushyes.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.http.HttpStatus;
import lombok.RequiredArgsConstructor;
import xyz.blushyes.service.AuthService;
import xyz.blushyes.service.UserService;
import xyz.blushyes.exception.BaseException;
import xyz.blushyes.po.User;
import xyz.blushyes.request.RegisterRequest;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserService userService;

    @Override
    public String login(String username, String password) {
        var user = userService.lambdaQuery()
                .eq(User::getUsername, username)
                .oneOpt()
                .orElseThrow(() -> new BaseException("用户名或密码错误！", HttpStatus.HTTP_FORBIDDEN));
        if (!password.equals(user.getPassword())) {
            throw new BaseException("用户名或密码错误！", HttpStatus.HTTP_FORBIDDEN);
        }
        StpUtil.login(user.getId());
        return StpUtil.getTokenValue();
    }

    @Override
    @Transactional(rollbackFor = BaseException.class)
    public void register(RegisterRequest request) {
        if (userService.lambdaQuery().eq(User::getUsername, request.getUsername()).exists()) {
            throw new BaseException("用户名已存在！");
        }
        var user = new User(request.getUsername(), request.getPassword());
        userService.save(user);
    }

    @Override
    public boolean checkState() {
        return StpUtil.isLogin();
    }
}
