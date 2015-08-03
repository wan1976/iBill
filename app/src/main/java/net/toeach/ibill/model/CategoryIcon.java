package net.toeach.ibill.model;

import org.parceler.Parcel;

/**
 * 分类图标
 */
@Parcel
public class CategoryIcon {
    private String name;// 分类名称
    private int value;// 分类图标
    private boolean checked;// 是否选中

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
