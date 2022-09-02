package com.neutech.controller;

import com.neutech.enumeration.ResultErrorEnum;
import com.neutech.vo.ResultVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/upload")
public class UploadController {

    @Value("${img.upload-prefix}")
    private String UploadPrefix;

    @PostMapping("/imgUpload")
    public ResultVO imgUpload(MultipartFile file){
        if(file==null){
            return ResultVO.error(ResultErrorEnum.NO_FILE_ERROR.getErrorCode(),
                    ResultErrorEnum.NO_FILE_ERROR.getErrorMsg());
        }
        //把文件存到磁盘上
        //暂时村自己电脑磁盘上
        //生成文件名,不重复就行
        String fileName=UUID.randomUUID().toString()+file.getOriginalFilename().substring(
                file.getOriginalFilename().lastIndexOf("."));
        //构建一个完整的文件路径
        try {
            file.transferTo(new File(UploadPrefix+fileName));
            return ResultVO.success("http://localhost:8088/"+fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return ResultVO.error(ResultErrorEnum.UPLOAD_FAIL.getErrorCode(),
                    ResultErrorEnum.UPLOAD_FAIL.getErrorMsg());
        }

    }
}
