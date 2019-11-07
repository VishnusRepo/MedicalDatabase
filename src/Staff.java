import java.util.Scanner;
//import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
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
			case 4:
				selectSymptom();
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
			System.out.println("-------------------------- MENU PAGE ----------------------------");
			System.out.println("1. ENTER VITALS");
			System.out.println("2. TREAT PATIENT");
			System.out.println("3. GO BACK");
	}
		catch (Exception ex) {
			System.out.println(ex);
		}
}

	public void selectSymptom() {
		try {
			System.out.println("----------------------Symptom List-------------------");
			Statement st = conn.createStatement();
			ResultSet r = st.executeQuery("Select Symptom_name as name from symptom ");
			int i = 1;
			while (r.next()) {
				System.out.println(i + ":-> " + r.getString("name"));
				i++;
			}
			Scanner sc = new Scanner(System.in);
			System.out.println("-------------------------- MENU PAGE ----------------------------");
			System.out.println("1. ENTER SEVERITY");
			System.out.println("2. GO BACK");
			int choice = sc.nextInt();
			System.out.println(choice);
			switch (choice) {
			case 1:
				addSeverityScale();
				break;
			case 2:
				staffHome();
			default:
				System.out.println("Invalid option,please enter a valid choice");
				selectSymptom();
				break;
			}
		
			
		}
		catch(Exception ex) {
			System.out.println(ex);
		}
	}
	
	public void addSeverityScale() {
		try {
		System.out.println("Please select a symptom to add scale");
		Scanner sc = new Scanner(System.in);
		String symptom = sc.nextLine();
		PreparedStatement st = conn.prepareStatement("Select * from symptom where Symptom_name =?" );
		st.setString(1, symptom);
		ResultSet rs = st.executeQuery();
		if (rs.next()) {
				addScale(symptom);
		}
		}
		catch(Exception ex) {
			System.out.println(ex);
		}
		
	}
	
	public void addScale(String symptom) throws SQLException {
		System.out.println("----------------------Symptom Levels-------------------");
		
		PreparedStatement st = conn.prepareStatement("Select value as value from symptom_scale where Symptom_name =?");
		st.setString(1, symptom);
		ResultSet rs = st.executeQuery();
		int i = 1;
		while (rs.next()) {
			System.out.println(i + ":-> " + rs.getString("value"));
			i++;
		}
		System.out.println("-------------------------- MENU PAGE ----------------------------");
		System.out.println("1. There is another level for the scale");
		System.out.println("2. There are no more levels, Go Back");
		Scanner sc = new Scanner(System.in);
		int staff_choice = sc.nextInt();
		switch (staff_choice) {
		case 1:
			addSeverityLevel(symptom);	
		case 2:
			selectSymptom();			
		default:
			System.out.println("Invalid option,please enter a valid choice");
			addScale(symptom);
			break;
		}
	 }
	
	
	public void addSeverityLevel(String symptom){

		try {
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter Scale value");
		String scale = scan.nextLine();
		PreparedStatement stmt1 = conn.prepareStatement("insert into symptom_scale values(?, ?)");
		stmt1.setString(1, symptom);
		stmt1.setString(2, scale);
		stmt1.executeUpdate();
		System.out.println("Severity Added");
		addScale(symptom);
		}
		catch(Exception ex) {
			System.out.println(ex);
		}
	}
}
	
