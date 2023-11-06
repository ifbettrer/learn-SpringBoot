//原始的各个数据
package com.example.server.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data   //自动添加getter&setter
@TableName("poi_table")  //指定表
public class Poi {
    private Integer id;
    private String name;
    private String description;
    private String coverUrl;
}
