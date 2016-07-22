package com.chanjet.hong.pg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class PostgreSQLJDBC {
	
	public static void main(String[] args) {
		PostgreSQLJDBC pg = new PostgreSQLJDBC();
		pg.testQuery();
	}
	
	public void testInsert(){
		  Connection c = null;
	      Statement stmt = null;
	      try {
//	         Class.forName("org.postgresql.Driver");
	         c = DriverManager
	            .getConnection("jdbc:postgresql://localhost:5432/bigdata",
	            "test", "test");
	         c.setAutoCommit(false);
	         System.out.println("Opened database successfully");

	         stmt = c.createStatement();
	         String sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
	               + "VALUES (1, 'Paul', 32, 'California', 20000.00 );";
	         stmt.executeUpdate(sql);

	         sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
	               + "VALUES (2, 'Allen', 25, 'Texas', 15000.00 );";
	         stmt.executeUpdate(sql);

	         sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
	               + "VALUES (3, 'Teddy', 23, 'Norway', 20000.00 );";
	         stmt.executeUpdate(sql);

	         sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
	               + "VALUES (4, 'Mark', 25, 'Rich-Mond ', 65000.00 );";
	         stmt.executeUpdate(sql);

	         stmt.close();
	         c.commit();
	         c.close();
	      } catch (Exception e) {
	         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
	         System.exit(0);
	      }
	      System.out.println("Records created successfully");
	 	
	}
	
	public void testQuery(){
		  Connection c = null;
	      Statement stmt = null;
	      try {
//	         Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/bigdata", "test", "test");
	         c.setAutoCommit(false);
	         System.out.println("Opened database successfully");

	         stmt = c.createStatement();
	         String sql = "select * from company";
	         ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				System.out.printf("%d %s %d %s %d\n",rs.getInt(1),rs.getString(2),rs.getInt(3)
						,rs.getString(4),rs.getInt(5));
//				System.out.print(rs.getInt(1));
//				System.out.println(rs.getString(2));
			}
			rs.close();
			stmt.close();
			c.close();
	      } catch (Exception e) {
				e.printStackTrace();
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
				System.exit(0);
			}
	}
	
	public void testConnect() {
		Connection c = null;
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/bigdata", "test", "test");
			System.out.println("Opened database successfully");
			
			Statement stmt = null;
			stmt = c.createStatement();
	         String sql = "CREATE TABLE COMPANY " +
	                      "(ID INT PRIMARY KEY     NOT NULL," +
	                      " NAME           TEXT    NOT NULL, " +
	                      " AGE            INT     NOT NULL, " +
	                      " ADDRESS        CHAR(50), " +
	                      " SALARY         REAL)";
	         stmt.executeUpdate(sql);
	         stmt.close();
	         c.close();
	         
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Opened database successfully");
	}
	
}



