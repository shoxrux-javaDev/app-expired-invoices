package com.javadev.appexpiredinvoices.service;

import com.javadev.appexpiredinvoices.entity.Attachment;
import com.javadev.appexpiredinvoices.entity.AttachmentContent;
import com.javadev.appexpiredinvoices.payload.Response;
import com.javadev.appexpiredinvoices.repo.AttachmentContentRepo;
import com.javadev.appexpiredinvoices.repo.AttachmentRepo;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Service
public class AttachmentService {
    final AttachmentRepo attachmentRepo;
    final AttachmentContentRepo attachmentContentRepo;

    public AttachmentService(AttachmentRepo attachmentRepo,
                             AttachmentContentRepo attachmentContentRepo) {
        this.attachmentRepo = attachmentRepo;
        this.attachmentContentRepo = attachmentContentRepo;
    }

    public Response uploadFile(List<MultipartFile> multipartFileList) {
        Attachment attachment = null;
        for (MultipartFile multipartFile : multipartFileList) {
            if (multipartFile != null) {
                attachment = uploadAllFile(multipartFile);
            }
        }
        return new Response("file successfully upload", true, attachment);
    }

    private Attachment uploadAllFile(MultipartFile multipartFile) {
        try {
            Attachment savedAttachment = attachmentRepo.save(new Attachment(multipartFile.getOriginalFilename(),
                    multipartFile.getContentType(), multipartFile.getSize()));
            attachmentContentRepo.save(new AttachmentContent(multipartFile.getBytes(), savedAttachment));
            return savedAttachment;
        } catch (NullPointerException | IOException e) {
            return null;
        }
    }

    public void getFile(Long id, HttpServletResponse resp) {
        try {
            Attachment attachment = attachmentRepo.getById(id);
            AttachmentContent attachmentContent = attachmentContentRepo.findAllByAttachment_Id(id);
            resp.setContentType(attachment.getContent());
            FileCopyUtils.copy(attachmentContent.getBytes(), resp.getOutputStream());
            resp.setHeader("content-type", "file:" + attachment.getName());
            resp.getOutputStream().flush();
            resp.getOutputStream().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
