import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

class Symptom
{
	public String symptom_name, bodypart, duration, reoccuring, severity, cause;
}

class PatientRule
{
	public int rule_id;
	public String symptom_name, bodypart, severity, equality_type, assessment;
}

public class Patient
{
	final Connection conn;
	final int patient_id;
	final Scanner scanner;
	
	
	public Patient(Connection conn, int patient_id)
	{
		this.conn = conn;
		this.patient_id = patient_id;
		scanner = new Scanner(System.in);
	}
	
	public void printMainMenu()
	{
		System.out.println("Choose an option\n1. Check-in\n2. Check-out acknowledgement\n3. back");
	}
	
	public void patientHome() throws SQLException
	{
		boolean looper = true;
		while(looper)
		{
			printMainMenu();
			final String input = scanner.nextLine();
			//Check-in
			if (input.equals("1"))
			{
				//create new checkin
				final int appointment_id = createNewCheckin();
				//enter symptoms
				symptomMenu(appointment_id);
			}
			//check-out acknowledgement
			else if (input.equals("2"))
			{
				
			}
			//back
			else if (input.equals("3"))
			{
				looper = false;
			}
			else
			{
				System.out.println("Invalid input. Please enter valid input");
			}
		}
	}
	
	private int createNewCheckin() throws SQLException
	{
		System.out.println("j");
		//create new checkin
		final String sessionCreateQuery = "insert into patient_session"
				+ "(appointment_id, patient_id, check_in_time)"
				+ " values(session_seq.nextval, " + patient_id + ", sysdate)";
		Statement st = conn.createStatement();
		st.executeUpdate(sessionCreateQuery);
		System.out.println("a");
		//obtain appointment_id of new checkin
		final String appointmentReadQuery = "select session_seq.currval from dual";
		ResultSet rs = st.executeQuery(appointmentReadQuery);
		System.out.println("f");
		rs.next();
		final int appointment_id = rs.getInt(1);
		System.out.println("Checkin created with appointment id: " + appointment_id);
		return appointment_id;
	}
	
	private void printSymptoms(ArrayList<String> allSymptoms)
	{
		System.out.println("Choose a symptom");
		for(int i=0; i<allSymptoms.size(); i++)
		{
			System.out.println((i+1) + ". " + allSymptoms.get(i));
		}
		System.out.println((allSymptoms.size()+1) + ". Other");
		System.out.println((allSymptoms.size()+2) + ". Done");
	}
	
	//to scan one symptom from console
	private Symptom scanSymptom(final String symptom_name) throws SQLException
	{
		Symptom currentSymptom = new Symptom();
		currentSymptom.symptom_name = symptom_name;
		//identify if symptom is associated with some body part
		final String associatedBodyPartQuery = "select bodypart_name from affected where symptom_name='" + symptom_name + "'";
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(associatedBodyPartQuery);
		
		//this symptom is associated with some bodypart
		if (rs.next())
		{
			final String associatedBodyPart = rs.getString("BODYPART_NAME");
			System.out.println("Associated body part: " + associatedBodyPart);
			currentSymptom.bodypart = associatedBodyPart;
		}
		else
		{
			System.out.println("Enter the bodypart where " + symptom_name + " is occuring");
			currentSymptom.bodypart = scanner.nextLine();
		}
		
		//scan duration, reoccuring?, severity, cause
		System.out.println("Since how many days is " + symptom_name + " is occuring?");
		currentSymptom.duration = scanner.nextLine();
		
		System.out.println("Is the problem reoccuring?(Y/N)");
		currentSymptom.reoccuring = scanner.nextLine();
		
		//identify the scale of severity for this symptom
		ResultSet rs2 = st.executeQuery("select value from symptom_scale where symptom_name='" + symptom_name + "'");
		String scaleValues = "";
		while(rs2.next())
		{
			scaleValues += rs2.getString("VALUE") + " ";
		}
		//if scale already existing, ask user to enter in given range. if not, enter in 1-10
		if(!scaleValues.equals(""))
		{
			System.out.println("Enter pain severity in the following range");
			System.out.println(scaleValues);
			currentSymptom.severity = scanner.nextLine();
		}
		
		System.out.println("Enter the incident which caused this");
		currentSymptom.cause = scanner.nextLine();
		
		return currentSymptom;
	}
	
	//main menu for symptom entry
	public void symptomMenu(final int appointment_id) throws SQLException
	{
		//Query list of symptoms from database
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select symptom_name from symptom");
		ArrayList<String> allSymptoms = new ArrayList<>();
		
		while(rs.next())
		{
			final String currSymptom = rs.getString("SYMPTOM_NAME");
			allSymptoms.add(currSymptom);
		}
		
		boolean endOfSymptomDataEntry = false;
		ArrayList<Symptom> patientSymptoms = new ArrayList<>();
		
		while(!endOfSymptomDataEntry)
		{
			printSymptoms(allSymptoms);
			final int input = Integer.parseInt(scanner.nextLine());
			if (input <= 0 || input > allSymptoms.size() + 2)//invalid
			{
				System.out.println("Input out of range. Please enter valid input");
			}
			else if (input == allSymptoms.size() + 2)//done
			{
				endOfSymptomDataEntry = true;
			}
			else if (input == allSymptoms.size() + 1)//other
			{
				patientSymptoms.add(scanSymptom(allSymptoms.get(input - 1)));
			}
			else
			{
				patientSymptoms.add(scanSymptom(allSymptoms.get(input - 1)));
			}
			
		}
		
		//add symptom entries in db
		for(int i=0;i<patientSymptoms.size(); i++)
		{
			Symptom currentSymptom = patientSymptoms.get(i);
			final String insertSymptomQuery = "insert into has_symptom"
					+ "(appointment_id, symptom_name, bodypart_name, duration, is_chronic, incident, severity)"
					+ " values (" + appointment_id + ", '" + currentSymptom.symptom_name + "', '" 
					+ currentSymptom.bodypart + "', '" + currentSymptom.duration + "', '" 
					+ currentSymptom.reoccuring + "', '" + currentSymptom.cause +"', '" + currentSymptom.severity + "')";
			st.executeUpdate(insertSymptomQuery);
		}
		
		//run rule engine to assess the condition
		runRuleEngine(appointment_id, patientSymptoms);
	}
	
	private void runRuleEngine(final int appointment_id, ArrayList<Symptom> patientSymptoms) throws SQLException
	{
		List<String> ass_codes = new LinkedList<String>();
		ass_codes.add("Quarantine");
		ass_codes.add("High");
		ass_codes.add("Normal");
		String outcome_ass_code = null;
		OUTER_LOOP: for(String ass_code: ass_codes)
		{
			//obtain list of all quarantine rule ids
			List<Integer> ruleSetIds = new LinkedList<>();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select distinct(rule_id) as RULE_ID from rules where ASS_CODE = '" + ass_code + "'");
			System.out.println("j");
			while(rs.next())
			{
				ruleSetIds.add(rs.getInt("RULE_ID"));
			}
			System.out.println("a");
			//process quarantine outcome rulesets to identify if there's any match
			for(int ruleSetId: ruleSetIds)
			{
				//fetch all the rules from ruleset where rule_id = quarantineRuleId
				List<PatientRule> currRuleSet = new LinkedList<>();
				rs = st.executeQuery("select symptom_name, bodypart_name, value, equality_type, ass_code from rules where rule_id="+ruleSetId);
				while(rs.next())
				{
					PatientRule currRule = new PatientRule();
					currRule.symptom_name = rs.getString("symptom_name");
					currRule.bodypart = rs.getString("bodypart_name");
					currRule.severity = rs.getString("value");
					currRule.equality_type = rs.getString("equality_type");
					currRule.assessment = rs.getString("ass_code");
					currRuleSet.add(currRule);
				}
				
				//now, identify if this rule set is THE pick.
				//how? every rule must be satisfied by some symptom
				boolean ruleSetSatisfied = true;
				for(PatientRule rule: currRuleSet)
				{
					boolean ruleSatisfied = false;
					for(Symptom symptom: patientSymptoms)
					{
						if ((rule.bodypart == null || 
								(rule.bodypart != null && symptom.bodypart.equalsIgnoreCase(rule.bodypart) ) )
								&& symptom.symptom_name.equalsIgnoreCase(rule.symptom_name)
								&& satisfiesEquality(rule.severity, rule.equality_type, symptom.severity))
						{
							ruleSatisfied = true;
							break;
						}
					}
					if (!ruleSatisfied)
					{
						//this will break loop over rules of current ruleset
						//so that we can continue with outer main loop -> next ruleset
						ruleSetSatisfied = false;
						break;
					}
				}
				if (ruleSetSatisfied)
				{
					//update patient_session with matched ass_code
					System.out.println("assessment: " + ass_code);
					final String updateAssessment = "update patient_session set ass_code='" + ass_code + "' where appointment_id=" + appointment_id;
					st.executeUpdate(updateAssessment);
					outcome_ass_code = ass_code;
					break OUTER_LOOP;
				}
			}
		}
		if (outcome_ass_code == null)
		{
			System.out.println("Not matching with any rule sets. Hence assigning assessment = Normal");
			final String updateAssessment = "update patient_session set ass_code='Normal' where appointment_id=" + appointment_id;
			Statement st = conn.createStatement();
			st.executeUpdate(updateAssessment);
		}
	}
	
	public boolean satisfiesEquality(String ruleVal, String equality, String patientVal)
	{
		if (ruleVal == null)
		{
			return true;
		}
		final int ruleValue = Integer.parseInt(ruleVal);
		final int patientValue = Integer.parseInt(patientVal);
		if (ruleVal.equals("="))
		{
			return ruleVal.equalsIgnoreCase(patientVal);
		}
		else if (ruleVal.equals("<"))
		{
			return patientValue < ruleValue;
		}
		else if (ruleVal.equals("<="))
		{
			return patientValue <= ruleValue;
		}
		else if (ruleVal.equals(">"))
		{
			return patientValue > ruleValue;
		}
		else if (ruleVal.equals(">="))
		{
			return patientValue >= ruleValue;
		}
		return true;
	}
}