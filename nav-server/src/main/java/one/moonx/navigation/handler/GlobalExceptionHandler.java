package one.moonx.navigation.handler;

import lombok.extern.slf4j.Slf4j;
import one.moonx.navigation.base.Result;
import one.moonx.navigation.exception.BaseException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    /**
     * 异常处理程序
     *
     * @param exception 例外
     * @return {@link Result }
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException exception) {
        log.error("异常信息：{}", exception.getMessage());
        return Result.error(exception.getMessage());
    }
}