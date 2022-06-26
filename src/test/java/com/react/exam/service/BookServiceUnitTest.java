package com.react.exam.service;

import com.react.exam.domain.BookRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * 단위테스트(Service와 관련된 애들만 메모리에 띄우면됨)
 * MockitoExtension : 서비스테스트에서는 Repository가 필요함. 이 어노테이션은 BookRepository를 가짜객체로 만들어줄수있음
 * @Mock : Spring MVC환경이아닌 가짜 환경(메모리)
 * @InjectMocks : BookService객체가 만들어질때, 현재파일에 @Mock으로 등록된 애들을 모두 DI주입받는다
 */
@ExtendWith(MockitoExtension.class)
public class BookServiceUnitTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;
}
