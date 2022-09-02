package com.neutech.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neutech.entity.Product;
import com.neutech.enumeration.ProductStatusEnum;
import com.neutech.enumeration.ResultErrorEnum;
import com.neutech.form.ProductForm;
import com.neutech.mapper.CategoryMapper;
import com.neutech.mapper.ProductMapper;
import com.neutech.service.ProductService;
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
public class ProductServiceImpl implements ProductService {


    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ResultVO listByDynamicPageDesc(Integer pageNum,Integer pageSize,ProductForm productForm) {
        //查询条件
        QueryWrapper<Product> wrapper=new QueryWrapper<>();
        wrapper.ne("status", ProductStatusEnum.DELETED.getStatusCode());

        wrapper.eq(productForm.getCategoryId()!=null,"category_id",
                productForm.getCategoryId());
        wrapper.like(productForm.getName()!=null,"name",
                productForm.getName());
        wrapper.eq(productForm.getPrice()!=null,"price",
                productForm.getPrice());
        //倒序排序条件
        wrapper.orderByDesc("update_time");
        //构建分页
        Page page=new Page(pageNum,pageSize);
        //调用查询得到ipage对象，ipage对象里边放的是实体类集合
        IPage iPage=productMapper.selectPage(page,wrapper);
        //获取实体类的集合
        List<Product> productList=iPage.getRecords();
        //转化为vo集合
        List<ProductVO> productVOList=new ArrayList<>();
        //遍历实体类集合
        for(Product product:productList){
            //创建vo对象并放入集合中
            ProductVO productVO=new ProductVO();
            //spring提供的对象拷贝工具方法
            //属性名相同类型是能转化她才能拷贝
            BeanUtils.copyProperties(product,productVO);
            //单独处理一下status属性
            productVO.setStatus(ProductStatusEnum.getInstance(product.getStatus()));
            productVOList.add(productVO);
        }
        //把vo集合再设置回ipage对象里
        iPage.setRecords(productVOList);
        return ResultVO.success(iPage);
    }

    @Override
    public ResultVO add(ProductForm productForm) {
        //校验状态值是否合法
        if(ProductStatusEnum.getInstance(productForm.getStatus())==null){
            return ResultVO.error(ResultErrorEnum.PARAM_WRONGFUL.getErrorCode(),"状态值不合法");
        }
        //判断类别是否合法
        if(categoryMapper.selectById(productForm.getCategoryId())==null){
            return ResultVO.error(ResultErrorEnum.PARAM_WRONGFUL.getErrorCode(),"类别不存在");
        }

        //把productForm转成实体类对象
        Product product=new Product();
        BeanUtils.copyProperties(productForm,product);

        //单独处理事件
        Date date=new Date();
        product.setCreateTime(date);
        product.setUpdateTime(date);

        productMapper.insert(product);

        return ResultVO.success();
    }

    @Override
    public ResultVO deleteByIds(Integer[] ids) {
        //Arrays.asList()需要传入一个可变参数，其实就是个数组
        //这个方法返回的ArrayList集合是其内部类，不是之前的ArrayList集合
        //虽然名字相同 但不是一个东西
        //这个内部类的集合长度不允许改变，不可以增加和删除
        List<Integer> idList= Arrays.asList(ids);
        /*for(Integer id:ids){
            idList.add(id);
        }*/
        //如何把数组转化为list集合
        productMapper.deleteBatchIds(idList);
        return ResultVO.success();
    }

    @Override
    public ResultVO updateById(ProductForm productForm) {
        //参数校验
        //类别id校验,校验类别是否存在
        if(categoryMapper.selectById(productForm.getCategoryId())==null){
            return ResultVO.error(ResultErrorEnum.PARAM_WRONGFUL.getErrorCode(),"类别不存在");
        }
        //状态的值一定要校验合法性
        if(ProductStatusEnum.getInstance(productForm.getStatus())==null){
            return ResultVO.error(ResultErrorEnum.PARAM_WRONGFUL.getErrorCode(),"状态值不合法");
        }

        //将form转化为实体类
        Product product=new Product();
        BeanUtils.copyProperties(productForm,product);

        //处理两个时间
        //createtime表示数据的增加的时间，这是在修改方法，增加时间不要改
        //createTime属性不赋值，默认为null即可
        //createTime得改
        product.setUpdateTime(new Date());

        productMapper.updateById(product);

        return ResultVO.success();
    }

    @Override
    public ResultVO getById(Integer id) {
        Product product = productMapper.selectById(id);
        if(product==null){
            return ResultVO.error(1,"商品不存在");
        }
        ProductVO productVO=new ProductVO();
        BeanUtils.copyProperties(product,productVO);
        //单独处理状态枚举
        productVO.setStatus(ProductStatusEnum.getInstance(product.getStatus()));
        return ResultVO.success(productVO);

    }
}
