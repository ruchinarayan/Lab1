package connection;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import model.TodoList;

public class TodoListConnector {

	public TodoListConnector() {
		try 
		{	
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
		}
	}

	private Connection getConnection() {
		try 
		{
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/world?useSSL=false", "root","ruchinarayan");
			return conn;
		} 
		
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}
	
	private Connection getNewConnection(String root, String pass){
		try
		{
			Connection conn1 = DriverManager.getConnection("jdbc:mysql://localhost/world?useSSL=false", root, pass);
			return conn1;
		}
		
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}
	/* START - To Check if value already exist in the table*/
	public int checkValue(int checkId)
	{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		int element = 0;
		try {
			List<TodoList> todos = new ArrayList<TodoList>();

			con = getConnection();

			stmt = con.prepareStatement("select * from todolist where id = "+checkId+";");
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				TodoList todo = new TodoList();
				todo.setId(rs.getInt(1));
				todo.setTodoMessage(rs.getString(2));
				todo.setTimesave(rs.getString(3));
				todos.add(todo);
				element = todos.size();
			}
			
			return element;
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
		} 
		finally 
		{
			if (rs != null) {
				try 
				{
					rs.close();
				} 
				catch (Exception ex) 
				{
					ex.printStackTrace();
				} 
				rs = null;
			}

			if (stmt != null) {
				try 
				{
					stmt.close();
				} 
				catch (SQLException ex) 
				{
					ex.printStackTrace();
				} 

				stmt = null;
			}
			if(con != null)
			{
				try 
				{
					con.close();
				} 
				catch (SQLException ex) 
				{
					ex.printStackTrace();
				} 

				con = null;
			}

		}
		return 0;
	}
	/*End - To Check if value already exist in the table*/
	
	/*Start - To insert a row in the table*/

	public boolean insertTodo(TodoList todo) {
		PreparedStatement stmt = null;
		Connection con = null;
		
		try {
			con = getConnection();

			stmt = con.prepareStatement("insert into todolist values(?,?,?)");
			stmt.setInt(1, todo.getId());
			stmt.setString(2, todo.getTodoMessage());
			stmt.setString(3, todo.getTimesave());
			stmt.executeUpdate();
			return true;

		} 
	
		catch(SQLException ex)
		{
			PreparedStatement stmt1 = null;
			try {
				stmt1 = con.prepareStatement("CREATE TABLE world.todolist (id INT NOT NULL, todomessage VARCHAR(45) NOT NULL, timesave VARCHAR(45) NOT NULL);");
				stmt1.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		catch (Exception ex) 
		{
			ex.printStackTrace();
		} 
		
		finally 
		{

			if (stmt != null) 
			{
				try 
				{
					stmt.close();
				} 
				catch (Exception ex) 
				{
					ex.printStackTrace();
				} 
				stmt = null;
			}
			if(con != null)
			{
				try 
				{
					con.close();
				} 
				catch (Exception ex) 
				{
					ex.printStackTrace();
				} 

				con = null;
			}

		}
		return false;
	}
	/*Start - To insert a row in the table*/
	
	/*Start - Get information based on id provided */
	
	public List<TodoList> getTodoListInfo(int id) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			List<TodoList> todos = new ArrayList<TodoList>();

			con = getConnection();
			stmt = con.prepareStatement("select * from todolist where id = ?");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				TodoList todo = new TodoList();
				todo.setId(rs.getInt(1));
				todo.setTodoMessage(rs.getString(2));
				todo.setTimesave(rs.getString(3));
				todos.add(todo);
			}
			return todos;
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
		} 
		finally 
		{
			if (rs != null) {
				try 
				{
					rs.close();
				} 
				catch (Exception ex) 
				{
					ex.printStackTrace();
				} 
				rs = null;
			}

			if (stmt != null) {
				try 
				{
					stmt.close();
				} 
				catch (SQLException ex) 
				{
					ex.printStackTrace();
				} 

				stmt = null;
			}
			if(con != null)
			{
				try 
				{
					con.close();
				} 
				catch (SQLException ex) 
				{
					ex.printStackTrace();
				} 

				con = null;
			}

		}
		return null;
	}
	/*End - Get information based on id provided */
	
	/*Start - Get All information  */
	
	public List<TodoList> getAllTodoListInfo() {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			List<TodoList> todos = new ArrayList<TodoList>();

			con = getConnection();

			stmt = con.prepareStatement("select * from todolist");
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				TodoList todo = new TodoList();
				todo.setId(rs.getInt(1));
				todo.setTodoMessage(rs.getString(2));
				todo.setTimesave(rs.getString(3));
				
				todos.add(todo);
			}
			return todos;
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
		} 
		finally 
		{
			if (rs != null) {
				try 
				{
					rs.close();
				} 
				catch (Exception ex) 
				{
					ex.printStackTrace();
				} 
				rs = null;
			}

			if (stmt != null) {
				try 
				{
					stmt.close();
				} 
				catch (SQLException ex) 
				{
					ex.printStackTrace();
				} 

				stmt = null;
			}
			if(con != null)
			{
				try 
				{
					con.close();
				} 
				catch (SQLException ex) 
				{
					ex.printStackTrace();
				} 

				con = null;
			}

		}
		return null;
	}
	
	/*End - Get All information */
	
	/*Start - Delete an entry from table */
	
	public List<TodoList> deleteTodoListInfo(int id) {
		PreparedStatement stmt = null;
		Connection con = null;
		try {
			con = getConnection();

			stmt = con.prepareStatement("delete from todolist where id = ?");
			stmt.setInt(1, id);
			stmt.executeUpdate();
			
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
		} 
		finally 
		{

			if (stmt != null) {
				try 
				{
					stmt.close();
				} 
				catch (SQLException ex) 
				{
					ex.printStackTrace();
				} 

				stmt = null;
			}
			if(con != null)
			{
				try 
				{
					con.close();
				} 
				catch (SQLException ex) 
				{
					ex.printStackTrace();
				} 

				con = null;
			}

		}
		return null;
	}
	
	/*End - Delete an entry from table*/
	

	/*Start - Migration of data into Another Database*/
	public void copyData(String root, String pass)
	{
		List<TodoList> insertVal = getAllTodoListInfo();
		TodoList todo = null;
		
		for(int i=0;i<insertVal.size();i++)
		{
			//System.out.println(insertVal.get(i));
			todo = insertVal.get(i);
			pasteDataIntoNewDB(root,pass,todo);
		}
		
	}
	
	
	
	public boolean pasteDataIntoNewDB(String root,String pass, TodoList todo)
	{
	
		PreparedStatement stmt = null;
		Connection con = null;
		
		try {
			con = getNewConnection(root,pass);
			stmt = con.prepareStatement("insert into world1.todolist values(?,?,?)");	
			stmt.setInt(1, todo.getId());
			stmt.setString(2, todo.getTodoMessage());
			stmt.setString(3, todo.getTimesave());
			return stmt.executeUpdate() > 0;

		} catch (Exception ex) 
		{
			ex.printStackTrace();
		} 
		finally 
		{

			if (stmt != null) 
			{
				try 
				{
					stmt.close();
				} 
				catch (Exception ex) 
				{
					ex.printStackTrace();
				} 
				stmt = null;
			}
			if(con != null)
			{
				try 
				{
					con.close();
				} 
				catch (Exception ex) 
				{
					ex.printStackTrace();
				} 

				con = null;
			}

		}
		return false;
		
	}
	/*End - Migration of data into Another Database*/
	
	
	/* START - Check the existance of the table in the database */
	public boolean checkTableExists(String tableName)
	{
		Connection conn = null;
		DatabaseMetaData dbm = null;
		ResultSet tables = null;
		
		conn = getConnection();
		try {
			dbm = conn.getMetaData();
			tables = dbm.getTables(null, null, tableName, null);
			if (tables.next()) {
			  return true;
			}
			else {
			  return false;
			}
		} 
		
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally 
		{
			if(conn != null)
			{
				try 
				{
					conn.close();
				} 
				catch (Exception ex) 
				{
					ex.printStackTrace();
				} 

				conn = null;
			}
		}
		return false;
	}
	
	/* END - Check the existance of the table in the database */
	
	/* START - Create Table if it does not exist */
	public void createTable(String tableName)
	{
		String createTable = "CREATE TABLE world."+tableName+" (id INT NOT NULL, todomessage VARCHAR(45) NOT NULL,timesave VARCHAR(45) NOT NULL);";
		PreparedStatement stmt = null;
		Connection con = null;
		
		con = getConnection();

		try {
			stmt = con.prepareStatement(createTable);
			stmt.executeUpdate();
		} 
		
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally 
		{

			if (stmt != null) 
			{
				try 
				{
					stmt.close();
				} 
				catch (Exception ex) 
				{
					ex.printStackTrace();
				} 
				stmt = null;
			}
			if(con != null)
			{
				try 
				{
					con.close();
				} 
				catch (Exception ex) 
				{
					ex.printStackTrace();
				} 

				con = null;
			}

		}
	
	}
	/* END - Create Table if it does not exist */
	
	
	/* START - Create New Schema */
	public boolean createNewSchema()
	{
		String createSchema = "create database world1";
		PreparedStatement stmt = null;
		Connection con = null;
		
		con = getConnection();

		try {
			stmt = con.prepareStatement(createSchema);
			stmt.executeUpdate();
		} 
		
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally 
		{

			if (stmt != null) 
			{
				try 
				{
					stmt.close();
				} 
				catch (Exception ex) 
				{
					ex.printStackTrace();
				} 
				stmt = null;
			}
			if(con != null)
			{
				try 
				{
					con.close();
				} 
				catch (Exception ex) 
				{
					ex.printStackTrace();
				} 

				con = null;
			}

		}
		return true;
	
	}
	/* END - Create New Schema */
	
	/* START - Create Table if it does not exist */
	public boolean createTableNewSchema(String tableName)
	{
		String createTable = "CREATE TABLE world1."+tableName+" (id INT NOT NULL, todomessage VARCHAR(45) NOT NULL,timesave VARCHAR(45) NOT NULL);";
		PreparedStatement stmt = null;
		Connection con = null;
		
		con = getConnection();

		try {
			stmt = con.prepareStatement(createTable);
			stmt.executeUpdate();
		} 
		
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally 
		{

			if (stmt != null) 
			{
				try 
				{
					stmt.close();
				} 
				catch (Exception ex) 
				{
					ex.printStackTrace();
				} 
				stmt = null;
			}
			if(con != null)
			{
				try 
				{
					con.close();
				} 
				catch (Exception ex) 
				{
					ex.printStackTrace();
				} 

				con = null;
			}

		}
		return true;
	
	}
	/* END - Create Table if it does not exist */
}
