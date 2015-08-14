package net.toeach.ibill.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Table;
import com.lidroid.xutils.db.annotation.Transient;

import org.parceler.Parcel;

import java.util.Date;

/**
 * 账单详细记录
 */
@Table(name = "bill_record")
@Parcel(Parcel.Serialization.BEAN)
public class BillRecord {
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
    @JSONField(name = "bill_date")
    @Column(column = "bill_date")
    private Date billDate;// 账单发生日期
    @JSONField(name = "create_time")
    @Column(column = "create_time")
    private Date createTime;// 记录时间
    @Transient
    private String catName;// 分类名称
    @Transient
    private String catIcon;// 分类图标

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

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getCatIcon() {
        return catIcon;
    }

    public void setCatIcon(String catIcon) {
        this.catIcon = catIcon;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    @Override
    public String toString() {
        return "BillRecord{" +
                "id=" + id +
                ", catId=" + catId +
                ", cost=" + cost +
                ", memo='" + memo + '\'' +
                ", billDate=" + billDate +
                ", createTime=" + createTime +
                ", catName='" + catName + '\'' +
                ", catIcon='" + catIcon + '\'' +
                '}';
    }
}
