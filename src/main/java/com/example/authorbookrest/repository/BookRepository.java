package com.example.authorbookrest.repository;

import com.example.authorbookrest.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;

public interface BookRepository extends JpaRepository<Book, Integer>,
        QuerydslPredicateExecutor<Book> {

}
