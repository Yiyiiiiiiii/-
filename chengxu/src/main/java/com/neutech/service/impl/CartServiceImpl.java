package com.neutech.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neutech.entity.Cart;
import com.neutech.entity.Product;
import com.neutech.enumeration.CartCheckedEnum;
import com.neutech.enumeration.ProductStatusEnum;
import com.neutech.enumeration.ResultErrorEnum;
import com.neutech.form.CartForm;
import com.neutech.mapper.CartMapper;
import com.neutech.mapper.ProductMapper;
import com.neutech.mapper.UserMapper;
import com.neutech.service.CartService;
import com.neutech.vo.CartVO;
import com.neutech.vo.ProductVO;
import com.neutech.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CartMapper cartMapper;

    @Override
    public ResultVO cartlistByDynamicPageDesc(Integer pageNum, Integer pageSize, CartForm cartForm) {
        //查询条件
        QueryWrapper<Cart> wrapper=new QueryWrapper<>();

        wrapper.eq(cartForm.getUserId()!=null,"user_id",
                cartForm.getUserId());
        wrapper.eq(cartForm.getProductId()!=null,"product_id",
                cartForm.getProductId());
        wrapper.eq(cartForm.getQuantity()!=null,"quantity",
                cartForm.getQuantity());


        //倒序排序条件
        wrapper.orderByDesc("update_time");
        //构建分页
        Page page=new Page(pageNum,pageSize);
        //调用查询得到ipage对象，ipage对象里边放的是实体类集合
        IPage iPage=cartMapper.selectPage(page,wrapper);
        //获取实体类的集合
        List<Cart> cartList=iPage.getRecords();
        //转化为vo集合
        List<CartVO> cartVOList=new ArrayList<>();
        //遍历实体类集合
        for(Cart cart:cartList){
            //创建vo对象并放入集合中
            CartVO cartVO=new CartVO();
            //spring提供的对象拷贝工具方法
            //属性名相同类型是能转化她才能拷贝
            BeanUtils.copyProperties(cart,cartVO);
            //单独处理一下status属性
            cartVO.setChecked(CartCheckedEnum.getInstance(cart.getChecked()));
            cartVOList.add(cartVO);
        }
        //把vo集合再设置回ipage对象里
        iPage.setRecords(cartVOList);
        return ResultVO.success(iPage);
    }


    @Override
    public ResultVO cartadd(CartForm cartForm) {
        //校验状态值是否合法
        if(CartCheckedEnum.getInstance(cartForm.getChecked())==null){
            return ResultVO.error(ResultErrorEnum.PARAM_WRONGFUL.getErrorCode(),"状态值不合法");
        }
        //判断product是否合法
        if(productMapper.selectById(cartForm.getProductId())==null){
            return ResultVO.error(ResultErrorEnum.PARAM_WRONGFUL.getErrorCode(),"product不存在");
        }

        if(userMapper.selectById(cartForm.getUserId())==null){
            return ResultVO.error(ResultErrorEnum.PARAM_WRONGFUL.getErrorCode(),"user不存在");
        }

        //把productForm转成实体类对象
        Cart cart=new Cart();
        BeanUtils.copyProperties(cartForm,cart);

        //单独处理事件
        Date date=new Date();
        cart.setCreateTime(date);
        cart.setUpdateTime(date);

        cartMapper.insert(cart);

        return ResultVO.success();
    }

    @Override
    public ResultVO cartdeleteByIds(Integer[] ids) {
        //Arrays.asList()需要传入一个可变参数，其实就是个数组
        //这个方法返回的ArrayList集合是其内部类，不是之前的ArrayList集合
        //虽然名字相同 但不是一个东西
        //这个内部类的集合长度不允许改变，不可以增加和删除
        List<Integer> idList= Arrays.asList(ids);
        /*for(Integer id:ids){
            idList.add(id);
        }*/
        //如何把数组转化为list集合
        cartMapper.deleteBatchIds(idList);
        return ResultVO.success();
    }

    @Override
    public ResultVO cartupdateById(CartForm cartForm) {
        //参数校验
        //类别id校验,校验类别是否存在
        if(productMapper.selectById(cartForm.getProductId())==null){
            return ResultVO.error(ResultErrorEnum.PARAM_WRONGFUL.getErrorCode(),"productid不存在");
        }

        if(userMapper.selectById(cartForm.getUserId())==null){
            return ResultVO.error(ResultErrorEnum.PARAM_WRONGFUL.getErrorCode(),"userid不存在");
        }
        //状态的值一定要校验合法性
        if(CartCheckedEnum.getInstance(cartForm.getChecked())==null){
            return ResultVO.error(ResultErrorEnum.PARAM_WRONGFUL.getErrorCode(),"状态值不合法");
        }
        //将form转化为实体类
        Cart cart=new Cart();
        BeanUtils.copyProperties(cartForm,cart);

        cart.setUpdateTime(new Date());

        cartMapper.updateById(cart);

        return ResultVO.success();
    }

    @Override
    public ResultVO cartgetById(Integer id) {
        Cart cart = cartMapper.selectById(id);
        if(cart==null){
            return ResultVO.error(1,"商品不存在");
        }
        CartVO cartVO=new CartVO();
        BeanUtils.copyProperties(cart,cartVO);
        //单独处理状态枚举
        cartVO.setChecked(CartCheckedEnum.getInstance(cart.getChecked()));
        return ResultVO.success(cartVO);

    }
}
