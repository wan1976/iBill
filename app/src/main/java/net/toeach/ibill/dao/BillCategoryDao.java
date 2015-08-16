package net.toeach.ibill.dao;

import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

import net.toeach.base.TBaseDao;
import net.toeach.ibill.IBillApplication;
import net.toeach.ibill.model.BillCategory;

/**
 * Category数据库访问对象
 */
public class BillCategoryDao extends TBaseDao<BillCategory> {
    private static BillCategoryDao instance = new BillCategoryDao();

    /**
     * 私有构造函数
     */
    private BillCategoryDao() {
        super(IBillApplication.getDbInstance());
    }

    /**
     * 返回实例对象
     *
     * @return
     */
    public static BillCategoryDao getInstance() {
        return instance;
    }

    /**
     * 获取分类对象
     * @param name 名称
     * @return 分类对象
     * @throws DbException 异常
     */
    public BillCategory getByName(String name) throws DbException {
        Selector selector = Selector.from(BillCategory.class)
                .where("cat_name", "=", name);
        return db.findFirst(selector);
    }
}
