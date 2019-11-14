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
			case 3:
				addSymptom();
				break;
			case 4:
				selectSymptom();
				break;
			case 5:
				addAssessmentRule();
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

	public void addSymptom() {
		System.out.println("----------------------Add Symptom-------------------");
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter Symptom Name");
		String symptom = scan.nextLine();
		try {
			PreparedStatement st = conn.prepareStatement("Select Symptom_name as name from symptom where upper(Symptom_name) =?");
			st.setString(1, symptom.toUpperCase());
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				System.out.println("Entered Symptom already exists");
				 addSymptom();
			}else {
				System.out.println("Enter Bodypart affected by the Symptom");
				String bodypart = scan.nextLine();
				System.out.println("Enter scale type for the Symptom");
				String scaletype = scan.nextLine();
				System.out.println("-------------------------- MENU PAGE ----------------------------");
				System.out.println("1. RECORD");
				System.out.println("2. GO BACK");
				int choice = scan.nextInt();
				switch (choice) {
				case 1:
					if(!symptom.contentEquals("")) {
						Statement stmt3 = conn.createStatement();
						ResultSet r = stmt3.executeQuery("Select Max(sym_code) as code from symptom");
						int i = 1;
						if(r.next()) {
							i = Integer.parseInt(r.getString("code").substring(3))+1;
						}
						PreparedStatement stmt1 = conn.prepareStatement("insert into symptom values(?,?)");
						stmt1.setString(1, symptom);
						stmt1.setString(2, "sym"+i);
						stmt1.executeUpdate();
						if(!bodypart.contentEquals("")) {		
							PreparedStatement stmt4 = conn.prepareStatement("Select bodypart_name as name from bodypart where upper(bodypart_name) =?");
							stmt4.setString(1, bodypart.toUpperCase());
							ResultSet rs4 = stmt4.executeQuery();
							if (rs4.next()) {
								PreparedStatement stmt2 = conn.prepareStatement("insert into affected values(?,?)");
								stmt2.setString(1, symptom);
								stmt2.setString(2, rs4.getString("name"));
								stmt2.executeUpdate();
							}
							else {
								System.out.println("Entered bodypart does not exist");
							}
						}
						System.out.println("Symptom added");
					}
					staffHome();
					break;
				case 2:
					staffHome();
				default:
					System.out.println("Invalid option,please enter a valid choice");
					addSymptom();
					break;
				}
			}
		}
		catch(Exception ex) {
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
		System.out.println("Please select a symptom from the displayed list to add scale");
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

	public void addAssessmentRule() {
		System.out.println("----------------------Add Assessment Rule-------------------");
		try {
			Statement stmt1 = conn.createStatement();
			ResultSet rs1 = stmt1.executeQuery("Select Max(rule_id) as id from rules");
			int id = 1;
			if(rs1.next()) {
				id = rs1.getInt("id")+1;
			}
			Statement stmt2 = conn.createStatement();
			ResultSet rs2 = stmt2.executeQuery("Select Symptom_name as name from symptom ");
			int i = 1;
			HashMap<Integer,String> map = new HashMap<>();
			while (rs2.next()) {
				String name = rs2.getString("name");
				map.put(i, name);
				System.out.println(i + ":-> " + name);
				i++;
			}
			int n = i;
			System.out.println(n + ":-> "+ "Priority");
		
			Scanner sc = new Scanner(System.in);
			List<Rule> rules = new LinkedList<Rule>();
			while(true) {
				System.out.println("Please select an option from the displayed list");			
				int choice = sc.nextInt();
				sc.nextLine();
				if(choice == n) {
					break;
				}
				String symptom_name = map.get(choice);
				String bodypart_name = "";
				String scale = "";
				PreparedStatement stmt3 = conn.prepareStatement("Select bodypart_name as name from affected where upper(Symptom_name) =? ");
				stmt3.setString(1, symptom_name.toUpperCase());
				ResultSet rs3 = stmt3.executeQuery();
				if (!rs3.next()) {
					System.out.println("Enter Bodypart");
					String bodypart = sc.nextLine();
					if(!bodypart.contentEquals("")) {		
						PreparedStatement stmt4 = conn.prepareStatement("Select bodypart_name as name from bodypart where upper(bodypart_name) =?");
						stmt4.setString(1, bodypart.toUpperCase());
						ResultSet rs4 = stmt4.executeQuery();
						if (rs4.next()) {
							bodypart_name =  rs4.getString("name");
						}
						else {
							System.out.println("Entered bodypart does not exist");
						}
					}
				}else {
					bodypart_name =  rs3.getString("name");
				}
				PreparedStatement stmt5 = conn.prepareStatement("Select value as value from symptom_scale where Symptom_name=? ");
				stmt5.setString(1, symptom_name);
				ResultSet rs5 = stmt5.executeQuery();
				while(rs5.next()) {
					String value = rs5.getString("value");
					System.out.println(value);
				}
				System.out.println("Please enter severity from above list");
				scale = sc.nextLine();
				System.out.println("Please enter equality");
				String type = sc.nextLine();
				Rule rule = new Rule(symptom_name,bodypart_name, scale, type);
				rules.add(rule);
			}
				System.out.println("Please enter priority from the below menu");
				System.out.println("High");
				System.out.println("Normal");
				System.out.println("Quarantine");
				String priority = sc.nextLine();
				for(int k=0;k<rules.size();k++) {
					PreparedStatement stmt6 = conn.prepareStatement("insert into rules values(?,?,?,?,?,?)");
					stmt6.setInt(1, id);
					stmt6.setString(2, rules.get(k).symptom_name);
					stmt6.setString(3,rules.get(k).bodypart_name);
					stmt6.setString(4,rules.get(k).value);
					stmt6.setString(5, rules.get(k).equality_type);
					stmt6.setString(6,priority);
					stmt6.executeUpdate();
					System.out.println("Assessment Rule added");
				}
				staffHome();
		}
		catch(Exception ex) {
			System.out.println(ex);
		}
	}
}
	
