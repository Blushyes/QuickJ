package xyz.blushyes.exception;


import org.springframework.core.env.Environment;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.dev33.satoken.exception.NotLoginException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import xyz.blushyes.common.R;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ErrorControllerAdvice {
    private final Environment environment;

    @ExceptionHandler(BaseException.class)
    @ResponseBody
    public R<String> baseException(Exception e) {
        return R.fail(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public R<String> notValid(MethodArgumentNotValidException e) {
        StringBuilder msg = new StringBuilder("参数校验失败：");
        BindingResult bindingResult = e.getBindingResult();
        bindingResult.getFieldErrors().forEach(
                fieldError -> msg.append(fieldError.getDefaultMessage()).append("，")
        );
        return R.fail(msg.toString().replaceAll("，$", ""));
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public R<String> exception(RuntimeException e) {
        log.error(e.getMessage());
        return R.fail("后端出现异常：" + e.getMessage());
    }

    @ExceptionHandler(NotLoginException.class)
    @ResponseBody
    public R<String> notLogin(NotLoginException e) {
        return R.fail("未登录");
    }
}