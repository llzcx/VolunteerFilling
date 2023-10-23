package com.social.demo.dao.repository;

import com.social.demo.common.JsonUtil;
import com.social.demo.data.dto.AddActivityDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

public class ValidationTest {

    // 创建验证器工厂
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    // 创建验证器
    Validator validator = factory.getValidator();

    @Test
    void AddActivityDto(){
        // 创建要验证的对象 11个字段
        AddActivityDto addActivityDto = new AddActivityDto();
        addActivityDto.setName("XX一日游");
        addActivityDto.setDescription("在活动报名和参与过程中，组织方可能会收集参与者的个人信息，" +
                "这些信息仅用于活动的组织和管理目的，并且将按照相关法律法规进行保护。\n");
        List list = List.of("https://example.com/photo1.jpg","https://example.com/photo2.jpg");
        addActivityDto.setPhoto(JsonUtil.object2StringSlice(list));
        addActivityDto.setGeohash("122.4194,37.7749");
        addActivityDto.setType(1);
        addActivityDto.setNumber(50);
        addActivityDto.setFee(50.0f);
        addActivityDto.setFeeType(1);
        addActivityDto.setNotice("参与者应遵守活动组织方的安排和要求。\n" +
                "请勿携带任何违禁物品或危险物品，以确保活动的安全进行。\n" +
                "在活动期间，请遵循现场工作人员的指示并配合工作人员的工作。");
        addActivityDto.setRequireCertified(true);
        addActivityDto.setRequireSex(true);

        // 执行验证
        Set<ConstraintViolation<AddActivityDto>> violations = validator.validate(addActivityDto);
        // 处理验证结果
        if (violations.isEmpty()) {
            // 验证通过，执行相应操作
            System.out.println("验证通过");
        } else {
            // 验证失败，处理错误信息
            for (ConstraintViolation<AddActivityDto> violation : violations) {
                System.out.println(violation.getMessage());
            }
        }
    }
}
