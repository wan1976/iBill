package net.toeach.ibill.dao;

import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

import net.toeach.base.TBaseDao;
import net.toeach.ibill.IBillApplication;
import net.toeach.ibill.model.BillForm;

import java.util.List;

/**
 * 主账单对象数据库访问对象
 */
public class BillFormDao extends TBaseDao<BillForm> {
    private static BillFormDao instance = new BillFormDao();

    /**
     * 私有构造函数
     */
    private BillFormDao() {
        super(IBillApplication.getDbInstance());
    }

    /**
     * 返回实例对象
     *
     * @return
     */
    public static BillFormDao getInstance() {
        return instance;
    }

    /**
     * 获取账单列表
     *
     * @param pageNo   分页号
     * @param pageSize 分页数据大小
     * @return 账单列表
     * @throws DbException 异常
     */
    public List<BillForm> getList(int pageNo, int pageSize) throws DbException {
        Selector selector = Selector.from(BillForm.class)
                .orderBy("_id", true)
                .limit(pageSize)
                .offset(pageSize * pageNo);
        return db.findAll(selector);
    }

    /**
     * 获取账单对象
     *
     * @param title 标题
     * @return 账单对象
     * @throws DbException 异常
     */
    public BillForm getByTitle(String title) throws DbException {
        Selector selector = Selector.from(BillForm.class)
                .where("title", "=", title);
        return db.findFirst(selector);
    }
}
