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
    private Long user_id;
    private String username;
    private String phone;
    private String address;
}
