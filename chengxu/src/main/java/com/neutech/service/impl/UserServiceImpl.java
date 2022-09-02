package com.neutech.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neutech.entity.User;
import com.neutech.enumeration.ResultErrorEnum;
import com.neutech.enumeration.UserStatusEnum;
import com.neutech.form.UserForm;
import com.neutech.mapper.UserMapper;
import com.neutech.service.UserService;
import com.neutech.vo.ResultVO;
import com.neutech.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public ResultVO login(User user, HttpSession httpSession) {
        //调用数据库查询 根据用户名查询用户数据
        QueryWrapper<User> wrapper=new QueryWrapper<>();
        wrapper.eq("username",user.getUsername());
        //userDate是数据库里查询出来的数据
        //用户不存在的话 userDate为null
        User userDate = userMapper.selectOne(wrapper);
        if(userDate==null){
            return ResultVO.error(ResultErrorEnum.USERNAME_NOT_EXISTS.getErrorCode(),
                    ResultErrorEnum.USERNAME_NOT_EXISTS.getErrorMsg());
        }
        //校验密码
        //userdate是数据库查出来的，user是前端传来的
        if(!userDate.getPassword().equals(user.getPassword())){
            return ResultVO.error(ResultErrorEnum.PASSWORD_INCORRECT.getErrorCode(),
                    ResultErrorEnum.PASSWORD_INCORRECT.getErrorMsg());
        }
        //保存登录信息 将登录信息保存到session
        httpSession.setAttribute("user",userDate);

        //处理密码
        userDate.setPassword("");
        //响应成功的时候把userData数据带上
        return ResultVO.success(userDate);
    }

    //me
    @Override
    public ResultVO userlistByDynamicPageDesc(Integer pageNum, Integer pageSize, UserForm userForm) {
        //查询条件
        QueryWrapper<User> wrapper=new QueryWrapper<>();

        //wrapper.ne("role", UserStatusEnum.BOSS.getStatusCode());
        //wrapper.ne("role", UserStatusEnum.COMMONUSER.getStatusCode());

        wrapper.like(userForm.getUsername()!=null,"username",
                userForm.getUsername());
        //倒序排序条件
        wrapper.orderByDesc("update_time");
        //构建分页
        Page page=new Page(pageNum,pageSize);
        //调用查询得到ipage对象，ipage对象里边放的是实体类集合
        IPage iPage=userMapper.selectPage(page,wrapper);
        //获取实体类的集合
        List<User> userList=iPage.getRecords();
        //转化为vo集合
        List<UserVO> userVOList=new ArrayList<>();
        //遍历实体类集合
        for(User user:userList){
            //创建vo对象并放入集合中
            UserVO userVO=new UserVO();
            //spring提供的对象拷贝工具方法
            //属性名相同类型是能转化她才能拷贝
            BeanUtils.copyProperties(user,userVO);
            //单独处理一下role属性
            userVO.setRole(UserStatusEnum.getInstance(user.getRole()));
            userVOList.add(userVO);
        }
        //把vo集合再设置回ipage对象里
        iPage.setRecords(userVOList);
        return ResultVO.success(iPage);
    }

    @Override
    public ResultVO useradd(UserForm userForm) {
        //校验状态值是否合法
        if(UserStatusEnum.getInstance(userForm.getRole())==null){
            return ResultVO.error(ResultErrorEnum.PARAM_WRONGFUL.getErrorCode(),"状态值不合法");
        }

        //把userForm转成实体类对象
        User user=new User();
        BeanUtils.copyProperties(userForm,user);

        //单独处理事件
        Date date=new Date();
        user.setCreateTime(date);
        user.setUpdateTime(date);

        userMapper.insert(user);

        return ResultVO.success();
    }

    @Override
    public ResultVO userdeleteByIds(Integer[] ids) {
        //Arrays.asList()需要传入一个可变参数，其实就是个数组
        //这个方法返回的ArrayList集合是其内部类，不是之前的ArrayList集合
        //虽然名字相同 但不是一个东西
        //这个内部类的集合长度不允许改变，不可以增加和删除
        List<Integer> idList= Arrays.asList(ids);
        /*for(Integer id:ids){
            idList.add(id);
        }*/
        //如何把数组转化为list集合
        userMapper.deleteBatchIds(idList);
        return ResultVO.success();
    }

    @Override
    public ResultVO userupdateById(UserForm userForm) {
        //参数校验
        //状态的值一定要校验合法性
        if(UserStatusEnum.getInstance(userForm.getRole())==null){
            return ResultVO.error(ResultErrorEnum.PARAM_WRONGFUL.getErrorCode(),"状态值不合法");
        }

        //将form转化为实体类
        User user=new User();
        BeanUtils.copyProperties(userForm,user);

        //处理两个时间
        //createtime表示数据的增加的时间，这是在修改方法，增加时间不要改
        //createTime属性不赋值，默认为null即可
        //createTime得改
        user.setUpdateTime(new Date());

        userMapper.updateById(user);

        return ResultVO.success();
    }

    @Override
    public ResultVO usergetById(Integer id) {
        User user = userMapper.selectById(id);
        if(user==null){
            return ResultVO.error(1,"用户不存在");
        }
        UserVO userVO=new UserVO();
        BeanUtils.copyProperties(user,userVO);
        //单独处理状态枚举
        userVO.setRole(UserStatusEnum.getInstance(user.getRole()));
        return ResultVO.success(userVO);

    }

    //me
}
