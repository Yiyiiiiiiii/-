package com.neutech.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ProductForm {
    private Integer id;
    @NotNull(message = "类别id不能为空")
    private Integer categoryId;
    @NotBlank(message = "商品名不能为空")
    private String name;
    @NotBlank(message = "简介不能为空")
    private String subtitle;
    @NotBlank(message = "主图地址不能为空")
    private String mainImage;
    @NotBlank(message = "从属图地址不能为空")
    private String subImages;
    @NotBlank(message = "详情不能为空")
    private String detail;
    @NotNull(message = "价格不能为空")
    @Min(value = 0,message = "价格不能小于0")
    private BigDecimal price;
    @NotNull(message = "库存不能为空")
    @Min(value = 0,message = "库存不能小于0")
    private Integer stock;
    @NotNull(message = "状态不能为空")
    private Integer status;

    public ProductForm() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public String getSubImages() {
        return subImages;
    }

    public void setSubImages(String subImages) {
        this.subImages = subImages;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ProductForm{" +
                "id=" + id +
                ", categoryId=" + categoryId +
                ", name='" + name + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", mainImage='" + mainImage + '\'' +
                ", subImages='" + subImages + '\'' +
                ", detail='" + detail + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", status=" + status +
                '}';
    }
}
