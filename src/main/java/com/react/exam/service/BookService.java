package com.react.exam.service;

import com.react.exam.domain.Book;
import com.react.exam.domain.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//기능정의 + 트랜젝션관리
@RequiredArgsConstructor //final붙은 필드명의 get,set생성하여 DI해줌
@Service
public class BookService {

    private final BookRepository bookRepository;

    @Transactional  //함수가 종료될때 커밋/롤백을 정하겠다다
   public Book save(Book book) {
        Book saveBook = bookRepository.save(book);
        return saveBook;
    }

    @Transactional(readOnly = true) //변경감지 비활성화,
    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("id를 확인해주세요"));
    }

    @Transactional(readOnly = true)
    public List<Book> findAll() {
        List<Book> all = bookRepository.findAll();
        return all;
    }

    @Transactional
    public Book update(Long id, Book book) {
        Book getbook = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("id를 확인해주세요~~"));   //db에서 가져왔으므로 영속화되어관리리
        getbook.setTitle(book.getTitle());
        getbook.setAuthor(book.getAuthor());
        return book;
    }   //종료->트랜젝션종료->영속화되어있는 데이터를 db로 갱신(flush) => 이때 커밋되는것을 더티체킹이라함

    @Transactional
    public String delete(Long id) {
        bookRepository.deleteById(id);
        return "ok";
    }
}
