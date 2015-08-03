package net.toeach.ibill.business;

import android.os.Handler;
import android.os.Message;

import net.toeach.base.TException;
import net.toeach.ibill.Constants;

/**
 * 业务类基类
 */
public class BaseManager {
    /**
     * 发送消息
     *
     * @param handler Handler对象
     * @param what    what
     */
    protected void handleMessage(Handler handler, int what) {
        handleMessage(handler, what, null);
    }

    /**
     * 发送消息
     *
     * @param handler Handler对象
     * @param what    what
     * @param obj     传值对象
     */
    protected void handleMessage(Handler handler, int what, Object obj) {
        if (handler != null) {
            Message msg = handler.obtainMessage();
            msg.what = what;
            if (obj != null) {
                msg.obj = obj;
            }
            handler.sendMessage(msg);
        }
    }

    /**
     * 发送系统错误处理消息
     *
     * @param handler Handler对象
     * @param e       TException对象
     */
    protected void handleException(Handler handler, TException e) {
        // 打印错误堆栈信息
        if (e != null) {
            e.printStackTrace();
        }

        // 发送消息
        if (handler != null) {
            Message msg = handler.obtainMessage();
            msg.what = Constants.MSG_EXCEPTION;
            msg.obj = e;
            handler.sendMessage(msg);
        }
    }

    /**
     * 发送错误处理消息
     *
     * @param handler Handler对象
     * @param errCode 错误代码
     */
    protected void handleError(Handler handler, int errCode) {
        // 发送消息
        if (handler != null) {
            Message msg = handler.obtainMessage();
            msg.what = Constants.MSG_ERROR;
            msg.obj = errCode;
            handler.sendMessage(msg);
        }
    }
}
