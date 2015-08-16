package net.toeach.ibill.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Table;
import com.lidroid.xutils.db.annotation.Transient;

import org.parceler.Parcel;

import java.util.Date;
import java.util.List;

/**
 * 主账单对象
 */
@Table(name = "bill_form")
@Parcel(Parcel.Serialization.BEAN)
public class BillForm {
    @JSONField(name = "id")
    @Column(column = "_id")
    private int id;// 标识

    @JSONField(name = "type")
    @Column(column = "type")
    private int type;// 类型，0-月账单，1-自定义账单

    @JSONField(name = "title")
    @Column(column = "title")
    private String title;// 账单标题

    @JSONField(name = "create_time")
    @Column(column = "create_time")
    private Date createTime;// 记录时间

    @Transient
    private List<BillSection> sections;// 账单子项目

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<BillSection> getSections() {
        return sections;
    }

    public void setSections(List<BillSection> sections) {
        this.sections = sections;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "BillForm{" +
                "id=" + id +
                ", type=" + type +
                ", title='" + title + '\'' +
                ", createTime=" + createTime +
                ", sections=" + sections +
                '}';
    }
}
