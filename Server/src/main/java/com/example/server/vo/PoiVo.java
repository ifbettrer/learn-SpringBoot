//选择要展示的数据
package com.example.server.vo;

import lombok.Data;

import java.util.List;

@Data
public class PoiVo {
    private Integer id;
    private String name;
    private String description;
    private List pics;
}
