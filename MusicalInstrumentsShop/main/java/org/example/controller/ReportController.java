package org.example.controller;

import org.example.model.security.Report;
import org.example.model.validation.Notification;
import org.example.service.ReportService;
import org.example.view.ReportView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;

public class ReportController {

    private final ReportView reportView;
    private final ReportService reportService;

    public ReportController(ReportView reportView, ReportService reportService) {
        this.reportView = reportView;
        this.reportService = reportService;

        this.reportView.setGenerateButtonListener(new GenerateButtonListener());
    }

    private class GenerateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Report report=null;
            String name = reportView.getTfName();
            String date = reportView.getTfDate();
            Date dateStart = null, dateEnd = null;
            String[] dates = date.split("->");

            try {
                dateStart = Date.valueOf(dates[0]);
                dateEnd = Date.valueOf(dates[1]);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(reportView, "Invalid date format. Please use yyyy-MM-dd.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                 report = reportService.getReport(name, dateStart, dateEnd);
                displayReportSuccessMessage();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(reportView.getContentPane(), "Error retrieving report: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

            reportView.setReport(report);

        }

        private void displayReportSuccessMessage() {
            JOptionPane.showMessageDialog(reportView.getContentPane(), "Report created successfully!");
        }
    }
}
