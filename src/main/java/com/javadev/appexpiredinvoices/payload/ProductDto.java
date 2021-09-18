package com.javadev.appexpiredinvoices.payload;

import com.javadev.appexpiredinvoices.entity.Attachment;
import com.javadev.appexpiredinvoices.entity.Category;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ProductDto {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private double price;

    @NotBlank
    private Long categoryId;

    private Long attachmentId;

}
