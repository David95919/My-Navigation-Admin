package one.moonx.navigation.pojo.dto;

import lombok.Data;

@Data
public class NavQuery {
    private String name;
    private Integer tag;
    private Integer category;
    private Long current;
    private Long size;
}
