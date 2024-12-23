package xcmg.global.cockpit.backend.com;

public enum ResultCode implements IErrorCode {
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    VALIDATE_FAILED(600, "参数检验失败"),
    UNAUTHORIZED(401,"暂未登录或token已经过期"),
    FORBIDDEN(403, "没有相关权限"),
    REQUEST_TIMEOUT(408, "没有相关权限"),

    UNKNOW_EXCEPTION(201, "未知异常"),
    RUNTIME_EXCEPTION(202, "运行时异常"),
    NULL_POINTER_EXCEPTION(203, "空指针异常"),
    CLASS_CAST_EXCEPTION(204, "类型转换异常"),
    IO_EXCEPTION(205, "IO异常"),
    INDEX_OUTOF_BOUNDS_EXCEPTION(206, "数组越界异常"),
    METHOD_ARGUMENT_TYPE_MISMATCH_EXCEPTIION(207, "参数类型不匹配"),
    MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION(208, "缺少参数"),
    HTTP_REQUEST_METHOD_NOT_SUPPORTED_EXCEPTION(209, "不支持的method类型"),
    PARAM_EXCEPTION(210, "参数异常"),
    NOT_FOUND_EXCEPTION(404, "接口不存在"),
    RATE_LIMIT_EXCEPTION(10000, "接口访问过于频繁，请稍后再试！"),
    IDEMPOTENT_EXCEPTION(20000, "接口不可以重复提交，请稍后再试！"),
    JSON_SERIALIZE_EXCEPTION(30000, "序列化数据异常"),
    JSON_DESERIALIZE_EXCEPTION(30001, "反序列化数据异常");

    private long code;
    private String message;

    private ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public long getCode() {
        return code;
    }
    @Override
    public String getMessage() {
        return message;
    }

}