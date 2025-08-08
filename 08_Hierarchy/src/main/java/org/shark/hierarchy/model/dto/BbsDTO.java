package org.shark.hierarchy.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class BbsDTO {
  private Integer bbsId;
  private String content;
  private Integer state;
  private Integer depth;
  private Integer groupId;
  private Integer groupOrder;
}