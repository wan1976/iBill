package net.toeach.ibill.dao;

import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;

import net.toeach.base.TBaseDao;
import net.toeach.ibill.IBillApplication;
import net.toeach.ibill.model.BillRecord;
import net.toeach.ibill.model.BillSection;
import net.toeach.ibill.model.BillSectionRecord;

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
     * 删除指定账单下的子账单数据
     *
     * @param parentId 账单标识
     * @throws DbException
     */
    public void deleteAllSections(int parentId) throws DbException {
        // 先删除对应子账单下的费用明细数据
        List<BillSection> sections = getList(parentId);
        for (BillSection section : sections) {
            db.delete(BillSectionRecord.class, WhereBuilder.b("section_id", "=", section.getId()));
        }
        // 再删除对应账单下的子账单数据
        db.delete(BillSection.class, WhereBuilder.b("parent_id", "=", parentId));
    }

    /**
     * 获取子账单列表
     *
     * @param parentId 账单标识
     * @return 子账单列表
     * @throws DbException 异常
     */
    public List<BillSection> getList(int parentId) throws DbException {
        Selector selector = Selector.from(BillSection.class)
                .where("parent_id", "=", parentId)
                .orderBy("_id", true);
        return db.findAll(selector);
    }

    /**
     * 查询子账单对象
     *
     * @param parentId 主账单表示
     * @param title    子账单名称
     * @return 子账单对象
     * @throws DbException 异常
     */
    public BillSection get(int parentId, String title) throws DbException {
        Selector selector = Selector.from(BillSection.class)
                .where("parent_id", "=", parentId)
                .and("title", "=", title);
        return db.findFirst(selector);
    }

    /**
     * 保存子账单和费用明细映射记录
     *
     * @param sectionId 子账单标识
     * @param record    费用记录
     * @throws DbException 异常
     */
    public void saveRecord(int sectionId, BillRecord record) throws DbException {
        BillSectionRecord bean = new BillSectionRecord();
        bean.setSectionId(sectionId);
        bean.setRecordId(record.getId());
        db.save(bean);
    }
}
