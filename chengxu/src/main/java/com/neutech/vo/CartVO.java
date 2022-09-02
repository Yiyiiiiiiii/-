package com.neutech.vo;

import com.neutech.enumeration.CartCheckedEnum;

public class CartVO {

    private Integer id;
    private Integer userId;
    private Integer productId;
    private Integer quantity;
    private CartCheckedEnum checked;

    public CartVO(){

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

    public CartCheckedEnum getChecked() {
        return checked;
    }

    public void setChecked(CartCheckedEnum checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return "CartVO{" +
                "id=" + id +
                ", userId=" + userId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", checked=" + checked +
                '}';
    }
}
