package connection;

import java.util.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import model.TodoList;


public class TodoListClient {

	public TodoListConnector connector;
	
	public TodoListClient(){
		connector = new TodoListConnector();
	}
	public void runQueries() throws SQLException{

		List<TodoList> todos = connector.getTodoListInfo(20);
		for(TodoList todo : todos){
			System.out.println(todo);
		}
	}
	public void insertTodo(TodoList todo) {
		boolean success = connector.insertTodo(todo);
		if(!success){
			System.err.println("Insertion Failed!!");
		}else{
			//System.out.println("Insertion Success!!");
		}
	}
	
	public static void main(String[] args) {
		
		Scanner scn = new Scanner(System.in);
		String option = null;
		
		while(true)
		{
			System.out.println("=========== To Access Database ============ ");
			System.out.println("POST [id] [todo message]: Enter New TODO Message with id ");
			System.out.println("GET [id] : Get TODO Message with id");
			System.out.println("GET : Display all TODO Messages ");
			System.out.println("DELETE [id] : Delete a TODO Message with id ");
			System.out.println("REPLICATE [URI] :  Migrates the data from current server to another URI server");
			System.out.println("=========================================== ");
			System.out.print("Enter request: ");
			option = scn.nextLine();
			
			String[] inputArr = option.split(" ");
			StringBuffer inp = new StringBuffer();
			
			for(int i=0;i<inputArr.length;i++)
			{
				inputArr[i] = inputArr[i].replace("[", "");
				inputArr[i] = inputArr[i].replace("]", "");
				if(i>=2)
				{
					inp.append(inputArr[i]);
					inp.append(" ");
				}
			}
			
			//System.out.println("Merged output: "+inp);
			
						
			// POST [id] [todo message] : Stores the string 'todo message' in the  database with the supplied integer 'id' and the client's timestamp.  Overwrite any  existing values
			if((inputArr[0].equals("POST")) && (inputArr.length>=3))
			{
				TodoListClient todoClient = new TodoListClient();
				TodoListConnector todoConn = new TodoListConnector();
				
				
				if(todoConn.checkTableExists("todolist") == true)
				{
					try {
						todoClient.runQueries();
						TodoList todo = new TodoList();
						
						todo.setId(Integer.parseInt(inputArr[1]));
						//todo.setTodoMessage(inputArr[2]);
						todo.setTodoMessage(inp.toString());
						
						Date date = new Date();
						String timesave = date.toString();
						todo.setTimesave(timesave);
						
						int val = todoConn.checkValue(Integer.parseInt(inputArr[1]));

						if(val == 1)
						{
							todoConn.deleteTodoListInfo(Integer.parseInt(inputArr[1]));
							todoClient.insertTodo(todo);
							System.out.println("Existing row has been updated!!");
							System.out.println();
						}
						else
						{
							todoClient.insertTodo(todo);
							System.out.println("Insertion Successful !!");
							System.out.println();
						}
						
						
					} 
					catch (Exception ex) {
						// TODO Auto-generated catch block
						ex.printStackTrace();
					} 
				}
				else
				{
					System.out.println("Table Does'nt Exist !!");
					todoConn.createTable("todolist");
					System.out.println("Table Created!!");
					try {
						todoClient.runQueries();
						TodoList todo = new TodoList();
						
						todo.setId(Integer.parseInt(inputArr[1]));
						todo.setTodoMessage(inputArr[2]);
						Date date = new Date();
						String timesave = date.toString();
						todo.setTimesave(timesave);
						
						int val = todoConn.checkValue(Integer.parseInt(inputArr[1]));

						if(val == 1)
						{
							todoConn.deleteTodoListInfo(Integer.parseInt(inputArr[1]));
							todoClient.insertTodo(todo);
							System.out.println("Existing row has been updated!!");
							System.out.println();
						}
						else
						{
							todoClient.insertTodo(todo);
							System.out.println("Insertion Successful !!");
							System.out.println();
						}
						
						
					} 
					catch (Exception ex) {
						// TODO Auto-generated catch block
						ex.printStackTrace();
					} 
				}
				
						
			}
			// GET [id] : Retrieves and displays the todo message to the console and  when it was posted
			else if((inputArr[0].equals("GET")) && (inputArr.length==2))
			{

				TodoListConnector conn = new TodoListConnector();
				List<TodoList> oneVal = conn.getTodoListInfo(Integer.parseInt(inputArr[1]));
				System.out.println("ID\tTODO Message\tTime");
				for(int i=0;i<oneVal.size();i++){
					System.out.print(oneVal.get(i).getId()+"\t");
					System.out.print(oneVal.get(i).getTodoMessage()+"\t\t");
					System.out.print(oneVal.get(i).getTimesave());
					System.out.println();
				} 
			}
			// "GET" : Retrieves a List of all todo messages as a map of <id,todo  message> pairs and prints it to the console
			else if((inputArr[0].equals("GET")) && (inputArr.length==1))
			{
				TodoListConnector connAll = new TodoListConnector();
				List<TodoList> allVals = connAll.getAllTodoListInfo();
				System.out.println("List of all the TODO messages : ");
				System.out.println("ID\tTODO Message");
				
				for(int i=0;i<allVals.size();i++){
					System.out.print(allVals.get(i).getId()+"\t");
					System.out.print(allVals.get(i).getTodoMessage()+"\t");
					System.out.println();
				} 
				
			}
			// DELETE [id] : Deletes the todo message at the given id from the database
			else if((inputArr[0].equals("DELETE")) && (inputArr.length==2))
			{
				TodoListConnector conn = new TodoListConnector();
				conn.deleteTodoListInfo(Integer.parseInt(inputArr[1]));
				System.out.println("TODO message with id "+Integer.parseInt(inputArr[1])+" has been deleted!!");
				System.out.println();
			}
			// REPLICATE [URI] : Migrates the database from one mysql server to  another. The destination is at 'URI' in the form "[host]/[database name]"
			/*
			else if((inputArr[0].equals("REPLICATE")) && (inputArr.length==2))
			{

				String[] newDB = inputArr[1].split("/");
				TodoListConnector connNew = new TodoListConnector();
				
				if(connNew.checkTableExists("todolist_New") == true)
				{
					connNew.copyData(newDB[0],newDB[1]);
				}
				else
				{
					System.out.println("Table Does'nt Exist !!");
					connNew.createTable("todolist_New");
					System.out.println("Table todolist_new has been created !!");
					connNew.copyData(newDB[0],newDB[1]);
				}
				
				System.out.println("All data has been migrated !!");
				System.out.println();
				
			}
			else
			{
				System.out.println("Not Valid request!");
				System.out.println();
			} 
			*/
			
			
			else if((inputArr[0].equals("REPLICATE")) && (inputArr.length==2))
			{

				String[] newDB = inputArr[1].split("/");
				TodoListConnector connNew = new TodoListConnector();
				
				connNew.createNewSchema();
				System.out.println("Schema world1 has been created !!");
				connNew.createTableNewSchema("todolist");
				connNew.copyData(newDB[0],newDB[1]);
				
				System.out.println("All data has been migrated !!");
				System.out.println();
			}
			
			else
			{
				System.out.println("Not a Valid Option !!");
				System.out.println();
			}
			
		}
	
	} 


}
