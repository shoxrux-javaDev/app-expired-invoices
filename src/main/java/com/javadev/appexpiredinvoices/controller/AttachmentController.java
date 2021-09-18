package com.javadev.appexpiredinvoices.controller;

import com.javadev.appexpiredinvoices.payload.Response;
import com.javadev.appexpiredinvoices.service.AttachmentService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api/attach")
public class AttachmentController {

    final AttachmentService attachmentService;

    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @PreAuthorize(value = "hasAnyAuthority('USER','ADMIN')")
    @PostMapping("/upload")
    public HttpEntity<?> uploadFile(@RequestParam List<MultipartFile> multipartFileList) {
        Response response = attachmentService.uploadFile(multipartFileList);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PreAuthorize(value = "hasAnyRole('USER','ADMIN')")
    @PostMapping("/download/{id}")
    public void getFile(@PathVariable Long id, HttpServletResponse resp) {
        attachmentService.getFile(id, resp);
    }
}
