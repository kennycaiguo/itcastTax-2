package cn.itcast.core.exception;


public class ServiceException extends SysException{
 
 
	public ServiceException() {
		//ҵ����Ĭ�ϴ�����Ϣ
		super("ҵ���������");
	}
 
 
	public ServiceException(String message) {
		super(message);
	}
	
}
