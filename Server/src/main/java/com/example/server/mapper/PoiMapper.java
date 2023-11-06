//一个接口
package com.example.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.server.pojo.Poi;

public interface PoiMapper extends BaseMapper<Poi> {  //继承一个Poi类型的数据
}
