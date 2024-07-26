package one.moonx.navigation.pojo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class WeatherIdDTO {
    public Integer code;
    public List<LocationDTO> location;
    public ReferDTO refer;

    @NoArgsConstructor
    @Data
    public static class ReferDTO {
        /**
         * 原始数据来源，或数据源说明，可能为空
         */
        public List<String> sources;
        /**
         * 数据许可或版权声明，可能为空
         */
        public List<String> license;
    }

    @NoArgsConstructor
    @Data
    public static class LocationDTO {
        /**
         * 城市名称
         */
        public String name;
        /**
         * 城市ID
         */
        public String id;
        /**
         * 城市纬度
         */
        public String lat;
        /**
         * 城市经度
         */
        public String lon;
        /**
         * 城市的上级行政区划名称
         */
        public String adm2;
        /**
         * 城市所属一级行政区域
         */
        public String adm1;
        /**
         * 城市所属国家名称
         */
        public String country;
        /**
         * 城市所在时区
         */
        public String tz;
        /**
         * 城市目前与UTC时间偏移的小时数
         */
        public String utcOffset;
        /**
         * 城市是否当前处于夏令时,1 表示当前处于夏令时,0 表示当前不是夏令时
         */
        public String isDst;
        /**
         * 城市的属性
         */
        public String type;
        /**
         * 地区评分
         */
        public String rank;
        /**
         * 该地区的天气预报网页链接，便于嵌入你的网站或应用
         */
        public String fxLink;
    }
}
