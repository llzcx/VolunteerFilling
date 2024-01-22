package com.social.demo.data.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;

/**
 * @author 杨世博
 * @date 2024/1/22 15:41
 * @description GradeContent
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GradeContent {
    /**
     * 语文
     */
    private Integer chinese;
    /**
     * 数学
     */
    private Integer math;
    /**
     * 英语
     */
    private Integer english;
    /**
     * 民理
     */
    private Integer ethnic;
    /**
     * 计算机
     */
    private Integer computer;
    /**
     * 体育
     */
    private Integer pe;
}
