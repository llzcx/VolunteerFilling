package com.social.demo.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
@Data
public class AdmissionsMajor {
    /**
     * 专业Id
     */
    @TableId(value = "major_id", type = IdType.AUTO)
    private Long majorId;
    /**
     * 专业名字
     */
    private String name;
    /**
     * 所属学院
     */
    private String college;
    /**
     * 剩余人数
     */
    private Integer enrollmentNumber;
    /**
     * 匹配方式
     */
    private Integer mateWay;
    /**
     * 志愿时间段Id
     */
    private Long timeId;
    /**
     * 分类
     */
    private String classification;

}
