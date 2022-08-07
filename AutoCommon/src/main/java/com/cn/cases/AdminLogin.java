package com.cn.cases;

import com.cn.entiy.ExcelEntity;
import com.cn.updateFile.Constants;
import com.cn.utils.CommonUtils;
import org.apache.log4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Map;

/**
 * @Author： Athena
 * @Date： 2022/8/6 22:03
 * @Desc： 管理员登录接口用例
 **/
public class AdminLogin {
    //打印日志
    private Logger logger = Logger.getLogger(AdminLogin.class);

    //数据驱动提供，通过dataProvider的值对应有此注解的方法名log4j.properties
    @Test(dataProvider = "ReadExcelDatas")
    //此处传参类型要和dataProvider提供的数据类型保持一直
    public void adminloginCase(ExcelEntity excelEntity) {
        //获取请求头，发起请求
        Map<String, Object> headers = CommonUtils.getDefaulHeaders();
        //发起请求
        String url = excelEntity.getUrl();
        String params = excelEntity.getParams();
        String type = excelEntity.getType();
        String body = CommonUtils.EnterRequest(headers, url, params, type);
        logger.info(body);
        //响应结果存储到map集合(传参说明：json 响应体、jsonpath匹配格式、存储key值)
        CommonUtils.responseDatasSave(body,"$.code","${code}");
        CommonUtils.responseDatasSave(body,"$.msg","${msg}");
        CommonUtils.responseDatasSave(body,"$.data.token","${token}");
        //将响应结果回写到excel
        CommonUtils.backWriteResponseExcel(body,0,1,8);
        //断言响应结果
        boolean assertResul = CommonUtils.assertResponseResult(body, excelEntity,logger);
        //将断言结果回写到excel
        String finallyResult = assertResul ?"passed":"failed";
        CommonUtils.backWriteResponseExcel(finallyResult,0,1,10);
    }
    /*
     * @ Author:Athena
     * @ Date 2022/8/6 22:06
     * @ Description //通过Testng的注解@DataProvider提供数据
     * @ Param[]
     * @ return java.lang.Object[]
     **/

    @DataProvider
    public Object[] ReadExcelDatas() {
        Object[] datasArray = CommonUtils.readExcelDatas();
        return datasArray;
    }
}
