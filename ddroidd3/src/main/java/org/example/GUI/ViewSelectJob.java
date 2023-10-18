package org.example.GUI;

import org.example.Model.Create;
import org.example.Model.Job;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ViewSelectJob extends JFrame {
    private final JFrame frame;
    private JLabel lTitle;
    private JButton bApply;
    private Create create = new Create();
    private JTable jobTable = new JTable();
    private String selectedJobName = "";

    public String getSelectedJobName() {
        return selectedJobName;
    }
    // Method to display a list of jobs
    public void displayJobs(List<Job> jobs) {
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
        panel.setBorder(BorderFactory.createTitledBorder("Jobs List"));

        panel.add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }
    public ViewSelectJob(String name) {
        frame = new JFrame(name);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.setSize(1600, 900);
        frame.setLocationRelativeTo(null);
    }

    public void prepareGui(Controller controller) {

        frame.getContentPane().removeAll();
        frame.getContentPane().setLayout(new BorderLayout());
        frame.setSize(1600, 900);
        frame.setLocationRelativeTo(null);

        lTitle = create.makeLabel("Selected Job:");

        bApply = create.makeButon("applyJob", " Apply ");
        bApply.addActionListener(controller);

        frame.setLayout(new FlowLayout());

        frame.add(lTitle);
        frame.add(bApply);

        frame.setVisible(true);

    }
}
