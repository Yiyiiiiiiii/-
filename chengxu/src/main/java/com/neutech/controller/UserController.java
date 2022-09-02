package com.neutech.controller;

import com.neutech.entity.User;
import com.neutech.enumeration.ResultErrorEnum;
import com.neutech.form.UserForm;
import com.neutech.service.UserService;
import com.neutech.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResultVO login(@RequestBody User user, HttpSession httpSession){
        //登录不需要校验
        return userService.login(user,httpSession);
    }

    //me

    @GetMapping("/userlistByDynamicPageDesc")
    public ResultVO userlistByDynamicPageDesc(@RequestParam(defaultValue = "1") Integer pageNum,
                                          @RequestParam(defaultValue = "10") Integer pageSize,
                                          UserForm userForm){


        return (ResultVO) userService.userlistByDynamicPageDesc(pageNum,pageSize,userForm);
    }

    @GetMapping("/usergetById")
    public ResultVO usergetById(Integer id){
        if (id==null){
            return ResultVO.error(ResultErrorEnum.PARAM_FORMAT_ERROR.getErrorCode(),
                    "id为必传项");
        }
        return userService.usergetById(id);
    }


    @PostMapping("/useradd")
    public ResultVO useradd(@RequestBody @Valid UserForm userForm,
                        BindingResult bindingResult){
        System.out.println(userForm);
        if(bindingResult.hasErrors()){
            return ResultVO.error(ResultErrorEnum.PARAM_FORMAT_ERROR.getErrorCode(),
                    bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        return userService.useradd(userForm);
    }

    @GetMapping("/userdeleteByIds")
    public ResultVO userdeleteByIds(@RequestParam(name="id",required = false)
                                        Integer[] ids){
        //校验数据
        if(ids==null||ids.length==0){
            return  ResultVO.error(ResultErrorEnum.PARAM_FORMAT_ERROR.getErrorCode(),
                    "id为必传项");
        }
        return userService.userdeleteByIds(ids);
    }

    @PostMapping("userupdateByIdAllArgs")
    public ResultVO userupdateByIdAllArgs(@RequestBody @Valid UserForm userForm,
                                      BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return ResultVO.error(ResultErrorEnum.PARAM_FORMAT_ERROR.getErrorCode(),
                    bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        if(userForm.getId()==null){
            return ResultVO.error(ResultErrorEnum.PARAM_FORMAT_ERROR.getErrorCode(),
                    "修改接口id不能为空");
        }
        return userService.userupdateById(userForm);
    }

    //me
}
