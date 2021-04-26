package com.lab4.demo.report;

import com.lab4.demo.book.model.Book;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static com.lab4.demo.report.ReportType.CSV;

@Service
public class CSVReportService implements ReportService {

    @Override
    public String export(List<Book> books){
        try {
            CSVFactory.createReport(books);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "CSV report exported";
    }

    @Override
    public ReportType getType() {
        return CSV;
    }
}
