package com.baizhi.ly.interfaces;

import com.baizhi.ly.entity.Album;
import com.baizhi.ly.entity.Article;
import com.baizhi.ly.entity.Attention;
import com.baizhi.ly.entity.Banner;
import com.baizhi.ly.service.AlbumService;
import com.baizhi.ly.service.ArticleService;
import com.baizhi.ly.service.AttentionService;
import com.baizhi.ly.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("interfaceJSP")
public class InterfaceJSP {
    @Autowired
    BannerService bannerService;
    @Autowired
    AlbumService albumService;
    @Autowired
    ArticleService articleService;
    @Autowired
    AttentionService attentionService;

//    ---------------------------------------------一级页面展示接口-------------------------------------------------------------------
    @RequestMapping("queryJSP")
    public Map queryOnePage(String uid,String type,String sub_type,Integer page,Integer rows){
        HashMap map = new HashMap();
        List<Banner> banners = bannerService.queryAll();
        List<Album> albums = albumService.queryAll();
        List<Article> articles = articleService.queryAll();
        List<Attention> attentions = attentionService.queryGuru(uid);
        ArrayList list = new ArrayList();
        for (Article article : articles) {
            for (Attention attention : attentions) {
                if (attention.getGuruId().equals(article.getGuruId())){
                    list.add(article);
                }
            }
        }
        map.put("status",-200);
        map.put("message","查询失败");
        if (type.equals("all")){
            map.put("status",200);
            map.put("head",banners);
            map.put("albums",albums);
            map.put("articles",articles);
            map.put("message","查询成功");
        }else if (type.equals("wen")){
            map.put("status",200);
            map.put("albums",albums);
            map.put("message","查询成功");
        }else if (type.equals("si")){
            if (sub_type.equals("ssyj")){
                map.put("status",200);
                map.put("articles",list);
                map.put("message","查询成功");
            }else {
                map.put("status",200);
                map.put("articles",articles);
                map.put("message","查询成功");
            }
        }
        return map;
    }
}
