package com.baizhi.ly.service.impl;

import com.baizhi.ly.ascept.Logs;
import com.baizhi.ly.dao.ChapterDao;
import com.baizhi.ly.entity.Chapter;
import com.baizhi.ly.service.ChapterService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class ChapterServiceImpl implements ChapterService {

    @Autowired
    ChapterDao chapterDao;

    //根据专辑id查询章节
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Map queryByPage(Integer page, Integer rows, String albumId) {

        HashMap hashMap = new HashMap();
        Chapter chapter = new Chapter();
        chapter.setAlbumId(albumId);
        List<Chapter> chapters = chapterDao.selectByRowBounds(chapter, new RowBounds((page-1)*rows,rows));
        //查询总条数
        int records = chapterDao.selectCount(chapter);
        //设置总页数
        int total = (records+(rows-1))/rows;
        hashMap.put("rows",chapters);
        hashMap.put("records",records);
        hashMap.put("total",total);
        hashMap.put("page",page);

        //rows 查询数据   page 当前页   records 总条数   total 总页数
        return hashMap;
    }

    //添加章节
    @Logs(name = "添加章节")
    public String insertChapter(Chapter chapter,String albumId) {
        chapter.setId(UUID.randomUUID().toString());
        chapter.setAlbumId(albumId);
        chapterDao.insert(chapter);
        return chapter.getId();
    }

    //修改章节
    @Logs(name = "修改章节")
    public String updateChapter(Chapter chapter) {
        chapter.setUrl(null);
        chapterDao.updateByPrimaryKeySelective(chapter);
        return chapter.getId();
    }

    //批量删除章节
    @Logs(name = "批量删除章节")
    public void deleteList(List list) {
        chapterDao.deleteByIdList(list);
    }
}
