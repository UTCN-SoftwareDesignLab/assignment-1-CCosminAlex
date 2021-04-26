package com.lab4.demo.report;

import com.lab4.demo.book.model.Book;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static com.lab4.demo.report.ReportType.PDF;

@Service
public class PdfReportService implements ReportService {
    @Override
    public String export(List<Book> books){
        try {
            PDFFACTORY.createPDF(books);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "PDF report exported";
    }


    @Override
    public ReportType getType() {
        return PDF;
    }
}
