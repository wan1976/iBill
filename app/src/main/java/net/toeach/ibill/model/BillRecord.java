package net.toeach.ibill.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Table;
import com.lidroid.xutils.db.annotation.Transient;

import java.util.Date;
import java.util.List;

/**
 * 账单详细记录
 */
@Table(name = "bill_record")
public class BillRecord {
    @Transient// Transient使这个列被忽略，不存入数据库
    public List<Attachment> attachments;// 附件列表
    @JSONField(name = "id")
    @Column(column = "_id")
    private int id;// 标识
    @JSONField(name = "cat_id")
    @Column(column = "cat_id")
    private int catId;// 分类id
    @JSONField(name = "cost")
    @Column(column = "cost")
    private int cost;// 金额（分）
    @JSONField(name = "memo")
    @Column(column = "memo")
    private String memo;// 备注
    @JSONField(name = "create_time")
    @Column(column = "create_time")
    private Date createTime;// 记录时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "BillRecord{" +
                "id=" + id +
                ", catId=" + catId +
                ", cost=" + cost +
                ", memo='" + memo + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
