package com.neutech.controller;

import com.neutech.enumeration.ResultErrorEnum;
import com.neutech.form.CategoryForm;
import com.neutech.form.ProductForm;
import com.neutech.service.CategoryService;
import com.neutech.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/listByStruct")
    public ResultVO listByStruct(){
        return categoryService.listByStruct();
    }

//me
    @GetMapping("/categorylistByDynamicPageDesc")
    public ResultVO categorylistByDynamicPageDesc(@RequestParam(defaultValue = "1") Integer pageNum,
                                          @RequestParam(defaultValue = "10") Integer pageSize,
                                          CategoryForm categoryForm){


        return categoryService.categorylistByDynamicPageDesc(pageNum,pageSize,categoryForm);
    }

    @GetMapping("/categorygetById")
    public ResultVO categorygetById(Integer id){
        if (id==null){
            return ResultVO.error(ResultErrorEnum.PARAM_FORMAT_ERROR.getErrorCode(),
                    "id为必传项");
        }
        return categoryService.categorygetById(id);
    }


    @PostMapping("/categoryadd")
    public ResultVO categoryadd(@RequestBody @Valid CategoryForm categoryForm,
                        BindingResult bindingResult){
        System.out.println(categoryForm);
        if(bindingResult.hasErrors()){
            return ResultVO.error(ResultErrorEnum.PARAM_FORMAT_ERROR.getErrorCode(),
                    bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        return categoryService.categoryadd(categoryForm);
    }

    @GetMapping("/categorydeleteByIds")
    public ResultVO categorydeleteByIds(@RequestParam(name="id",required = false)
                                        Integer[] ids){
        //校验数据
        if(ids==null||ids.length==0){
            return  ResultVO.error(ResultErrorEnum.PARAM_FORMAT_ERROR.getErrorCode(),
                    "id为必传项");
        }
        return categoryService.categorydeleteByIds(ids);
    }

    @PostMapping("categoryupdateByIdAllArgs")
    public ResultVO categoryupdateByIdAllArgs(@RequestBody @Valid CategoryForm categoryForm,
                                      BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return ResultVO.error(ResultErrorEnum.PARAM_FORMAT_ERROR.getErrorCode(),
                    bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        if(categoryForm.getId()==null){
            return ResultVO.error(ResultErrorEnum.PARAM_FORMAT_ERROR.getErrorCode(),
                    "修改接口id不能为空");
        }
        return categoryService.categoryupdateById(categoryForm);
    }
}
