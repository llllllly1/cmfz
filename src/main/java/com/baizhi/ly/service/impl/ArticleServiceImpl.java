package com.baizhi.ly.service.impl;

import com.baizhi.ly.ascept.Logs;
import com.baizhi.ly.dao.ArticleDao;
import com.baizhi.ly.entity.Article;
import com.baizhi.ly.service.ArticleService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleDao articleDao;

    //分页查询文章
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Map queryByPage(Integer page, Integer rows) {
        HashMap hashMap = new HashMap();
        List<Article> articles = articleDao.selectByRowBounds(new Article(), new RowBounds((page-1)*rows,rows));
        //查询总条数
        int records = articleDao.selectCount(new Article());
        //设置总页数
        int total = (records+(rows-1))/rows;
        hashMap.put("rows",articles);
        hashMap.put("records",records);
        hashMap.put("total",total);
        hashMap.put("page",page);

        //rows 查询数据   page 当前页   records 总条数   total 总页数
        return hashMap;
    }

    //添加文章
    @Logs(name = "添加文章")
    public String insertArticle(Article article) {
        article.setId(UUID.randomUUID().toString());
        article.setCreateDate(new Date());
        article.setPublishDate(new Date());
        articleDao.insert(article);
        return article.getId();
    }

    //修改文章
    @Logs(name = "修改文章")
    public String updateArticle(Article article) {
        articleDao.updateByPrimaryKeySelective(article);
        return article.getId();
    }

    //批量删除文章
    @Logs(name = "批量删除文章")
    public void delete(String id) {
        articleDao.deleteByPrimaryKey(id);
    }

    //查询所有文章
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Article> queryAll() {
        List<Article> articles = articleDao.selectAll();
        return articles;
    }



//    --------------------------------------------根据id查询文章--------------------------------------------------------------------
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Article queryOne(String id) {
        Article article = articleDao.selectByPrimaryKey(id);
        return article;
    }
}
