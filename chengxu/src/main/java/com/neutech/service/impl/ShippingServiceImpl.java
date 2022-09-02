package com.neutech.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neutech.entity.Product;
import com.neutech.entity.Shipping;
import com.neutech.enumeration.ProductStatusEnum;
import com.neutech.enumeration.ResultErrorEnum;
import com.neutech.form.ShippingForm;
import com.neutech.mapper.ShippingMapper;
import com.neutech.mapper.UserMapper;
import com.neutech.service.ShippingService;
import com.neutech.vo.ProductVO;
import com.neutech.vo.ResultVO;
import com.neutech.vo.ShippingVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Service
public class ShippingServiceImpl implements ShippingService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ShippingMapper shippingMapper;


    @Override
    public ResultVO shippinglistByDynamicPageDesc(Integer pageNum, Integer pageSize, ShippingForm shippingForm) {
        //查询条件
        QueryWrapper<Shipping> wrapper=new QueryWrapper<>();

        wrapper.eq(shippingForm.getUserId()!=null,"user_id",
                shippingForm.getUserId());
        wrapper.like(shippingForm.getReceiverName()!=null,"Receivername",
                shippingForm.getReceiverName());

        //倒序排序条件
        wrapper.orderByDesc("update_time");
        //构建分页
        Page page=new Page(pageNum,pageSize);
        //调用查询得到ipage对象，ipage对象里边放的是实体类集合
        IPage iPage=shippingMapper.selectPage(page,wrapper);
        //获取实体类的集合
        List<Shipping> shippingList=iPage.getRecords();
        //转化为vo集合
        List<ShippingVO> shippingVOList=new ArrayList<>();
        //遍历实体类集合
        for(Shipping shipping:shippingList){
            //创建vo对象并放入集合中
            ShippingVO shippingVO=new ShippingVO();
            //spring提供的对象拷贝工具方法
            //属性名相同类型是能转化她才能拷贝
            BeanUtils.copyProperties(shipping,shippingVO);
            shippingVOList.add(shippingVO);
        }
        //把vo集合再设置回ipage对象里
        iPage.setRecords(shippingVOList);
        return ResultVO.success(iPage);
    }

    @Override
    public ResultVO shippinggetById(Integer id) {
        Shipping shipping = shippingMapper.selectById(id);
        if(shipping==null){
            return ResultVO.error(1,"商品不存在");
        }
        ShippingVO shippingVO=new ShippingVO();
        BeanUtils.copyProperties(shipping,shippingVO);
        return ResultVO.success(shippingVO);

    }



    @Override
    public ResultVO shippingadd(ShippingForm shippingForm) {
        //判断类别是否合法
        if(userMapper.selectById(shippingForm.getUserId())==null){
            return ResultVO.error(ResultErrorEnum.PARAM_WRONGFUL.getErrorCode(),"用户不存在");
        }

        //把productForm转成实体类对象
        Shipping shipping = new Shipping();
        BeanUtils.copyProperties(shippingForm,shipping);

        //单独处理事件
        Date date=new Date();
        shipping.setCreateTime(date);
        shipping.setUpdateTime(date);

        shippingMapper.insert(shipping);

        return ResultVO.success();
    }

    @Override
    public ResultVO shippingdeleteByIds(Integer[] ids) {
        List<Integer> idList= Arrays.asList(ids);
        shippingMapper.deleteBatchIds(idList);
        return ResultVO.success();
    }

    @Override
    public ResultVO shippingupdateById(ShippingForm shippingForm) {
        //参数校验
        //类别id校验,校验类别是否存在
        if(userMapper.selectById(shippingForm.getUserId())==null){
            return ResultVO.error(ResultErrorEnum.PARAM_WRONGFUL.getErrorCode(),"类别不存在");
        }

        //将form转化为实体类
        Shipping shipping = new Shipping();
        BeanUtils.copyProperties(shippingForm,shipping);

        //处理两个时间
        //createtime表示数据的增加的时间，这是在修改方法，增加时间不要改
        //createTime属性不赋值，默认为null即可
        //createTime得改
        shipping.setUpdateTime(new Date());

        shippingMapper.updateById(shipping);

        return ResultVO.success();
    }
}
