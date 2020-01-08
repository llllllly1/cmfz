package com.baizhi.ly.entity;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Counter {

  @Id
  private String id;
  private String title;
  private Integer count;
  @JSONField(format = "yyyy-MM-dd")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private java.util.Date createDate;
  private String userId;
  private String courseId;


}
