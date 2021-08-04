package org.example.repository;

import org.example.model.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
    //<데이터베이스 테이블과 연결될 객체, 해당 객체의 Id에 해당하는 필드 타입 선언>
    //TodoRequest와 TodoResponse는 데이터를 요청하고 응답하는 역할이므로 데이터베이스에 저장할 필요가 없음
}
