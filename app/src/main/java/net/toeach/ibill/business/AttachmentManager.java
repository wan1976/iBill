package net.toeach.ibill.business;

import android.os.Handler;
import android.text.TextUtils;

import com.lidroid.xutils.exception.DbException;

import net.toeach.base.TException;
import net.toeach.ibill.dao.AttachmentDao;
import net.toeach.ibill.model.Attachment;

import java.util.List;

/**
 * 账单记录附件业务类
 */
public class AttachmentManager extends BaseManager {
    public final static int MSG_SAVE_SUCCESS = 1001;// 保存成功
    public final static int MSG_DEL_SUCCESS = 1002;// 删除成功


    private static AttachmentManager instance = new AttachmentManager();
    private AttachmentDao dao;// dao对象

    /**
     * 私有构造函数
     */
    private AttachmentManager() {
        dao = AttachmentDao.getInstance();
    }

    /**
     * 返回实例对象
     */
    public static AttachmentManager getInstance() {
        return instance;
    }

    /**
     * 增加附件
     *
     * @param bean    附件对象
     * @param handler Handler对象
     */
    public void add(Attachment bean, Handler handler) {
        if (bean.getBillId() == 0) {// 账单id不为0
            handleError(handler, 1001);
            return;
        }
        if (TextUtils.isEmpty(bean.getFileUri())) {// 附件URI不为空
            handleError(handler, 1002);
            return;
        }
        if (TextUtils.isEmpty(bean.getMime())) {// MIME不为空
            handleError(handler, 1003);
            return;
        }

        try {
            dao.save(bean);// 保存数据
            handleMessage(handler, MSG_SAVE_SUCCESS);// 发送消息通知UI
        } catch (DbException e) {
            handleException(handler, new TException("Database occurs error."));// 抛出系统错误
        }
    }

    /**
     * 删除附件
     *
     * @param bean    附件对象
     * @param handler Handler对象
     */
    public void delete(Attachment bean, Handler handler) {
        try {
            dao.delete(bean.getId());// 删除数据
            handleMessage(handler, MSG_DEL_SUCCESS);// 发送消息通知UI
        } catch (DbException e) {
            handleException(handler, new TException("Database occurs error."));// 抛出系统错误
        }
    }

    /**
     * 获取全部附件
     *
     * @param parentId 账单记录id
     * @param handler  Handler对象
     */
    public void getList(int parentId, Handler handler) {
        try {
            List<Attachment> list = dao.getList(parentId);// 获取附件列表
            handleMessage(handler, MSG_DEL_SUCCESS, list);// 发送消息通知UI
        } catch (DbException e) {
            handleException(handler, new TException("Database occurs error."));// 抛出系统错误
        }
    }
}
