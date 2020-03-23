package com.lly.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ExcelUtils {

	/**
	 * 
	 * @param filePath �ļ�·������
	 * @param size     ��ȡĳ��ҳǩ������
	 * @return map<����,List<Map<�ֶ���,�ֶ�ֵ>>> ��������
	 * @throws Exception
	 */
	/*
	 * public static Map<String,List<Map<String, String>>> getExcelData(String
	 * filePath) throws Exception{
	 * 
	 * Map<String,List<Map<String, String>>> listvos = new
	 * HashMap<String,List<Map<String, String>>>();
	 * 
	 * if(filePath==null){ return null; } if(filePath.endsWith(".xls")){
	 * listvos=readXls( filePath); }
	 * 
	 * if(filePath.endsWith(".xlsx")){ listvos=readXlsx( filePath); }
	 * 
	 * return listvos; }
	 */

	/**
	 *
	 * @Title: readXls @Description: 2003excel�ļ��Ľ��� @param @param
	 *         dataFile @param @param fields @param @param
	 *         size @param @return @param @throws Exception �趨�ļ� @return
	 *         map<����,List<Map<String,String>>> �������� @author liubyc @throws
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map<String, List<Map<String, String>>> readXls(InputStream is, String[] fields) throws Exception {

		Map<String, List<Map<String, String>>> result = new HashMap<String, List<Map<String, String>>>();

		List<Map<String, String>> listvos = new ArrayList<Map<String, String>>();

		// ������Excel�������ļ�������
		HSSFWorkbook workbook = new HSSFWorkbook(is);
		// ��ȡsheetҳǩ������
		int sheetNumber = workbook.getNumberOfSheets();
		// ѭ�����е�Ҷǩ
		for (int k = 0; k < sheetNumber; k++) {
			// �����Թ�����sheet1�����á�
			HSSFSheet sheet = workbook.getSheetAt(k);
			String sheetName = sheet.getSheetName();

			// �������е�Ԫ�񣬶�ȡ��Ԫ��
			int row_num = sheet.getLastRowNum();
			// �����ǰҳǩ����Ϊ�ջ���ֻ�б�ͷ������excel��������
			if (row_num <= 0) {
				continue;
			}
			// ��ȡ����
			int cell_num = sheet.getRow(0).getPhysicalNumberOfCells();
			int fields_num = fields.length;
			if (cell_num != fields_num) {
				throw new Exception("���������ֶλ�ȱ���ֶΣ����飡");
			}
			HSSFRow r = null;
			Map map = null;
			boolean flag = true;
			// ��ͷ��һ���������洢
			ArrayList<String> headerlist = new ArrayList<String>();
			for (int i = 0; i <= row_num; i++) {
				map = new HashMap<String, String>();
				r = sheet.getRow(i);
				if (r == null) {
					continue;
				}
				flag = true;
				// ѭ��������Ԫ�񣬲��жϸ�����Ԫ�������
				for (int j = 0; j < cell_num; j++) {
					String value = getValue(r.getCell(j));
					// ����ǵ�һ����˵���Ǳ�ͷ
					if (i == 0) {
						// ��ͷ���Ӣ���ֶ���
						headerlist.add(fields[j]);
					} else {
						map.put(headerlist.get(j), value);
						if (!"".equals(value)) {
							flag = false;
						}
					}
				}
				if (flag) {
					continue;
				}
				listvos.add(map);
			}
			result.put(sheetName, listvos);
		}
		return result;

	}

	/**
	 *
	 * @Title: readXlsx @Description: 2007excel�ļ��Ľ��� @param @param filePath �ļ�·��
	 *         C:/Users/zhilijuan/Desktop/��������ģ��.xlsx @param @param size
	 *         ��ȡ�ڼ���Ҷǩ @param @return @param @throws Exception�趨�ļ� @return
	 *         map<����,List<Map<String,String>>> �������� @author liubyc @throws
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map<String, List<Map<String, String>>> readXlsx(InputStream is, String[] fields) throws Exception {

		Map<String, List<Map<String, String>>> result = new HashMap<String, List<Map<String, String>>>();

		List<Map<String, String>> lists = new ArrayList<Map<String, String>>();

		// ������Excel�������ļ�������
		XSSFWorkbook workbook = new XSSFWorkbook(is);
		// ��ȡsheetҳǩ������
		int sheetNumber = workbook.getNumberOfSheets();
		// ѭ�����е�Ҷǩ
		for (int k = 0; k < sheetNumber; k++) {
			// �����Թ�����sheet1�����á�
			XSSFSheet sheet = workbook.getSheetAt(k);
			String sheetName = sheet.getSheetName();
			// �������е�Ԫ�񣬶�ȡ��Ԫ��
			int row_num = sheet.getLastRowNum();
			// �����ǰҳǩ����Ϊ�ջ���ֻ�б�ͷ������excel��������
			if (row_num <= 0) {
				continue;
			}
			// ��ȡ����
			int cell_num = sheet.getRow(0).getPhysicalNumberOfCells();
			int fields_num = fields.length;
			if (cell_num != fields_num) {
				throw new Exception("���������ֶλ�ȱ���ֶΣ����飡");
			}
			// ��ͷ��һ���������洢
			ArrayList<String> headerlist = new ArrayList<String>();

			XSSFRow r = null;
			Map map = null;
			boolean flag = true;
			for (int i = 0; i <= row_num; i++) {
				map = new HashMap<String, String>();
				r = sheet.getRow(i);
				if (r == null) {
					continue;
				}
				flag = true;
				// ѭ��������Ԫ�񣬲��жϸ�����Ԫ�������
				for (int j = 0; j < cell_num; j++) {
					String value = getValue(r.getCell(j));
					// ����ǵ�һ����˵���Ǳ�ͷ
					if (i == 0) {
						headerlist.add(fields[j]);
					} else {
						map.put(headerlist.get(j), value);
						if (!"".equals(value)) {
							flag = false;
						}
					}
				}
				if (flag) {
					continue;
				}
				lists.add(map);
			}
			result.put(sheetName, lists);
		}
		return result;
	}

	// �жϵ�Ԫ�������
	public static String getValue(Cell cell) {
		String value = "";
		if (null == cell) {
			return value;
		}
		switch (cell.getCellType()) {
		// ��ֵ��
		case Cell.CELL_TYPE_NUMERIC:
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				// �����date������ ����ȡ��cell��dateֵ
				Date date = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				value = format.format(date);
				;
			} else {// ������
				BigDecimal big = new BigDecimal(String.valueOf(cell.getNumericCellValue()));
				// ��ѧ������
				value = big.toPlainString();
				// ���1234.0 ȥ�������.0
				if (null != value && !"".equals(value.trim())) {
					String[] item = value.split("[.]");
					if (1 < item.length && "0".equals(item[1])) {
						value = item[0];
					}
				}
			}
			break;
		// �ַ�������
		case Cell.CELL_TYPE_STRING:
			value = cell.getStringCellValue().toString();
			break;
		// ��������
		case Cell.CELL_TYPE_BOOLEAN:
			value = " " + cell.getBooleanCellValue();
			break;
		default:
			value = cell.getStringCellValue().toString();
		}
		if ("null".endsWith(value.trim())) {
			value = "";
		}
		return value;
	}

	// ����Excel���
	public static String createExcel(String path, List<String[]> fieldsList, List<String[]> fieldNamesList,
			List<List<Map<String, Object>>> lists, String[] sheetNames) throws Exception {
		String msg = "";
		// ����������
		HSSFWorkbook workbook = new HSSFWorkbook();

		// if (path == null || path.length() <= 0) {
		// return
		// nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("hq10002_0","0hq10002-0006")/*@res
		// "�ļ�·������Ϊ�գ�"*/;
		// }
		// if (fieldsList == null || fieldsList.size() <= 0) {
		// return
		// nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("hq10002_0","0hq10002-0007")/*@res
		// "�����ֶβ���Ϊ�գ�"*/;
		// }
		// if (fieldNamesList == null || fieldNamesList.size() <= 0) {
		// return
		// nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("hq10002_0","0hq10002-0007")/*@res
		// "�����ֶβ���Ϊ�գ�"*/;
		// }

		int sheetLen = sheetNames.length;
		HSSFCell cell = null;
		HSSFRow row = null;
		for (int index = 0; index < sheetLen; index++) {
			// �����µ�һҳ
			HSSFSheet sheet = workbook.createSheet(sheetNames[index]);
			// ����Ҫ��ʾ������,����һ����Ԫ�񣬵�һ������Ϊ�����꣬�ڶ�������Ϊ�����꣬����������Ϊ����
			List<Map<String, Object>> list = lists.get(index);
			String[] fields = fieldsList.get(index);
			String[] fieldNames = fieldNamesList.get(index);
			int field_len = fieldNames.length;
			row = sheet.createRow(0);// ��һ��
			for (int i = 0; i < field_len; i++) {
				cell = row.createCell(i);
				cell.setCellValue(fieldNames[i]);
			}

			if (list != null && list.size() > 0) {
				Map<String, Object> map = null;
				for (int i = 0; i < list.size(); i++) {
					map = list.get(i);
					row = sheet.createRow(i + 1);
					for (int j = 0; j < field_len; j++) {
						cell = row.createCell(j);
						cell.setCellValue(map.get(fields[j]) == null ? "" : map.get(fields[j]).toString());
					}
				}
			}
		}

		FileOutputStream os = new FileOutputStream(path);
		// �Ѵ���������д�뵽������У����ر������
		workbook.write(os);
		os.close();
		return msg;
	}

	// ����Excel���2010
	/**
	 * 
	 * @param path           ��Ҫ���ļ���ŵ�ʲôλ��
	 * @param listvos        ��Ҫ���������е�vo���� ���<����>
	 * @param fieldNamesList ÿ��ҶǩEXCEL�ֶ����ƣ�������˳������Map<����,List<�ֶ���������>>
	 * @param nameTOcodes    ÿ��Ҷǩ���ֶ��������ֶα����ϵӳ��Map<����,Map<�ֶ����ƣ��ֶα���>>
	 * @return
	 * @throws Exception
	 */
	public static String createExcelXlsx(String path, List<List> listvos) throws Exception {
		String msg = "";

		// ����������
		XSSFWorkbook workbook = new XSSFWorkbook();

		if (path == null || path.length() <= 0) {
			return "·��Ϊ�գ��޷�������";
		}
		if (listvos == null || listvos.size() <= 0) {
			return "��������Ϊ�գ��޷�������";
		}

		HashMap<String, String> tablecodeTOName = new HashMap<String, String>();
		Map<String, Map<String, String>> nameTOcodes = new HashMap<String, Map<String, String>>();
		Map<String, List<String>> sheetListNames = new HashMap<String, List<String>>();

		// ��Ҫ�洢���Ӧ�ж���Щ�����ڽ���xmlʱȡ��

		// ����excel��ģ��
		String errormsg = createExcelTemplateForXml(workbook, nameTOcodes, tablecodeTOName, sheetListNames);
		if (errormsg != null && !"".equals(errormsg)) {
			return errormsg;
		}

		// ������� ֮��������������������Ϊ�д�������xml����ʱ������ֵ��Ҫѭ�� column*row��

		FileOutputStream os = new FileOutputStream(path);
		// �Ѵ���������д�뵽������У����ر������
		workbook.write(os);
		os.close();
		return msg;
	}

	/**
	 * 
	 * @param workbook        excelģ��
	 * @param nameTOcodes     ÿ����� �ֶε� ���ƺͱ����ֵ�� ӳ��Map<����,Map<�ֶ����ƣ��ֶα���>>
	 *                        �ڶ���map������ӹ��ģ�����ֵΪnew hashmap����
	 * @param tablecodeTOName HashMap<�����, ������>
	 * @return
	 * @throws Exception
	 */
	public static String createExcelTemplateForXml(XSSFWorkbook workbook, Map<String, Map<String, String>> nameTOcodes,
			HashMap<String, String> tablecodeTOName, Map<String, List<String>> sheetListNames) throws Exception {

		// String patch="C:/Users/zhilijuan/Desktop/excelout.xml";
		// ����������
		try {
			InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("excelOutTmplate.xml");
			// ����һ���ĵ���������
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			// ͨ����������DocumentBuilder����
			DocumentBuilder builder = factory.newDocumentBuilder();
			// ��ָ��file�����ݽ��� ����һ��Document�Ķ���
			// Document doc=builder.parse("file:///" + patch);
			Document doc = builder.parse(in);
			Element element = doc.getDocumentElement();// ��ȡ��Ԫ��
			// System.out.println(element);
			NodeList nodeList = doc.getElementsByTagName("table");
			// System.out.println(nodeList.getLength());
			int len = nodeList.getLength();
			for (int i = 0; i < len; i++) {
				Node node = nodeList.item(i);
				String tablecode = node.getAttributes().getNamedItem("tablecode").getNodeValue();
				String tablename = node.getAttributes().getNamedItem("tablename").getNodeValue();
				if (tablecode == null || tablename == null) {
					return "XML��ʽ���ԣ����е��������ݶ���Ҫά����";
				}
				// ��������ͱ�����
				tablecodeTOName.put(tablecode, tablename);

				// �������ӽڵ㣬����ͳ�Ƶı�����
				if (!nameTOcodes.containsKey(tablecode)) {
					continue;
				}
				// ��װÿ��Ҷǩ���ݵļ�ֵ��
				Map<String, String> nameTocode = nameTOcodes.get(tablecode);

				// ���ÿ��Ҷǩ �ֶε�˳��
				List<String> sheetListName = sheetListNames.get(tablecode);

				// ����xml����д��Ҷǩ����
				XSSFSheet sheet = workbook.createSheet(tablename);
				XSSFRow row = sheet.createRow(0);// ��һ��
				int len2 = nodeList.item(i).getChildNodes().getLength();
				int rownum = 0;
				for (int j = 0; j < len2; j++) {
					Node node1 = nodeList.item(i).getChildNodes().item(j);
					if (node1.getNodeType() == 1) {
						String nodeName = node1.getNodeName();
						String content = node1.getFirstChild().getNodeValue();
						if (nodeName == null || content == null) {
							return "XML��ʽ���ԣ����е��������ݶ���Ҫά����";
						}
						// ����ֵ
						nameTocode.put(content, nodeName);

						// ����ֵ (����)
						sheetListName.add(content);

						// ����ÿ��Ҷǩ������
						XSSFCell cell = row.createCell(rownum);
						cell.setCellValue(content);

						rownum++;
					}
				}
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @param response
	 * @param listvo       ����ͨ���������
	 * @param templateName ģ���ļ�����
	 * @param datafilename ����������ļ�����
	 * @param startrow     �ӵڼ��п�ʼ��������
	 * @param sepcilValues �����⴦��ĵ�Ԫ�� ����Ϊ String[3] �ֱ�洢 �кš��кš�����ĵ�Ԫ������
	 * @throws BusinessException
	 */

	public static void excelDataDownload(HttpServletResponse response, List listvo, String templateName,
			String datafilename, Integer startrow)  {
		InputStream fs;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			Date d = new Date();
			String str = sdf.format(d);
			datafilename = datafilename + str;
//        datafilename=new String(datafilename.getBytes("GBK"));
			fs = ExcelUtils.class.getClassLoader().getResourceAsStream(templateName + ".xls");
			POIFSFileSystem ps = new POIFSFileSystem(fs); // ʹ��POI�ṩ�ķ����õ�excel����Ϣ
			HSSFWorkbook wb = new HSSFWorkbook(ps);

			HSSFSheet sheet = wb.getSheetAt(0); // ��ȡ����������Ϊһ��excel�����ж��������
			HSSFRow row = sheet.getRow(0);

			int begainRow = 0;
			if ("".equals(row) || row == null) {
				begainRow = 0;
			} else {
				begainRow = sheet.getLastRowNum();
				begainRow = begainRow + 1;
			}
			// ���ָ����ʼ��
			if (startrow != null) {
				begainRow = startrow;
			}
			response.setContentType("application/vnd.ms-excel");
			response.addHeader("Content-Disposition", (new StringBuilder()).append("attachment;filename=")
					.append(datafilename).append(".xls").toString());
			OutputStream out = response.getOutputStream();
			int endrow = sheet.getLastRowNum();
//        sheet.shiftRows(endrow, endrow, listvo.size());
			sheet.shiftRows(begainRow, endrow + 1, listvo.size());
			int i = 0;
			HSSFFont fontDefault = wb.createFont();
			fontDefault.setFontHeightInPoints((short) 10);
			fontDefault.setFontName("΢���ź�");

			HSSFFont font = wb.createFont();
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			font.setFontHeightInPoints((short) 10);
			font.setFontName("΢���ź�");

			HSSFCellStyle cellStyle1 = wb.createCellStyle();
			cellStyle1.setBorderBottom(HSSFCellStyle.BORDER_THIN); // �±߿�
			cellStyle1.setBorderLeft(HSSFCellStyle.BORDER_THIN);// ��߿�
			cellStyle1.setBorderTop(HSSFCellStyle.BORDER_THIN);// �ϱ߿�
			cellStyle1.setBorderRight(HSSFCellStyle.BORDER_THIN);// �ұ߿�
			cellStyle1.setAlignment(HSSFCellStyle.ALIGN_RIGHT);// ����
			cellStyle1.setFont(fontDefault);

			HSSFCellStyle cellStyle2 = wb.createCellStyle();
			cellStyle2.setBorderBottom(HSSFCellStyle.BORDER_THIN); // �±߿�
			cellStyle2.setBorderLeft(HSSFCellStyle.BORDER_THIN);// ��߿�
			cellStyle2.setBorderTop(HSSFCellStyle.BORDER_THIN);// �ϱ߿�
			cellStyle2.setBorderRight(HSSFCellStyle.BORDER_THIN);// �ұ߿�
			cellStyle2.setAlignment(HSSFCellStyle.ALIGN_LEFT); // ����
			cellStyle2.setFont(fontDefault);

			HSSFCellStyle cellStyle3 = wb.createCellStyle();
			cellStyle3.setBorderBottom(HSSFCellStyle.BORDER_THIN); // �±߿�
			cellStyle3.setBorderLeft(HSSFCellStyle.BORDER_THIN);// ��߿�
			cellStyle3.setBorderTop(HSSFCellStyle.BORDER_THIN);// �ϱ߿�
			cellStyle3.setBorderRight(HSSFCellStyle.BORDER_THIN);// �ұ߿�
			cellStyle3.setAlignment(HSSFCellStyle.ALIGN_RIGHT); // ����
			cellStyle3.setFont(font);

			HSSFCellStyle cellStyle4 = wb.createCellStyle();
			cellStyle4.setBorderBottom(HSSFCellStyle.BORDER_THIN); // �±߿�
			cellStyle4.setBorderLeft(HSSFCellStyle.BORDER_THIN);// ��߿�
			cellStyle4.setBorderTop(HSSFCellStyle.BORDER_THIN);// �ϱ߿�
			cellStyle4.setBorderRight(HSSFCellStyle.BORDER_THIN);// �ұ߿�
			cellStyle4.setAlignment(HSSFCellStyle.ALIGN_LEFT); // ����
			cellStyle4.setFont(font);

			HSSFCellStyle cellStyle5 = wb.createCellStyle();
			cellStyle5.setBorderBottom(HSSFCellStyle.BORDER_THIN); // �±߿�
			cellStyle5.setBorderLeft(HSSFCellStyle.BORDER_THIN);// ��߿�
			cellStyle5.setBorderTop(HSSFCellStyle.BORDER_THIN);// �ϱ߿�
			cellStyle5.setBorderRight(HSSFCellStyle.BORDER_THIN);// �ұ߿�
			cellStyle5.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// ��ֱ����
			cellStyle5.setAlignment(HSSFCellStyle.ALIGN_CENTER);// ˮƽ����
			cellStyle5.setFont(fontDefault);

			String idx_name = "";// ҵ������ ÿ�κϲ���ȶԵ�ֵ
			List<Integer> merger = new ArrayList();// ÿ�κϲ��Ŀ�ʼ���к�
			while (i < listvo.size()) {
				Boolean isSum = false;
				Boolean isCportTile = false;
				// ������
				row = sheet.getRow((short) (begainRow + i));
				if (row == null) {
					row = sheet.createRow((short) (begainRow + i)); // �������кź�׷������
				}
				Map<Integer, Object> data = (Map) listvo.get(i);

				if (templateName.equals("A")) {// c��ڶ���ҵ�����ߺϲ�
					if (i == 0) {// ��ʼ������
						idx_name = (String) data.get(0);
						merger.add(begainRow + i);
					} else if (((idx_name != null)
							&& (!idx_name.equals((String) (data.get(0) == null ? "" : data.get(0)))))
							|| (idx_name == null && idx_name != data.get(0))) {
						merger.add(begainRow + i);// ����Ⱦ�Ҫ��¼�µĺϲ���
						idx_name = (String) data.get(0);// �ȶԵ�ֵҪ��ɳ���ֵ
					}
				}

				if (templateName.equals("C-1") && ((String) data.get(7)).length() < 12) {
					isCportTile = true; // C��һ����Ŀ�Ͷ�����Ŀ�Ӵ�
				}
				if (data.values().contains("�ϼ�") || data.values().toString().contains("С��")) {
					isSum = true;
				}
				for (Map.Entry<Integer, Object> entry : data.entrySet()) {
					if ((templateName.equals("C-1") && (entry.getKey() == 7))) {
						// c��������в����
						continue;
					}
//				System.out.println("key:"+entry.getKey()+","+"value:"+entry.getValue());
					HSSFCell cell = row.getCell(entry.getKey());

					if (cell == null) {
						cell = row.createCell(entry.getKey());
					}

					if (entry.getValue() instanceof BigDecimal) {
						if (((templateName.equals("C-1") || (templateName.equals("A")))) && (entry.getKey() == 0)) {
							if (isSum) {
								cell.setCellStyle(cellStyle4);
							} else {
								cell.setCellStyle(cellStyle2);
							}
							if (isCportTile && entry.getKey() == 1) {
								cell.setCellStyle(cellStyle4);
							}
							cell.setCellValue(entry.getValue() == null ? "" : entry.getValue().toString());
						} else {
							if (isSum) {
								cell.setCellStyle(cellStyle3);
							} else {
								cell.setCellStyle(cellStyle1);
							}

							DecimalFormat df = new DecimalFormat("##,##0.00");
							cell.setCellValue(df.format(((BigDecimal) entry.getValue()).doubleValue()));
							if (entry.getValue() == null) {
								cell.setCellValue(0);
							}
						}

					} else {
						if (isSum) {
							cell.setCellStyle(cellStyle4);
						} else {
							cell.setCellStyle(cellStyle2);
						}
						if (isCportTile && entry.getKey() == 1) {
							cell.setCellStyle(cellStyle4);
						}
//					 if(templateName.equals("A")&&entry.getKey()==1){
//						 HSSFCellStyle cellStyle =  cell.getCellStyle();
//						 cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER); // ����
//						 cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//��ֱ
//					 }
						cell.setCellValue(entry.getValue() == null ? "" : entry.getValue().toString());
					}

				}

				i++;
			}
			if (templateName.equals("A")) {// A��ϲ�ҵ������
				for (int k = 0; k < merger.size(); k++) {
					if (k < merger.size() - 1) {

						CellRangeAddress cra = new CellRangeAddress(merger.get(k), merger.get(k + 1) - 1, 0, 0);
						sheet.addMergedRegion(cra);
						if (sheet.getRow(merger.get(k)) != null) {
							sheet.getRow(merger.get(k)).getCell(0).setCellStyle(cellStyle5);
						}

					}

				}
//			 for (int m = 0;m<sheet.getNumMergedRegions();m++){
//				 if(sheet.getMergedRegion(i)!=null){
//					 sheet.getMergedRegion(i).s ; 
//				 }
//				
//			 }

			}

			wb.write(out);
			out.flush();
			out.close();
		} catch (Exception e) {

		}
	}

	public static void dealSpecailExcelData(HSSFSheet sheet, List<String[]> values) {
		if (values != null) {
			for (String[] value : values) {
				if (value == null || value.length != 3) {
					return;
				}
				String rowStr = value[0];
				String columnStr = value[1];
				String data = value[2];
				HSSFRow row = sheet.getRow(Integer.parseInt(rowStr == null ? "0" : rowStr));// �õ���
				if (null == row) {
					row = sheet.createRow(Integer.parseInt(columnStr == null ? "0" : columnStr));
				}
				HSSFCell cell = row.getCell(Integer.parseInt(columnStr == null ? "0" : columnStr));// �õ���
				if (null == cell) {
					cell = row.createCell(Integer.parseInt(columnStr == null ? "0" : columnStr));
				}
				cell.setCellValue(data);
			}
		}
	}

	/**
	 * 
	 * @param response
	 * @param listvo       ����ͨ���������
	 * @param templateName ģ���ļ�����
	 * @param datafilename ����������ļ�����
	 * @param startrow     �ӵڼ��п�ʼ��������
	 * @param sepcilValues �����⴦��ĵ�Ԫ�� ����Ϊ String[3] �ֱ�洢 �кš��кš�����ĵ�Ԫ������
	 * @throws BusinessException
	 */

	public static void excelDataDownload(HttpServletResponse response, List listvo1, List listvo2, String templateName,
			String datafilename, Integer startrow, List<String[]> sepcilValues) {
		InputStream fs;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			Date d = new Date();
			String str = sdf.format(d);
			datafilename = datafilename + str;
//          datafilename=new String(datafilename.getBytes("GBK"));
			fs = ExcelUtils.class.getClassLoader().getResourceAsStream(templateName + ".xls");
			POIFSFileSystem ps = new POIFSFileSystem(fs); // ʹ��POI�ṩ�ķ����õ�excel����Ϣ
			HSSFWorkbook wb = new HSSFWorkbook(ps);

			HSSFSheet sheet = wb.getSheetAt(0); // ��ȡ����������Ϊһ��excel�����ж��������
			HSSFRow row = sheet.getRow(0);

			int begainRow = 0;
			if ("".equals(row) || row == null) {
				begainRow = 0;
			} else {
				begainRow = sheet.getLastRowNum();
				begainRow = begainRow + 1;
			}
			// ���ָ����ʼ��
			if (startrow != null) {
				begainRow = startrow;
			}
			response.setContentType("application/vnd.ms-excel");
			response.addHeader("Content-Disposition", (new StringBuilder()).append("attachment;filename=")
					.append(datafilename).append(".xls").toString());
			OutputStream out = response.getOutputStream();
			int endrow1 = sheet.getLastRowNum();
			if (!listvo1.isEmpty()) {
				sheet.shiftRows(begainRow, endrow1+1, listvo1.size());
			}
			int i = 0;
			HSSFFont fontDefault = wb.createFont();
			fontDefault.setFontHeightInPoints((short) 10);
			fontDefault.setFontName("΢���ź�");

			HSSFFont font = wb.createFont();
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			font.setFontHeightInPoints((short) 10);
			font.setFontName("΢���ź�");

			HSSFCellStyle cellStyle1 = wb.createCellStyle();
			cellStyle1.setBorderBottom(HSSFCellStyle.BORDER_THIN); // �±߿�
			cellStyle1.setBorderLeft(HSSFCellStyle.BORDER_THIN);// ��߿�
			cellStyle1.setBorderTop(HSSFCellStyle.BORDER_THIN);// �ϱ߿�
			cellStyle1.setBorderRight(HSSFCellStyle.BORDER_THIN);// �ұ߿�
			cellStyle1.setAlignment(HSSFCellStyle.ALIGN_RIGHT);// ����
			cellStyle1.setFont(fontDefault);

			HSSFCellStyle cellStyle2 = wb.createCellStyle();
			cellStyle2.setBorderBottom(HSSFCellStyle.BORDER_THIN); // �±߿�
			cellStyle2.setBorderLeft(HSSFCellStyle.BORDER_THIN);// ��߿�
			cellStyle2.setBorderTop(HSSFCellStyle.BORDER_THIN);// �ϱ߿�
			cellStyle2.setBorderRight(HSSFCellStyle.BORDER_THIN);// �ұ߿�
			cellStyle2.setAlignment(HSSFCellStyle.ALIGN_LEFT); // ����
			cellStyle2.setFont(fontDefault);

			HSSFCellStyle cellStyle3 = wb.createCellStyle();
			cellStyle3.setBorderBottom(HSSFCellStyle.BORDER_THIN); // �±߿�
			cellStyle3.setBorderLeft(HSSFCellStyle.BORDER_THIN);// ��߿�
			cellStyle3.setBorderTop(HSSFCellStyle.BORDER_THIN);// �ϱ߿�
			cellStyle3.setBorderRight(HSSFCellStyle.BORDER_THIN);// �ұ߿�
			cellStyle3.setAlignment(HSSFCellStyle.ALIGN_RIGHT); // ����
			cellStyle3.setFont(font);
			
			HSSFCellStyle cellStyle4 = wb.createCellStyle();
			cellStyle4.setBorderBottom(HSSFCellStyle.BORDER_THIN); // �±߿�
			cellStyle4.setBorderLeft(HSSFCellStyle.BORDER_THIN);// ��߿�
			cellStyle4.setBorderTop(HSSFCellStyle.BORDER_THIN);// �ϱ߿�
			cellStyle4.setBorderRight(HSSFCellStyle.BORDER_THIN);// �ұ߿�
			cellStyle4.setAlignment(HSSFCellStyle.ALIGN_LEFT); // ����
			cellStyle4.setFont(font);
			

			HSSFCellStyle cellStyle5 = wb.createCellStyle();
			cellStyle5.setBorderBottom(HSSFCellStyle.BORDER_THIN); // �±߿�
			cellStyle5.setBorderLeft(HSSFCellStyle.BORDER_THIN);// ��߿�
			cellStyle5.setBorderTop(HSSFCellStyle.BORDER_THIN);// �ϱ߿�
			cellStyle5.setBorderRight(HSSFCellStyle.BORDER_THIN);// �ұ߿�
			cellStyle5.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// ��ֱ����
			cellStyle5.setAlignment(HSSFCellStyle.ALIGN_CENTER);// ˮƽ����
			cellStyle5.setFont(fontDefault);

			String idx_name = "";// ҵ������ ÿ�κϲ���ȶԵ�ֵ
			List<Integer> merger = new ArrayList();// ÿ�κϲ��Ŀ�ʼ���к�
			while (i < listvo1.size()) {
				//�����п�
				sheet.setColumnWidth(begainRow + i, 256*30+184);
				Boolean isSum = false;
				Boolean isCportTile = false;
				// ������
				row = sheet.getRow((short) (begainRow + i));
				if (row == null) {
					row = sheet.createRow((short) (begainRow + i)); // �������кź�׷������
				}
				
					Map<Integer, Object> data = (Map) listvo1.get(i);
					for (Map.Entry<Integer, Object> entry : data.entrySet()) {
						System.out.println("key1:"+entry.getKey()+","+"value1:"+entry.getValue());
						HSSFCell cell = row.getCell(entry.getKey());
						
						if (cell == null) {
							cell = row.createCell(entry.getKey());
						}

						if (entry.getValue() instanceof BigDecimal) {
							if (((templateName.equals("C-1") || (templateName.equals("A")))) && (entry.getKey() == 0)) {
								if (isSum) {
									cell.setCellStyle(cellStyle4);
								} else {
									cell.setCellStyle(cellStyle2);
								}
								if (isCportTile && entry.getKey() == 1) {
									cell.setCellStyle(cellStyle4);
								}
								cell.setCellValue(entry.getValue() == null ? "" : entry.getValue().toString());
							} else {
								if (isSum) {
									cell.setCellStyle(cellStyle3);
								} else {
									cell.setCellStyle(cellStyle1);
								}
								DecimalFormat df = new DecimalFormat("##,##0.00");
								cell.setCellValue(df.format(((BigDecimal) entry.getValue()).doubleValue()));
								if (entry.getValue() == null) {
									cell.setCellValue(0);
								}
							}

						} else {
							if (isSum) {
								cell.setCellStyle(cellStyle4);
							} else {
								cell.setCellStyle(cellStyle2);
							}
							if (isCportTile && entry.getKey() == 1) {
								cell.setCellStyle(cellStyle4);
							}
							cell.setCellValue(entry.getValue() == null ? "" : entry.getValue().toString());
						}

					}

					i++;
			}
			
			int endrow2 = sheet.getLastRowNum();
			int begainRow2 = begainRow + 2 + i;
			if (!listvo2.isEmpty()) {
				sheet.shiftRows(begainRow2, endrow2 + 1, listvo2.size());
			}
			int j = 0;
			idx_name = "";// ҵ������ ÿ�κϲ���ȶԵ�ֵ
			merger = new ArrayList();// ÿ�κϲ��Ŀ�ʼ���к�
			while (j < listvo2.size()) {
				//�����п�
				sheet.setColumnWidth(begainRow2 + j, 256*30+184);
				Boolean isSum = false;
				Boolean isCportTile = false;
				// ������
				row = sheet.getRow((short) (begainRow2 + j));
				if (row == null) {
					row = sheet.createRow((short) (begainRow2 + j)); // �������кź�׷������
				}
				if (!listvo2.isEmpty()) {
					Map<Integer, Object> data1 = (Map) listvo2.get(j);
					for (Map.Entry<Integer, Object> entry : data1.entrySet()) {
						System.out.println("2key:"+entry.getKey()+","+"2value:"+entry.getValue());
						HSSFCell cell = row.getCell(entry.getKey());

						if (cell == null) {
							cell = row.createCell(entry.getKey());
						}

						if (entry.getValue() instanceof BigDecimal) {
							if (((templateName.equals("C-1") || (templateName.equals("A")))) && (entry.getKey() == 0)) {
								if (isSum) {
									cell.setCellStyle(cellStyle4);
								} else {
									cell.setCellStyle(cellStyle2);
								}
								if (isCportTile && entry.getKey() == 1) {
									cell.setCellStyle(cellStyle4);
								}
								cell.setCellValue(entry.getValue() == null ? "" : entry.getValue().toString());
							} else {
								if (isSum) {
									cell.setCellStyle(cellStyle3);
								} else {
									cell.setCellStyle(cellStyle1);
								}
								DecimalFormat df = new DecimalFormat("##,##0.00");
								cell.setCellValue(df.format(((BigDecimal) entry.getValue()).doubleValue()));
								if (entry.getValue() == null) {
									cell.setCellValue(0);
								}
							}

						} else {
							if (isSum) {
								cell.setCellStyle(cellStyle4);
							} else {
								cell.setCellStyle(cellStyle2);
							}
							if (isCportTile && entry.getKey() == 1) {
								cell.setCellStyle(cellStyle4);
							}
							cell.setCellValue(entry.getValue() == null ? "" : entry.getValue().toString());
						}

					}
					j++;
				}
			}
			dealSpecailExcelData(sheet, sepcilValues);
			wb.write(out);
			out.flush();
			out.close();
		} catch (IOException e) {

		}
	}
	
	public static String ClobToString(Clob clob) throws SQLException, IOException {
	      String reString = "";
	      Reader is = clob.getCharacterStream();// �õ���
	      BufferedReader br = new BufferedReader(is);
	      String s = br.readLine();
	      StringBuffer sb = new StringBuffer();
	      while (s != null) {// ִ��ѭ�����ַ���ȫ��ȡ����ֵ��StringBuffer��StringBufferת��STRING
	       sb.append(s);
	       s = br.readLine();
	      }
	      reString = sb.toString();
	      if(br!=null){
	          br.close();
	      }
	      if(is!=null){
	          is.close();
	      }
	      return reString;
	}
	
}
