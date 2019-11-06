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
	
	public Staff(Connection conn) {
		// TODO Auto-generated constructor stub
		this.conn=conn;
	
}
	public void staffHome() {
		try {
			System.out.println("--------------------------Welcome Staff----------------------------");
			System.out.println("1. Checked-in patient list");
			System.out.println("2. Treated patient list");
			System.out.println("3. Add Symptoms");
			System.out.println("4. Add severity scale");
			System.out.println("5. Add assessment rule");
			System.out.println("6. Logout");
			System.out.println("Enter your choice :-> ");
			Scanner sc = new Scanner(System.in);
			int staff_choice = sc.nextInt();
			switch (staff_choice) {
			case 1:
				checkedInPatientList();
				break;
			case 6:
				System.out.println("Logging out..");
				TimeUnit.SECONDS.sleep(3);
				System.exit(0);
				break;
			default:
				System.out.println("Invalid option,please enter a valid choice");
				staffHome();
				break;
			}
		} catch(Exception ex) {
			System.out.print(ex);
		}
		
	}
	
	public void checkedInPatientList()  {
		try {
			System.out.println("----------------------CHECKED-IN PATIENT LIST--------------------");
			Statement st = conn.createStatement();
			//PreparedStatement stmt = conn.prepareStatement(
				//	"SELECT P.PATIENT_ID AS PID,P.FIRST_NAME AS FNAME,P.LAST_NAME AS LNAME FROM PATIENT P,patient_session S WHERE P.PATIENT_ID = S.PATIENT_ID");
			ResultSet r = st.executeQuery("SELECT PATIENT.PATIENT_ID AS PID, PATIENT.FIRST_NAME AS FNAME, PATIENT.LAST_NAME AS LNAME FROM PATIENT,PATIENT_SESSION WHERE PATIENT.PATIENT_ID= PATIENT_SESSION.PATIENT_ID");
			//ResultSet r = st.executeQuery("SELECT P.PATIENT_ID AS PID,P.FIRST_NAME AS FNAME,P.LAST_NAME AS LNAME FROM PATIENT P,patient_session S WHERE P.PATIENT_ID = S.PATIENT_ID");
			while (r.next()) {
				System.out.println("Patient ID :-> " + r.getInt("PID"));
				System.out.println("First Name :-> " + r.getString("FNAME"));
				System.out.println("Last Name :-> " + r.getString("LNAME"));
			}

} catch (Exception ex) {
	System.out.println(ex);
}
		try {
			System.out.println("--------------------------MENU PAGE ----------------------------");
			System.out.println("1. ENTER VITALS");
			System.out.println("2. TREAT PATIENT");
			System.out.println("3. GO BACK");
	}
		catch (Exception ex) {
			System.out.println(ex);
		}
}
}
	
