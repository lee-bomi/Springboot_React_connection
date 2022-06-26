package com.react.exam.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository // Jparepository를 상속하면 안적어줘도 된다
//CRUD함수를 들고있음
public interface BookRepository extends JpaRepository<Book, Long> {

}
