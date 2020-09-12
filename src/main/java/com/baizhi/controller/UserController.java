package com.baizhi.controller;

import com.baizhi.util.CheckerUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fancz
 * @Title: ${file_name}
 * @date 2020/8/13 19:28
 */
@RestController
@RequestMapping("/test")
public class UserController {
    @RequestMapping("/test")
    public void test1(HttpServletResponse response, HttpServletRequest request) throws IOException {
        List<String> list = (List<String>)request.getSession().getAttribute("list");
        if(CheckerUtil.isEmpty(list)){
            list = new ArrayList<>();
        }
        list.add("zyy");
        request.getSession().setAttribute("list",list);
        response.getWriter().println("sessionid="+request.getSession().getId());
    }

}
