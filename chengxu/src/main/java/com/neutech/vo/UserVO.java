package com.neutech.vo;

import com.neutech.enumeration.UserStatusEnum;

public class UserVO {

    private Integer id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String headImg;
    private UserStatusEnum role;

    public UserVO(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public UserStatusEnum getRole() {
        return role;
    }

    public void setRole(UserStatusEnum role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserVO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", headImg='" + headImg + '\'' +
                ", role=" + role +
                '}';
    }
}
