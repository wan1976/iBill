package net.toeach.ibill.business;

import android.os.Handler;
import android.text.TextUtils;

import com.lidroid.xutils.exception.DbException;

import net.toeach.base.TException;
import net.toeach.ibill.dao.BillRecordDao;
import net.toeach.ibill.model.BillRecord;

import java.util.List;

/**
 * 账单记录业务类
 */
public class BillRecordManager extends BaseManager {
    public final static int MSG_SAVE_SUCCESS = 3001;// 保存成功
    public final static int MSG_DEL_SUCCESS = 3002;// 删除成功


    private static BillRecordManager instance = new BillRecordManager();
    private BillRecordDao dao;// dao对象

    /**
     * 私有构造函数
     */
    private BillRecordManager() {
        dao = BillRecordDao.getInstance();
    }

    /**
     * 返回实例对象
     */
    public static BillRecordManager getInstance() {
        return instance;
    }

    /**
     * 增加账单记录
     *
     * @param bean    附件对象
     * @param handler Handler对象
     */
    public void add(BillRecord bean, Handler handler) {
        if (bean.getCatId() == 0) {// 分类id不为0
            handleError(handler, 1004);
            return;
        }
        if (bean.getCost() == 0) {// 金额不为0
            handleError(handler, 1005);
            return;
        }
        if (TextUtils.isEmpty(bean.getMemo())) {// 备注不能为空
            handleError(handler, 1006);
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
     * @param bean    账单记录对象
     * @param handler Handler对象
     */
    public void delete(BillRecord bean, Handler handler) {
        try {
            dao.delete(bean.getId());// 删除数据
            handleMessage(handler, MSG_DEL_SUCCESS);// 发送消息通知UI
        } catch (DbException e) {
            handleException(handler, new TException("Database occurs error."));// 抛出系统错误
        }
    }

    /**
     * 获取账单记录
     *
     * @param catId    分类id
     * @param pageNo   分页号
     * @param pageSize 分页数据大小
     * @param handler  Handler对象
     */
    public void getList(int catId, int pageNo, int pageSize, Handler handler) {
        try {
            List<BillRecord> list = dao.getList(catId, pageNo, pageSize);// 获取账单记录列表
            handleMessage(handler, MSG_DEL_SUCCESS, list);// 发送消息通知UI
        } catch (DbException e) {
            handleException(handler, new TException("Database occurs error."));// 抛出系统错误
        }
    }
}
