package it2d;

import java.util.Scanner;

public class Job_Listing {
    Scanner sc = new Scanner(System.in);

    public void Job_Listings() {
        String response;

        do {
            System.out.println("|--------------------|     JOB LIST     |----------------------|");
            System.out.println("|--------------------|Choose an action: |----------------------|");
            System.out.println("|--------------------|A. ADD JOB        |----------------------|");
            System.out.println("|--------------------|B. VIEW JOB       |----------------------|");
            System.out.println("|--------------------|C. UPDATE JOB     |----------------------|");
            System.out.println("|--------------------|D. DELETE JOB     |----------------------|");
            System.out.println("|--------------------|E. EXIT           |----------------------|");

            boolean validInput = false;
            String action;
            do {
                System.out.print("|-------------------|Enter action letter (A, B, C, D): ");
                action = sc.nextLine().toUpperCase();

                if (action.equals("A") || action.equals("B") || action.equals("C") || action.equals("D") || action.equals("E")) {
                    validInput = true;
                } else {
                    System.out.println("|--------------------|Invalid input! Please enter A, B, C, D, or E.");
                }
            } while (!validInput);

            switch (action) {
                case "A":
                    AddJob();
                    break;
                case "B":
                    viewJob();
                    break;
                case "C":
                    viewJob();
                    updateJob();
                    viewJob();
                    break;
                case "D":
                    viewJob();
                    deleteJob();
                    viewJob();
                    break;
                case "E":
                    System.out.println("|--------------------|Exiting...");
                    return;
                default:
                    System.out.println("|--------------------|Invalid option. Please try again.");
            }

            System.out.print("|--------------------|Do you want to continue? (yes or no): ");
            response = sc.next();
        } while (response.equalsIgnoreCase("yes"));

        System.out.println("|--------------------|Thank you, see you!");
    }

    public void AddJob() {
        System.out.print("|-------------------|JOB Title: ");
        String title = sc.nextLine();
        System.out.print("|-------------------|JOB Department: ");
        String dep = sc.nextLine();
        System.out.print("|-------------------|JOB Location: ");
        String loc = sc.nextLine();
        System.out.print("|-------------------|JOB Application Deadline: ");
        String ad = sc.nextLine();

        String sql = "INSERT INTO JobListings (JobTitle, Department, Location, ApplicationDeadline) VALUES (?, ?, ?, ?)";
        config conf = new config();
        conf.addRecord(sql, title, dep, loc, ad);
    }

    public void viewJob() {
        config conf = new config();
        String query = "SELECT * FROM JobListings";
        String[] headers = {"JobID", "JobTitle", "Department", "Location", "ApplicationDeadline"};
        String[] columns = {"JobID", "JobTitle", "Department", "Location", "ApplicationDeadline"};
        conf.viewRecords(query, headers, columns);
    }

    public void updateJob() {
        config conf = new config();
        String JobID;
        while (true) {
            System.out.print("|--------------------|Enter JobID: ");
            JobID = sc.nextLine();

            String checkEmployeeQuery = "SELECT 1 FROM JobListings WHERE JobID = ?";
            if (conf.recordExists(checkEmployeeQuery, JobID)) {
                break;
            } else {
                System.out.println("JobID does not exist. Please try again.");
            }
        }

        System.out.print("|-------------------|EDIT JOB Title: ");
        String ntitle = sc.nextLine();
        System.out.print("|-------------------|EDIT JOB Department: ");
        String ndep = sc.nextLine();
        System.out.print("|-------------------|EDIT JOB Location: ");
        String nloc = sc.nextLine();
        System.out.print("|-------------------|EDIT JOB ApplicationDeadline: ");
        String nad = sc.nextLine();

        String qry = "UPDATE JobListings SET JobTitle = ?, Department = ?, Location = ?, ApplicationDeadline = ? WHERE JobID = ?";
        conf.updateRecord(qry, ntitle, ndep, nloc, nad, JobID);
    }

    public void deleteJob() {
        config conf = new config();
        String JobID;
        while (true) {
            System.out.print("|--------------------|Enter JobID: ");
            JobID = sc.nextLine();

            String checkEmployeeQuery = "SELECT 1 FROM JobListings WHERE JobID = ?";
            if (conf.recordExists(checkEmployeeQuery, JobID)) {
                break;
            } else {
                System.out.println("JobID does not exist. Please try again.");
            }
        }

        String qry = "DELETE FROM JobListings WHERE JobID = ?";
        conf.deleteRecord(qry, JobID);
    }
}
