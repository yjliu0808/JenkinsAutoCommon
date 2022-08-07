import com.alibaba.fastjson.JSONPath;
import com.cn.entiy.ExcelEntity;
import com.cn.updateFile.Constants;
import com.cn.utils.CommonUtils;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author： Athena
 * @Date： 2022/8/6 21:47
 * @Desc： 测试已封装好的方法
 **/
public class testDemo {
    /*
     * @ Author:Athena
     * @ Date 2022/8/6 22:00
     * @ Description //测试解析excel数据封装到实体类对象
     * @ Param[]
     * @ return void
     **/
    @Test
    public void excelReadDemo() {
        Object[] excelArray = CommonUtils.readExcelDatas();
        for (Object excel : excelArray) {
            System.out.println(excel);
        }
    }

    /*
     * @ Author:Athena
     * @ Date 2022/8/6 22:35
     * @ Description //获取请求头已封装的方法测试
     * @ Param[]
     * @ return void
     **/

    @Test
    public static void getHeadersMap() {
        CommonUtils.getDefaulHeaders();
        Set<String> keys = Constants.headersMap.keySet();
        for (String key : keys) {
            System.out.println(key);
            System.out.println(Constants.headersMap.get(key));
        }

    }

    /*
     * @ Author:Athena
     * @ Date 2022/8/6 22:46
     * @ Description //新添加其他的请求头-测试Demo
     * @ Param  key value格式添加请求头
     * @ return
     **/
    @Test
    public static void getDefaulHeadersAdd() {
        String headerKey = "请求头名称";
        String headervalue = "请求头值";
        CommonUtils.getDefaulHeadersAdd(headerKey, headervalue);
        Set<String> keys = Constants.headersMap.keySet();
        for (String key : keys) {
            System.out.println(key);
            System.out.println(Constants.headersMap.get(key));
        }

    }
    /*
     * @ Author:Athena
     * @ Date 2022/8/7 8:08
     * @ Description //调用发起请求方法Demo测试
     * @ Param
     * @ return
     **/

    @Test
    public static void EnterRequest() {
        ExcelEntity excelEntity = new ExcelEntity();
        Map<String, Object> headers = CommonUtils.getDefaulHeaders();
        String url = "http://ny.testing.thinkworld360.com/gateway/api/system/user/login";
        String params = "{\"username\":\"admin\",\"password\":\"admin123\"}";
        String type = "post";
        String body = CommonUtils.EnterRequest(headers, url, params, type);
        System.out.println(body);
    }

    /*
     * @ Author:Athena
     * @ Date 2022/8/7 8:09
     * @ Description //封装响应结果到map类-demo测试
     * @ Param
     * @ return
     **/
    @Test
    public static void responseDatasSave() {
        String body = "{\"code\":0,\"msg\":\"\\u767b\\u5f55\\u6210\\u529f\",\"data\":{\"username\":\"admin\",\"role_id\":5,\"token\":\"712c54b5-efa8-74a0-2a1c-fd200c145892\",\"last_login_time\":1558941483,\"last_ip\":\"127.0.0.1\"},\"time\":1659802289}";
        String jsonpath = "$.code";
        String key = "code";
        CommonUtils.responseDatasSave(body, jsonpath, key);

        String body1 = "{\"code\":0,\"msg\":\"\\u767b\\u5f55\\u6210\\u529f\",\"data\":{\"username\":\"admin\",\"role_id\":5,\"token\":\"712c54b5-efa8-74a0-2a1c-fd200c145892\",\"last_login_time\":1558941483,\"last_ip\":\"127.0.0.1\"},\"time\":1659802289}";
        String jsonpath1 = "$.data.token";
        String key1 = "token";
        CommonUtils.responseDatasSave(body1, jsonpath1, key1);

        Set<String> keys = Constants.responseResultSave.keySet();
        for (String responseResultkey : keys) {
            System.out.println(Constants.responseResultSave.get(responseResultkey));
        }
    }

    /*
     * @ Author:Athena
     * @ Date 2022/8/7 9:11
     * @ Description //回写响应内容到excel
     * @ Param
     * @ return
     **/
    @Test
    public void backWriteResponseExcel() {
        String body = "回写内容";
        int sheetNum = 0;
        int rowNum = 1;
        int cellNum = 8;
        CommonUtils.backWriteResponseExcel(body, sheetNum, rowNum, cellNum);
    }

    /*
     * @ Author:Athena
     * @ Date 2022/8/7 14:46
     * @ Description // 断言响应结果
     * @ Param
     * @ return
     **/
    @Test
    public void assertResponseResultDemo() {
        String body = "{\"code\":0,\"msg\":\"\\u767b\\u5f55\\u6210\\u529f\",\"data\":{\"username\":\"admin\",\"role_id\":5,\"token\":\"7ee2af25-4bae-4ec5-a505-8ab956db432b\",\"last_login_time\":1558941483,\"last_ip\":\"127.0.0.1\"},\"time\":1659788928}";
        ExcelEntity excelEntity = new ExcelEntity();
        Logger logger = Logger.getLogger(testDemo.class);
        String expectedReponsejson = excelEntity.getExpectedResult();
        String expectedJson = "{\"$.code\":0,\"$.data.username\":\"admin\"}";
        excelEntity.setExpectedResult(expectedJson);
        boolean assertResult = CommonUtils.assertResponseResult(body, excelEntity, logger);

    }

    /*
     * @ Author:Athena
     * @ Date 2022/8/7 16:53
     * @ Description //JSONPath的常用方法Demo
     * @ Param
     * @ return
     **/
    @Test
    public void jsonpathDemo() {
        String body = "{\"code\":0,\"msg\":\"\\u767b\\u5f55\\u6210\\u529f\",\"data\":{\"username\":\"admin\",\"role_id\":5,\"token\":\"7ee2af25-4bae-4ec5-a505-8ab956db432b\",\"last_login_time\":1558941483,\"last_ip\":\"127.0.0.1\"},\"time\":1659788928}";
        String jsonpath = "$.data.username";
        Map<String, Object> map = new HashMap<>();
        Object x = JSONPath.read(body, jsonpath);
        if (x != null) {
            map.put("存储匹配结果", x);
        } else {
            System.out.println("null");
        }
        Set<String> keys = map.keySet();
        for (String key : keys) {
            System.out.println(key);
            System.out.println(map.get(key));
        }

    }


}
