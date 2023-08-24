package xyz.blushyes.exception;


import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.blushyes.common.R;

@ControllerAdvice
public class ErrorControllerAdvice {
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
        e.printStackTrace();
        return R.fail("后端出现异常：" + e.getMessage());
    }
}