package net.toeach.ibill.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Table;
import com.lidroid.xutils.db.annotation.Transient;

import java.util.Date;
import java.util.List;

/**
 * 账单子项目对象
 */
@Table(name = "bill_section")
public class BillSection {
    @JSONField(name = "id")
    @Column(column = "_id")
    private int id;// 标识

    @JSONField(name = "parent_id")
    @Column(column = "parent_id")
    private int parentId;// 主账单标识

    @JSONField(name = "title")
    @Column(column = "title")
    private String title;// 子标题

    @JSONField(name = "create_time")
    @Column(column = "create_time")
    private Date createTime;// 记录时间

    @Transient
    private List<BillRecord> records;// 费用明细

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<BillRecord> getRecords() {
        return records;
    }

    public void setRecords(List<BillRecord> records) {
        this.records = records;
    }

    @Override
    public String toString() {
        return "BillSection{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", title='" + title + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
