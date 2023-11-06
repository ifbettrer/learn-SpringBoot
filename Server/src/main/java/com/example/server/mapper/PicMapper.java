package com.example.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.server.pojo.Pic;
import org.apache.ibatis.annotations.Param;

public interface PicMapper extends BaseMapper<Pic> {
    int deleteByPoiId(@Param("poiId") int poiId);  //使用PicMapper.xml中的代码进行实现
}
