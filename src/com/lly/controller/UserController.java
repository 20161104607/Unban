package com.lly.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.lly.entity.Archive;
import com.lly.entity.Child;
import com.lly.entity.Child_YiMIao;
import com.lly.entity.Doctor;
import com.lly.entity.User;
import com.lly.entity.YiMiao;
import com.lly.service.ArchiveService;
import com.lly.service.ChildService;
import com.lly.service.DoctorService;
import com.lly.service.UserService;
import com.lly.service.YiMiaoService;
import com.lly.util.ExcelUtils;
import com.lly.util.HttpUtils;

@Controller
public class UserController<E> {

	@Autowired
	private UserService userservice;
	@Autowired
	private ArchiveService archiveservice;
	@Autowired
	private DoctorService doctorservice;
	@Autowired
	private ChildService childservice;
	@Autowired
	private YiMiaoService ymservice;

	// ��ת����¼����
	@RequestMapping("login.html")
	public String to_login() {
		return "login";
	}

	// ��ת����ҳ
	@RequestMapping("index.html")
	public String to_index() {
		return "index_user";
	}

	// ����Ȩ�޵�¼
	@RequestMapping("login.do")
	public ModelAndView login(String username, String password, HttpServletRequest request) {

		Integer role = this.userservice.login_role(username, password);
		System.out.println(role);
		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession();
		if (role!=null) {
			switch (role) {
			case 0:
				User user = this.userservice.login_user(username, password);
				Archive archive = this.archiveservice.findByUserId(user.getId());
				session.setAttribute("login_user", user);
				if (session.getAttribute("user_archive") != null) {
					session.getAttribute("user_archive");
				}
				session.setAttribute("user_archive", archive);
				mv.setViewName("index_user");
				break;
			case 1:
				User user_doc = this.userservice.login_user(username, password);
				session.setAttribute("login_user", user_doc);
				Doctor doctor = this.doctorservice.findByUserId(user_doc.getId());
				session.setAttribute("login_doc", doctor);

				SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd");
				String datetime = tempDate.format(new java.util.Date());

				session.setAttribute("lltime", datetime);

				mv.setViewName("index_doctor");
				break;
			case 2:
				User user_doc1 = this.userservice.login_user(username, password);
				session.setAttribute("login_user", user_doc1);
				mv.setViewName("index_admin");
				break;
			default:
				mv.addObject("message", "�û������������");
				mv.setViewName("login");
				break;
			}
		}else {
			mv.addObject("message", "�û������������");
			mv.setViewName("login");
		}
		
		return mv;
	}

	// �ǳ�
	@RequestMapping(value = "statics/jsp/loginout.html", method = RequestMethod.GET)
	public String loginOuut(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String date = (String) session.getAttribute("lltime");
		User user = (User) session.getAttribute("login_user");
		this.userservice.updatelltime(date, user.getId());
		session.invalidate();
		return "redirect:http://localhost:8080/Urban_lly/login.html";
	}

	// ��ͨ�û��޸���Ϣ
	@RequestMapping("updateArchive.do")
	public ModelAndView updateInfo(Archive archive, HttpSession session) {
		this.archiveservice.updateInfo(archive);
		return null;
	}

	// ��ͨ�û��޸�����
	@RequestMapping("statics/jsp/updatePsword.do")
	public String updatepsword(String password, HttpSession session) {
		User user = (User) session.getAttribute("login_user");
		this.userservice.updatePsword(password, user.getId());
		return "redirect:http://localhost:8080/Urban_lly/login.html";
	}

	// �����û�(ҽ�������û�)
	@RequestMapping("addUser.do")
	public ModelAndView addUser(User u) {
		u.setRole(0);
		this.userservice.addUser(u);
		return new ModelAndView("");

	}

	// ���ӵ���
	@RequestMapping("statics/jsp/addArchives.do")
	public String insertArchive(Integer id, Integer user_id, String name, String sex, String from, String birthday,
			Integer age, String faith, String native1, String degree, Integer postal_code, String phone, String email,
			String address, Double high, Double weight, Integer id_number, String marital, Integer chronic_disease,
			Integer children, Integer vaccine, Integer doctor, HttpSession session) {
		User user_login = (User) session.getAttribute("login_user");
		Doctor doctor_login = (Doctor) session.getAttribute("login_doc");
		// ���ӵ�����ͬʱ�����û�
		insertUser(name, from);
		List<User> findOrder = this.userservice.findOrder();
		Archive archive = new Archive(0, findOrder.get(0).getId(), name, sex, from, birthday, age, faith, native1, degree, postal_code,
				phone, email, address, high, weight, id_number, marital, chronic_disease, children, vaccine,
				doctor_login.getId());
		this.archiveservice.insertArchive(archive);
		return "redirect:http://localhost:8080/Urban_lly/statics/jsp/findAllArchive.do";
	}

	public void insertUser(String name,String from) {
		User user = new User(0, name, "123456", 0, null, from);
		this.userservice.addUser(user);
	}
	// ����ҽ���������е���
	@RequestMapping(value = "statics/jsp/findAllArchive.do", method = RequestMethod.GET)
	public ModelAndView findAllArchives(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("login_user");
		Integer doc_id = this.doctorservice.findDocId(user.getId());
		List<Archive> findByDoctor = this.archiveservice.findByDoctor(doc_id);
		session.setAttribute("findByDoctor", findByDoctor);
		return new ModelAndView("forward:dangan.jsp");
	}

	// ����鿴��������ϸ��Ϣ
	@RequestMapping("statics/jsp/showInfo.do")
	public String showInfo(Integer id, HttpSession session) {
		Archive archive = this.archiveservice.findByID(id);
		if (session.getAttribute("onclick_archive") != null) {
			session.removeAttribute("onclick_archive");
		}
		session.setAttribute("onclick_archive", archive);
		return "forward:dangan_info.jsp";
	}

	// ���ɾ������
	@RequestMapping("statics/jsp/deleteInfo.do")
	public String delete(Integer id) {
		this.archiveservice.deleteInfo(id);
		return "redirect:http://localhost:8080/Urban_lly/statics/jsp/findAllArchive.do";
	}

	// �������Բ�1
	@RequestMapping("statics/jsp/chronic_disease1.do")
	public String find1(HttpSession session) {
		Doctor doctor = (Doctor) session.getAttribute("login_doc");
		List<Archive> chronic1 = this.archiveservice.findByChronic1(doctor.getId());
		if (session.getAttribute("chronic") != null) {
			session.removeAttribute("chronic");
		}
		session.setAttribute("chronic", chronic1);
		String str = "��Ѫѹ���ߵ�����Ϣ";
		if (session.getAttribute("message") != null) {
			session.removeAttribute("message");
		}
		session.setAttribute("message", str);
		return "forward:dangan_chronic_disease.jsp";
	}

	// �������Բ�2
	@RequestMapping("statics/jsp/chronic_disease2.do")
	public String find2(HttpSession session) {
		Doctor doctor = (Doctor) session.getAttribute("login_doc");
		List<Archive> chronic2 = this.archiveservice.findByChronic2(doctor.getId());
		if (session.getAttribute("chronic") != null) {
			session.removeAttribute("chronic");
		}
		session.setAttribute("chronic", chronic2);
		String str = "���򲡻��ߵ�����Ϣ";
		if (session.getAttribute("message") != null) {
			session.removeAttribute("message");
		}
		session.setAttribute("message", str);
		return "forward:dangan_chronic_disease.jsp";
	}

	// �������Բ�1
	@RequestMapping("statics/jsp/chronic_disease3.do")
	public String find3(HttpSession session) {
		Doctor doctor = (Doctor) session.getAttribute("login_doc");
		List<Archive> chronic3 = this.archiveservice.findByChronic3(doctor.getId());
		if (session.getAttribute("chronic") != null) {
			session.removeAttribute("chronic");
		}
		session.setAttribute("chronic", chronic3);
		String str = "���񼲲����ߵ�����Ϣ";
		if (session.getAttribute("message") != null) {
			session.removeAttribute("message");
		}
		session.setAttribute("message", str);
		return "forward:dangan_chronic_disease.jsp";
	}

	@RequestMapping("statics/jsp/findAllChlid.do")
	public String findAllChildren(HttpSession session) {
		Doctor doctor = (Doctor) session.getAttribute("login_doc");
		List<Child> list = this.childservice.findByDoctor(doctor.getId());
		session.setAttribute("findList", list);
		return "forward:childdangan.jsp";
	}

	// ���Ӷ�ͯ����
	@RequestMapping("statics/jsp/addchildArchives.do")
	public String addChild(String name, String sex, String age, Integer father_id, Integer mother_id, Integer doctor,
			HttpSession session) {
		Doctor doctor_1 = (Doctor) session.getAttribute("login_doc");
		Child child = new Child(0, name, age, sex, doctor_1.getId(), father_id, mother_id);
		this.childservice.insertChild(child);
		session.setAttribute("tage", child);
		return "redirect:http://localhost:8080/Urban_lly/statics/jsp/addGuanlian.do";
	}

	// ���ӹ���
	@RequestMapping("statics/jsp/addGuanlian.do")
	public String addGuanlian(HttpSession session) {
		Child child = (Child) session.getAttribute("tage");
		this.childservice.insertGuanlian(child);
		return "redirect:http://localhost:8080/Urban_lly/statics/jsp/addYiMiaoChild.do";
	}
	//���Ӷ�ͯ���絵��
	@RequestMapping("statics/jsp/addYiMiaoChild.do")
	public String addYiMiaoChild(HttpSession session) {
		Doctor doctor_1 = (Doctor) session.getAttribute("login_doc");
		Child_YiMIao yiMIao = new Child_YiMIao(0, 1, doctor_1.getId(), 0, 0, 0, 0);
		this.ymservice.insertYi(yiMIao);
		return "redirect:http://localhost:8080/Urban_lly/statics/jsp/findAllChlid.do";
	}
	// ������������
	@RequestMapping("statics/jsp/findAllYiMiao.do")
	public String findAllYiMiao(HttpSession session) {
		List<YiMiao> list = this.ymservice.findAll();
		session.setAttribute("YiMiao", list);
		Doctor doctor = (Doctor) session.getAttribute("login_doc");
		// �ѽ��֣�δ���֣��������ٷֱȣ�Ĭ�ϲ���δ����
		List<Child_YiMIao> findByzhonglei = this.ymservice.findByzhonglei("niudou", doctor.getId());
		// ����δ���ֶ�ͯ��Ϣ
		List<Child> list_child = new ArrayList<Child>();
		for (int i = 0; i < findByzhonglei.size(); i++) {
			Child child = this.childservice.findById(findByzhonglei.get(i).getChild_id());
			list_child.add(child);
		}
		session.setAttribute("find_yimiao", list_child);
		// ����
		Integer num = this.childservice.countInfo(doctor.getId());
		// �ѽ���
		Integer yijiezhong = num - list_child.size();
		// �ٷ���
		String str = "";
		if (yijiezhong == 0) {
			str = "0%";
		} else {
			str = (num / yijiezhong) * 100 + "%";
		}
		session.setAttribute("num", num);
		session.setAttribute("yijiezhong", yijiezhong);
		session.setAttribute("str", str);
		session.setAttribute("zhonglei", "ţ��");
		return "forward:yimiao.jsp";
	}

	@RequestMapping("statics/jsp/findYiMiao.do")
	public String findYiMiao(String zhonglei, HttpSession session) {
		String str = "";
		if (zhonglei == null) {
			zhonglei  = (String) session.getAttribute("zhonglei");
		}
		switch (zhonglei) {
		case "ţ��":
			str = "niudou";
			break;
		case "������":
			str = "kajiemiao";
			break;
		case "�Ҹ�":
			str = "yigan";
			break;
		case "С�����":
			str = "xiaoermabi";
			break;
		default:
			break;
		}
		Doctor doctor = (Doctor) session.getAttribute("login_doc");
		List<Child_YiMIao> findByzhonglei = this.ymservice.findByzhonglei(str, doctor.getId());
		List<Child> list_child = new ArrayList<Child>();
		for (int i = 0; i < findByzhonglei.size(); i++) {
			Child child = this.childservice.findById(findByzhonglei.get(i).getChild_id());
			list_child.add(child);
		}
		session.setAttribute("find_yimiao", list_child);
		// ����
		Integer num = this.childservice.countInfo(doctor.getId());
		// �ѽ���
		Integer yijiezhong = num - list_child.size();
		// �ٷ���
		String str1 = "";
		if (yijiezhong == 0) {
			str1 = "0%";
		} else {
			str1 = (num / yijiezhong) * 100 + "%";
		}
		if (session.getAttribute("num") != null) {
			session.removeAttribute("num");
		}
		session.setAttribute("num", num);
		if (session.getAttribute("yijiezhong") != null) {
			session.removeAttribute("yijiezhong");
		}
		session.setAttribute("yijiezhong", yijiezhong);
		if (session.getAttribute("str") != null) {
			session.removeAttribute("str");
		}
		session.setAttribute("str", str1);
		if (session.getAttribute("zhonglei") != null) {
			session.removeAttribute("zhonglei");
		}
		session.setAttribute("zhonglei", zhonglei);
		return "forward:yimiao.jsp";
	}

	// ��������ҽ��
	@RequestMapping("statics/jsp/findAllDoctor.do")
	public String findAllDoctor(HttpSession session) {
		List<Doctor> allDoctor = this.doctorservice.findAllDoctor();
		session.setAttribute("AllDoctor", allDoctor);
		return "forward:dangan_doctor.jsp";
	}

	/**
	 * Excel��������
	 */
	@RequestMapping(value = "statics/jsp/doctordownload.do", method = RequestMethod.GET)
	public void downstudents(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// һ���Ӻ�̨������
		List<Doctor> list = null;
		list = this.doctorservice.findAllDoctor();
		try {
			// ���� ����ת��excel
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/x-download");

			String fileName = "doctor.xls";
			fileName = URLEncoder.encode(fileName, "UTF-8");
			response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
			// ��һ��������һ���µĹ�����
			HSSFWorkbook wb = new HSSFWorkbook();
			// �ڶ���������һ��Sheetҳ
			HSSFSheet sheet = wb.createSheet("docotrsheet");
			HSSFCellStyle style = wb.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// ���þ���(��Ч)
			sheet.setDefaultRowHeight((short) (256));// �����и�
			sheet.setColumnWidth(0, 2000);// �����п�
			sheet.setColumnWidth(1, 5000);
			sheet.setColumnWidth(2, 5500);
			sheet.setColumnWidth(3, 5500);

			HSSFFont font = wb.createFont();
			font.setFontName("����");// ��������
			font.setFontHeightInPoints((short) 16);// �������ִ�С

			HSSFRow row = sheet.createRow(0);
			HSSFCell cell = row.createCell(0);
			cell.setCellValue("����");
			cell = row.createCell(1);
			cell.setCellValue("����");
			cell = row.createCell(2);
			cell.setCellValue("�绰");
			cell = row.createCell(3);
			cell.setCellValue("��������");
			cell = row.createCell(4);
			cell.setCellValue("���֤����");
			
			HSSFRow rows;
			HSSFCell cells;
			for (int i = 0; i < list.size(); i++) {
				// �������������sheetҳ�ﴴ��һ��
				rows = sheet.createRow(i + 1);
				// ���Ĳ����ڸ��д���һ����Ԫ��
				cells = rows.createCell(0);
				// ���岽���ڸõ�Ԫ��������ֵ
				cells.setCellValue(list.get(i).getName());
				cells = rows.createCell(1);
				cells.setCellValue(list.get(i).getAge());
				cells = rows.createCell(2);
				cells.setCellValue(list.get(i).getPhone());
				cells = rows.createCell(3);
				cells.setCellValue(list.get(i).getAddress());
				cells = rows.createCell(4);
				cells.setCellValue(list.get(i).getId_number());

			}

			OutputStream out = response.getOutputStream();
			wb.write(out);
			out.close();
			wb.cloneSheet(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//����ҽ��
	@RequestMapping("statics/jsp/addDoctor.do")
	public String addDoctor(String name,Integer phone,String address,String id_number,Integer age) {
		addUSer(name,address);
		List<User> list = this.userservice.findOrder();
		Doctor doctor = new Doctor(0, list.get(0).getId(), name, phone, address, id_number, age);
		this.doctorservice.addDoctor(doctor);
		return "redirect:http://localhost:8080/Urban_lly/statics/jsp/findAllDoctor.do";
	}
	public void addUSer(String name,String address) {
		SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd");
		String datetime = tempDate.format(new java.util.Date());
		User user = new User(0, name, "123456", 1, datetime, address);
		this.userservice.addUser(user);
	}
	@RequestMapping("statics/jsp/dangandownload.do")
	public String dowloadDangan(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// һ���Ӻ�̨������
		HttpSession session = request.getSession();
		List<Archive> list = null;
		Doctor doctor = (Doctor) session.getAttribute("login_doc");
		list = this.archiveservice.findByDoctor(doctor.getId());
		try {
			// ���� ����ת��excel
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/x-download");

			String fileName = "������.xls";
			fileName = URLEncoder.encode(fileName, "UTF-8");
			response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
			// ��һ��������һ���µĹ�����
			HSSFWorkbook wb = new HSSFWorkbook();
			// �ڶ���������һ��Sheetҳ
			HSSFSheet sheet = wb.createSheet("archivessheet");
			HSSFCellStyle style = wb.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// ���þ���(��Ч)
			sheet.setDefaultRowHeight((short) (256));// �����и�
			sheet.setColumnWidth(0, 2000);// �����п�
			sheet.setColumnWidth(1, 1000);
			sheet.setColumnWidth(2, 1500);
			sheet.setColumnWidth(3, 2000);
			sheet.setColumnWidth(4, 2000);
			sheet.setColumnWidth(5, 3000);
			sheet.setColumnWidth(6, 3000);

			HSSFFont font = wb.createFont();
			font.setFontName("����");// ��������
			font.setFontHeightInPoints((short) 16);// �������ִ�С

			HSSFRow row = sheet.createRow(0);
			HSSFCell cell = row.createCell(0);
			cell.setCellValue("����");
			cell = row.createCell(1);
			cell.setCellValue("�Ա�");
			cell = row.createCell(2);
			cell.setCellValue("����");
			cell = row.createCell(3);
			cell.setCellValue("����");
			cell = row.createCell(4);
			cell.setCellValue("�Ļ��̶�");
			cell = row.createCell(5);
			cell.setCellValue("�ڽ�����");
			cell = row.createCell(6);
			cell.setCellValue("��ϵ�绰");
			
			HSSFRow rows;
			HSSFCell cells;
			for (int i = 0; i < list.size(); i++) {
				// �������������sheetҳ�ﴴ��һ��
				rows = sheet.createRow(i + 1);
				// ���Ĳ����ڸ��д���һ����Ԫ��
				cells = rows.createCell(0);
				// ���岽���ڸõ�Ԫ��������ֵ
				cells.setCellValue(list.get(i).getName());
				cells = rows.createCell(1);
				cells.setCellValue(list.get(i).getSex());
				cells = rows.createCell(2);
				cells.setCellValue(list.get(i).getFrom());
				cells = rows.createCell(3);
				cells.setCellValue(list.get(i).getAge());
				cells = rows.createCell(4);
				cells.setCellValue(list.get(i).getDegree());
				cells = rows.createCell(5);
				cells.setCellValue(list.get(i).getFaith());
				cells = rows.createCell(6);
				cells.setCellValue(list.get(i).getPhone());
			}

			OutputStream out = response.getOutputStream();
			wb.write(out);
			out.close();
			wb.cloneSheet(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect:http://localhost:8080/Urban_lly/statics/jsp/findAllArchive.do";
	}
	@RequestMapping("statics/jsp/ertongdownload.do")
	public String ertongdaochu(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// һ���Ӻ�̨������
		HttpSession session = request.getSession();
		List<Child> list = null;
		Doctor doctor = (Doctor) session.getAttribute("login_doc");
		list = this.childservice.findByDoctor(doctor.getId());
		try {
			// ���� ����ת��excel
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/x-download");

			String fileName = "��ͯ����.xls";
			fileName = URLEncoder.encode(fileName, "UTF-8");
			response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
			// ��һ��������һ���µĹ�����
			HSSFWorkbook wb = new HSSFWorkbook();
			// �ڶ���������һ��Sheetҳ
			HSSFSheet sheet = wb.createSheet("docotrsheet");
			HSSFCellStyle style = wb.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// ���þ���(��Ч)
			sheet.setDefaultRowHeight((short) (256));// �����и�
			sheet.setColumnWidth(0, 2000);// �����п�
			sheet.setColumnWidth(1, 5000);
			sheet.setColumnWidth(2, 5500);
			sheet.setColumnWidth(3, 5500);
			sheet.setColumnWidth(4, 5500);
			sheet.setColumnWidth(5, 5500);
			sheet.setColumnWidth(6, 5500);
			

			HSSFFont font = wb.createFont();
			font.setFontName("����");// ��������
			font.setFontHeightInPoints((short) 16);// �������ִ�С

			HSSFRow row = sheet.createRow(0);
			HSSFCell cell = row.createCell(0);
			cell.setCellValue("����");
			cell = row.createCell(1);
			cell.setCellValue("����");
			cell = row.createCell(2);
			cell.setCellValue("�Ա�");
			cell = row.createCell(3);
			cell.setCellValue("��������");
			cell = row.createCell(4);
			cell.setCellValue("���׵绰");
			cell = row.createCell(5);
			cell.setCellValue("ĸ������");
			cell = row.createCell(6);
			cell.setCellValue("ĸ�׵绰");
			
			HSSFRow rows;
			HSSFCell cells;
			for (int i = 0; i < list.size(); i++) {
				// �������������sheetҳ�ﴴ��һ��
				rows = sheet.createRow(i + 1);
				// ���Ĳ����ڸ��д���һ����Ԫ��
				cells = rows.createCell(0);
				// ���岽���ڸõ�Ԫ��������ֵ
				cells.setCellValue(list.get(i).getName());
				cells = rows.createCell(1);
				cells.setCellValue(list.get(i).getAge());
				cells = rows.createCell(2);
				cells.setCellValue(list.get(i).getSex());
				cells = rows.createCell(3);
				cells.setCellValue(list.get(i).getFather_name());
				cells = rows.createCell(4);
				cells.setCellValue(list.get(i).getFather_phone());
				cells = rows.createCell(5);
				cells.setCellValue(list.get(i).getMother_name());
				cells = rows.createCell(6);
				cells.setCellValue(list.get(i).getMother_phone());

			}

			OutputStream out = response.getOutputStream();
			wb.write(out);
			out.close();
			wb.cloneSheet(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect:http://localhost:8080/Urban_lly/statics/jsp/findAllChlid.do";
	}
	//���Ͷ���
	@RequestMapping("statics/jsp/faduanxin.do")
	public void faduanx(String phone) {
		 String host = "http://smsmsgs.market.alicloudapi.com";
		    String path = "/smsmsgs";
		    String method = "GET";
		    String appcode = "7dbd85f49f914543ab3e2b95f9fbb9e4";
		    Map<String, String> headers = new HashMap<String, String>();
		    //�����header�еĸ�ʽ(�м���Ӣ�Ŀո�)ΪAuthorization:APPCODE 83359fd73fe94948385f570e3c139105
		    headers.put("Authorization", "APPCODE " + appcode);
		    Map<String, String> querys = new HashMap<String, String>();
		    querys.put("param", "������|������|����������");
		    querys.put("phone", phone);
		    querys.put("sign", "175622");
		    querys.put("skin", "2006");
	            //JDK 1.8ʾ�����������������أ�  http://code.fegine.com/Tools.zip

		    try {
		    	/**
		    	* ��Ҫ��ʾ����:
		    	* HttpUtils���
		    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
	                * ����ֱ�����أ�
	                * http://code.fegine.com/HttpUtils.zip
		    	* ����
		    	*
		    	* ��Ӧ�����������
		    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
	                * ���jar������pom��ֱ�����أ�
	                * http://code.fegine.com/aliyun-jar.zip
		    	*/
		    	HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
		    	//System.out.println(response.toString());�粻���json, ������д��룬��ӡ����ͷ��״̬�롣
	                //״̬��: 200 ������400 URL��Ч��401 appCode���� 403 �������ꣻ 500 API���ܴ���
		    	//��ȡresponse��body
		    	System.out.println(EntityUtils.toString(response.getEntity()));
		    } catch (Exception e) {
		    	e.printStackTrace();
		    }
		}
	
	
	//�޸�Ϊ�ѽ���
	@RequestMapping("statics/jsp/updateyijiezhong.do")
	public String updateyijiezhogn(Integer id,HttpSession session) {
		String zhonglei = (String) session.getAttribute("zhonglei");
		String str = "";
		switch (zhonglei) {
		case "ţ��":
			str = "niudou";
			break;
		case "������":
			str = "kajiemiao";
			break;
		case "�Ҹ�":
			str = "yigan";
			break;
		case "С�����":
			str = "xiaoermabi";
			break;
		default:
			break;
		}
		this.ymservice.updateInfo(id,str);
		return "redirect:http://localhost:8080/Urban_lly/statics/jsp/findYiMiao.do";
	}
}
