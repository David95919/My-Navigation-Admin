package one.moonx.navigation.pojo.dto;

import lombok.Data;

import java.util.List;

@Data
public class NavQuery {
    private String name;
    private List<Integer> tag;
    private Integer category;
    private Long current;
    private Long size;
}
