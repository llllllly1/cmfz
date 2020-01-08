package com.baizhi.ly.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Guru {

  private String id;
  private String name;
  private String photo;
  private String status;
  private String nickName;
}
