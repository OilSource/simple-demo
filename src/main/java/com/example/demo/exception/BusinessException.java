package com.example.demo.exception;

/**
 *  <p> 自定义异常类 </p>
 *
 * @description :
 * @author : zhengqing
 * @date : 2019/8/26 15:11
 */
public class BusinessException extends RuntimeException {

    /**
     * 异常状态码
     */
    private Integer code;

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public Integer getCode() {
        return code;
    }

}
