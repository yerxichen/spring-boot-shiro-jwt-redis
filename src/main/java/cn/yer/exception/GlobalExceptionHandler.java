package cn.yer.exception;


import cn.yer.response.BaseRespCode;
import cn.yer.util.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 系统繁忙，请稍候再试"
     */
    @ExceptionHandler(Exception.class)
    public <T> Result<Object> handleException(Exception e) {
        logger.error("默认异常", e);
        return Result.error(BaseRespCode.SYSTEM_BUSY.getCode(),BaseRespCode.SYSTEM_BUSY.getMsg());
    }

    /**
     * 自定义全局异常处理
     */
    @ExceptionHandler(value = GlobalException.class)
    <T> Result<Object> globalExceptionHandler(GlobalException e) {
        logger.error("自定义异常：", e.getDetailMessage());
        return Result.error(e.getMessageCode(), e.getDetailMessage());
    }

}
