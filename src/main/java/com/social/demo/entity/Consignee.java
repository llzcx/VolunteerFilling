package com.social.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 收件信息
 *
 * @author 杨世博
 * @date 2023/12/13 21:20
 * @description Consignee
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Consignee {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 收件人
     */
    private String username;
    /**
     * 电话号码
     */
    private String phone;
    /**
     * 收件地址
     */
    private String address;
}
