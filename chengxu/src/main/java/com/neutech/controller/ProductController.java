package com.neutech.controller;

import com.neutech.entity.Product;
import com.neutech.enumeration.ResultErrorEnum;
import com.neutech.form.ProductForm;
import com.neutech.service.ProductService;
import com.neutech.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/product")
//@CrossOrigin //处理跨域问题
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/listByDynamicPageDesc")
    public ResultVO listByDynamicPageDesc(@RequestParam(defaultValue = "1") Integer pageNum,
                           @RequestParam(defaultValue = "10") Integer pageSize,
                                          ProductForm productForm){


        return productService.listByDynamicPageDesc(pageNum,pageSize,productForm);
    }

    @GetMapping("/getById")
    public ResultVO getById(Integer id){
        if (id==null){
            return ResultVO.error(ResultErrorEnum.PARAM_FORMAT_ERROR.getErrorCode(),
                    "id为必传项");
        }
        return productService.getById(id);
    }


    @PostMapping("/add")
    public ResultVO add(@RequestBody @Valid ProductForm productForm,
            BindingResult bindingResult){
        System.out.println(productForm);
        if(bindingResult.hasErrors()){
            return ResultVO.error(ResultErrorEnum.PARAM_FORMAT_ERROR.getErrorCode(),
                    bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        return productService.add(productForm);
    }

    @GetMapping("/deleteByIds")
    public ResultVO deleteByIds(@RequestParam(name="id",required = false)
                                            Integer[] ids){
        //校验数据
        if(ids==null||ids.length==0){
            return  ResultVO.error(ResultErrorEnum.PARAM_FORMAT_ERROR.getErrorCode(),
                    "id为必传项");
        }
        return productService.deleteByIds(ids);
    }

    @PostMapping("updateByIdAllArgs")
    public ResultVO updateByIdAllArgs(@RequestBody @Valid ProductForm productForm,
                                      BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return ResultVO.error(ResultErrorEnum.PARAM_FORMAT_ERROR.getErrorCode(),
                    bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        if(productForm.getId()==null){
            return ResultVO.error(ResultErrorEnum.PARAM_FORMAT_ERROR.getErrorCode(),
                    "修改接口id不能为空");
        }
        return productService.updateById(productForm);
    }

}

