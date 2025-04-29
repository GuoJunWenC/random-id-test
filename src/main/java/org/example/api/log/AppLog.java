package org.example.api.log;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class AppLog implements Serializable {
    private static final long serialVersionUID = 7228578092155793211L;
    /** 请求路径 */
    private String path;
    /** 请求param */
    private Map<String, String[]> parameters;
    /** 请求体 */
    private Object reqBody;
    /**
     * 返回体
     */
    private Object respBody;
    private StringBuffer aBuffe;

}
