package org.example.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 异常状态枚举
 */
@Getter
@AllArgsConstructor
public enum ErrorCodeEnum {
    ERROR(9999, "系统异常"),
    HTTP_CONNECTION_OVERTIME(9998, "连接超时"),
    FREQUENTLY_REQUEST(9003, "操作频繁"),
    INVALID_RSA_KEY(9002, "超时失效"),
    TOKEN_TIMEOUT(9005, "token失效"),
    INVALID_PARAMS(9001, "非法参数"),
    INVALID_LOGIN_USER(9006, "该登录用户非企业内部用户，为企业外部联系人"),
    SIGN_ERROR(9000, "签名错误"),
    INVALID_STATUS(9004, "状态不符"),

    OK(200, "请求通过"),
    NO(201, "请求不通过"),
    TIP(202, "提示"),


    SUBMIT_NEED_CERTIFICATION(2006, "提交成功,请进行实名认证"),

    BLACK_LIST(3001, "账号黑名单"),
    UN_CERTIFICATED(3002, "转账账号未通过实名认证");

    /**
     * 状态码
     */
    private final Integer code;
    /**
     * 状态说明
     */
    private final String message;
    /**
     * 根据code获取message
     *
     * @param code 类型编码
     * @return Integer
     */
    public static String getMessage(Integer code) {
        return Arrays.stream(ErrorCodeEnum.class.getEnumConstants())
                .filter(e -> e.getCode().equals(code))
                .findFirst().map(ErrorCodeEnum::getMessage).orElseThrow(IllegalArgumentException::new);
    }
}
