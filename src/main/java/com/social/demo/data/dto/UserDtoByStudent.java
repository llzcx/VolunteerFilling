package com.social.demo.data.dto;

import com.social.demo.constant.RegConstant;
import com.social.demo.data.bo.ConsigneeBo;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
     * 联系电话
     */
    @Pattern(regexp= RegConstant.PHONE)
    private String phone;
    /**
     * 收件信息
     */
    private ConsigneeBo consigneeBo;
}
