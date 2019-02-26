package cn.itcast.core.action;

import com.opensymphony.xwork2.ActionSupport;

public  abstract class BaseAction extends ActionSupport{

	//这里selectedRow是批量操作时的id数组
		protected String[] selectedRow; 
		
		public String[] getSelectedRow() {
			return selectedRow;
		}
		public void setSelectedRow(String[] selectedRow) {
			this.selectedRow = selectedRow;
		}
}
