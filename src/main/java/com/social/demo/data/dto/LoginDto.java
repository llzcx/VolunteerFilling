package com.social.demo.data.dto;

import com.social.demo.constant.RegConstant;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 陈翔
 */
@Data
public class LoginDto implements Serializable {
    /**
     * 登录类型
     * 限制枚举范围为1~2
     */
    @NotNull
    @Max(value = 2)
    @Min(value = 1)
    private Integer type;

    /**
     * 普通账号密码登录
     */
    @NotNull
    @Pattern(regexp=RegConstant.USER_NUMBER)
    private String userNumber;

    @NotNull
    @Pattern(regexp = RegConstant.PASSWORD)
    private String password;

    public LoginDto() {
    }

    public LoginDto(Integer type, String userNumber, String password) {
        this.type = type;
        this.userNumber = userNumber;
        this.password = password;
    }
}
