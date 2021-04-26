package com.lab4.demo.Book;

import com.lab4.demo.BaseControllerTest;
import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.book.BookController;
import com.lab4.demo.book.BookService;
import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.dto.BookDTO;
import com.lab4.demo.report.CSVReportService;
import com.lab4.demo.report.PdfReportService;
import com.lab4.demo.report.ReportServiceFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static com.lab4.demo.TestCreationFactory.randomLong;
import static com.lab4.demo.report.ReportType.CSV;
import static com.lab4.demo.report.ReportType.PDF;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class BookControllerTest extends BaseControllerTest {
    @InjectMocks
    private BookController bookController;

    @Mock
    private BookService bookService;

    @Mock
    private ReportServiceFactory reportServiceFactory;

    @Mock
    private CSVReportService csvReportService;

    @Mock
    private PdfReportService pdfReportService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        bookController = new BookController(reportServiceFactory, bookService);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }
    @Test
    void allBooks() throws Exception {
        List<BookDTO> books = TestCreationFactory.listOf(BookDTO.class);
        when(bookService.findAll()).thenReturn(books);

        ResultActions response = mockMvc.perform(get("/api/books"));

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(books));
    }

    @Test
    void create() throws Exception {
        BookDTO reqBook = BookDTO.builder()
                .author("test")
                .genre("test")
                .price(56)
                .quantity(4)
                .title("test")
                .build();
        when(bookService.create(reqBook)).thenReturn(reqBook);
        ResultActions resultActions = performPostWithRequestBody("/api/books",reqBook);
        resultActions.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqBook));
    }

    @Test
    void edit() throws Exception {
        BookDTO reqBook = BookDTO.builder()
                .author("test")
                .genre("test")
                .price(56)
                .quantity(4)
                .title("test")
                .build();
        when(bookService.edit(reqBook)).thenReturn(reqBook);
        ResultActions resultActions = performPatchWithRequestBodyAndPathVariable("/api/books", reqBook);
        resultActions.andExpect(status().isOk());
    }


    @Test
    void delete() throws Exception {
        long id = randomLong();
        doNothing().when(bookService).delete(id);

        ResultActions result = performDeleteWIthPathVariable("/api/books/{id}", id);
        verify(bookService, times(1)).delete(id);

        result.andExpect(status().isOk());
    }

    @Test
    void exportReport() throws Exception {
        when(reportServiceFactory.getReportService(PDF)).thenReturn(pdfReportService);
        when(reportServiceFactory.getReportService(CSV)).thenReturn(csvReportService);
        List<Book> books = new ArrayList<>();
        String pdfResponse = "PDF!";
        when(pdfReportService.export(books)).thenReturn(pdfResponse);
        String csvResponse = "CSV!";
        when(csvReportService.export(books)).thenReturn(csvResponse);

        ResultActions pdfExport = mockMvc.perform(get("/api/books/export/{type}" , "PDF"));
        ResultActions csvExport = mockMvc.perform(get("/api/books/export/{type}" , "CSV"));

        pdfExport.andExpect(status().isOk())
                .andExpect(content().string(pdfResponse));
        csvExport.andExpect(status().isOk())
                .andExpect(content().string(csvResponse));
    }
}