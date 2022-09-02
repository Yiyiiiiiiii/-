package com.neutech.service;

import com.neutech.form.ProductForm;
import com.neutech.vo.ResultVO;


public interface ProductService {
    ResultVO listByDynamicPageDesc(Integer pageNum,Integer pageSize,ProductForm productForm);

    ResultVO add(ProductForm productForm);

    ResultVO deleteByIds(Integer[] ids);

    ResultVO updateById(ProductForm productForm);

    ResultVO getById(Integer id);
}
