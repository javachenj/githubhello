package com.nancal.excel.easyExcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

public class ExcelListener extends AnalysisEventListener<DemoDataVo> {

    //一行一行读取excel内容
    @Override
    public void invoke(DemoDataVo demoData, AnalysisContext analysisContext) {
        System.out.println("*******"+demoData);
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头："+headMap);
    }

    //读取完整后执行
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
