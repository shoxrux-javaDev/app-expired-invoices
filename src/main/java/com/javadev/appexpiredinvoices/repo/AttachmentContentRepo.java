package com.javadev.appexpiredinvoices.repo;

import com.javadev.appexpiredinvoices.entity.AttachmentContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentContentRepo extends JpaRepository<AttachmentContent, Long> {

    AttachmentContent findAllByAttachment_Id(Long attachment_id);

}
