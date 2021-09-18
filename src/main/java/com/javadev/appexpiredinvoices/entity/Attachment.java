package com.javadev.appexpiredinvoices.entity;

import com.javadev.appexpiredinvoices.entity.template.AbsLong;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@DynamicInsert
@Entity
public class Attachment extends AbsLong {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private long size;

    @OneToOne(mappedBy = "attachment",cascade = CascadeType.PERSIST)
    private AttachmentContent attachmentContent;

    public Attachment(AttachmentContent attachmentContent) {
        attachmentContent.setAttachment(this);
        this.attachmentContent = attachmentContent;
    }

    public Attachment(String name, String content, long size) {
        this.name = name;
        this.content = content;
        this.size = size;
    }
}
