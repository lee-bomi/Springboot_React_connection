package com.react.exam.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.react.exam.domain.Book;
import com.react.exam.domain.BookRepository;
import com.react.exam.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.mockito.Mockito.when;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 통합테스트(모든 빈을 IOC에 띄우고 테스트) - 다 IOC에 올라감
 * SpringBootTest.WebEnvironment.MOCK) : 실제 톰켓이아닌 다른 톰켓으로 테스트
 * SpringBootTest.WebEnvironment.RANDOM_PORT) : 실제 톰켓으로 띄움
 * @AutoConfigureMockMvc : MockMvc를 IOC에 등록해서 DI해줌
 * @Transactional : 각 테스트 종료시 트랜젝션을 roll-back해줌 ->독립테스트를 가능하게함
 */
@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private BookService bookService;
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    public void init() {
        entityManager.createNativeQuery("ALTER TABLE book AUTO_INCREMENT=1").executeUpdate();
    }

    @Test
    public void saveTest() throws Exception {
        //given
        Book book = new Book(null, "스프링따라하기", "코스");
        String content = new ObjectMapper().writeValueAsString(book);
        //스텁 : 실제서비스에선 필요없음
        when(bookService.save(book)).thenReturn(new Book(1L, "스프링따라하기", "코스"));

        //when(테스트실행)
        ResultActions resultAction= mockMvc.perform(MockMvcRequestBuilders.post("/book")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content)
                .accept(MediaType.APPLICATION_JSON_UTF8));

        //검증
        resultAction
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("스프링따라하기"))
                .andDo(MockMvcResultHandlers.print());

    }
}
