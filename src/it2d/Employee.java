package it2d;

import java.util.Scanner;

public class Employee {
    

    public void Employees() { 
        String response;

        do {
            Scanner sc=new Scanner(System.in);
            System.out.println("|--------------------|Choose an action: |----------------------|");
            System.out.println("|--------------------|1. ADD EMPLOYEE   |----------------------|");
            System.out.println("|--------------------|2. VIEW EMPLOYEES |----------------------|");
            System.out.println("|--------------------|3. UPDATE EMPLOYEE|----------------------|");
            System.out.println("|--------------------|4. DELETE EMPLOYEE|----------------------|");
            System.out.println("|--------------------|5. EXIT           |----------------------|");
            
            System.out.print  ("|-------------------|Enter action number:");
            System.out.println("|---------------------|");
            int action = sc.nextInt();
            
            switch (action) {
                case 1:
                    addEmployee();
                    break;
                case 2:
                    viewEmployees();
                    break;  
                case 3:
                    viewEmployees();
                    updateEmployee();
                    viewEmployees();
                    break;
                case 4:
                    viewEmployees();
                    deleteEmployee();
                    viewEmployees();
                    break;
                case 5:
                    System.out.println("|-------------------|Exiting...|-------------------|");
                    return;
                default:
                    System.out.println("|-------------------|Invalid option. Please try again.|-------------------|");
                    break;
            }

            System.out.print("|-------------------|Do you want to continue? (yes or no): ");
            response = sc.next();

        } while (response.equalsIgnoreCase("yes"));
        
        System.out.println("Thank you, see you!");
       
    }

    public void addEmployee() {
        Scanner sc = new Scanner(System.in);
       
        System.out.print("|-------------------|Employee First Name: ");
        String fname = sc.next();
        System.out.print("|-------------------|Employee Last Name: ");
        String lname = sc.next();
        System.out.print("|-------------------|Employee Email: ");
        String email = sc.next();
        System.out.print("|-------------------|Employee Status: ");
        String status = sc.next();
        System.out.print("|-------------------|Employee Phonenumber: ");
        String pnum = sc.next();
        System.out.print("|-------------------|Employee Position: ");
        String ep =sc.next();
        System.out.print("|-------------------|Employee Address:");
        String ea =sc.next();
        String sql = "INSERT INTO tbl_employee (e_pnumber,e_position,e_address,e_fname, e_Lname, e_email, e_status) VALUES (? ,? ,? ,?, ?, ?, ?)";
        config conf = new config();
        conf.addRecord(sql, fname, lname, email, status, pnum, ep, ea);
    }

    public void viewEmployees() {
        config conf = new config();
        String query = "SELECT * FROM tbl_employee";    
        String[] headers = {"Employee ID", "Employee First Name", "Employee Last Name", "Employee Email", "Employee Status"};
        String[] columns = {"e_id", "e_fname", "e_Lname", "e_email", "e_status"};

        conf.viewRecords(query, headers, columns);
    }

    public void updateEmployee() {
        Scanner sc = new Scanner(System.in);
        System.out.print("|-------------------|Enter the ID to update: ");
        int id = sc.nextInt();

        System.out.print("|-------------------|EDIT First Name: ");
        String nfname = sc.next();
        System.out.print("|-------------------|EDIT Last Name: ");
        String nlname = sc.next();
        System.out.print("|-------------------|EDIT Email: ");
        String nemail = sc.next();
        System.out.print("|-------------------|EDIT Status: ");
        String nstatus = sc.next();

        String qry = "UPDATE tbl_employee SET e_fname = ?, e_Lname = ?, e_email = ?, e_status = ? WHERE e_id = ?";
        config conf = new config();
        conf.updateRecord(qry, nfname, nlname, nemail, nstatus, id); 
    }

    public void deleteEmployee() {
        Scanner sc = new Scanner(System.in);
        System.out.print("|-------------------|Enter the ID to delete: ");
        int id = sc.nextInt();
        
        String qry = "DELETE FROM tbl_employee WHERE e_id = ?";
        config conf = new config();
        conf.deleteRecord(qry, id);
    }
}
