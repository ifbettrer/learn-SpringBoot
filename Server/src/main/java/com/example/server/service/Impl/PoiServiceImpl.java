//接口的实现
package com.example.server.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.server.mapper.PicMapper;
import com.example.server.mapper.PoiMapper;
import com.example.server.pojo.Pic;
import com.example.server.pojo.Poi;
import com.example.server.service.IPoiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PoiServiceImpl extends ServiceImpl<PoiMapper, Poi> implements IPoiService {

    @Autowired
    private PoiMapper poiMapper;

    @Autowired
    private PicMapper picMapper;

    @Override
    public void SaveMain(Poi poi, List<Pic> pics){
        poiMapper.insert(poi);  //插入到poi_table后，poi_id就可以获取了，后面可以应用

        if(pics != null){
            for(Pic pic: pics){
                pic.setPoiId(poi.getId());
                picMapper.insert(pic);
            }
        }
    }

    @Override
    public void deleteMain(Integer id) {
        poiMapper.deleteById(id);  //只是删除poi_table中的数据,这个函数是包里有的
        picMapper.deleteByPoiId(id);  //这个函数需要定义，因为我们提前不知道要删除的是哪些图片，为什么不在service里面弄，因为这里是用的poi_service里的
    }

    @Override
    public void updateMain(Poi poi, List<Pic> pics) {
        poiMapper.updateById(poi);
        picMapper.deleteByPoiId(poi.getId());  //先删除要编辑图片的所有信息

        if(pics != null){  //再将所有的图片的信息插入进去
            for(Pic pic: pics){
                pic.setPoiId(poi.getId());
                picMapper.insert(pic);
            }
        }
    }
}
