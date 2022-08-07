package com.cn.utils;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.alibaba.fastjson.JSONPath;
import com.cn.cases.AdminLogin;
import com.cn.entiy.ExcelEntity;
import com.cn.updateFile.Constants;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.restassured.RestAssured.given;

/**
 * @Author： Athena
 * @Date： 2022/8/6 21:08
 * @Desc： 解析excel中的数据到实体类对象
 **/
public class CommonUtils {
    /*
     * @ Author:Athena
     * @ Date 2022/8/6 21:11
     * @ Description //读取excel中的数据封装到实体类
     * @ Param
     * @ return
     **/

    public static Object[] readExcelDatas() {
        //定义返回的数据变量
        Object[] excelEntityArray = null;
        //1.加载excel文件
        try {
            FileInputStream fis = new FileInputStream(Constants.excel_path);
            //创建easypoi包中的导入对象ImportParams
            ImportParams importParams = new ImportParams();
            //3.设置读取excel中的第几个sheet,从0开始
            importParams.setStartSheetIndex(Constants.startSheetIndex);
            //4.读取几个sheet
            importParams.setSheetNum(Constants.sheetNum);
            //5.通过easypoi的ExcelImportUtil导入工具(Excel导入数据源IO流映射实体类)返回结果为对应实体类的list集合
            List<ExcelEntity> excelEntityList = ExcelImportUtil.importExcel(fis, ExcelEntity.class, importParams);
            //6.list集合类型转换为一维数组
            excelEntityArray = excelEntityList.toArray();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return excelEntityArray;

    }

    /*
     * @ Author:Athena
     * @ Date 2022/8/6 22:21
     * @ Description //获取请求头
     * @ Param
     * @ return
     **/
    public static Map<String, Object> getDefaulHeaders() {
        Constants.headersMap.put(Constants.contentType, Constants.applicationJson);
        return Constants.headersMap;
    }
    /*
     * @ Author:Athena
     * @ Date 2022/8/6 23:00
     * @ Description //添加其他请求头
     * @ Param 传参解释：headerKey 请求头名称：headerValue 请求头值
     * @ return map
     **/

    public static Map<String, Object> getDefaulHeadersAdd(String headerKey, String headerValue) {
        //请求头需要其他的参数可合理添加：headerKey:headerValue
        Constants.headersMap.put(headerKey, headerValue);
        return Constants.headersMap;
    }

    /*
     * @ Author:Athena
     * @ Date 2022/8/6 23:02
     * @ Description //发起请求
     * @ Param
     * @ return
     **/
    public static String EnterRequest(Map<String, Object> headers, String url, String params, String type) {
        //定义请求返回响应结果变量 body
        String body = null;
        //判断请求提交类型 type
        if ("get".equals(type)) {
            body = given().headers(headers).get(url).asString();
        } else if ("post".equals(type)) {
            body = given().headers(headers).body(params).post(url).asString();
        } else if ("patch".equals(type)) {
            body = given().headers(headers).body(params).patch(url).asString();
        } else if("put".equals(type)){
            given().headers(headers).body(params).put().asString();
        }
        return body;
    }

    /*
     * @ Author:Athena
     * @ Date 2022/8/7 0:09
     * @ Description //存储响应结果到map统一管理使用
     * @ Param 响应结果、匹配格式、存放key
     * @ return
     **/
    public static void responseDatasSave(String body, String jsonPath, String key) {
        //1.fastjson包内的JSONPath，匹配传入的json内容和格式，得到值
        Object responseResultValue = JSONPath.read(body, jsonPath);
        //将获取的responseValue存储到map中
        if (responseResultValue != null) {
            Constants.responseResultSave.put(key, responseResultValue);
        }
    }

    /*
     * @ Author:Athena
     * @ Date 2022/8/7 8:40
     * @ Description //将响应结果回写到excel中
     * @ Param body回写内容，sheetNum 回写sheet(0开始) rowNum回写行号(1开始)，cellNum回写列号(0开始)
     * @ return
     **/
    public static void backWriteResponseExcel(String  body,int sheetNum,int rowNum,int cellNum) {
        //创建文件流
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(Constants.excel_path);
            Workbook sheets = WorkbookFactory.create(fis);
            //分别或许回写对应的sheet/row/cell
            Sheet sheet = sheets.getSheetAt(sheetNum);
            Row row = sheet.getRow(rowNum);
            //防止空指针异常
            Cell cell  = row.getCell(cellNum,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            cell.setCellType(CellType.STRING);
            //回写内容change the cell to a string cell
            cell.setCellValue(body);
            //创建输出流
            FileOutputStream fos = new FileOutputStream(Constants.excel_path);
            sheets.write(fos);
            fis.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /*
     * @ Author:Athena
     * @ Date 2022/8/7 14:13
     * @ Description //断言响应结果
     * @ Param
     * @ return
     **/
    public static boolean  assertResponseResult(String body,ExcelEntity excelEntity,Logger logger){
        //定义断言结果 assertResult
        boolean assertResult = true;
        //期望响应结果和实际响应结果比较，格式{"$.code":0}
        //1.获取excel中期望的响应结果
        String expectedReponsejson= excelEntity.getExpectedResult();
        //将期望json转map存储
        Map<String,Object> expectedJsonMap = JSONObject.parseObject(expectedReponsejson,Map.class);
        //2.获取期望结果转map存储后所有keys
        Set<String> expectedKeys = expectedJsonMap.keySet();
        //3.遍历所有的keys,通过key获取value
        for(String expectedKey : expectedKeys){
            //获取期望值
            Object expectedValue = expectedJsonMap.get(expectedKey);
            //获取实际值
            Object actualReponseValue = JSONPath.read(body,expectedKey);
            //断言实际数据和期望数据的比较
            if(!expectedValue.equals(actualReponseValue)){
                assertResult = false;
            }
        }
        if(assertResult){
            logger.info("断言响应结果成功");
        }else {
            logger.info("断言响应结果失败");
        }
        return assertResult;
    }
    /*
     * @ Author:Athena
     * @ Date 2022/8/7 16:19
     * @ Description //回写断言结果到excel
     * @ Param
     * @ return
     **/

}
