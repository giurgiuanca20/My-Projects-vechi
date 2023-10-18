package org.example.Model;

public class Job {
    private int id;
    private String jobTitle;
    private String jobDescription;
    private int employer;

    public Job() {

    }

    public void setEmployer(int employer) {
        this.employer = employer;
    }

    public int getEmployer() {
        return employer;
    }

    public int getId() {
        return id;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

}
