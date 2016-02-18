package model;

public class TodoList {
	private int id;
	private String todoMessage;
	private String timesave;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTodoMessage() {
		return todoMessage;
	}
	public void setTodoMessage(String todoMessage) {
		this.todoMessage = todoMessage;
	}
	public String getTimesave() {
		return timesave;
	}
	public void setTimesave(String timesave) {
		this.timesave = timesave;
	}
	
	public String toString(){
		return "Id :"+id+",  Todo Message:"+todoMessage+" Time :"+timesave;
	}
	
}
