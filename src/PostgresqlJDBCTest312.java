import java.sql.*;
import java.io.Console;
/**
 * This class implements all the queries of exercises 3.12 from the textbook
 * using the university database
 * @author Gajjan Jasani
 * @version 4/11/2017
 *
 */
class PostgresqlJDBCTest312 { 

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
        //String tableInstructor = "instructor";
        String tableSection = "section";
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
        // connection string
        String cs = "jdbc:postgresql://" +serverIp + "/" + databasename +
        		"?user=" +username+ "&password=" + password; 
        // Trying to connect to the database
        try { 
            conn = DriverManager.getConnection(cs);
            stmt = conn.createStatement( );
            
            //-------------- QUERY A -------------------------------------------
            System.out.println("\nCreate a new course “CS-001”, "
            		+ "titled “Weekly Seminar”, with 0 credits.");
            System.out.println("\nCOURSE TABLE - BEFORE the Changes...\n");
            // Printing Table 
            rset = stmt.executeQuery(
              "select * from " + tableCourse );
            while( rset.next( ) ) { 
            	System.out.print( rset.getString( 1 ) + "\t"); 
            	System.out.print( rset.getString( 2 ) + "\t"); 
            	System.out.print( rset.getString( 3 ) + "\t"); 
            	System.out.println( rset.getDouble( 4 ) ); 
            } 
            
			String sql = ""; 
			
			// removing check constraint
			/*
            sql = "ALTER TABLE "+tableCourse+" DROP CONSTRAINT IF "
            		+ "EXISTS course_credits_check";
			stmt.executeUpdate(sql);
			*/
            
            PreparedStatement pstmt = conn.prepareStatement( 
                  "Insert into "+tableCourse+" values (?, ?, ?, ?)"); 
            pstmt.setString(1, "CS-001"); 
            pstmt.setString(2, "Weekly Seminar"); 
            pstmt.setString(3, "Comp. Sci."); 
            pstmt.setDouble(4, 1); 
            pstmt.execute(); 
            pstmt.close();
            
			// Printing Table 
            System.out.println("\nCOURSE TABLE - AFTER the changes...\n");
            rset = stmt.executeQuery(
                    "select * from " + tableCourse );
            while( rset.next( ) ) { 
            	System.out.print( rset.getString( 1 ) + "\t"); 
            	System.out.print( rset.getString( 2 ) + "\t"); 
            	System.out.print( rset.getString( 3 ) + "\t"); 
            	System.out.println( rset.getDouble( 4 ) ); 
            } 
            System.out.println();
            //-------------- End of Query A ------------------------------------
            
            
            //-------------- QUERY B -------------------------------------------
            System.out.println("\nCreate a section of this course "
            		+ "in Fall 2009, with sec id of 1");
            System.out.println("\nSECTION TABLE - BEFORE the Changes...\n");
            // Printing Table 
            rset = stmt.executeQuery(
              "select * from " + tableSection );
            while( rset.next( ) ) { 
            	System.out.print( rset.getString( 1 ) + "\t"); 
            	System.out.print( rset.getString( 2 ) + "\t"); 
            	System.out.print( rset.getString( 3 ) + "\t"); 
            	System.out.print( rset.getDouble( 4 ) + "\t"); 
            	System.out.print( rset.getString( 5 ) + "\t"); 
            	System.out.print( rset.getString( 6 ) + "\t");
            	System.out.println( rset.getString( 7 ) ); 
            } 
            
            // removing check constraint
            /*
			sql = "ALTER TABLE "+tableSection+" DROP CONSTRAINT IF "
					+ "EXISTS section_semester_check";
			stmt.executeUpdate(sql);
			*/
            
            PreparedStatement pstmt1 = conn.prepareStatement( 
                  "Insert into "+tableSection+" values (?, ?, ?, ?, ?, ?, ?)"); 
            pstmt1.setString(1, "CS-001"); 
            pstmt1.setString(2, "1"); 
            pstmt1.setString(3, "Fall");
            pstmt1.setString(2, "1");
            pstmt1.setDouble(4, 2009);
            pstmt1.setString(5, "Painter");
            pstmt1.setString(6, "514");
            pstmt1.setString(7, "A");
            pstmt1.execute(); 
            pstmt1.close();
            // Printing Table 
            System.out.println("\nSECTION TABLE - AFTER the changes...\n");
            rset = stmt.executeQuery(
                    "select * from " + tableSection );
            while( rset.next( ) ) { 
            	System.out.print( rset.getString( 1 ) + "\t"); 
            	System.out.print( rset.getString( 2 ) + "\t"); 
            	System.out.print( rset.getString( 3 ) + "\t"); 
            	System.out.print( rset.getDouble( 4 ) + "\t"); 
            	System.out.print( rset.getString( 5 ) + "\t"); 
            	System.out.print( rset.getString( 6 ) + "\t");
            	System.out.println( rset.getString( 7 ) );
            } 
            System.out.println();
            //-------------- End of Query B ------------------------------------
            
            
            //-------------- QUERY C -------------------------------------------
            System.out.println("\nEnroll every student in the Comp. Sci. "
            		+ "department in the above section.");
            System.out.println("\nTAKES TABLE - BEFORE the Changes...\n");
            // Printing Table 
            rset = stmt.executeQuery(
              "select * from " + tableTakes );
            while( rset.next( ) ) { 
            	System.out.print( rset.getString( 1 ) + "\t"); 
            	System.out.print( rset.getString( 2 ) + "\t"); 
            	System.out.print( rset.getString( 3 ) + "\t"); 
            	System.out.print( rset.getString( 4 ) + "\t");
            	System.out.print( rset.getDouble( 5 ) + "\t"); 
            	System.out.println( rset.getString( 6 ) ); 
            } 
            
            PreparedStatement pstmt2 = conn.prepareStatement( 
                  "Insert into "+tableTakes+" values(?,'CS-001','1',"
                  		+ "'Fall',2009,'A')");
            rset = stmt.executeQuery(
                    "select ID from "+tableStudent+" where dept_name = "
                    		+ "'Comp. Sci.'");
            while ( rset.next() ){
            	String ID = rset.getString(1);
            	pstmt2.setString(1, ID);
            	pstmt2.executeUpdate(); 
            }
            pstmt2.close();
            
			System.out.println("\nTAKES TABLE - AFTER the Changes...\n");
            // Printing Table 
            rset = stmt.executeQuery(
              "select * from " + tableTakes );
            while( rset.next( ) ) { 
            	System.out.print( rset.getString( 1 ) + "\t"); 
            	System.out.print( rset.getString( 2 ) + "\t"); 
            	System.out.print( rset.getString( 3 ) + "\t"); 
            	System.out.print( rset.getString( 4 ) + "\t");
            	System.out.print( rset.getDouble( 5 ) + "\t"); 
            	System.out.println( rset.getString( 6 ) ); 
            }
                    
            System.out.println();
            //-------------- End of Query C ------------------------------------
            
            
            //-------------- QUERY D -------------------------------------------
            System.out.println("\nDelete enrollments in the above section "
            		+ "where the student’s name is Chavez.");
            PreparedStatement pstmt3 = conn.prepareStatement( 
                  "Insert into "+tableTakes+" values('23121','CS-001','1',"
                  		+ "'Fall',2009,'A')");
            pstmt3.executeUpdate();
            pstmt3.close();
            System.out.println("\nTAKES TABLE - BEFORE the Changes...\n");
            // Printing Table 
            rset = stmt.executeQuery(
                    "select * from " + tableTakes );
                  while( rset.next( ) ) { 
                  	System.out.print( rset.getString( 1 ) + "\t"); 
                  	System.out.print( rset.getString( 2 ) + "\t"); 
                  	System.out.print( rset.getString( 3 ) + "\t"); 
                  	System.out.print( rset.getString( 4 ) + "\t");
                  	System.out.print( rset.getDouble( 5 ) + "\t"); 
                  	System.out.println( rset.getString( 6 ) ); 
             }
            
            sql = "Delete from "+tableTakes+" where course_id = 'CS-001' and "
            		+ "ID in (Select ID From "
            		+tableStudent+" Where student.name = 'Chavez')";
            stmt.executeUpdate(sql);
            
            System.out.println("\nTAKES TABLE - AFTER the Changes...\n");
            // Printing Table 
            rset = stmt.executeQuery(
                    "select * from " + tableTakes );
            while( rset.next( ) ) { 
            	System.out.print( rset.getString( 1 ) + "\t"); 
            	System.out.print( rset.getString( 2 ) + "\t"); 
            	System.out.print( rset.getString( 3 ) + "\t"); 
            	System.out.print( rset.getString( 4 ) + "\t");
          		System.out.print( rset.getDouble( 5 ) + "\t"); 
          		System.out.println( rset.getString( 6 ) ); 
            } 
                     
            System.out.println();
            //-------------- End of Query D ------------------------------------
           
          //-------------- QUERY E -------------------------------------------
            System.out.println("\nDelete the course CS-001.");
            
            System.out.println("\nCOURSE TABLE - BEFORE the Changes...\n");
            // Printing Table 
            rset = stmt.executeQuery(
              "select * from " + tableCourse );
            while( rset.next( ) ) { 
            	System.out.print( rset.getString( 1 ) + "\t"); 
            	System.out.print( rset.getString( 2 ) + "\t"); 
            	System.out.print( rset.getString( 3 ) + "\t"); 
            	System.out.println( rset.getDouble( 4 ) ); 
            } 
			
            sql = "Delete from "+tableSection+" where course_id = 'CS-001'";
          	stmt.executeUpdate(sql);
          	
          	sql = "Delete from "+tableCourse+" where course_id = 'CS-001'";
                  	stmt.executeUpdate(sql);
            
			// Printing Table 
            System.out.println("\nCOURSE TABLE - AFTER the changes...\n");
            rset = stmt.executeQuery(
                    "select * from " + tableCourse );
            while( rset.next( ) ) { 
            	System.out.print( rset.getString( 1 ) + "\t"); 
            	System.out.print( rset.getString( 2 ) + "\t"); 
            	System.out.print( rset.getString( 3 ) + "\t"); 
            	System.out.println( rset.getDouble( 4 ) ); 
            } 
            System.out.println();
            //-------------- End of Query E ------------------------------------
            
          //-------------- QUERY F -------------------------------------------
            System.out.println("\nDelete all takes tuples corresponding to any "
            		+ "section of any course \nwith the word “database” as a "
            		+ "part of the title; ignore case \nwhen matching "
            		+ "the word with the title.");
            System.out.println("\nTAKES TABLE - BEFORE the Changes...\n");
            // Printing Table 
            rset = stmt.executeQuery(
                    "select * from " + tableTakes );
                  while( rset.next( ) ) { 
                  	System.out.print( rset.getString( 1 ) + "\t"); 
                  	System.out.print( rset.getString( 2 ) + "\t"); 
                  	System.out.print( rset.getString( 3 ) + "\t"); 
                  	System.out.print( rset.getString( 4 ) + "\t");
                  	System.out.print( rset.getDouble( 5 ) + "\t"); 
                  	System.out.println( rset.getString( 6 ) ); 
             }
            
            sql = "Delete from "+tableTakes+" where course_id in (Select "
            		+ "course_id From "
            		+tableCourse+" Where lower(title) like '%database%')";
            stmt.executeUpdate(sql);
            
            System.out.println("\nTAKES TABLE - AFTER the Changes...\n");
            // Printing Table 
            rset = stmt.executeQuery(
                    "select * from " + tableTakes );
            while( rset.next( ) ) { 
            	System.out.print( rset.getString( 1 ) + "\t"); 
            	System.out.print( rset.getString( 2 ) + "\t"); 
            	System.out.print( rset.getString( 3 ) + "\t"); 
            	System.out.print( rset.getString( 4 ) + "\t");
          		System.out.print( rset.getDouble( 5 ) + "\t"); 
          		System.out.println( rset.getString( 6 ) ); 
            } 
                     
            System.out.println();
            //-------------- End of Query F ------------------------------------
            
            // closing connections
            stmt.close(); 
            rset.close(); 
            
        } catch (SQLException e) { 
            System.out.println("Exception: " + e.toString()); 
            System.exit(0); 
        }
    } // end of main 
}
