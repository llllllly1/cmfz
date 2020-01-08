package com.baizhi.ly.interfaces;

import com.baizhi.ly.entity.Article;
import com.baizhi.ly.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("interfaceArticle")
public class InterfaceArticle {
    @Autowired
    ArticleService articleService;
//    ---------------------------------------------文章详情接口---------------------------------------------------------------------
    @RequestMapping("queryArticleOne")
    public Map queryArticleOne(String id){
        HashMap map = new HashMap();
        try{
            Article article = articleService.queryOne(id);
            map.put("status",200);
            map.put("article",article);
        }catch (Exception e){
            map.put("status",-200);
            map.put("message","查询失败");
        }
        return map;

    }
}
