package cn.itcast.core.exception;

public class ActionException extends SysException{
	 
	public ActionException() {
		super("����������!");
	}
 
	public ActionException(String message) {
		super(message);
	}
	
}
