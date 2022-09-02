package com.neutech.service;

import com.neutech.entity.User;
import com.neutech.form.UserForm;
import com.neutech.vo.ResultVO;

import javax.servlet.http.HttpSession;

public interface UserService {
    ResultVO login(User user, HttpSession httpSession);

    ResultVO useradd(UserForm userForm);

    ResultVO userdeleteByIds(Integer[] ids);

    ResultVO userupdateById(UserForm userForm);

    ResultVO usergetById(Integer id);

    Object userlistByDynamicPageDesc(Integer pageNum, Integer pageSize, UserForm userForm);
}
