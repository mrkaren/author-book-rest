package com.example.authorbookrest.service;

import com.example.authorbookrest.dto.BookDto;
import com.example.authorbookrest.dto.BookSearchDto;
import com.example.authorbookrest.entity.Book;
import com.example.authorbookrest.entity.Currency;
import com.example.authorbookrest.entity.QBook;
import com.example.authorbookrest.mapper.BookMapper;
import com.example.authorbookrest.repository.BookRepository;
import com.example.authorbookrest.repository.CurrencyRepository;
import com.example.authorbookrest.util.RoundUtil;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final CurrencyRepository currencyRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public List<BookDto> search(int page, int size,
                                BookSearchDto bookSearchDto) {
//        Sort sort = Sort.by(Sort.Direction.DESC, "id");
//
//        if (bookSearchDto.getSortBy() != null) {
//            sort = Sort.by("asc".equalsIgnoreCase(bookSearchDto.getSortDirection())
//                    ? Sort.Direction.ASC : Sort.Direction.DESC, bookSearchDto.getSortBy());
//        }

//        PageRequest pageRequest = PageRequest.of(page, size, sort);

//        Page<Book> all = bookRepository.findAll(pageRequest);

        List<Book> all = searchBooksByFilter(page, size, bookSearchDto);

        List<BookDto> bookDtos = bookMapper.mapListToDtos(all);
        List<Currency> currencies = currencyRepository.findAll();
        if (!currencies.isEmpty()) {
            Currency currency = currencies.get(0);
            for (BookDto bookDto : bookDtos) {
                double priceAmd = bookDto.getPriceAmd();
                bookDto.setPriceRub(RoundUtil.round(priceAmd / currency.getRub(), 2));
                bookDto.setPriceUsd(RoundUtil.round(priceAmd / currency.getUsd(), 1));

            }
        }
        return bookDtos;
    }

    private List<Book> searchBooksByFilter(int page, int size, BookSearchDto bookSearchDto) {
        QBook qBook = QBook.book;
        var query = new JPAQuery<Book>(entityManager);
        JPAQuery<Book> from = query.from(qBook);

        if (bookSearchDto.getLanguage() != null) {
            from.where(qBook.language.eq(bookSearchDto.getLanguage()));
        }

        if (bookSearchDto.getDescription() != null && !bookSearchDto.getDescription().isEmpty()) {
            from.where(qBook.description.contains(bookSearchDto.getDescription()));
        }

        if (bookSearchDto.getSerialNumber() != null &&
                !bookSearchDto.getSerialNumber().isEmpty()) {
            from.where(qBook.serialNumber.contains(bookSearchDto.getSerialNumber()));
        }

        if (bookSearchDto.getMinPrice() > 0 && bookSearchDto.getMaxPrice() > 0) {
            from.where(qBook.priceAmd.gt(bookSearchDto.getMinPrice())
                    .and(qBook.priceAmd.lt(bookSearchDto.getMaxPrice())));
        }

        if (bookSearchDto.getAuthorName() != null && !bookSearchDto.getAuthorName().isEmpty()) {
            from.where(qBook.author.name.contains(bookSearchDto.getAuthorName()));
        }
        if (page > 0) {
            from.offset((long) page * size);
        }
        from.limit(size);

        PathBuilder<Object> orderByExpression = new PathBuilder<Object>(Book.class, bookSearchDto.getSortBy());

        from.orderBy( new OrderSpecifier("asc".equalsIgnoreCase(bookSearchDto.getSortDirection()) ? Order.ASC
                : Order.DESC, orderByExpression));

        return from.fetch();
    }

}
