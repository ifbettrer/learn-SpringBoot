package com.example.server.controllers;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.server.form.PoiForm;
import com.example.server.mapper.PoiMapper;
import com.example.server.pojo.Pic;
import com.example.server.pojo.Poi;
import com.example.server.service.IPicService;
import com.example.server.service.IPoiService;
import com.example.server.vo.PoiVo;
import com.example.server.vo.Result;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController  //设置访问的地址，将前端传来的参数传入
@Slf4j  //打印log
@RequestMapping("/poi")  //注解
public class PoiController {

    @Autowired  //加载时会定义poiMapper，自动进行实例化
//    private PoiMapper poiMapper;
    private IPoiService poiService;

    @Autowired
    private IPicService picService;

    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "30") int pageSize){
        log.info("my info: pageNum = {}, pageSize = {}", pageNum, pageSize);
        Page<Poi> page = new Page<Poi>(pageNum, pageSize);
        IPage<Poi> pageResult = poiService.page(page);  //增加一个service过程

//        List<Poi> poiList = pageResult.getRecords();
//        List voList = new ArrayList();
//
//        for (Poi poi: poiList){
//            PoiVo poiVo = new PoiVo();
//            BeanUtils.copyProperties(poi, poiVo);  //快速赋值，成员变量要一致，前提需要使用getter&setter
//            voList.add(poiVo);
//        }
        //lamda表达式形式，同上
        List voList = pageResult.getRecords().stream().map(poi -> {
            PoiVo poiVo = new PoiVo();
            QueryWrapper query = new QueryWrapper();
            query.eq("poi_id",poi.getId());   //所属于的poi的id
            List<Pic> pics = picService.list(query);
            picService.list(query);
            BeanUtils.copyProperties(poi,poiVo);
            poiVo.setPics(pics);
            return poiVo;
        }).collect(Collectors.toList());

//        var poiVo1 = new PoiVo();
//        poiVo1.name = "zhangsan";
//        poiVo1.description = "this is zhangsan";
//        var poiVo2 = new PoiVo();
//        poiVo2.name = "lisi";
//        poiVo2.description = "this is lisi";
//
//        List poiVos = new ArrayList<PoiVo>();
//        poiVos.add(poiVo1);
//        poiVos.add(poiVo2);

        /*Result r = new Result();
        r.code = 0;
        r.msg = "suc";
        r.data = poiVos;*/
        pageResult.setRecords(voList);
        return Result.success(pageResult);
    }

    @GetMapping("/detail/{id}")
    public Result detail(@PathVariable int id){
        log.info("poi detail, id = {}", id);
        /*var poiVo = new PoiVo();
        poiVo.name = "张三";
        poiVo.description = "法外狂徒张三";*/

//        Poi poi = poiMapper.selectById(id);
        PoiVo poiVo = new PoiVo();
        Poi poi = poiService.getById(id);  //通过service访问
        QueryWrapper query = new QueryWrapper();
        query.eq("poi_id",poi.getId());
        List<Pic> pics = picService.list(query);
        BeanUtils.copyProperties(poi, poiVo);
        poiVo.setPics(pics);
        return Result.success(poiVo);
    }

    @PostMapping ("/add")
    public Result add(@RequestBody PoiForm poiForm){
        log.info("poi add, poi = {}", poiForm);
        Poi poi = new Poi();
        BeanUtils.copyProperties(poiForm, poi);
        poiService.SaveMain(poi, poiForm.getPics());
        return Result.success();
    }

    @PutMapping("/edit/{id}")
    public Result edit(@RequestBody PoiForm poiForm, @PathVariable int id){
        log.info("poi edit, poi = {}", poiForm);

        Poi poi = new Poi();
        BeanUtils.copyProperties(poiForm, poi);
        poi.setId(id);
        poiService.updateMain(poi, poiForm.getPics());
        return  Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable int id){
        log.info("poi delete, id = {}", id);

        poiService.deleteMain(id);
        return Result.success();
    }
}
