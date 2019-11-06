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
		System.out.println("1. Patient Signin");
		System.out.println("2. Staff Singin");
		System.out.println("3. Signup");
		System.out.println("4. DemoQueries");
		System.out.println("5. Exit");
        System.out.println("Enter your choice :-> ");
		int select = scan.nextInt();
	   switch(select){
	   case 1:
		   patientCheck(conn);
			break;
	   case 2: 
		   staffCheck(conn);
		   break;
	   case 3:
		   patientSingup(conn);
		   break;
	   case 4:
		   break;
	   case 5:
		   System.exit(0);
		   break;
		default:
			System.out.println("Enter a valid choice");
			loginmenu(conn);
		}
	}
	
	public static void patientCheck(Connection conn)throws Exception
	{
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter Facility id");
		String facilityid = scan.nextLine();
		System.out.println("Enter Lastname");
		String lastname = scan.nextLine();
		System.out.println("Enter DOB: In format MM/DD/YYYY");
		String dob = scan.nextLine();
		System.out.println("Enter City");
		String city = scan.nextLine();
		System.out.println("Is Patient : Indicate yes/no");
		String isPatient = scan.nextLine();
		PreparedStatement stmt = conn.prepareStatement("select register.facility_id, patient.last_name, patient.dob, patient.city as city from patient, register where (register.patient_id = patient.patient_id AND patient.last_name = ? and to_char(patient.dob, 'mm/dd/yyyy') = ?)");
		stmt.setString(1, lastname);
		stmt.setString(2, dob);
		ResultSet rs = stmt.executeQuery();
		if (!rs.next()) {
			System.out.println("Login Incorrect.\n");
			loginmenu(conn);
		}
		String data_pwd = rs.getString("city");
        
        if (!city.equals(data_pwd)) 
        {
            System.out.println("Login Incorrect.");
            System.out.println(data_pwd);
            loginmenu(conn);
        }
        else {
        	System.out.println(data_pwd);
        }
     
	}
	
	public static void staffCheck(Connection conn)throws Exception
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
     
	}
	
	public static void patientSingup(Connection conn)throws Exception
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
		stmt1.executeUpdate();
		System.out.println("Patient Signed up");
		
	}
	    
}