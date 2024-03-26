package com.social.demo.data.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author 杨世博
 * @date 2024/3/18 21:38
 * @description YPage
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class YPage<T> extends Page<T> {
    private String signature;
    private String teacherSignature;
    public YPage(long current, long size, long total, String signature, String teacherSignature) {
        super(current, size, total);
        this.signature = signature;
        this.teacherSignature = teacherSignature;
    }
}
