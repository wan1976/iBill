package net.toeach.ibill.model;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Table;
import com.lidroid.xutils.db.annotation.Transient;

import org.parceler.Parcel;

/**
 * 账单类型
 */
@Table(name = "bill_category")
@Parcel(Parcel.Serialization.BEAN)
public class BillCategory {
    @Column(column = "_id")
    private int id;// 标识

    @Column(column = "cat_name")
    private String name;// 分类名称

    @Column(column = "cat_icon")
    private String icon;// 分类图标

    @Transient
    private boolean checked;// 选中状态

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return "BillCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", checked='" + checked + '\'' +
                '}';
    }
}
