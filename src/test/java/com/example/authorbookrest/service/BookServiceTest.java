package com.example.authorbookrest.service;

import com.example.authorbookrest.entity.Book;
import com.example.authorbookrest.mapper.BookMapper;
import com.example.authorbookrest.repository.BookRepository;
import com.example.authorbookrest.repository.CurrencyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    private BookService bookService;

    BookRepository bookRepository = Mockito.mock(BookRepository.class);

    @MockBean
    BookMapper bookMapper;

    @MockBean
    CurrencyRepository currencyRepository;

    @BeforeEach
    public void beforeAll() {
        bookService = new BookService(bookRepository, bookMapper, currencyRepository);
    }


    @Test
    void save() {
        Book book = Book.builder()
                .description("asdf")
                .serialNumber("asfdsad")
                .build();
        bookService.save(book);
        verify(bookRepository,  times(1)).save(any());
    }
}
