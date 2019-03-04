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
import cn.itcast.nsfw.role.entity.Role;
import cn.itcast.nsfw.user.dao.UserDao;
import cn.itcast.nsfw.user.entity.User;
import cn.itcast.nsfw.user.entity.UserRole;
import cn.itcast.nsfw.user.entity.UserRoleId;
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
		userDao.delete(id);
		//ɾ���û���Ӧ������Ȩ��
		userDao.deleteUserRoleByUserId(id.toString());
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
			//�ж��Ƿ���03�汾��Excel(����07��)
			boolean is03Excel = userExcelFileName.matches("^.+\\.(?i)(xls)$");
			//1����ȡ������
			Workbook workbook = is03Excel ? new HSSFWorkbook(fileInputStream)
					: new XSSFWorkbook(fileInputStream);
			//2����ȡ������
			Sheet sheet=workbook.getSheetAt(0);
			//3����ȡ��
			if(sheet.getPhysicalNumberOfRows()>2){
				User user=null;
				for (int i = 2; i < sheet.getPhysicalNumberOfRows(); i++) {
					//4����ȡ��Ԫ��
					Row row=sheet.getRow(i);
					user=new User();
					
					//�û���
					Cell cell1=row.getCell(0);
					user.setName(cell1.getStringCellValue());
					//�˺�
					Cell cell2=row.getCell(1);
					user.setAccount(cell2.getStringCellValue());
					//��������
					Cell cell3=row.getCell(2);
					user.setDept(cell3.getStringCellValue());
					//�Ա�
					Cell cell4=row.getCell(3);
					user.setGender(cell4.getStringCellValue().equals("��"));
					//��������
					Cell cell5=row.getCell(4);
					user.setEmail(cell5.getStringCellValue());
					
					//�����û��ĳ�ʼ����Ϊ123456
					user.setPassword("123456");
					//Ĭ���û�״̬Ϊ��Ч
					user.setState(User.USER_STATE_VALID);
					
					//5�������û�
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
	@Override
	public void saveUserAndRole(User user, String[] userRoleIds) {
		//1.�����û�
		save(user);
		//2.�����û����ڵĽ�ɫ
		if(userRoleIds!=null){
			for (String roleId:userRoleIds) {
				userDao.saveUserRole(new UserRole(new UserRoleId(user.getId(),new Role(roleId))));
			}
		}
	}
	 
	@Override
	public void updateUserAndRole(User user, String[] userRoleIds) {
		//1.�����û�ɾ�����û������н�ɫ
		userDao.deleteUserRoleByUserId(user.getId());
		//2.�����û�
		update(user);
		//3.�����û���Ӧ�Ľ�ɫ
		if(userRoleIds!=null){
			for (String roleId:userRoleIds) {
				userDao.saveUserRole(new UserRole(new UserRoleId(user.getId(),new Role(roleId))));
			}
		}
	}
	@Override
	public List<UserRole> getUserRolesByUserId(String id) {
		return userDao.getUserRolesByUserId(id);
	}

	@Override
	public List<User> findUserByAccountAndPass(String account, String password) {
		return userDao.findUsersByAcccountAndPass(account, password);
	}

}
