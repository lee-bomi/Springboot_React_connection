package com.react.exam.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.react.exam.domain.Book;
import com.react.exam.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import javax.persistence.EntityManager;
import javax.print.attribute.standard.Media;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * 단위테스트(컨트롤러관련 로직만 띄우기)
 * @WebMvcTest : 독립적인환경에서 단위테스트가능
 * @MockBean : MOCK 공간이아닌 IOC공간에 가짜 객체를 임시로 띄우는 기능
 */
@Slf4j
@WebMvcTest
public class BookControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    public void init() {
        entityManager.createNativeQuery("ALTER TABLE book ALTER COLUMN id RESTART WITH 1").executeUpdate();
    }

    /**
     * writeValueAsString : object -> json으로 변환(json데이터를 파라미터로 던지기위함)
     * readValueAsString : json으로 -> object로 변환
     */
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

    @Test
    public void findAllTest() throws Exception {
        //given
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "스프링부트 따라하기", "코스"));
        books.add(new Book(2L, "리엑트 따라하기", "코스"));
        when(bookService.findAll()).thenReturn(books);

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/book")
                .accept(MediaType.APPLICATION_JSON));
        
        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.[0].title").value("스프링부트 따라하기"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public Book findByIdTest() throws Exception {
        //given
        Long id = 1L;
        when(bookService.findById(id)).thenReturn(new Book(1L, "자바 공부하기", "쌀"));

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/book/{id}", id)
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("자바 공부하기"))
                .andDo(MockMvcResultHandlers.print());
    }
}
