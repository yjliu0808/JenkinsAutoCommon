package com.cn.updateFile;/**
 * @description
 * @author Grey
 * @date 2022/8/6 21:17
 */

import com.cn.entiy.ExcelEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author： Athena
 * @Date： 2022/8/6 21:17
 * @Desc： 常量类定义（可更新）
 **/
public class Constants {
    //excel用例文档的路径
    public static final String excel_path="src\\main\\resources\\cases.xlsx";
   // public static final String excel_path="src\\main\\resources\\cases3.xlsx";
    //excel用例文档中开始读取sheet值 (startSheetIndex)
    public static int startSheetIndex = 0;
    //excel用例文档中读取几个sheet值 (sheetNum)
    public static int sheetNum = 1;
    //excel用例文档中回写列号 (sheetNum)
    public static int cellNum = 8;
    //定义请求头存放的map变量 headersMap
    public  static  Map<String ,Object> headersMap = new HashMap<>();
    //定义请求头具体参数 contentType 和 applicationJson
    public static String contentType = "Content-Type";
    public static String applicationJson = "application/json";
    //定义发起请求传入的参数 (按照实际映射的实体类更新)
    public static ExcelEntity excelEntity;
    //定义封装响应结果的数据类型map
    public static Map<String,Object> responseResultSave = new HashMap<>();








}
