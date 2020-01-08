package com.baizhi.ly.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Attention {

  @Id
  private String id;
  private String guruId;
  private String userId;


}
