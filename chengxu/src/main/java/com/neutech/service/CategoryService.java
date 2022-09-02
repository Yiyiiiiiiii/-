package com.neutech.service;

import com.neutech.form.CategoryForm;
import com.neutech.vo.ResultVO;

public interface CategoryService {

    ResultVO listByStruct();

    ResultVO categorylistByDynamicPageDesc(Integer pageNum, Integer pageSize, CategoryForm categoryForm);

    ResultVO categorygetById(Integer id);

    ResultVO categoryadd(CategoryForm categoryForm);

    ResultVO categorydeleteByIds(Integer[] ids);

    ResultVO categoryupdateById(CategoryForm categoryForm);

}
