package org.shark.pagination.model.dto;

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
public class UserDTO {
  private Integer uid;
  private String firstName;
  private String lastName;
  private String email;
  private String gender;
  private String ipAddress;
}
