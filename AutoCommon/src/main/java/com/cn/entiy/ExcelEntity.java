package com.cn.entiy;
import cn.afterturn.easypoi.excel.annotation.Excel;
/**
 * @Author： Athena
 * @Date： 2022/8/6 19:54
 * @Desc： Excel用例参数映射实体类
 **/
public class ExcelEntity {
    @Excel(name="caseId用例编号")
    private int caseId;
    @Excel(name="desc用例描述")
    private String desc;
    @Excel(name="name接口名")
    private String name;
    @Excel(name="url接口地址")
    private String url;
    @Excel(name="params参数")
    private String params;
    @Excel(name="type请求提交类型")
    private String type;
    @Excel(name="contentType请求内容类型")
    private String contentType;
    @Excel(name="expectedResult期望结果")
    private String expectedResult;
    @Excel(name="responseContent回写接口响应结果")
    private String responseContent;
    @Excel(name="sql语句")
    private String sql;
    @Excel(name="finallyResult断言结果")
    private String finallyResult;

    public ExcelEntity() {
    }

    public ExcelEntity(int caseId, String desc, String name, String url, String params, String type, String contentType, String expectedResult, String responseContent, String sql, String finallyResult) {
        this.caseId = caseId;
        this.desc = desc;
        this.name = name;
        this.url = url;
        this.params = params;
        this.type = type;
        this.contentType = contentType;
        this.expectedResult = expectedResult;
        this.responseContent = responseContent;
        this.sql = sql;
        this.finallyResult = finallyResult;
    }

    @Override
    public String toString() {
        return "ExcelEntity{" +
                "caseId=" + caseId +
                ", desc='" + desc + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", params='" + params + '\'' +
                ", type='" + type + '\'' +
                ", contentType='" + contentType + '\'' +
                ", expectedResult='" + expectedResult + '\'' +
                ", responseContent='" + responseContent + '\'' +
                ", sql='" + sql + '\'' +
                ", finallyResult='" + finallyResult + '\'' +
                '}';
    }

    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getExpectedResult() {
        return expectedResult;
    }

    public void setExpectedResult(String expectedResult) {
        this.expectedResult = expectedResult;
    }

    public String getResponseContent() {
        return responseContent;
    }

    public void setResponseContent(String responseContent) {
        this.responseContent = responseContent;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getFinallyResult() {
        return finallyResult;
    }

    public void setFinallyResult(String finallyResult) {
        this.finallyResult = finallyResult;
    }
}
