package org.shark.di.service;

import org.shark.di.dao.DIDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/*
 * @Service
 * 
 * 1. 비즈니스 로직을 담당하는 클래스임을 명확히 나타내는 어노테이션입니다.
 * 2. 컨트롤러(@Controller)와 레파지토리(@Repository) 사이에서 핵심 비즈니스 로직을 구현하는 역할을 합니다.
 * 3 @Component의 특수화 된 어노테이션으로 Spring Container에 자동으로 빈으로 등록됩니다.
 */

/*
 * 생성자 주입의 경우 @Autowired를 생략할 수 있으므로,
 * 아래 생성자 자동 코드 생성 어노테이션들을 사용할 수 있습니다.
 * 
 * @AllArgsConstructor
 * 
 * 1. 모든 필드를 매개변수로 포함하는 생성자를 만듭니다.
 * 2. final이 아니거나 @NonNull이 없는 필드도 모두 포함됩니다.
 * 3. @NonNull 필드는 null 체크가 생성됩니다.
 * 
 * @RequiredArgsConstructor
 * 
 * 1. 초기화되지 않은 final 필드와 @NonNull이 있고 초기화되지 않는 필드만 매개변수로 포함하는 생성자를 만듭니다.
 * 2. 반드시 초기화가 필요하거나 null이 아니어야 하는 필드를 포함한 생성자를 만듭니다.
 * 3. 스프링에서 권장하는 필드의 불변성 유지(final)를 위한 의존성 주입에서 많이 사용합니다.
 */

// @AllArgsConstructor
@RequiredArgsConstructor
@Service
public class DIServiceImpl  implements DIService {

  private final DIDao diDao;

  @Override
  public void serviceMethod() {
    System.out.println("serviceMethod");
    diDao.daoMethod();
  }

}
