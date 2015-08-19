package net.toeach.ibill.business;

import android.os.Handler;

import com.lidroid.xutils.exception.DbException;

import net.toeach.base.TException;
import net.toeach.ibill.dao.BillCategoryDao;
import net.toeach.ibill.dao.BillRecordDao;
import net.toeach.ibill.model.BillCategory;
import net.toeach.ibill.model.BillRecord;

import java.util.List;

/**
 * 费用明细记录业务类
 */
public class BillRecordManager extends BaseManager {
    public final static int MSG_SAVE_SUCCESS = 3001;// 保存成功
    public final static int MSG_DEL_SUCCESS = 3002;// 删除成功
    public final static int MSG_LIST_SUCCESS = 3003;// 获取列表成功


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
     * 增加费用明细记录
     *
     * @param bean    费用明细对象
     * @param handler Handler对象
     */
    public void save(BillRecord bean, Handler handler) {
        if (bean.getCost() == 0) {// 金额不为0
            handleError(handler, 1005);
            return;
        }
//        if (TextUtils.isEmpty(bean.getMemo())) {// 备注不能为空
//            handleError(handler, 1006);
//            return;
//        }

        try {
            dao.save(bean);// 保存数据
            handleMessage(handler, MSG_SAVE_SUCCESS);// 发送消息通知UI
        } catch (DbException e) {
            handleException(handler, new TException("Database occurs error."));// 抛出系统错误
        }
    }

    /**
     * 删除费用明细记录
     *
     * @param id      费用明细记录对象表示
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
     * 批量删除费用明细记录
     *
     * @param list    费用明细记录对象列表
     * @param handler Handler对象
     */
    public void deleteAll(List<BillRecord> list, Handler handler) {
        try {
            dao.deleteAll(list);// 删除数据
            handleMessage(handler, MSG_DEL_SUCCESS);// 发送消息通知UI
        } catch (DbException e) {
            handleException(handler, new TException("Database occurs error."));// 抛出系统错误
        }
    }

    /**
     * 获取费用明细记录
     *
     * @param pageNo   分页号
     * @param pageSize 分页数据大小
     * @param handler  Handler对象
     */
    public void getList(int pageNo, int pageSize, Handler handler) {
        try {
            List<BillRecord> list = dao.getList(pageNo, pageSize);// 获取费用明细记录列表
            if (list != null) {
                for (BillRecord bean : list) {
                    BillCategory cat = BillCategoryDao.getInstance().findById(bean.getCatId());
                    if (cat != null) {
                        bean.setCatName(cat.getName());
                        bean.setCatIcon(cat.getIcon());
                    }
                }
            }
            handleMessage(handler, MSG_LIST_SUCCESS, list);// 发送消息通知UI
        } catch (DbException e) {
            handleException(handler, new TException("Database occurs error."));// 抛出系统错误
        }
    }
}
