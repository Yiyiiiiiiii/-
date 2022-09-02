package com.neutech.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CartForm {
    private Integer id;
    @NotNull(message = "用户id不能为空")
    private Integer userId;
    @NotNull(message = "商品id不能为空")
    private Integer productId;
    @NotNull(message = "数量不能为空")
    @Min(value = 0,message = "数量不能小于0")
    private Integer quantity;
    @NotNull(message = "是否选择不能为空")
    private Integer checked;

    public CartForm(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getChecked() {
        return checked;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return "CartForm{" +
                "id=" + id +
                ", userId=" + userId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", checked=" + checked +
                '}';
    }
}
