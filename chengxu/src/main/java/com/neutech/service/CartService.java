package com.neutech.service;

import com.neutech.form.CartForm;
import com.neutech.vo.ResultVO;

public interface CartService {
    ResultVO cartlistByDynamicPageDesc(Integer pageNum, Integer pageSize, CartForm cartForm);

    ResultVO cartgetById(Integer id);

    ResultVO cartadd(CartForm cartForm);

    ResultVO cartdeleteByIds(Integer[] ids);

    ResultVO cartupdateById(CartForm cartForm);
}
