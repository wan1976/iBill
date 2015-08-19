package net.toeach.ibill.model;


import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Table;

/**
 * 子项目费用明细映射表
 */
@Table(name = "bill_section_record")
public class BillSectionRecord {
    @Column(column = "_id")
    private int id;// 标识

    @Column(column = "section_id")
    private int sectionId;// 子账单标识

    @Column(column = "record_id")
    private int recordId;// 费用明细标识

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    @Override
    public String toString() {
        return "BillSectionRecord{" +
                "id=" + id +
                ", sectionId=" + sectionId +
                ", recordId=" + recordId +
                '}';
    }
}
