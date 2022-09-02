package com.neutech.controller;

import com.neutech.enumeration.ResultErrorEnum;
import com.neutech.form.ProductForm;
import com.neutech.form.ShippingForm;
import com.neutech.service.ShippingService;
import com.neutech.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/shipping")
public class ShippingController {

    @Autowired
    private ShippingService shippingService;

    @GetMapping("/shippinglistByDynamicPageDesc")
    public ResultVO shippinglistByDynamicPageDesc(@RequestParam(defaultValue = "1") Integer pageNum,
                                          @RequestParam(defaultValue = "10") Integer pageSize,
                                          ShippingForm shippingForm){


        return shippingService.shippinglistByDynamicPageDesc(pageNum,pageSize,shippingForm);
    }

    @GetMapping("/shippinggetById")
    public ResultVO shippinggetById(Integer id){
        if (id==null){
            return ResultVO.error(ResultErrorEnum.PARAM_FORMAT_ERROR.getErrorCode(),
                    "id为必传项");
        }
        return shippingService.shippinggetById(id);
    }



    @PostMapping("/shippingadd")
    public ResultVO shippingadd(@RequestBody @Valid ShippingForm shippingForm,
                        BindingResult bindingResult){
        System.out.println(shippingForm);
        if(bindingResult.hasErrors()){
            return ResultVO.error(ResultErrorEnum.PARAM_FORMAT_ERROR.getErrorCode(),
                    bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        return shippingService.shippingadd(shippingForm);
    }

    @GetMapping("/shippingdeleteByIds")
    public ResultVO shippingdeleteByIds(@RequestParam(name="id",required = false)
                                        Integer[] ids){
        //校验数据
        if(ids==null||ids.length==0){
            return  ResultVO.error(ResultErrorEnum.PARAM_FORMAT_ERROR.getErrorCode(),
                    "id为必传项");
        }
        return shippingService.shippingdeleteByIds(ids);
    }

    @PostMapping("shippingupdateByIdAllArgs")
    public ResultVO shippingupdateByIdAllArgs(@RequestBody @Valid ShippingForm shippingForm,
                                      BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return ResultVO.error(ResultErrorEnum.PARAM_FORMAT_ERROR.getErrorCode(),
                    bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        if(shippingForm.getId()==null){
            return ResultVO.error(ResultErrorEnum.PARAM_FORMAT_ERROR.getErrorCode(),
                    "修改接口id不能为空");
        }
        return shippingService.shippingupdateById(shippingForm);
    }

}
