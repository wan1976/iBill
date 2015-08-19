package net.toeach.ibill.model;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Table;

/**
 * 附件
 */
@Table(name = "bill_attachment")
public class Attachment {
    @Column(column = "_id")
    private int id;// 标识

    @Column(column = "bill_id")
    private int billId;// 账单id

    @Column(column = "file_uri")
    private String fileUri;// 附件URI

    @Column(column = "mime")
    private String mime;// 附件MIME类型

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public String getFileUri() {
        return fileUri;
    }

    public void setFileUri(String fileUri) {
        this.fileUri = fileUri;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "id=" + id +
                ", billId=" + billId +
                ", fileUri='" + fileUri + '\'' +
                ", mime='" + mime + '\'' +
                '}';
    }

}
