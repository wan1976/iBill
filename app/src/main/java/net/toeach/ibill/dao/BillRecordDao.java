package net.toeach.ibill.dao;

import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

import net.toeach.base.TBaseDao;
import net.toeach.ibill.IBillApplication;
import net.toeach.ibill.model.BillRecord;

import java.util.List;

/**
 * Category数据库访问对象
 */
public class BillRecordDao extends TBaseDao<BillRecord> {
    private static BillRecordDao instance = new BillRecordDao();

    /**
     * 私有构造函数
     */
    private BillRecordDao() {
        super(IBillApplication.getDbInstance());
    }

    /**
     * 返回实例对象
     *
     * @return
     */
    public static BillRecordDao getInstance() {
        return instance;
    }

    /**
     * 获取费用明细对象
     *
     * @param pageNo   分页号
     * @param pageSize 分页数据大小
     * @return 费用明细列表
     * @throws DbException 异常
     */
    public List<BillRecord> getList(int pageNo, int pageSize) throws DbException {
        Selector selector = Selector.from(BillRecord.class)
                .orderBy("bill_date", true)
                .limit(pageSize)
                .offset(pageSize * pageNo);
        return db.findAll(selector);
    }

    /**
     * 获取分类下的费用明细对象
     *
     * @param catId    分类id
     * @return 费用明细列表
     * @throws DbException 异常
     */
    public List<BillRecord> getList(int catId) throws DbException {
        Selector selector = Selector.from(BillRecord.class)
                .where("cat_id", "=", catId)
                .orderBy("bill_date", true);
        return db.findAll(selector);
    }
}
