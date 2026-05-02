import java.sql.*;
import java.util.*;
public class project1 {
    public static void main(String[] args) throws Exception{
        Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/SMS","root","Boadsu@2006");
        System.out.println("Connected to database");
        Scanner sc = new Scanner(System.in);
        while(true) {
            System.out.println("Choose a task u want to perform:");
            System.out.println("1.Add Students");
            System.out.println("2.View All Students ");
            System.out.println("3.Search Student");
            System.out.println("4.Update Student ");
            System.out.println("5.Delete Student");
            System.out.println("6.Exit");
            int i = sc.nextInt();

            if(i==1){
                sc.nextLine();
                System.out.print("Enter name of student:");
                String name= sc.nextLine();
                System.out.print("Enter student id:");
                int id = sc.nextInt();
                System.out.print("Enter marks obtained by student:");
                int marks = sc.nextInt();
                String query = "Insert into student(id,name,marks)values(?,?,?)";
                PreparedStatement ps = con.prepareStatement(query);
                try {
                    ps.setInt(1,id);
                    ps.setString(2,name);
                    ps.setInt(3,marks);
                    int rows=ps.executeUpdate();
                    System.out.println("DATA INSERTED");
                    System.out.println("Rows affected:"+rows);
                } catch (SQLException e) {
                    System.out.println("id already in use");
                }
                ps.close();
                System.out.println("\n-------------------------------------------\n");
            }
            else if (i==2) {
                String query= "select * from student";
                PreparedStatement ps = con.prepareStatement(query);
                ResultSet rs= ps.executeQuery();
                while(rs.next()){
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    int marks = rs.getInt("marks");
                    System.out.println(id+" "+name+" "+marks);
                }
                rs.close();
                System.out.println("\n-------------------------------------------\n");
            }
            else if (i==3) {
                System.out.print("Enter id of student to search:");
                int id = sc.nextInt();

                String query= "select name,marks from student where id = ?";
                PreparedStatement ps= con.prepareStatement(query);
                ps.setInt(1,id);
                ResultSet rs= ps.executeQuery();

                    if(!rs.next()){
                        System.out.println("Not found");
                    }
                    else {
                        do {
                            String name = rs.getString("name");
                            int marks = rs.getInt("marks");
                            System.out.println(name + " " + marks);
                        }
                        while(rs.next());
                    }

                System.out.println("\n-------------------------------------------\n");
                rs.close();
                ps.close();
            }
            else if (i==4) {
                String query ="update student set name=? , marks=? where id=?";
                PreparedStatement ps= con.prepareStatement(query);

                System.out.print("Enter id of student to update:");
                int id = sc.nextInt();
                sc.nextLine();
                System.out.print("New Name:");
                String name = sc.nextLine();
                System.out.print("New Marks:");
                int marks = sc.nextInt();

                ps.setString(1,name);
                ps.setInt(2,marks);
                ps.setInt(3,id);
                int rows=ps.executeUpdate();
                if(rows>0) {
                    System.out.println("Rows affected:" + rows);
                    System.out.println("UPDATED STUDENT RECORD OF ID:" + id);
                }
                else{
                    System.out.println("ID NOT FOUND");
                }
                System.out.println("\n-------------------------------------------\n");
                ps.close();
            }
            else if (i==5) {
                System.out.print("Enter id of student to delete:");
                int id = sc.nextInt();
                String query = "Delete from student where id = ?";
                PreparedStatement ps= con.prepareStatement(query);
                ps.setInt(1,id);
                int rows=ps.executeUpdate();
                System.out.println("Rows affected:"+rows);
                ps.close();
                if(rows>0) {
                    System.out.println("Deleted id:" + id);
                }
                else{
                    System.out.println("ID not found");
                }
                System.out.println("\n-------------------------------------------\n");

            }
            else if (i==6) {
                break;
            }
            else{
                System.out.println("Invalid input");
            }



        }
        con.close();
    }
}
