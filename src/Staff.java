import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import java.util.concurrent.TimeUnit;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.*;

public class Staff {

	Connection conn;
	String emp_id;
	
	public Staff(Connection conn, String emp_id) {
		// TODO Auto-generated constructor stub
		this.conn=conn;
		this.emp_id=emp_id;
		
}
	public void staffHome() {
		try {
			System.out.println("--------------------------Welcome Staff----------------------------");
			System.out.println("1. Checked-in patient list");
			System.out.println("2. Treated patient list");
			System.out.println("3. Add Symptoms");
			System.out.println("4. Add severity scale");
			System.out.println("5. Add assessment rule");
			System.out.println("6. Go back");
			System.out.println("Enter your choice :-> ");
			Scanner sc = new Scanner(System.in);
			int staff_choice = sc.nextInt();
			switch (staff_choice) {
			case 1:
				checkedInPatientList();
				break;
			default:
				System.out.println("Invalid option");
				break;
			}
		} catch(Exception ex) {
			System.out.print(ex);
		}
		
	}
	
	public void checkedInPatientList()  {
		try {
			System.out.println("----------------------CHECKED-IN PATIENT LIST--------------------");
			PreparedStatement stmt = conn.prepareStatement(
					"SELECT P.PATIENT_ID AS PID,P.FIRST_NAME AS FNAME,P.LAST_NAME AS LNAME FROM PATIENT P,patient_session S WHERE P.PATIENT_ID = S.PATIENT_ID");
			ResultSet r = stmt.executeQuery();
			while (r.next()) {
				System.out.println("Hii");
				System.out.println("Patient ID :-> " + r.getInt("PID"));
				System.out.println("First Name :-> " + r.getString("FNAME"));
				System.out.println("Last Name :-> " + r.getString("LNAME"));
			}

} catch (Exception ex) {
	System.out.println(ex);
}
	}
}
