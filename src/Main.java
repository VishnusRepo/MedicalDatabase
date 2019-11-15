import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

class Main
{
	static final String jdbcURL = "jdbc:oracle:thin:@orca.csc.ncsu.edu:1521:orcl01";
	static final String username = "apayyav";
	static final String password = "200262861";
	public static void main(String[] args)

	{
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			final Connection conn = DriverManager.getConnection(jdbcURL, username, password);
			
			Statement st = conn.createStatement();
			loginmenu(conn);
			
			/*ResultSet rs = st.executeQuery("select table_name from all_tables where owner='APAYYAV'");
			
			System.out.println("Below are list of created tables");
			while(rs.next())
			{
				System.out.println(rs.getString("TABLE_NAME"));
			}*/
			
		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
	}

	public static void loginmenu(Connection conn) throws Exception {
		Scanner scan = new Scanner(System.in);
		System.out.println("----------------------------Medical Facility--------------------------");
		System.out.println("1. Sign In");
		System.out.println("2. Sign Up(patient)");
		System.out.println("3. Demo Queries");
		System.out.println("4. Exit");
        System.out.println("Enter your choice :-> ");
		int select = scan.nextInt();
	   switch(select){
	   case 1:
		   userCheck(conn);
			break;
	   case 2: 
		   patientSignup(conn);
		   break;
	   case 3:
		   break;
	   case 4:
		   System.exit(0);
		   break;
		default:
			System.out.println("Enter a valid choice");
			loginmenu(conn);
		}
	}
	
	public static void userCheck(Connection conn)throws Exception
	{
		System.out.println("SIGN IN PAGE");
		Statement st = conn.createStatement();
		ResultSet r = st.executeQuery("SELECT FACILITY_ID AS FID FROM MEDICALFACILITY");
		System.out.println("List of Facility ID's");
		while (r.next()) {
			System.out.println(r.getInt("FID"));
		}
		Scanner scan1 = new Scanner(System.in);
		System.out.println("Enter Facility id");
		String fid = scan1.nextLine();
		System.out.println("Enter Lastname");
		String lastname = scan1.nextLine();
		System.out.println("Enter DOB: In format MM/DD/YYYY");
		String dob = scan1.nextLine();
		System.out.println("Enter City");
		String city = scan1.nextLine();
		System.out.println("Is Patient : Indicate yes/no");
		String isPatient = scan1.nextLine();
		int facilityid = Integer.parseInt(fid);
		System.out.println("1. Sign In");
		System.out.println("2. Go Back");
		int choice = scan1.nextInt();
		   switch(choice){
		   case 1:
				if(isPatient.equals("yes")) {
					PreparedStatement stmt = conn.prepareStatement("select PATIENT_ID FROM PATIENT where upper(patient.last_name) = ? and to_char(patient.dob, 'mm/dd/yyyy') = ? and upper(patient.city)=?");
					stmt.setString(1, lastname.toUpperCase());
					stmt.setString(2, dob);
					stmt.setString(3, city.toUpperCase());
					ResultSet rs = stmt.executeQuery();
					if (rs.next()) {
						System.out.println("Patient Login Successful");
						int patient_id= rs.getInt("PATIENT_ID");
						PreparedStatement stmt2 = conn.prepareStatement("select * from register where patient_id = ? ");
						stmt2.setInt(1,patient_id);
						ResultSet rs1 = stmt2.executeQuery();
						if(rs1!=null) {
							Patient P = new Patient(conn,patient_id);
							P.patientHome();
						}else {
							PreparedStatement stmt1 = conn.prepareStatement("insert into register values(?, ?)");
	                        stmt1.setInt(1, patient_id);
	                        stmt1.setInt(2, facilityid);
	                        stmt1.executeQuery();
							Patient P = new Patient(conn,patient_id);
							P.patientHome();
					   }
					}
					else {
						System.out.println("Login Incorrect.\n");
						loginmenu(conn);
					}
			     }
					else {
						PreparedStatement stmt = conn.prepareStatement("select EMPLOYEE_ID from MEDICAL_STAFF where upper(MEDICAL_STAFF.last_name) = ? and to_char(MEDICAL_STAFF.dob, 'mm/dd/yyyy') = ? and upper(MEDICAL_STAFF.CITY) = ?");
						stmt.setString(1, lastname.toUpperCase());
						stmt.setString(2, dob);
						stmt.setString(3, city.toUpperCase());
						ResultSet rs = stmt.executeQuery();
						PreparedStatement stmt2 = conn.prepareStatement("select EMPLOYEE_ID from NON_MEDICAL_STAFF where upper(NON_MEDICAL_STAFF.last_name) = ? and to_char(NON_MEDICAL_STAFF.dob, 'mm/dd/yyyy') = ? and upper(NON_MEDICAL_STAFF.CITY) = ?");
						stmt2.setString(1, lastname.toUpperCase());
						stmt2.setString(2, dob);
						stmt2.setString(3, city.toUpperCase());
						ResultSet rs1 = stmt2.executeQuery();
						
						if (rs.next()) {
							String employeecheck_id = rs.getString("EMPLOYEE_ID");
							System.out.println("Login Successful");
							Staff s = new Staff(conn,employeecheck_id,facilityid);
							s.staffHome();
							}
						if (rs1.next()) {
							String employeecheck_id = rs1.getString("EMPLOYEE_ID");
							System.out.println("Login Successful");
							Staff s = new Staff(conn,employeecheck_id,facilityid);
							s.staffHome();
							}
						else {
							System.out.println("Sign In Incorrect, Please Enter again\n");
							userCheck(conn);
						}
					}
				break;
		   case 2: 
			   loginmenu(conn);
			   break;
		   default:
				System.out.println("Enter a valid choice");
				loginmenu(conn);
		   }
	}
	
	
	/*public static void staffcheck(Connection conn)throws Exception
	{
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter Employee id");
		String employeeId = scan.nextLine();
		System.out.println("Enter Name");
		String name = scan.nextLine();
		PreparedStatement stmt1 = conn.prepareStatement("select staff_name as name, employee_id as id from medical_staff where employee_id = ?");
		stmt1.setString(1, employeeId);
		ResultSet rs1 = stmt1.executeQuery();
		PreparedStatement stmt2 = conn.prepareStatement("select staff_name as name, employee_id as id from non_medical_staff where employee_id = ?");
		stmt2.setString(1, employeeId);
		ResultSet rs2 = stmt2.executeQuery();
		String data_pwd = "";
		if (rs1.next()){
			data_pwd= rs1.getString("name");
		}
		else if(rs2.next())
		{
			data_pwd= rs2.getString("name");
		}
		else {
			System.out.println("Login Incorrect.\n");
			loginmenu(conn);
		}
        
        if (!name.equals(data_pwd)) 
        {
            System.out.println("Login Incorrect.");
            loginmenu(conn);
        }
        else {
        	System.out.println(name + "Logged in");
        	Staff S= new Staff(conn);
        	S.staffHome();
        }
     
	}*/
	
	public static void patientSignup(Connection conn)throws Exception
	{
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter First Name");
		String firstname = scan.nextLine();
		System.out.println("Enter Lastname");
		String lastname = scan.nextLine();
		System.out.println("Enter DOB: In format MM/DD/YYYY");
		String dob = scan.nextLine();
		System.out.println("Enter Address: Apartment No.");
		int aptno = Integer.parseInt(scan.nextLine());
		System.out.println("Enter Street");		
		String street = scan.nextLine();
		System.out.println("Enter City");		
		String city = scan.nextLine();
		System.out.println("Enter State");		
		String state = scan.nextLine();
		System.out.println("Enter Country");		
		String country = scan.nextLine();
	    System.out.println("Enter Phone Number with out any country codes");	
	    String phnumber = scan.nextLine();
		//String isPatientUp = scan.nextLine();
		System.out.println("1. Sign Up");
		System.out.println("2. Go Back");
		int choice = scan.nextInt();
		switch(choice) {
			case 1:
				PreparedStatement stmt1 = conn.prepareStatement("insert into patient values(patient_seq.nextval, ?, ?, to_date(?, 'mm/dd/yyyy'),?, ?, ?,?, ?, ?)");
				stmt1.setString(1, firstname);
				stmt1.setString(2, lastname);
				stmt1.setString(3,dob);
				stmt1.setString(4,phnumber);
				stmt1.setInt(5, aptno);
				stmt1.setString(6, street);
				stmt1.setString(7, city);
				stmt1.setString(8,state);
				stmt1.setString(9,country);
				System.out.println("Patient Signed up, Now please sign in");
				stmt1.executeUpdate();
				conn.commit();
				userCheck(conn);
				break;
			case 2:
				loginmenu(conn);
				break;
			default:
				System.out.println("Please Enter a valid Option");
				patientSignup(conn);
				break;
		}
		//stmt1.executeUpdate();
		
		
	}    
}