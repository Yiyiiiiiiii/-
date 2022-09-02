package com.neutech.controller;


import com.neutech.enumeration.ResultErrorEnum;
import com.neutech.form.CartForm;
import com.neutech.form.ProductForm;
import com.neutech.service.CartService;
import com.neutech.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/cartlistByDynamicPageDesc")
    public ResultVO cartlistByDynamicPageDesc(@RequestParam(defaultValue = "1") Integer pageNum,
                                          @RequestParam(defaultValue = "10") Integer pageSize,
                                          CartForm cartForm){


        return (ResultVO) cartService.cartlistByDynamicPageDesc(pageNum,pageSize,cartForm);
    }

    @GetMapping("/cartgetById")
    public ResultVO cartgetById(Integer id){
        if (id==null){
            return ResultVO.error(ResultErrorEnum.PARAM_FORMAT_ERROR.getErrorCode(),
                    "id为必传项");
        }
        return cartService.cartgetById(id);
    }


    @PostMapping("/cartadd")
    public ResultVO cartadd(@RequestBody @Valid CartForm cartForm,
                        BindingResult bindingResult){
        System.out.println(cartForm);
        if(bindingResult.hasErrors()){
            return ResultVO.error(ResultErrorEnum.PARAM_FORMAT_ERROR.getErrorCode(),
                    bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        return cartService.cartadd(cartForm);
    }

    @GetMapping("/cartdeleteByIds")
    public ResultVO cartdeleteByIds(@RequestParam(name="id",required = false)
                                        Integer[] ids){
        //校验数据
        if(ids==null||ids.length==0){
            return  ResultVO.error(ResultErrorEnum.PARAM_FORMAT_ERROR.getErrorCode(),
                    "id为必传项");
        }
        return cartService.cartdeleteByIds(ids);
    }

    @PostMapping("cartupdateByIdAllArgs")
    public ResultVO cartupdateByIdAllArgs(@RequestBody @Valid CartForm cartForm,
                                      BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return ResultVO.error(ResultErrorEnum.PARAM_FORMAT_ERROR.getErrorCode(),
                    bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        if(cartForm.getId()==null){
            return ResultVO.error(ResultErrorEnum.PARAM_FORMAT_ERROR.getErrorCode(),
                    "修改接口id不能为空");
        }
        return cartService.cartupdateById(cartForm);
    }

}
