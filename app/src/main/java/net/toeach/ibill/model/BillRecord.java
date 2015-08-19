package net.toeach.ibill.model;

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
    @Column(column = "_id")
    private int id;// 标识

    @Column(column = "cat_id")
    private int catId;// 分类id

    @Column(column = "cost")
    private int cost;// 金额（分）

    @Column(column = "memo")
    private String memo;// 备注

    @Column(column = "bill_date")
    private Date billDate;// 账单发生日期

    @Column(column = "bill_month")
    private String billMonth;// 账单所在月份

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

    public String getBillMonth() {
        return billMonth;
    }

    public void setBillMonth(String billMonth) {
        this.billMonth = billMonth;
    }

    @Override
    public String toString() {
        return "BillRecord{" +
                "id=" + id +
                ", catId=" + catId +
                ", cost=" + cost +
                ", memo='" + memo + '\'' +
                ", billDate=" + billDate +
                ", billMonth=" + billMonth +
                ", createTime=" + createTime +
                ", catName='" + catName + '\'' +
                ", catIcon='" + catIcon + '\'' +
                '}';
    }
}
