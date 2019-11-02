import java.sql.Connection;
import java.sql.DriverManager;
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
		/*Scanner scan = new Scanner(System.in);
		System.out.println("Enter Username:-> E.g For student Severus Snape with id 111 -> SS111 ");
		String user = scan.nextLine();
		System.out.println("Enter Password:-> ");
		String pwd = scan.nextLine();
		PreparedStatement stmt = conn.prepareStatement("SELECT USERNAME,PASSWORD,ROLE,PERSON_ID FROM USER_LOGIN WHERE USERNAME=?");
		stmt.setString(1, user);
		ResultSet rs = stmt.executeQuery();
		if (!rs.next()) {
			System.out.println("Login Incorrect.\n");
			loginmenu(conn);
		}
		String data_pwd = rs.getString("PASSWORD");
        String role = rs.getString("ROLE");
        String username = rs.getString("USERNAME");
        int personid = rs.getInt("PERSON_ID");
        if (!pwd.equals(data_pwd)) 
        {
            System.out.println("Login Incorrect.");
            loginmenu(conn);
        }*/
     
	}
}