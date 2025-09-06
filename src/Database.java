import java.sql.*;
import java.util.Scanner;

public class Database {

    public static void main(String[] args) throws ClassNotFoundException {

        try {

            // Load driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Create connection
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentsdata", "root", "");
            Scanner sn = new Scanner(System.in);
            System.out.println("Connected to database successfully!");

            // Create table
            Statement stmt = con.createStatement();

            String tableData = "CREATE TABLE IF NOT EXISTS Students (" +
                    "ID INT PRIMARY KEY AUTO_INCREMENT," +
                    "Name VARCHAR(50)," +
                    "Age INT)";
            stmt.executeUpdate(tableData);
            System.out.println("Table Created Successfully");

            // Add students
            System.out.println("How many students do you want to add?");

            int n = sn.nextInt();
            sn.nextLine();
            for (int i = 0; i < n; i++) {
                System.out.print("Enter name: ");
                String name = sn.nextLine();
                System.out.print("Enter age: ");
                int age = sn.nextInt();
                sn.nextLine();

                String newStudent = "INSERT INTO Students(name, age) VALUES(?, ?)";
                PreparedStatement ps = con.prepareStatement(newStudent);
                ps.setString(1, name);
                ps.setInt(2, age);

                int rows = ps.executeUpdate();
                System.out.println("Student" + rows + "added!");
            }

            // Read students data
            System.out.println("All Student Records: ");

            ResultSet readData = stmt.executeQuery("SELECT * FROM Students");
            while (readData.next()) {
                int sId = readData.getInt("ID");
                String sName = readData.getString("Name");
                int sAge = readData.getInt("Age");

                System.out.println(sId + "|" + sName + "|" + sAge);
            }

            // Update student's data
            System.out.println("Update a record ");
            System.out.println("Enter ID of student to update: ");
            int id = sn.nextInt();
            sn.nextLine();
            System.out.println("Enter new Name: ");
            String newName = sn.nextLine();
            System.out.println("Enter new Age: ");
            int newAge = sn.nextInt();

            String updateData = "UPDATE Students SET Name=?, Age=? WHERE ID=?";
            PreparedStatement updateStmt = con.prepareStatement(updateData);
            updateStmt.setString(1, newName);
            updateStmt.setInt(2, newAge);
            updateStmt.setInt(3, id);
            int updated = updateStmt.executeUpdate();
            System.out.println("Number of " + updated + " record(s) updated");


            // Delete student
            System.out.println("Enter ID of student that want to delete: ");
            int deleteId = sn.nextInt();
            String deleteData = "DELETE FROM Students WHERE ID=?";
            PreparedStatement deleteStmt = con.prepareStatement(deleteData);
            deleteStmt.setInt(1, deleteId);
            int deleted = deleteStmt.executeUpdate();
            System.out.println("Number of " + deleted + " record(s) deleted");

            // Retrieve existing data
            System.out.println("Existing Student Records: ");

            ResultSet readNewData = stmt.executeQuery("SELECT * FROM Students");
            while (readNewData.next()) {
                int exId = readNewData.getInt("ID");
                String exName = readNewData.getString("Name");
                int exAge = readNewData.getInt("Age");

                System.out.println(exId + "|" + exName + "|" + exAge);
            }



            con.close();
            sn.close();


        }catch(SQLException e){
            System.out.println("Error: " + e.getMessage());
            System.out.println("Code: " + e.getErrorCode());
            System.out.println("State: " + e.getSQLState());
            e.printStackTrace();
        }
    }
}
