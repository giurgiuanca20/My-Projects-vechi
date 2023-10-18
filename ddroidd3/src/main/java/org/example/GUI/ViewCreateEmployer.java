package org.example.GUI;

import org.example.Model.Applicant;
import org.example.Model.Create;
import org.example.Model.Job;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ViewCreateEmployer extends JFrame {
    private final JFrame frame;
    private JLabel lExcellent, lTitle, lSummary, lFirstName, lLastName, lEmail, lCompanyName, lDeleteJob;
    private JButton bViewApplicants, bAddJob, bDeleteJob, bViewJobs, bApplicantsForJob;
    private JTextField tDeleteJob;
    private Create create = new Create();


    public ViewCreateEmployer(String name) {
        frame = new JFrame(name);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1600, 900);
        frame.setLocationRelativeTo(null);

    }
    // JTables to display job, applicant, and applicant job information
    private JTable jobTable = new JTable();
    private JTable applicantTable = new JTable();
    private JTable applicantJobTable = new JTable();
    private String selectedJobName = "";
    // Method to get the selected job's name
    public String getSelectedJob() {
        return selectedJobName;
    }
    // Method to display jobs for an employer
    public void displayJobsEmployer(List<Job> jobs) {

        String[] res = {" "};

        Object[][] data = new Object[jobs.size()][2];

        for (int i = 0; i < jobs.size(); i++) {
            Job job = jobs.get(i);
            data[i][0] = job.getJobTitle();
            data[i][1] = job.getJobDescription();
        }

        String[] columnNames = {"Title", "Description"};

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        jobTable.setModel(model);

        JScrollPane scrollPane = new JScrollPane(jobTable);
        jobTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = jobTable.getSelectedRow();
                if (selectedRow >= 0) {
                    Object jobName = jobTable.getValueAt(selectedRow, 0);
                    Object jobDescription = jobTable.getValueAt(selectedRow, 1);

                    System.out.println("Nume Job: " + jobName);
                    System.out.println("Descriere Job: " + jobDescription);
                    selectedJobName = String.valueOf(jobName);
                }
            }
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Jobs for employ"));

        panel.add(scrollPane, BorderLayout.CENTER);
        frame.add(scrollPane);
        frame.setVisible(true);
    }
    // Method to display a list of applicants for an employer
    public void displayApplicantsEmployer(List<Applicant> applicants) {

        Object[][] data = new Object[applicants.size()][4];

        for (int i = 0; i < applicants.size(); i++) {
            Applicant applicant = applicants.get(i);
            data[i][0] = applicant.getFirstName();
            data[i][1] = applicant.getLastName();
            data[i][2] = applicant.getPhone();
            data[i][3] = applicant.getEmail();

        }

        String[] columnNames = {"First Name", "Last Name", "Phone", "Email"};

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        applicantTable.setModel(model);

        JScrollPane scrollPane = new JScrollPane(applicantTable);
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Applicants for employ"));

        panel.add(scrollPane, BorderLayout.CENTER);

        frame.add(panel);
        frame.setVisible(true);
    }
    // Method to display a list of applicants for a job
    public void displayApplicantsJob(List<Applicant> applicants) {

        Object[][] data = new Object[applicants.size()][4];

        for (int i = 0; i < applicants.size(); i++) {
            Applicant applicant = applicants.get(i);
            data[i][0] = applicant.getFirstName();
            data[i][1] = applicant.getLastName();
            data[i][2] = applicant.getPhone();
            data[i][3] = applicant.getEmail();

        }

        String[] columnNames = {"First Name", "Last Name", "Phone", "Email"};

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        applicantJobTable.setModel(model);

        JScrollPane scrollPane = new JScrollPane(applicantJobTable);
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Applicants for Job"));

        panel.add(scrollPane, BorderLayout.CENTER);

        frame.add(panel);
        frame.setVisible(true);
    }
    // Method to get the text entered in the "Delete Job" text field
    public String gettDeleteJob() {
        return tDeleteJob.getText();
    }

    public void prepareGui(Controller controller, String firstName, String lastName, String email, String companyName) {
        lExcellent = create.makeLabel("Excellent!");
        lTitle = create.makeLabel("See you in November 2023!");

        lSummary = create.makeLabel("Submission Summary:");
        lFirstName = create.makeLabel("First name:    " + firstName);
        lLastName = create.makeLabel("Last Name:    " + lastName);
        lEmail = create.makeLabel("Email:    " + email);
        lCompanyName = create.makeLabel("Company name:    " + companyName);
        lDeleteJob = create.makeLabel("Delete Job:    ");

        tDeleteJob = create.makeTextField();

        bViewApplicants = create.makeButon("viewApplicantsForCurrentEmployer", " View Applicants ");
        bViewApplicants.addActionListener(controller);
        bAddJob = create.makeButon("createJob", " Add job ");
        bAddJob.addActionListener(controller);
        bDeleteJob = create.makeButon("deleteJob", " Delete job ");
        bDeleteJob.addActionListener(controller);
        bViewJobs = create.makeButon("viewEmployerJob", " View jobs ");
        bViewJobs.addActionListener(controller);
        bApplicantsForJob = create.makeButon("viewApplicantsForJob", " View applicants for selected job ");
        bApplicantsForJob.addActionListener(controller);


        frame.setLayout(new FlowLayout());
        frame.add(lTitle);
        frame.add(lExcellent);
        frame.add(lSummary);
        frame.add(lFirstName);
        frame.add(lLastName);
        frame.add(lEmail);
        frame.add(lCompanyName);

        frame.add(bAddJob);
        frame.add(lDeleteJob);
        frame.add(tDeleteJob);
        frame.add(bDeleteJob);
        frame.add(bViewApplicants);
        frame.add(bViewJobs);
        frame.add(bApplicantsForJob);

        frame.setVisible(true);

    }
}

