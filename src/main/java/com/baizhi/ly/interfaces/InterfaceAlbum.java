package com.baizhi.ly.interfaces;

import com.baizhi.ly.entity.Album;
import com.baizhi.ly.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("interfaceAlbum")
public class InterfaceAlbum {
    @Autowired
    AlbumService albumService;

//  ---------------------------------------------------专辑详情接口---------------------------------------------------------------------
    @RequestMapping("queryAlbumOne")
    public Map queryOne(String id){
        HashMap map = new HashMap();
        try{
            Album album = albumService.queryOne(id);
            map.put("status",200);
            map.put("article",album);
        }catch (Exception e){
            map.put("status",-200);
            map.put("message","查询失败");
        }
        return map;
    }
}
