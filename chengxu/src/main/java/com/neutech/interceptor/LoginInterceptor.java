package com.neutech.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neutech.entity.User;
import com.neutech.enumeration.ResultErrorEnum;
import com.neutech.vo.ResultVO;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       // //判断是否在登录状态
        //从session获取登录信息
        HttpSession httpSession=request.getSession();
        User user=(User)httpSession.getAttribute("user");
        if(user==null){
            //没有登录信息 返回给前端告诉他未登录
            ResultVO resultVO=ResultVO.error(ResultErrorEnum.NOT_LOGGED_IN.getErrorCode(),
                    ResultErrorEnum.NOT_LOGGED_IN.getErrorMsg());
            //给响应对象设置响应格式
            response.setContentType("application/json;charset=utf-8");
            //获取输出流
            PrintWriter out=response.getWriter();

            //resultVo对象转化成json格式
            ObjectMapper objectMapper=new ObjectMapper();

            //使用输出流把转化好的json字符串输出
            out.print(objectMapper.writeValueAsString(resultVO));

            //关闭输出流
            out.close();

            //return false 是拦截器的 false表示不要继续调用controller
            return false;
        }
        //表示放行 继续执行controller
        return true;
    }
}
