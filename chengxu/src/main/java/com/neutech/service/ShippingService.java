package com.neutech.service;

import com.neutech.form.ShippingForm;
import com.neutech.vo.ResultVO;

public interface ShippingService {
    ResultVO shippinglistByDynamicPageDesc(Integer pageNum, Integer pageSize, ShippingForm shippingForm);

    ResultVO shippinggetById(Integer id);

    ResultVO shippingadd(ShippingForm shippingForm);

    ResultVO shippingdeleteByIds(Integer[] ids);

    ResultVO shippingupdateById(ShippingForm shippingForm);

}
