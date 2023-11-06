//接口
package com.example.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.server.pojo.Pic;
import com.example.server.pojo.Poi;

import java.util.List;

public interface IPoiService extends IService<Poi> {  //继承IService，Poi类
    void SaveMain(Poi poi, List<Pic> pics);
    void deleteMain(Integer id);
    void updateMain(Poi poi, List<Pic> pics);
}
