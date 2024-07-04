package one.moonx.navigation.pojo.dto;

import lombok.Data;

import java.util.List;

@Data
public class NavDTO {
    private Integer id;

    private String name;

    private String url;

    private String description;

    private List<Integer> tags;

    private Integer category;
}
