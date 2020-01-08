package com.baizhi.ly.service.impl;

import com.baizhi.ly.DTO.UserDTO;
import com.baizhi.ly.dao.UsersDao;
import com.baizhi.ly.entity.User;
import com.baizhi.ly.service.UserService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UsersDao usersDao;

    //用户注册信息统计
    public Integer queryUserByTime(String sex, Integer day) {
        Integer integer = usersDao.queryUserByTime(sex, day);
        return integer;
    }

    //用户分布地区统计
    public List<UserDTO> queryUserBySexLocation(String sex) {
        List<UserDTO> userDTOS = usersDao.queryUserBySexLocation(sex);
        return userDTOS;
    }

    //添加用户
    public String insert(User user) {
        user.setId(UUID.randomUUID().toString());
        user.setLastLogin(new Date());
        user.setRegistDate(new Date());
        usersDao.insert(user);
        return user.getId();
    }

    //修改用户
    public User update(User user) {
        usersDao.updateByPrimaryKeySelective(user);
        User user1 = usersDao.selectOne(user);
        return user1;
    }

    //删除用户
    public void delete(List id) {
        usersDao.deleteByIdList(id);
    }

    //根据名字查一个
    public User queryOne(User user) {
        User user1 = usersDao.selectOne(user);
        return user1;
    }
    //根据id查一个
    public User queryId(String id){
        User user = usersDao.selectByPrimaryKey(id);
        return user;
    }

    //随机查询五个用户，除自己
    public Set<User> queryAll(String id) {
        List<User> users = usersDao.selectAll();
        HashSet<User> hashSet = new HashSet<User>();
        while (true){
            User user = users.get((int) (Math.random() * users.size()));
            if (!user.getId().equals(id)){
                hashSet.add(user);
            }
            if (hashSet.size()==5)return hashSet;
        }
    }

    //分页查询所有用户
    public Map queryAllUser(Integer page, Integer rows) {
        HashMap hashMap = new HashMap();
        List<User> users = usersDao.selectByRowBounds(new User(), new RowBounds((page-1)*rows,rows));
        //查询总条数
        int records = usersDao.selectCount(new User());
        //设置总页数
        int total = (records+(rows-1))/rows;
        hashMap.put("rows",users);
        hashMap.put("records",records);
        hashMap.put("total",total);
        hashMap.put("page",page);

        //rows 查询数据   page 当前页   records 总条数   total 总页数
        return hashMap;
    }
}
