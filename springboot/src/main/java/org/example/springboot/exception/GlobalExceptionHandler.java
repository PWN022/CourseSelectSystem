package org.example.springboot.exception;

import jakarta.validation.ConstraintViolationException;
import org.example.springboot.common.Result;
import org.example.springboot.common.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(ServiceException.class)
    public Result<?> handleServiceException(ServiceException e) {
        log.error(e.getMessage(), e);
        return Result.error(e.getCode(), e.getMessage());
    }

    /**
     * 参数验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        String message = "参数验证失败";
        if (e.getBindingResult() != null && e.getBindingResult().getFieldError() != null) {
            String fieldMessage = e.getBindingResult().getFieldError().getDefaultMessage();
            if (fieldMessage != null) {
                message = fieldMessage;
            }
        }
        return Result.error(ResultCode.VALIDATE_FAILED.getCode(), message);
    }

    /**
     * 约束违反异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<?> handleConstraintViolationException(ConstraintViolationException e) {
        log.error(e.getMessage(), e);
        return Result.error(ResultCode.VALIDATE_FAILED.getCode(), e.getMessage());
    }

    /**
     * 处理空指针异常
     */
    @ExceptionHandler(NullPointerException.class)
    public Result<?> handleNullPointerException(NullPointerException e) {
        log.error("空指针异常: {}", e.getMessage(), e);
        return Result.error(ResultCode.SYSTEM_ERROR.getCode(), "系统内部错误");
    }

    /**
     * 处理数字格式异常
     */
    @ExceptionHandler(NumberFormatException.class)
    public Result<?> handleNumberFormatException(NumberFormatException e) {
        log.error("数字格式异常: {}", e.getMessage(), e);
        return Result.error(ResultCode.VALIDATE_FAILED.getCode(), "参数格式错误");
    }

    /**
     * 处理类型转换异常
     */
    @ExceptionHandler(ClassCastException.class)
    public Result<?> handleClassCastException(ClassCastException e) {
        log.error("类型转换异常: {}", e.getMessage(), e);
        return Result.error(ResultCode.VALIDATE_FAILED.getCode(), "数据类型错误");
    }

    /**
     * 处理SQL异常
     */
    @ExceptionHandler(org.springframework.dao.DataAccessException.class)
    public Result<?> handleDataAccessException(org.springframework.dao.DataAccessException e) {
        log.error("数据库访问异常: {}", e.getMessage(), e);
        return Result.error(ResultCode.SYSTEM_ERROR.getCode(), "数据库操作失败");
    }

    /**
     * 处理其他异常
     */
    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e) {
        log.error("未知异常: {}", e.getMessage(), e);
        return Result.error(ResultCode.SYSTEM_ERROR.getCode(), "系统异常，请联系管理员");
    }
}