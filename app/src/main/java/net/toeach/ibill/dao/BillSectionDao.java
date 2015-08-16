package net.toeach.ibill.dao;

import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

import net.toeach.base.TBaseDao;
import net.toeach.ibill.IBillApplication;
import net.toeach.ibill.model.BillSection;

import java.util.List;

/**
 * 子账单对象数据库访问对象
 */
public class BillSectionDao extends TBaseDao<BillSection> {
    private static BillSectionDao instance = new BillSectionDao();

    /**
     * 私有构造函数
     */
    private BillSectionDao() {
        super(IBillApplication.getDbInstance());
    }

    /**
     * 返回实例对象
     *
     * @return
     */
    public static BillSectionDao getInstance() {
        return instance;
    }

    /**
     * 获取子账单列表
     *
     * @return 账单列表
     * @throws DbException 异常
     */
    public List<BillSection> getList(int parentId) throws DbException {
        Selector selector = Selector.from(BillSection.class)
                .where("parent_id", "=", parentId)
                .orderBy("_id", true);
        return db.findAll(selector);
    }
}
