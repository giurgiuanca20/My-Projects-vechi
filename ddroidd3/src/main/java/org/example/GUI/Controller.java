package org.example.GUI;

import org.example.DAO.*;
import org.example.Model.*;
import org.example.database.Connect;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static javax.swing.JOptionPane.showMessageDialog;


public class Controller implements ActionListener {
    private ViewHome viewHome;
    private ViewApplicant viewApplicant;
    private ViewCreatApplicant viewCreatApplicant;
    private ViewEmployer viewEmployer;
    private ViewCreateEmployer viewCreateEmployer;
    private ViewJob viewJob;
    private ViewLogIn viewLogIn;
    private ViewSelectJob viewSelectJob;
    private Connection conn;
    private Applicant applicant = new Applicant();
    private Employer employer = new Employer();
    private Job job = new Job();
    private Account account = new Account();
    private Applicantsjobs applicantsjobs = new Applicantsjobs();
    private int idCurrentAccount = -1;
    private int aux = 1;
    private ApplicantDAO applicantDAO;
    private EmployerDAO employerDAO;
    private JobDAO jobDAO;
    private AccountDAO accountDAO;
    private ApplicantsJobsDAO applicantsjobsDAO;
    private String jobName;

    // Helper method to check if a phone number is valid
    private boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "^[0-9]{10}$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);

        return matcher.matches();
    }
    // Helper method to check if an email is valid
    public static boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public Controller(ViewHome v) {
        this.conn = Connect.getConnection();

        this.viewHome = v;
        this.viewApplicant = new ViewApplicant("Applicant");
        this.viewCreatApplicant = new ViewCreatApplicant("Final Applicant");
        this.viewEmployer = new ViewEmployer("Employer");
        this.viewCreateEmployer = new ViewCreateEmployer("Final Employer");
        this.viewJob = new ViewJob("Job");
        this.viewLogIn = new ViewLogIn("Log In");
        this.viewSelectJob = new ViewSelectJob("Select Job");

        this.applicantDAO = new ApplicantDAO();
        this.employerDAO = new EmployerDAO();
        this.jobDAO = new JobDAO();
        this.accountDAO = new AccountDAO();
        this.applicantsjobsDAO = new ApplicantsJobsDAO();

    }


    public void actionPerformed(ActionEvent ev) {
        String s = ev.getActionCommand();
        // Handle different actions based on their action command
        switch (s) {
            case "joinApplicant":
                viewApplicant.prepareGui(this);
                viewHome.setVisible(false);
                break;
            case "createApplicant":
                aux = 1;
                String errorMessage = " ";

                if (viewApplicant.gettFirstNameEmpty() == true) {
                    aux = 0;
                    errorMessage = "An error occurred: Invalid first name.\n";
                }
                if (viewApplicant.gettLastNameEmpty() == true) {
                    aux = 0;
                    errorMessage += "An error occurred: Invalid last name.\n";
                }
                if (isValidPhoneNumber(viewApplicant.gettPhone()) == false || viewApplicant.gettPhoneEmpty() == true) {
                    aux = 0;
                    errorMessage += "An error occurred: Invalid  phone\n";
                }
                if (isValidEmail(viewApplicant.gettEmail()) == false || viewApplicant.gettEmailEmpty() == true) {
                    aux = 0;
                    errorMessage += "An error occurred: Invalid email.\n";
                }
                if (viewApplicant.gettAddress1Empty() == true) {
                    aux = 0;
                    errorMessage = "An error occurred: Invalid address.\n";
                }
                if (viewApplicant.gettUsernameEmpty() == true) {
                    aux = 0;
                    errorMessage = "An error occurred: Invalid username.\n";
                }
                if (viewApplicant.gettPasswordEmpty() == true) {
                    aux = 0;
                    errorMessage = "An error occurred: Invalid password.\n";
                }
                viewApplicant.validare(errorMessage);
                if (errorMessage.equals(" ") == false)
                    showMessageDialog(null, "Invalid informations!");
                if (aux == 1) {
                    applicant.setFirstName(viewApplicant.gettFirstName());
                    applicant.setLastName(viewApplicant.gettLastName());
                    applicant.setPhone(viewApplicant.gettPhone());
                    applicant.setEmail(viewApplicant.gettEmail());
                    applicant.setAddress1(viewApplicant.gettAddress1());
                    applicant.setAddress2(viewApplicant.gettAddress2());
                    System.out.println(viewApplicant.getComboBoxCountry());
                    applicant.setCountry(applicantDAO.findIdByName(viewApplicant.getComboBoxCountry(), "country"));
                    applicant.setCity(applicantDAO.findIdByName(viewApplicant.getComboBoxCity(), "city_" + viewApplicant.getComboBoxCountry()));
                    applicant.setAccount(accountDAO.getLastIndex() + 1);
                    idCurrentAccount = accountDAO.getLastIndex() + 1;
                    applicantDAO.insert(applicant);
                    account.setUsername(viewApplicant.gettUsername());
                    account.setPassword(viewApplicant.gettPassword());
                    account.setType(1);

                    accountDAO.insert(account);

                    viewCreatApplicant.prepareGui(this, viewApplicant.gettFirstName(), viewApplicant.gettLastName(), viewApplicant.gettPhone(), viewApplicant.gettEmail(), viewApplicant.gettAddress1(), viewApplicant.gettAddress2(), "", "", "");
                }
                break;
            case "searchJob":
                viewSelectJob.prepareGui(this);
                viewSelectJob.displayJobs(jobDAO.findAll());
                break;
            case "applyJob":
                jobName = viewSelectJob.getSelectedJobName();
                applicantsjobs.setJobs(jobDAO.findIdBytitle(jobName, "joblisting"));
                applicantsjobs.setApplicants(idCurrentAccount);
                applicantsjobsDAO.insert(applicantsjobs);
                break;

            case "joinEmployer":
                viewEmployer.prepareGui(this);
                viewHome.setVisible(false);
                break;
            case "createEmployer":
                aux = 1;
                errorMessage = " ";

                if (viewEmployer.gettFirstNameEmpty() == true) {
                    aux = 0;
                    errorMessage = "An error occurred: Invalid first name.\n";
                }
                if (viewEmployer.gettLastNameEmpty() == true) {
                    aux = 0;
                    errorMessage += "An error occurred: Invalid last name.\n";
                }
                if (isValidEmail(viewEmployer.gettEmail()) == false || viewEmployer.gettEmailEmpty() == true) {
                    aux = 0;
                    errorMessage += "An error occurred: Invalid email.\n";
                }
                if (viewEmployer.gettCompanyNameEmpty() == true) {
                    aux = 0;
                    errorMessage = "An error occurred: Invalid company name.\n";
                }
                if (viewEmployer.gettUsernameEmpty() == true) {
                    aux = 0;
                    errorMessage = "An error occurred: Invalid username.\n";
                }
                if (viewEmployer.gettPasswordEmpty() == true) {
                    aux = 0;
                    errorMessage = "An error occurred: Invalid password.\n";
                }
                viewEmployer.validare(errorMessage);
                System.out.println(errorMessage);
                if (errorMessage.equals(" ") == false)
                    showMessageDialog(null, "Invalid informations!");
                if (aux == 1) {
                    employer.setEmployerFirstName(viewEmployer.gettFirstName());
                    employer.setEmployerLastName(viewEmployer.gettLastName());
                    employer.setEmployerEmail(viewEmployer.gettEmail());
                    employer.setEmployerCompanyName(viewEmployer.gettCompanyName());
                    //         e.setJobListings
                    employer.setAccount(accountDAO.getLastIndex() + 1);
                    idCurrentAccount = accountDAO.getLastIndex() + 1;
                    System.out.println("em:" + idCurrentAccount);
                    employerDAO.insert(employer);

                    account.setUsername(viewEmployer.gettUsername());
                    account.setPassword(viewEmployer.gettPassword());
                    account.setType(2);

                    accountDAO.insert(account);
                    viewCreateEmployer.prepareGui(this, viewEmployer.gettFirstName(), viewEmployer.gettLastName(), viewEmployer.gettEmail(), viewEmployer.gettCompanyName());
                }
                break;
            case "viewApplicantsForCurrentEmployer":
                viewCreateEmployer.displayApplicantsEmployer(employerDAO.getAllApplicants(idCurrentAccount));
                break;
            case "createJob":
                viewJob.prepareGui(this);
                break;
            case "deleteJob":
                if (idCurrentAccount == jobDAO.findAccountByTitle(viewCreateEmployer.gettDeleteJob())) { ///daca el a postat jobul
                    jobDAO.delete(viewCreateEmployer.gettDeleteJob(), "jobtitle");
                }
                viewCreateEmployer.displayJobsEmployer(employerDAO.getAllJobs(idCurrentAccount));
                break;
            case "viewEmployerJob":
                viewCreateEmployer.displayJobsEmployer(employerDAO.getAllJobs(idCurrentAccount));
                break;
            case "viewApplicantsForJob":
                jobName = viewCreateEmployer.getSelectedJob();
                viewCreateEmployer.displayApplicantsJob(employerDAO.getApplicantsJob(jobName));
                break;
            case "addJob":

                aux = 1;
                errorMessage = " ";

                if (viewJob.gettJobTitleEmpty() == true) {
                    aux = 0;
                    errorMessage = "An error occurred: Invalid title.\n";
                }
                if (viewJob.gettJobDescriptionEmpty() == true) {
                    aux = 0;
                    errorMessage += "An error occurred: Invalid Description.\n";
                }
                viewJob.validare(errorMessage);
                if (errorMessage.equals(" ") == false)
                    showMessageDialog(null, "Invalid informations!");
                if (aux == 1) {
                    job.setJobTitle(viewJob.gettJobTitle());
                    job.setJobDescription(viewJob.gettJobDescription());
                    job.setEmployer(idCurrentAccount);
                    jobDAO.insert(job);
                }
                break;
            case "LogIn":
                viewLogIn.prepareGui(this);
                break;
            case "FinishLogIn":
                int aux = accountDAO.findIdByAccount(viewLogIn.gettUsername(), viewLogIn.gettPassword());
                idCurrentAccount = accountDAO.findPersonByAccount(aux);

                if (aux == -1)
                    showMessageDialog(null, "Incorect username or password");
                else if (accountDAO.findTypeById(aux) == 1) {

                    String tabel = "applicant";
                    viewCreatApplicant.prepareGui(this, accountDAO.findStringById("firstname", tabel, aux),
                            accountDAO.findStringById("lastname", tabel, aux),
                            accountDAO.findStringById("phone", tabel, aux),
                            accountDAO.findStringById("email", tabel, aux),
                            accountDAO.findStringById("address1", tabel, aux),
                            accountDAO.findStringById("address2", tabel, aux),
                            "Romania", "", "Iasi");
                } else {
                    String tabel = "employer";
                    viewCreateEmployer.prepareGui(this, employerDAO.findStringById("employerfirstname", tabel, aux),
                            employerDAO.findStringById("employerlastname", tabel, aux),
                            employerDAO.findStringById("employeremail", tabel, aux),
                            employerDAO.findStringById("employercompanyName", tabel, aux));
                }
                break;
            case "comboBoxCountry":
                viewApplicant.createCity();
                break;

            default:
        }

    }
}
