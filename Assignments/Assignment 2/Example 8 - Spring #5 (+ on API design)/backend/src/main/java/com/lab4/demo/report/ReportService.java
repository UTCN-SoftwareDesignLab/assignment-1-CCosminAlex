package com.lab4.demo.report;

import com.lab4.demo.book.model.Book;

import java.util.List;

public interface ReportService {
    String export(List<Book> books);

    ReportType getType();
}
