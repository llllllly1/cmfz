package com.baizhi.ly.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * (Log)实体类
 *
 * @author makejava
 * @since 2019-12-04 19:52:42
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Log implements Serializable {
    @Id
    private String id;
    
    private String name;
    
    private String thing;
    
    private Integer status;

    private Date thingDate;


}