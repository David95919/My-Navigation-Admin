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
public class ResultPage<T> implements Serializable {

    private Integer code;
    private String msg;
    private Long total;
    private T records;

    public static <T> ResultPage<T> error(String msg) {
        ResultPage result = new ResultPage();
        result.msg = msg;
        result.code = 0;
        return result;
    }

    public static class success {
        public static <T> ResultPage<T> records(T object, Long total) {
            ResultPage<T> result = new ResultPage<T>();
            result.records = object;
            result.code = 1;
            result.total = total;
            return result;
        }


        public static <T> ResultPage<T> msgAndRecords(String msg, T object, Long total) {
            ResultPage<T> result = success.records(object, total);
            result.msg = msg;
            return result;
        }

    }

}