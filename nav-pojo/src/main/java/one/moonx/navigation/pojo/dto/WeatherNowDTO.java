package one.moonx.navigation.pojo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class WeatherNowDTO {

    public Integer code;
    /**
     * 更新时间
     */
    public String updateTime;
    public String fxLink;
    public NowDTO now;
    public ReferDTO refer;

    @NoArgsConstructor
    @Data
    public static class NowDTO {
        /**
         * 数据观测时间
         */
        public String obsTime;
        /**
         * 温度 摄氏度
         */
        public String temp;
        /**
         * 体感温度 摄氏度
         */
        public String feelsLike;
        /**
         * 图标代码
         */
        public String icon;
        /**
         * 天气状况的文字描述
         */
        public String text;
        /**
         * 风向360角度
         */
        public String wind360;
        /**
         * 风向
         */
        public String windDir;
        /**
         * 风力等级
         */
        public String windScale;
        /**
         * 风速，公里/小时
         */
        public String windSpeed;
        /**
         * 相对湿度，百分比数值
         */
        public String humidity;
        /**
         * 过去1小时降水量，默认单位：毫米
         */
        public String precip;
        /**
         * 大气压强，默认单位：百帕
         */
        public String pressure;
        /**
         * 能见度，默认单位：公里
         */
        public String vis;
        /**
         * 云量，百分比数值。可能为空
         */
        public String cloud;
        /**
         * 露点温度。可能为空
         */
        public String dew;
    }

    @NoArgsConstructor
    @Data
    public static class ReferDTO {
        public List<String> sources;
        public List<String> license;
    }
}
