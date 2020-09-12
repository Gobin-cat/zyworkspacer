package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author fancz
 * @Title: ${file_name}
 * @date 2020/8/12 22:37
 */
@Data
@Component
//@AllArgsConstructor添加构造方法
public class User implements Serializable {
    private Integer id;
    private String name;
    private String password;
}
