package net.toeach.ibill.dao;

import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

import net.toeach.base.TBaseDao;
import net.toeach.ibill.IBillApplication;
import net.toeach.ibill.model.Attachment;

import java.util.List;

/**
 * Attachment数据库访问对象
 */
public class AttachmentDao extends TBaseDao<Attachment> {
    private static AttachmentDao instance = new AttachmentDao();

    /**
     * 私有构造函数
     */
    private AttachmentDao() {
        super(IBillApplication.getDbInstance());
    }

    /**
     * 返回实例对象
     *
     * @return
     */
    public static AttachmentDao getInstance() {
        return instance;
    }

    /**
     * 获取对应账单下的全部附件
     *
     * @param parentId 账单id
     * @return 附件列表
     * @throws DbException 异常对象
     */
    public List<Attachment> getList(int parentId) throws DbException {
        Selector selector = Selector.from(Attachment.class)
                .where("bill_id", "=", parentId)
                .orderBy("_id", false);
        return db.findAll(selector);
    }
}
