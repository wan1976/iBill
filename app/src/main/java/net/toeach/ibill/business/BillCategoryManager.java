package net.toeach.ibill.business;

import android.os.Handler;
import android.text.TextUtils;

import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.util.LogUtils;

import net.toeach.base.TException;
import net.toeach.ibill.dao.BillCategoryDao;
import net.toeach.ibill.model.BillCategory;

import java.util.List;

/**
 * 分类业务类
 */
public class BillCategoryManager extends BaseManager {
    public final static int MSG_SAVE_SUCCESS = 2001;// 保存成功
    public final static int MSG_DEL_SUCCESS = 2002;// 删除成功
    public final static int MSG_LIST_SUCCESS = 2003;// 获取列表成功

    private static BillCategoryManager instance = new BillCategoryManager();
    private BillCategoryDao dao;// dao对象

    /**
     * 私有构造函数
     */
    private BillCategoryManager() {
        dao = BillCategoryDao.getInstance();
    }

    /**
     * 返回实例对象
     */
    public static BillCategoryManager getInstance() {
        return instance;
    }

    /**
     * 保存数据
     * @param mode 0:新增，1:编辑
     * @param bean 分类对象
     * @param handler Handler对象
     */
    public void save(int mode, BillCategory bean, Handler handler) {
        LogUtils.d(bean.toString());
        if(mode == 0) {
            add(bean, handler);
        } else if(mode == 1){
            modify(bean, handler);
        }
    }
    /**
     * 增加分类
     *
     * @param bean    分类对象
     * @param handler Handler对象
     */
    public void add(BillCategory bean, Handler handler) {
        if (TextUtils.isEmpty(bean.getName())) {// 分类名称不为空
            handleError(handler, 1007);
            return;
        }
        if (TextUtils.isEmpty(bean.getIcon())) {// 分类图标不为空
            handleError(handler, 1008);
            return;
        }

        try {
            if (dao.getByName(bean.getName()) != null) {// 分类名称已经存在
                handleError(handler, 1009);
                return;
            }

            dao.save(bean);// 保存数据
            handleMessage(handler, MSG_SAVE_SUCCESS);// 发送消息通知UI
        } catch (DbException e) {
            handleException(handler, new TException("Database occurs error."));// 抛出系统错误
        }
    }

    /**
     * 修改分类
     *
     * @param bean    分类对象
     * @param handler Handler对象
     */
    public void modify(BillCategory bean, Handler handler) {
        if (TextUtils.isEmpty(bean.getName())) {// 分类名称不为空
            handleError(handler, 1007);
            return;
        }
        if (TextUtils.isEmpty(bean.getIcon())) {// 分类图标不为空
            handleError(handler, 1008);
            return;
        }

        try {
            BillCategory cat = dao.getByName(bean.getName());
            if (cat != null && cat.getId() != bean.getId()) {// 分类名称已经存在
                handleError(handler, 1009);
                return;
            }

            dao.save(bean);// 保存数据
            handleMessage(handler, MSG_SAVE_SUCCESS);// 发送消息通知UI
        } catch (DbException e) {
            handleException(handler, new TException("Database occurs error."));// 抛出系统错误
        }
    }

    /**
     * 删除分类
     *
     * @param bean    分类对象
     * @param handler Handler对象
     */
    public void delete(BillCategory bean, Handler handler) {
        try {
            dao.delete(bean.getId());// 删除数据
            handleMessage(handler, MSG_DEL_SUCCESS);// 发送消息通知UI
        } catch (DbException e) {
            handleException(handler, new TException("Database occurs error."));// 抛出系统错误
        }
    }

    /**
     * 获取全部分类
     *
     * @param handler Handler对象
     */
    public void getList(Handler handler) {
        try {
            List<BillCategory> list = dao.getList("_id", false);// 获取分类列表
            handleMessage(handler, MSG_LIST_SUCCESS, list);// 发送消息通知UI
        } catch (DbException e) {
            handleException(handler, new TException("Database occurs error."));// 抛出系统错误
        }
    }
}
