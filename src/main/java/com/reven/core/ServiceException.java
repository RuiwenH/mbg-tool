package com.reven.core;

/**
 * @ClassName:  ServiceException   
 * @Description:服务（业务）异常，controller 返回异步请求数据时建议处理本异常
 * @author reven
 * @date   2018年8月28日
 */
public class ServiceException extends Exception {

    private static final long serialVersionUID = 1L;

    /**   
     * @Fields code : 异常编码，用于判断或者国际化   
     */
    protected String code;

    public ServiceException(Throwable throwable) {
        super(throwable);
    }

    /**   
     * @Title:  ServiceException   
     * @Description: 用指定的详细信息和原因构造一个新的异常
     * @param:  @param message 自定义异常信息
     * @param:  @param throwable 原因
     * @throws   
     */
    public ServiceException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, String code, Throwable throwable) {
        super(message, throwable);
        this.code = code;
    }

    public ServiceException(String message, String code) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
