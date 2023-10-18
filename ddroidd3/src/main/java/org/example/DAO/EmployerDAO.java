package org.example.DAO;

import org.example.Model.Applicant;
import org.example.Model.Employer;
import org.example.Model.Job;
import org.example.database.Connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployerDAO extends AbstractDAO<Employer> {
    public static List<Job> getAllJobs(int employerId) {
        List<Job> jobs = new ArrayList<>();
        try (Connection connection = Connect.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM job WHERE employer = ?");
        ) {
            statement.setInt(1, employerId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Job job = new Job();
                    job.setId(resultSet.getInt("id"));
                    job.setJobTitle(resultSet.getString("jobtitle"));
                    job.setJobDescription(resultSet.getString("jobdescription"));

                    jobs.add(job);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return jobs;
    }
    public static List<Applicant> getAllApplicants(int employerId) {
        List<Applicant> applicants = new ArrayList<>();

        try (Connection connection = Connect.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT DISTINCT firstname,lastname,phone,email FROM applicant JOIN applicantsjobs ON applicant.id = applicantsjobs.applicants JOIN job ON applicantsjobs.jobs = job.id JOIN employer ON job.employer = ?");
        ) {
            statement.setInt(1, employerId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Applicant aplicant = new Applicant();
                    aplicant.setFirstName(resultSet.getString("firstname"));
                    aplicant.setLastName(resultSet.getString("lastname"));
                    aplicant.setPhone(resultSet.getString("phone"));
                    aplicant.setEmail(resultSet.getString("email"));

                    applicants.add(aplicant);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return applicants;
    }
    public static List<Applicant> getApplicantsJob(String jobTitle) {
        List<Applicant> applicants = new ArrayList<>();

        try (Connection connection = Connect.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT DISTINCT firstname,lastname,phone,email FROM applicant JOIN applicantsjobs ON applicant.id = applicantsjobs.applicants JOIN job ON applicantsjobs.jobs = job.id JOIN employer ON job.jobtitle = ?");
        ) {
            statement.setString(1, jobTitle);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Applicant aplicant = new Applicant();
                    aplicant.setFirstName(resultSet.getString("firstname"));
                    aplicant.setLastName(resultSet.getString("lastname"));
                    aplicant.setPhone(resultSet.getString("phone"));
                    aplicant.setEmail(resultSet.getString("email"));

                    applicants.add(aplicant);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return applicants;
    }
}
