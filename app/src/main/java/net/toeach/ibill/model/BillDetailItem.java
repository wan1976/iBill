package net.toeach.ibill.model;

/**
 * Created by Administrator on 2015/8/19.
 */
public class BillDetailItem {
    private String title;
    private String cost;
    private String date;
    private String memo;
    private int type;// 0：Section，1:账单记录，2:合计

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
