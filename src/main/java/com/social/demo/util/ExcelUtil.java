package com.social.demo.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 杨世博
 * @date 2023/11/20 14:35
 * @description 表格操作工具类
 */
public class ExcelUtil{

    public static  <T> @NotNull List<T> readExcelUtil(Class<T> tClass, String fileName){
        List<T> list = new ArrayList<>();

        EasyExcel.read(fileName, tClass, new AnalysisEventListener<T>() {
            /**
             * 每解析一行Excel表格，就会被调用一次
             * @param t
             * @param analysisContext
             */
            @Override
            public void invoke(T t, AnalysisContext analysisContext) {
                list.add(t);
            }

            /**
             * 当全部解析完被调用
             * @param analysisContext
             */
            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {

            }
        }).sheet().doRead();

        return list;
    }
}
