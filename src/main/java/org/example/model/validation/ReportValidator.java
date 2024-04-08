package org.example.model.validation;



import org.example.model.security.Report;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ReportValidator {

  public static final int MIN_NAME_LENGTH = 2;

  private final Report report;
  private final List<String> errors;


  public ReportValidator(Report report) {
    errors = new ArrayList<>();
    this.report = report;
  }

  public boolean validate() {
    validateName(this.report.getName());
    validateDate(this.report.getDateStart(),this.report.getDateEnd());
    return this.errors.isEmpty();
  }

  private void validateName(String name) {
    if (name.length() < MIN_NAME_LENGTH) {
      errors.add("Name too short!");
    }
    if (containsSpecialCharacter(name)) {
      errors.add("Name can't contain a special character!");
    }
  }

  private void validateDate(Date dateStart, Date dateEnd) {
    if (dateStart.after(dateEnd)) {
      errors.add("Start date should be before end date!");
    }
  }

  private boolean containsSpecialCharacter(String password) {
    return password.matches(".*[^a-zA-Z0-9 ].*");
  }


  public List<String> getErrors() {
    return errors;
  }
}
