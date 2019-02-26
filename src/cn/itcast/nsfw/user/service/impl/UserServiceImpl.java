package cn.itcast.nsfw.user.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import cn.itcast.core.util.ExcelUtil;
import cn.itcast.nsfw.user.dao.UserDao;
import cn.itcast.nsfw.user.entity.User;
import cn.itcast.nsfw.user.service.UserService;
@Service("userService")
public class UserServiceImpl implements UserService{
	@Resource
	private UserDao userDao;
	@Override
	public void save(User user) {
		// TODO Auto-generated method stub
		userDao.save(user);
	}

	@Override
	public void update(User user) {
		// TODO Auto-generated method stub
		userDao.update(user);
	}

	@Override
	public void delete(Serializable id) {
		// TODO Auto-generated method stub
		userDao.delete(id);
	}

	@Override
	public User findObjectById(Serializable id) {
		// TODO Auto-generated method stub
		
		return userDao.findObjectById(id);
	}

	@Override
	public List<User> findObjects() {
		// TODO Auto-generated method stub
		
		return userDao.findObjects();
	}
	@Override
	public void exportExcel(List<User> userList,
			ServletOutputStream outputStream) {
		ExcelUtil.exportUserExcel(userList, outputStream);
	}
	@Override
	public void importExcel(File userExcel, String userExcelFileName) {
		try {
			FileInputStream fileInputStream = new FileInputStream(userExcel);
			//判断是否是03版本的Excel(还是07的)
			boolean is03Excel = userExcelFileName.matches("^.+\\.(?i)(xls)$");
			//1、读取工作簿
			Workbook workbook = is03Excel ? new HSSFWorkbook(fileInputStream)
					: new XSSFWorkbook(fileInputStream);
			//2、读取工作表
			Sheet sheet=workbook.getSheetAt(0);
			//3、读取行
			if(sheet.getPhysicalNumberOfRows()>2){
				User user=null;
				for (int i = 2; i < sheet.getPhysicalNumberOfRows(); i++) {
					//4、读取单元格
					Row row=sheet.getRow(i);
					user=new User();
					
					//用户名
					Cell cell1=row.getCell(0);
					user.setName(cell1.getStringCellValue());
					//账号
					Cell cell2=row.getCell(1);
					user.setAccount(cell2.getStringCellValue());
					//所属部门
					Cell cell3=row.getCell(2);
					user.setDept(cell3.getStringCellValue());
					//性别
					Cell cell4=row.getCell(3);
					user.setGender(cell4.getStringCellValue().equals("男"));
					//电子邮箱
					Cell cell5=row.getCell(4);
					user.setEmail(cell5.getStringCellValue());
					
					//导入用户的初始密码为123456
					user.setPassword("123456");
					//默认用户状态为有效
					user.setState(User.USER_STATE_VALID);
					
					//5、保存用户
					userDao.save(user);
				}
			}
			workbook.close();
			fileInputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public List<User> findUserByAccountAndId(String id, String account) {
		return userDao.findUserByAccountAndId(id,account);
	}
}
