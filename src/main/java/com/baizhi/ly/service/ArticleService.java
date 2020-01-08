package com.baizhi.ly.service;

import com.baizhi.ly.entity.Article;

import java.util.List;
import java.util.Map;

public interface ArticleService {
    //文章分页查所有
    public abstract Map queryByPage(Integer page, Integer rows);
    //添加文章
    public abstract String insertArticle(Article article);
    //修改文章
    public abstract String updateArticle(Article article);
    //批量删除文章
    public abstract void delete(String id);
    //查询所有文章
    public abstract List<Article> queryAll();
    //根据id查询文章
    public abstract Article queryOne(String id);
}
