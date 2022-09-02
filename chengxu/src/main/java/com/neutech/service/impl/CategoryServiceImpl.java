package com.neutech.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neutech.entity.Category;
import com.neutech.entity.Product;
import com.neutech.enumeration.CategoryStatusEnum;
import com.neutech.enumeration.ProductStatusEnum;
import com.neutech.enumeration.ResultErrorEnum;
import com.neutech.form.CategoryForm;
import com.neutech.mapper.CategoryMapper;
import com.neutech.mapper.UserMapper;
import com.neutech.service.CategoryService;
import com.neutech.vo.CategoryVO;
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
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;


    @Override
    public ResultVO listByStruct()
    {
        return ResultVO.success(categoryMapper.listParent());
    }


    //me
    @Override
    public ResultVO categorylistByDynamicPageDesc(Integer pageNum, Integer pageSize, CategoryForm categoryForm) {
        //查询条件
        QueryWrapper<Category> wrapper=new QueryWrapper<>();

        wrapper.eq(categoryForm.getParentId()!=null,"parent_id",
                categoryForm.getParentId());

        /*wrapper.eq(categoryForm.getSortOrder()!=null,"SortOrder",
                categoryForm.getSortOrder());*/

        wrapper.like(categoryForm.getName()!=null,"name",
                categoryForm.getName());

        //倒序排序条件
        wrapper.orderByAsc("update_time");
        //构建分页
        Page page=new Page(pageNum,pageSize);
        //调用查询得到ipage对象，ipage对象里边放的是实体类集合
        IPage iPage=categoryMapper.selectPage(page,wrapper);
        //获取实体类的集合
        List<Category> categoryList=iPage.getRecords();
        //转化为vo集合
        List<CategoryVO> categoryVOList=new ArrayList<>();
        //遍历实体类集合
        for(Category category:categoryList){
            //创建vo对象并放入集合中
            CategoryVO categoryVO = new CategoryVO();
            //spring提供的对象拷贝工具方法
            //属性名相同类型是能转化她才能拷贝
            BeanUtils.copyProperties(category,categoryVO);
            //单独处理一下status属性
            categoryVO.setStatus(CategoryStatusEnum.getInstance(category.getStatus()));
            categoryVOList.add(categoryVO);
        }
        //把vo集合再设置回ipage对象里
        iPage.setRecords(categoryVOList);
        return ResultVO.success(iPage);
    }

    @Override
    public ResultVO categorygetById(Integer id) {
        Category category = categoryMapper.selectById(id);
        if(category==null){
            return ResultVO.error(1,"商品不存在");
        }
        CategoryVO categoryVO = new CategoryVO();
        BeanUtils.copyProperties(category,categoryVO);
        //单独处理状态枚举
        categoryVO.setStatus(CategoryStatusEnum.getInstance(category.getStatus()));
        return ResultVO.success(categoryVO);

    }

    @Override
    public ResultVO categoryadd(CategoryForm categoryForm) {
        //校验状态值是否合法
        if(CategoryStatusEnum.getInstance(categoryForm.getStatus())==null){
            return ResultVO.error(ResultErrorEnum.PARAM_WRONGFUL.getErrorCode(),"状态值不合法");
        }
        //把productForm转成实体类对象
        Category category=new Category();
        BeanUtils.copyProperties(categoryForm,category);

        //单独处理事件
        Date date=new Date();
        category.setCreateTime(date);
        category.setUpdateTime(date);

        categoryMapper.insert(category);

        return ResultVO.success();
    }

    @Override
    public ResultVO categorydeleteByIds(Integer[] ids) {
        List<Integer> idList= Arrays.asList(ids);
        categoryMapper.deleteBatchIds(idList);
        return ResultVO.success();
    }

    @Override
    public ResultVO categoryupdateById(CategoryForm categoryForm) {
        //参数校验
        //状态的值一定要校验合法性
        if(CategoryStatusEnum.getInstance(categoryForm.getStatus())==null){
            return ResultVO.error(ResultErrorEnum.PARAM_WRONGFUL.getErrorCode(),"状态值不合法");
        }

        //将form转化为实体类
        Category category=new Category();
        BeanUtils.copyProperties(categoryForm,category);

        //处理两个时间
        //createtime表示数据的增加的时间，这是在修改方法，增加时间不要改
        //createTime属性不赋值，默认为null即可
        //createTime得改
        category.setUpdateTime(new Date());

        categoryMapper.updateById(category);

        return ResultVO.success();
    }
}
