package com.baizhi.ly.interfaces;

import com.baizhi.ly.entity.Attention;
import com.baizhi.ly.entity.Guru;
import com.baizhi.ly.service.AttentionService;
import com.baizhi.ly.service.GuruService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("interfaceGuru")
public class InterfaceGuru {
    @Autowired
    AttentionService attentionService;
    @Autowired
    GuruService guruService;

    //--------------------------------------------------展示上师列表---------------------------------------------------------------------
    @RequestMapping("queryGuru")
    public Map queryGuru(String uid){
        HashMap map = new HashMap();
        try {
            List<Attention> attentions = attentionService.queryGuru(uid);
            List<Guru> gurus = guruService.queryAll();
            HashSet hashSet = new HashSet();
            for (Guru guru : gurus) {
                for (Attention attention : attentions) {
                    if(attention.getGuruId().equals(guru.getId())){
                        hashSet.add(guru);
                    }
                }
            }
            map.put("status",200);
            map.put("list",hashSet);
        }catch (Exception e){
            map.put("status",-200);
            map.put("message","查询失败");
        }
        return map;
    }

    //-------------------------------------------------关注上师----------------------------------------------------------------------
    @RequestMapping("insert")
    public Map insert(String uid,String guruID){
        HashMap map = new HashMap();
        try {
            attentionService.insert(uid, guruID);
            map.put("status",200);
            map.put("message","添加成功");
        }catch (Exception e){
            map.put("status",-200);
            map.put("message","添加失败");
        }
        return map;
    }
}
