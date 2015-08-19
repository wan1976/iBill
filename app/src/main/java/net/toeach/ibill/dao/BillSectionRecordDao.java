package net.toeach.ibill.dao;

import android.database.Cursor;

import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;

import net.toeach.base.TBaseDao;
import net.toeach.ibill.IBillApplication;
import net.toeach.ibill.model.BillRecord;
import net.toeach.ibill.model.BillSectionRecord;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 子账单对象数据库访问对象
 */
public class BillSectionRecordDao extends TBaseDao<BillSectionRecord> {
    private static BillSectionRecordDao instance = new BillSectionRecordDao();

    /**
     * 私有构造函数
     */
    private BillSectionRecordDao() {
        super(IBillApplication.getDbInstance());
    }

    /**
     * 返回实例对象
     *
     * @return 实例对象
     */
    public static BillSectionRecordDao getInstance() {
        return instance;
    }

    /**
     * 删除子账单下的费用明细数据
     *
     * @param sectionId 子账单标识
     * @throws DbException 异常
     */
    public void deleteBySection(int sectionId) throws DbException {
        db.delete(BillSectionRecord.class, WhereBuilder.b("section_id", "=", sectionId));
    }

    /**
     * 获取子账单下的费用明细对象
     *
     * @param sectionId 子账单标识
     * @return 费用明细列表
     * @throws DbException 异常
     */
    public List<BillRecord> getList(int sectionId) throws DbException {
        List<BillRecord> list = new ArrayList<>();

        String sql = "select a.* from bill_record a, bill_section_record b" +
                " where a._id=b.record_id and b.section_id=" + sectionId +
                " order by a.bill_date asc";

        Cursor c = db.execQuery(sql);
        if (c != null) {
            try {
                while (c.moveToNext()) {
                    BillRecord bean = new BillRecord();
                    bean.setId(c.getInt(c.getColumnIndex("_id")));
                    bean.setCatId(c.getInt(c.getColumnIndex("cat_id")));
                    bean.setCost(c.getInt(c.getColumnIndex("cost")));
                    bean.setMemo(c.getString(c.getColumnIndex("memo")));
                    bean.setBillDate(new Date(c.getLong(c.getColumnIndex("bill_date"))));
                    list.add(bean);
                }
            } finally {
                if (c != null) {
                    c.close();
                }
            }
        }

        return list;
    }
}
