import java.sql.*;
import java.io.Console;
/**
 * This class implements all the queries of exercises 3.11 from the textbook
 * using the university database
 * @author Gajjan Jasani
 * @version 4/8/2017
 *
 */
class PostgresqlJDBCTest311 { 

	// Main method - Entry point for the program
    public static void main( String args[] ) { 
	
    	/** Console for user input */
		Console cnsl = null;
		/** local host server ip holder */
		String serverIp = "localhost";
		/** database name holder */
		String databasename = "";
		/** Username holder */
		String username = "";
		/** password holder */
		String password = "";
		
		// Installing drivers for postgresql
        try { 
            Class.forName("org.postgresql.Driver").newInstance(); 
        } catch (Exception e) { 
            System.out.println("Exception: " + e.toString()); 
            System.exit(0); 
        }
        
		try {
			// creates a console object
			cnsl = System.console();
			// Prompting for database name, username and password
			if (cnsl != null) {
				databasename = 
						cnsl.readLine("Please enter the database name: ");
				username = 
						cnsl.readLine("Please enter your username: ");
				password = new String(
							cnsl.readPassword("Please enter your password: "));
         } 
            
		} catch(Exception e){
			System.out.println(e.getMessage());
		}
		// list of all the tables
		
        //String tableClassroom = "classroom";
        //String tableDepartment = "department";
        String tableCourse = "course";
        String tableInstructor = "instructor";
        //String tableSection = "section";
        //String tableTeaches = "teaches";
        String tableStudent = "student";
        String tableTakes = "takes";
        //String tableAdvisor = "advisor";
        //String tableTime_slot = "time_slot";
        //String tablePrereq = "prereq";
        
        // connection, result sets and other statement holders for multiple uses
        Connection conn = null; 
        Statement stmt = null; 
        ResultSet rset = null;
        ResultSetMetaData rsmd = null;
        // connection string
        String cs = "jdbc:postgresql://" +serverIp + "/" + databasename +
        		"?user=" +username+ "&password=" + password; 
        // Trying to connect to the database
        try { 
            conn = DriverManager.getConnection(cs); 
            stmt = conn.createStatement( ); 
            
            
            //-------------- QUERY A -------------------------------------------
            System.out.println("\nFind the names of all students who have taken "
            		+ "at least one Comp. Sci. course;\nmake sure there are no"
            		+ " duplicate names in the result.");
            System.out.println("\nResult should be:\nname\nWilliams\nBrown"
            		+ "\nZhang\nShankar\n");
            rset = stmt.executeQuery(
                 "select distinct name " +
                  "from "+tableCourse+" NATURAL JOIN "+tableStudent+
                  " NATURAL JOIN "+tableTakes+
                  " where student.dept_name = 'Comp. Sci.'");
            // Using metadata for column name
            rsmd = rset.getMetaData();
            for (int i = 1; i <= rsmd.getColumnCount(); i++){
            	System.out.println(rsmd.getColumnName(i));
            } 
            System.out.println("------");
            while(rset.next()) { 
              System.out.println(rset.getString( 1 ));
            }            
            System.out.println();
            //-------------- End of Query A ------------------------------------
            
            
            //-------------- QUERY B -------------------------------------------
            System.out.println("\nFind the IDs and names of all students who "
            		+ "have not taken any\ncourse offering before Spring 2009");
            System.out.println("\nResult should be:\n ID\tname\n00128\tZhang\n"
            		+ "12345\tShankar\n19991\tBrandt\n23121\tChavez\n"
            		+ "44553\tPeltier\n45678\tLevy\n54321\tWilliams\n"
            		+ "55739\tSanchez\n70557\tSnow\n76543\tBrown\n76653\tAoi\n"
            		+ "98765\tBourikas\n98988\tTanaka\n");
            rset = stmt.executeQuery(
                 "select id, name" +
                  " from "+tableStudent+
                  " except"+
                  " select id, name" +
                  " from "+tableStudent+" NATURAL JOIN "+tableTakes+
                  " where year<2009" + 
                  " order by id, name");
            System.out.println(" ID\tname ");
            System.out.println("----------------");
            // printing the result set
            while(rset.next()) { 
              System.out.print(rset.getString( 1 )+"\t");
              System.out.println(rset.getString( 2 ));
            }            
            System.out.println();
            //-------------- End of Query B ------------------------------------
            
            
            //-------------- QUERY C -------------------------------------------
            System.out.println("\nFor each department, find the maximum salary "
            		+ "of instructors in that department.\nYou may assume that "
            		+ "every department has at least one instructor");
            System.out.println("\nResult should be:\n dept_name\tmaxSalary\n"
            		+ "Comp. Sci.\t92000.00\nElec. Eng.\t80000.00\n"
            		+ "History\t62000.00\nMusic\t40000.00\nFinance\t90000.00\n"
            		+ "Physics\t95000.00\nBiology\t72000.00\n");
            rset = stmt.executeQuery(
                 "select dept_name, max(salary)as maxSalary" +
                  " from "+tableInstructor+ 
                  " group by dept_name");
            System.out.println(" dept_name\tmaxSalary ");
            System.out.println("----------------------");
            // printing the result set
            while(rset.next()) { 
              System.out.print(rset.getString( 1 )+"\t");
              System.out.println(rset.getString( 2 ));
            }            
            System.out.println();
            //-------------- End of Query C ------------------------------------
            
            
            //-------------- QUERY D -------------------------------------------
            System.out.println("\nFind the lowest, across all departments, of "
            		+ "the per-department maximumsalary \ncomputed by the "
            		+ "preceding query");
            System.out.println("\nResult should be:\n lowest_salary "
            		+ "\n40000.00\n");
            rset = stmt.executeQuery(
                 "select min(maxSalary) as lowest_salary" +
                  " from (select dept_name, max(salary) as maxSalary"+
                		 " from "+tableInstructor+
                		 " group by dept_name ) as maxSalTable");
            rsmd = rset.getMetaData();
            for (int i = 1; i <= rsmd.getColumnCount(); i++){
            	System.out.println(rsmd.getColumnName(i));
            }
            System.out.println("---------------");
            // printing the result set
            while(rset.next()) { 
                System.out.println(rset.getString( 1 ));
            }            
            System.out.println();
            //-------------- End of Query D ------------------------------------
            
            // closing connections
            stmt.close(); 
            rset.close(); 
        
        } catch (SQLException e) { 
            System.out.println("Exception: " + e.toString()); 
            System.exit(0); 
        }
    } // end of main 
}
