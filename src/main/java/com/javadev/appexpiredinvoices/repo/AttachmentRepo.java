package com.javadev.appexpiredinvoices.repo;

import com.javadev.appexpiredinvoices.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AttachmentRepo extends JpaRepository<Attachment, Long> {
}
