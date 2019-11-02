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
		System.out.println("1. Signin");
		System.out.println("2. Singup");
		System.out.println("3. DemoQueries");
		System.out.println("4. Exit");
        System.out.println("Enter your choice :-> ");
		int select = scan.nextInt();
		if(select == 1)
		{
			userCheck(conn);
		}
		else
		{
			System.exit(0);
		}
	}
	
	public static void userCheck(Connection conn)throws Exception
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
		PreparedStatement stmt = conn.prepareStatement("select register.facility_id, patient.last_name, patient.dob, patient.city As city from patient, register where (register.patient_id = patient.patient_id AND patient.last_name = ? and to_char(patient.dob, 'mm/dd/yyyy') = ?)");
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
}