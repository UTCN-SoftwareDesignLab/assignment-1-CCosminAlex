package com.lab4.demo.book;

import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.dto.BookDTO;
import com.lab4.demo.item.ItemService;
import com.lab4.demo.item.model.dto.ItemDTO;
import com.lab4.demo.report.ReportServiceFactory;
import com.lab4.demo.report.ReportType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lab4.demo.UrlMapping.*;

@RestController
@RequestMapping(BOOKS)
@RequiredArgsConstructor
public class BookController {

    private final ReportServiceFactory reportServiceFactory;
    private final BookService bookService;

    @GetMapping
    public List<BookDTO> allBooks() {
        return bookService.findAll();
    }

    @PostMapping
    public BookDTO create(@RequestBody BookDTO book) {

        return bookService.create(book);
    }

    @PatchMapping(ENTITY)
    public BookDTO changeQuantity(@PathVariable Book book, @RequestBody int newQuantity) {
        return bookService.changeQuantity(book, newQuantity);
    }

    @PutMapping()
    public BookDTO edit(@RequestBody BookDTO bookDTO) {
        return bookService.edit(bookDTO);
    }

    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }

    @GetMapping(ENTITY)
    public BookDTO getItem(@PathVariable Long id) {
        return bookService.get(id);
    }

    @GetMapping(EXPORT_REPORT)
    public String exportReport(@PathVariable ReportType type) {
        return reportServiceFactory.getReportService(type).export();
    }

}
