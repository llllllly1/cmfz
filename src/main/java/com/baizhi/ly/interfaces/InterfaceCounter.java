package com.baizhi.ly.interfaces;

import com.baizhi.ly.entity.Counter;
import com.baizhi.ly.service.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("interfaceCounter")
public class InterfaceCounter {
    @Autowired
    CounterService counterService;

//    -------------------------------------------------根据用户id展示计数器--------------------------------------------------------------------
    @RequestMapping("queryCounter")
    public Map queryCounter(String uid){
        HashMap map = new HashMap();
        try{
            List<Counter> counters = counterService.queryOne(uid);
            map.put("status",200);
            map.put("counters",counters);
        }catch (Exception e){
            map.put("status",-200);
            map.put("message","查询失败");
        }
        return map;
    }
    //    -------------------------------------------------根据用户id添加计数器----------------------------------------------------
    @RequestMapping("insertCounter")
    public Map insertCounter(String uid,Counter counter,String courseID){
        HashMap map = new HashMap();
        try{
            Counter counter1 = counterService.insert(counter, uid, courseID);
            map.put("status",200);
            map.put("counters",counter1);
        }catch (Exception e){
            map.put("status",-200);
            map.put("message","添加失败");
        }
        return map;
    }

    //    -------------------------------------------------根据用户id修改计数器----------------------------------------------------
    @RequestMapping("updateCounter")
    public Map updateCounter(Counter counter){
        HashMap map = new HashMap();
        try{
            Counter counter1 = counterService.update(counter);
            map.put("status",200);
            map.put("counters",counter1);
        }catch (Exception e){
            map.put("status",-200);
            map.put("message","修改失败");
        }
        return map;
    }

    //    -------------------------------------------------根据用户id删除计数器----------------------------------------------------
    @RequestMapping("deleteCounter")
    public Map deleteCounter(String id){
        HashMap map = new HashMap();
        try{
            Counter counter1 = counterService.delete(id);
            map.put("status",200);
            map.put("counters",counter1);
        }catch (Exception e){
            map.put("status",-200);
            map.put("message","删除失败");
        }
        return map;
    }
}
