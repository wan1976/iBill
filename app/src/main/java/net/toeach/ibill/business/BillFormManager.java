package net.toeach.ibill.business;

import android.os.Handler;
import android.text.TextUtils;

import com.lidroid.xutils.exception.DbException;

import net.toeach.base.TException;
import net.toeach.ibill.dao.BillFormDao;
import net.toeach.ibill.model.BillForm;

import java.util.List;

/**
 * 账单业务类
 */
public class BillFormManager extends BaseManager {
    public final static int MSG_SAVE_SUCCESS = 4001;// 保存成功
    public final static int MSG_DEL_SUCCESS = 4002;// 删除成功
    public final static int MSG_LIST_SUCCESS = 4003;// 获取列表成功


    private static BillFormManager instance = new BillFormManager();
    private BillFormDao dao;// dao对象

    /**
     * 私有构造函数
     */
    private BillFormManager() {
        dao = BillFormDao.getInstance();
    }

    /**
     * 返回实例对象
     */
    public static BillFormManager getInstance() {
        return instance;
    }

    /**
     * 增加账单记录
     *
     * @param bean    账单对象
     * @param handler Handler对象
     */
    public void save(BillForm bean, Handler handler) {
        if (TextUtils.isEmpty(bean.getTitle())) {// 账单标题不能为空
            handleError(handler, 1010);
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
     * 删除账单记录
     *
     * @param id      账单记录对象表示
     * @param handler Handler对象
     */
    public void deleteById(int id, Handler handler) {
        try {
            dao.deleteById(id);// 删除数据
            handleMessage(handler, MSG_DEL_SUCCESS);// 发送消息通知UI
        } catch (DbException e) {
            handleException(handler, new TException("Database occurs error."));// 抛出系统错误
        }
    }

    /**
     * 批量删除账单记录
     *
     * @param list    账单记录对象列表
     * @param handler Handler对象
     */
    public void deleteAll(List<BillForm> list, Handler handler) {
        try {
            dao.deleteAll(list);// 删除数据
            handleMessage(handler, MSG_DEL_SUCCESS);// 发送消息通知UI
        } catch (DbException e) {
            handleException(handler, new TException("Database occurs error."));// 抛出系统错误
        }
    }

    /**
     * 获取账单记录
     *
     * @param pageNo   分页号
     * @param pageSize 分页数据大小
     * @param handler  Handler对象
     */
    public void getList(int pageNo, int pageSize, Handler handler) {
        try {
            List<BillForm> list = dao.getList(pageNo, pageSize);// 获取账单记录列表
            handleMessage(handler, MSG_LIST_SUCCESS, list);// 发送消息通知UI
        } catch (DbException e) {
            handleException(handler, new TException("Database occurs error."));// 抛出系统错误
        }
    }
}
