package one.moonx.navigation.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 后端统一返回结果
 *
 * @param <T>
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Result<T> implements Serializable {

    private Integer code; //编码：1成功，0和其它数字为失败
    private String msg; //错误信息
    private T data; //数据

    public static <T> Result<T> success() {
        Result<T> result = new Result<T>();
        result.code = 1;
        return result;
    }

    public static <T> Result<T> error(String msg) {
        Result result = new Result();
        result.msg = msg;
        result.code = 0;
        return result;
    }

    public static class success {
        public static <T> Result<T> data(T object) {
            Result<T> result = new Result<T>();
            result.data = object;
            result.code = 1;
            return result;
        }

        public static <T> Result<T> msg(String msg) {
            Result<T> result = new Result<T>();
            result.msg = msg;
            result.code = 1;
            return result;
        }

        public static <T> Result<T> msgAndData(String msg, T object) {
            Result<T> result = success.data(object);
            result.msg = msg;
            return result;
        }

    }

}