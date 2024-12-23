package xcmg.global.cockpit.backend.com;

public class RetResult<T> {

    private long code;
    private String message;
    private T data;

    protected RetResult() {
    }

    protected RetResult(long code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功返回结果
     *
     *  获取的数据
     */
    public static <T> RetResult<T> success() {
        return new RetResult<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), null);
    }

    /**
     * 成功返回结果
     *
     * @param message
     *            获取的数据
     */
    public static <T> RetResult<T> success(String message) {
        return new RetResult<T>(ResultCode.SUCCESS.getCode(), message, null);
    }

    /**
     * 成功返回结果
     *
     * @param data
     *            获取的数据
     */
    public static <T> RetResult<T> success(T data) {
        return new RetResult<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    /**
     * 成功返回结果
     *
     * @param data
     *            获取的数据
     * @param message
     *            提示信息
     */
    public static <T> RetResult<T> success(T data, String message) {
        return new RetResult<T>(ResultCode.SUCCESS.getCode(), message, data);
    }

    /**
     * 失败返回结果
     *
     * @param errorCode
     *            错误码
     */
    public static <T> RetResult<T> failed(IErrorCode errorCode) {
        return new RetResult<T>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    /**
     * 失败返回结果
     *
     * @param errorCode
     *            错误码
     * @param message
     *            错误信息
     */
    public static <T> RetResult<T> failed(IErrorCode errorCode, String message) {
        return new RetResult<T>(errorCode.getCode(), message, null);
    }

    /**
     * 失败返回结果
     *
     * @param message
     *            提示信息
     */
    public static <T> RetResult<T> failed(String message) {
        return new RetResult<T>(ResultCode.FAILED.getCode(), message, null);
    }

    /**
     * 失败返回结果
     */
    public static <T> RetResult<T> failed() {
        return failed(ResultCode.FAILED);
    }

    /**
     * 参数验证失败返回结果
     */
    public static <T> RetResult<T> validateFailed() {
        return failed(ResultCode.VALIDATE_FAILED);
    }

    /**
     * 参数验证失败返回结果
     *
     * @param message
     *            提示信息
     */
    public static <T> RetResult<T> validateFailed(String message) {
        return new RetResult<T>(ResultCode.VALIDATE_FAILED.getCode(), message, null);
    }

    /**
     * 未登录返回结果
     */
    public static <T> RetResult<T> unauthorized(T data) {
        return new RetResult<T>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage(), data);
    }

    /**
     * 未授权返回结果
     */
    public static <T> RetResult<T> forbidden(T data) {
        return new RetResult<T>(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMessage(), data);
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RetResult{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

}
