package net.toeach.base;

/**
 * 自定义异常 <br/>
 * com.xiesi.demo.utils.TException
 *
 * @author 万云  <br/>
 * @version 1.0
 */
public class TException extends java.lang.Exception {
    private static final long serialVersionUID = -423751101517441257L;

    /**
     * 构造函数
     *
     * @param msg 异常描述
     */
    public TException(String msg) {
        super(msg);
    }

    /**
     * 构造函数
     *
     * @param cause 引起异常的原因
     */
    public TException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造函数
     *
     * @param msg   异常描述
     * @param cause 引起异常的原因
     */
    public TException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
