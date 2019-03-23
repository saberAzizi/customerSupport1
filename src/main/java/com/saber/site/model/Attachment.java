package com.saber.site.model;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "attachment")
public class Attachment implements Serializable {
    private long attachmentId;
    private String contentType;
    private String attachmentName;
    private byte[] contents;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attachment_id")
    public long getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(long attachmentId) {
        this.attachmentId = attachmentId;
    }

    @Basic
    @Column(name = "mimContent_type",nullable = false)
    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Basic
    @Column(name = "attachment_name",nullable = false)
    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    @Lob
    @Column(name = "contents",nullable = false)
    public byte[] getContents() {
        return contents;
    }

    public void setContents(byte[] contents) {
        this.contents = contents;
    }
}
