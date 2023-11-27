package com.social.demo.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 杨世博
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoByStudent {
    /**
     * 姓名
     */
    private String username;
    /**
     * 家庭地址
     */
    private String address;
    /**
     * 联系电话
     */
    private String phone;
}
