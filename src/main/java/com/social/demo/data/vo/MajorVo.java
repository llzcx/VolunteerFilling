package com.social.demo.data.vo;
import com.social.demo.constant.RegConstant;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import java.util.List;
/**
 * @author 周威宇
 */
@Data
public class MajorVo {
    /**
     * 专业id
     */
    private Long majorId;
    /**
     * 院校id
     */
    private Long schoolId;
    /**
     * 专业名称
     */
    private String name;
    /**
     * 所属学院
     */
    private String college;
    /**
     * 选科规则
     */
    private List<SubjectRuleVo> subjectRule;
    /**
     * 招生人数
     */
    private Integer enrollmentNumber;
}
