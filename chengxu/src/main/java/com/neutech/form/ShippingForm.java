package com.neutech.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ShippingForm {

    private Integer id;
    @NotNull(message = "用户id不能为空")
    private Integer userId;
    @NotBlank(message = "收件人不能为空")
    private String receiverName;
    @NotBlank(message = "买方电话不能为空")
    private String receiverPhone;
    @NotBlank(message = "买方省份不能为空")
    private String receiverProvince;
    @NotBlank(message = "买方城市不能为空")
    private String receiverCity;
    @NotBlank(message = "买方区县不能为空")
    private String receiverDistrict;
    @NotBlank(message = "买方具体地址不能为空")
    private String receiverAddress;
    @NotBlank(message = "买方邮编不能为空")
    private String receiverZip;

    public ShippingForm(){

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

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getReceiverProvince() {
        return receiverProvince;
    }

    public void setReceiverProvince(String receiverProvince) {
        this.receiverProvince = receiverProvince;
    }

    public String getReceiverCity() {
        return receiverCity;
    }

    public void setReceiverCity(String receiverCity) {
        this.receiverCity = receiverCity;
    }

    public String getReceiverDistrict() {
        return receiverDistrict;
    }

    public void setReceiverDistrict(String receiverDistrict) {
        this.receiverDistrict = receiverDistrict;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getReceiverZip() {
        return receiverZip;
    }

    public void setReceiverZip(String receiverZip) {
        this.receiverZip = receiverZip;
    }

    @Override
    public String toString() {
        return "ShippingForm{" +
                "id=" + id +
                ", userId=" + userId +
                ", receiverName='" + receiverName + '\'' +
                ", receiverPhone='" + receiverPhone + '\'' +
                ", receiverProvince='" + receiverProvince + '\'' +
                ", receiverCity='" + receiverCity + '\'' +
                ", receiverDistrict='" + receiverDistrict + '\'' +
                ", receiverAddress='" + receiverAddress + '\'' +
                ", receiverZip='" + receiverZip + '\'' +
                '}';
    }
}
