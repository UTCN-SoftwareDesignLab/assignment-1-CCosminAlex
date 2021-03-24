package service.report;

import model.Report;
import repository.report.ReportRepository;

import java.sql.Date;
import java.util.List;

public class ReportServiceIMP implements ReportService{

    private ReportRepository reportRepository;

    public ReportServiceIMP(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public boolean addReport(int idEmployee, Date date, String activity) {
        return reportRepository.addReport(idEmployee, date, activity);
    }

    @Override
    public List<Report> findAll() {
        return reportRepository.findAll();
    }
}