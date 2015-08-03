package com.xd.service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import sun.misc.BASE64Encoder;

import com.xd.dao.user.UserDao;
import com.xd.model.Department;
import com.xd.model.User;

@Service
public class UserService {
	private UserDao userDao;

	// 初始化
	public UserService() {
		userDao = new UserDao();
	}

	/**
	 * 获取md5加密后的字符串
	 */
	private String getMD5String(String source){
		String md5string="";
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			BASE64Encoder base64en = new BASE64Encoder();
			md5string = base64en.encode(md5.digest(source.getBytes("utf-8")));
		} catch (NoSuchAlgorithmException e) {
			System.out.println("MD5加密异常1......");
			e.printStackTrace();
		}
		 catch (UnsupportedEncodingException e) {
			 System.out.println("MD5加密异常2......");
			 e.printStackTrace();
		}
		return md5string;
	}
	/**
	 * 判断登录名是否可用
	 * @param user
	 * @return
	 */
	private boolean checkLoginName(User user) {
		User u=new User();
		boolean result=false;
		u.setLoginName(user.getLoginName());
		int i=userDao.getUserCount(u);
		if(i>=1){
			result=false;//该登录名不可用
		}else{
			result=true;//登录名可用
		}
		return result;
	}
	
	/**
	 * 获取人员数量
	 * 
	 * @param user
	 * @return 数量
	 */
	public int getUserCount(User user) {
		String MD5Password="";
		if(!"".equals(user.getPassword())&&user.getPassword()!=null){
			MD5Password=getMD5String(user.getPassword());
			user.setPassword(MD5Password);
		}
		return userDao.getUserCount(user);
	}
	/**
	 * 插入人员
	 * @param user
	 * @return
	 */
private String insertUser(User user){
	String result="";
	String md5Password=getMD5String("123456");
	user.setPassword(md5Password);
	if(user.getEntry().equals("")){
		user.setEntry(null);
	}
	if(user.getBirth().equals("")){
		user.setBirth(null);
	}
	int i = userDao.insertUser(user);
	if(i>0){
		result="注册成功";
	}else {
		result="注册失败";
	}
	return result;
}
/**
 * 更新人员信息
 * @param user
 * @return
 */
	private String updateUser(User user){
		String result="";
		int i=0;
		i = userDao.updateUser(user);
		if(i==0){
			result="修改失败";
		}else {
			result="修改成功";
		}
		return result;
	}
	
	/**
	 * 保存用户信息
	 * 
	 * @param user
	 * @return
	 */
	public String saveUser(User user) {
		int i = 0;
		String result = "";
		if(user.getName().equals("")||user.getName()==null){
			result="请填写真实姓名";
		}else if(user.getLoginName().equals("")||user.getLoginName()==null) {
			result="请填写登录名";
		}
		else {
			boolean b=checkLoginName(user);
			if(b){//登陆名可用
				if (user.getId() == 0) {// 插入
					result=insertUser(user);
				}
				else {// 更新
					result=updateUser(user);
				}
			}else {//登录名被占用
				result="该登陆名被占用!";
			}
		}
		
		return result;
	}

	/**
	 * 获取部门
	 */
	public List<Department> getDepart(){
		return userDao.getDepart();
	}
	
	public List<User> getUserList(User user){
		user.setPageStart(user.getcurrentPage());
		
		return userDao.getUserList(user);
	}
	
	/**
	 * 删除用户
	 * @return 删除id
	 */
	public Map<String,String> deleteUser(User user){
		int i=userDao.checkUserForDelete(user);
		Map<String,String> map=new HashMap<String, String>();
		if(i==0){//未发现未还书籍,可以删除
			i=userDao.deleteUser(user);
			if(i>0){
				map.put("status", "success");
				map.put("msg", "删除成功");
			}else {
				map.put("status", "error");
				map.put("msg", "删除失败");
			}
			
		}else if(i>0){//有未还书籍,无法删除
			map.put("status", "error");
			map.put("msg", "该成员还有"+i+"书没有还.");
		}else {
			System.out.println("查阅是否有未还书籍时异常.....");
			map.put("status", "error");
			map.put("msg", "程序出现异常,请联系工作人员.");
		}
		return map;
	}
	
}
