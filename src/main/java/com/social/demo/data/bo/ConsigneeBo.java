package com.social.demo.data.bo;

import com.social.demo.constant.RegConstant;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 杨世博
 * @date 2023/12/13 21:40
 * @description ConsigneeVo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsigneeBo {
    @NotNull
    private String username;
    @Pattern(regexp=RegConstant.PHONE)
    private String phone;
    @NotNull
    private String address;
}
