package net.toeach.ibill.business;

import android.os.Handler;
import android.text.TextUtils;

import com.lidroid.xutils.exception.DbException;

import net.toeach.base.TException;
import net.toeach.ibill.dao.BillSectionDao;
import net.toeach.ibill.model.BillSection;

import java.util.List;

/**
 * 子账单业务类
 */
public class BillSectionManager extends BaseManager {
    public final static int MSG_SAVE_SUCCESS = 5001;// 保存成功
    public final static int MSG_DEL_SUCCESS = 5002;// 删除成功
    public final static int MSG_LIST_SUCCESS = 5003;// 获取列表成功


    private static BillSectionManager instance = new BillSectionManager();
    private BillSectionDao dao;// dao对象

    /**
     * 私有构造函数
     */
    private BillSectionManager() {
        dao = BillSectionDao.getInstance();
    }

    /**
     * 返回实例对象
     */
    public static BillSectionManager getInstance() {
        return instance;
    }

    /**
     * 增加子账单记录
     *
     * @param bean    子账单对象
     * @param handler Handler对象
     */
    public void save(BillSection bean, Handler handler) {
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
     * 删除子账单记录
     *
     * @param id      子账单记录对象表示
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
     * 批量删除子账单记录
     *
     * @param list    账单记录对象列表
     * @param handler Handler对象
     */
    public void deleteAll(List<BillSection> list, Handler handler) {
        try {
            dao.deleteAll(list);// 删除数据
            handleMessage(handler, MSG_DEL_SUCCESS);// 发送消息通知UI
        } catch (DbException e) {
            handleException(handler, new TException("Database occurs error."));// 抛出系统错误
        }
    }

    /**
     * 获取子账单记录
     *
     * @param parentId 账单标识
     * @param handler  Handler对象
     */
    public void getList(int parentId, Handler handler) {
        try {
            List<BillSection> list = dao.getList(parentId);// 获取子账单记录列表
            handleMessage(handler, MSG_LIST_SUCCESS, list);// 发送消息通知UI
        } catch (DbException e) {
            handleException(handler, new TException("Database occurs error."));// 抛出系统错误
        }
    }
}
