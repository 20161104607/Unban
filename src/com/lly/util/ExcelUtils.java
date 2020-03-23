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
	 * @param filePath 文件路径名称
	 * @param size     获取某个页签的内容
	 * @return map<表名,List<Map<字段名,字段值>>> 返回类型
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
	 * @Title: readXls @Description: 2003excel文件的解析 @param @param
	 *         dataFile @param @param fields @param @param
	 *         size @param @return @param @throws Exception 设定文件 @return
	 *         map<表名,List<Map<String,String>>> 返回类型 @author liubyc @throws
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map<String, List<Map<String, String>>> readXls(InputStream is, String[] fields) throws Exception {

		Map<String, List<Map<String, String>>> result = new HashMap<String, List<Map<String, String>>>();

		List<Map<String, String>> listvos = new ArrayList<Map<String, String>>();

		// 创建对Excel工作簿文件的引用
		HSSFWorkbook workbook = new HSSFWorkbook(is);
		// 获取sheet页签总数量
		int sheetNumber = workbook.getNumberOfSheets();
		// 循环所有的叶签
		for (int k = 0; k < sheetNumber; k++) {
			// 创建对工作表sheet1的引用。
			HSSFSheet sheet = workbook.getSheetAt(k);
			String sheetName = sheet.getSheetName();

			// 遍历所有单元格，读取单元格
			int row_num = sheet.getLastRowNum();
			// 如果当前页签行数为空或者只有表头，则不做excel解析操作
			if (row_num <= 0) {
				continue;
			}
			// 获取列数
			int cell_num = sheet.getRow(0).getPhysicalNumberOfCells();
			int fields_num = fields.length;
			if (cell_num != fields_num) {
				throw new Exception("存在冗余字段或缺少字段，请检查！");
			}
			HSSFRow r = null;
			Map map = null;
			boolean flag = true;
			// 表头第一行列描述存储
			ArrayList<String> headerlist = new ArrayList<String>();
			for (int i = 0; i <= row_num; i++) {
				map = new HashMap<String, String>();
				r = sheet.getRow(i);
				if (r == null) {
					continue;
				}
				flag = true;
				// 循环各个单元格，并判断各个单元格的类型
				for (int j = 0; j < cell_num; j++) {
					String value = getValue(r.getCell(j));
					// 如果是第一行则说明是表头
					if (i == 0) {
						// 表头添加英文字段名
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
	 * @Title: readXlsx @Description: 2007excel文件的解析 @param @param filePath 文件路径
	 *         C:/Users/zhilijuan/Desktop/渠道导入模板.xlsx @param @param size
	 *         读取第几个叶签 @param @return @param @throws Exception设定文件 @return
	 *         map<表名,List<Map<String,String>>> 返回类型 @author liubyc @throws
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map<String, List<Map<String, String>>> readXlsx(InputStream is, String[] fields) throws Exception {

		Map<String, List<Map<String, String>>> result = new HashMap<String, List<Map<String, String>>>();

		List<Map<String, String>> lists = new ArrayList<Map<String, String>>();

		// 创建对Excel工作簿文件的引用
		XSSFWorkbook workbook = new XSSFWorkbook(is);
		// 获取sheet页签总数量
		int sheetNumber = workbook.getNumberOfSheets();
		// 循环所有的叶签
		for (int k = 0; k < sheetNumber; k++) {
			// 创建对工作表sheet1的引用。
			XSSFSheet sheet = workbook.getSheetAt(k);
			String sheetName = sheet.getSheetName();
			// 遍历所有单元格，读取单元格
			int row_num = sheet.getLastRowNum();
			// 如果当前页签行数为空或者只有表头，则不做excel解析操作
			if (row_num <= 0) {
				continue;
			}
			// 获取列数
			int cell_num = sheet.getRow(0).getPhysicalNumberOfCells();
			int fields_num = fields.length;
			if (cell_num != fields_num) {
				throw new Exception("存在冗余字段或缺少字段，请检查！");
			}
			// 表头第一行列描述存储
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
				// 循环各个单元格，并判断各个单元格的类型
				for (int j = 0; j < cell_num; j++) {
					String value = getValue(r.getCell(j));
					// 如果是第一行则说明是表头
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

	// 判断单元格的类型
	public static String getValue(Cell cell) {
		String value = "";
		if (null == cell) {
			return value;
		}
		switch (cell.getCellType()) {
		// 数值型
		case Cell.CELL_TYPE_NUMERIC:
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				// 如果是date类型则 ，获取该cell的date值
				Date date = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				value = format.format(date);
				;
			} else {// 纯数字
				BigDecimal big = new BigDecimal(String.valueOf(cell.getNumericCellValue()));
				// 科学计数法
				value = big.toPlainString();
				// 解决1234.0 去掉后面的.0
				if (null != value && !"".equals(value.trim())) {
					String[] item = value.split("[.]");
					if (1 < item.length && "0".equals(item[1])) {
						value = item[0];
					}
				}
			}
			break;
		// 字符串类型
		case Cell.CELL_TYPE_STRING:
			value = cell.getStringCellValue().toString();
			break;
		// 布尔类型
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

	// 创建Excel表格
	public static String createExcel(String path, List<String[]> fieldsList, List<String[]> fieldNamesList,
			List<List<Map<String, Object>>> lists, String[] sheetNames) throws Exception {
		String msg = "";
		// 创建工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();

		// if (path == null || path.length() <= 0) {
		// return
		// nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("hq10002_0","0hq10002-0006")/*@res
		// "文件路径不能为空！"*/;
		// }
		// if (fieldsList == null || fieldsList.size() <= 0) {
		// return
		// nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("hq10002_0","0hq10002-0007")/*@res
		// "导出字段不能为空！"*/;
		// }
		// if (fieldNamesList == null || fieldNamesList.size() <= 0) {
		// return
		// nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("hq10002_0","0hq10002-0007")/*@res
		// "导出字段不能为空！"*/;
		// }

		int sheetLen = sheetNames.length;
		HSSFCell cell = null;
		HSSFRow row = null;
		for (int index = 0; index < sheetLen; index++) {
			// 创建新的一页
			HSSFSheet sheet = workbook.createSheet(sheetNames[index]);
			// 创建要显示的内容,创建一个单元格，第一个参数为列坐标，第二个参数为行坐标，第三个参数为内容
			List<Map<String, Object>> list = lists.get(index);
			String[] fields = fieldsList.get(index);
			String[] fieldNames = fieldNamesList.get(index);
			int field_len = fieldNames.length;
			row = sheet.createRow(0);// 第一行
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
		// 把创建的内容写入到输出流中，并关闭输出流
		workbook.write(os);
		os.close();
		return msg;
	}

	// 创建Excel表格2010
	/**
	 * 
	 * @param path           需要把文件存放到什么位置
	 * @param listvos        需要导出的所有的vo集合 多表<多行>
	 * @param fieldNamesList 每个叶签EXCEL字段名称，按排列顺序排列Map<表名,List<字段名称排列>>
	 * @param nameTOcodes    每个叶签中字段名称与字段编码关系映射Map<表名,Map<字段名称，字段编码>>
	 * @return
	 * @throws Exception
	 */
	public static String createExcelXlsx(String path, List<List> listvos) throws Exception {
		String msg = "";

		// 创建工作薄
		XSSFWorkbook workbook = new XSSFWorkbook();

		if (path == null || path.length() <= 0) {
			return "路径为空，无法导出！";
		}
		if (listvos == null || listvos.size() <= 0) {
			return "导出内容为空，无法导出！";
		}

		HashMap<String, String> tablecodeTOName = new HashMap<String, String>();
		Map<String, Map<String, String>> nameTOcodes = new HashMap<String, Map<String, String>>();
		Map<String, List<String>> sheetListNames = new HashMap<String, List<String>>();

		// 主要存储这次应判断哪些表，用于解析xml时取数

		// 生成excel空模板
		String errormsg = createExcelTemplateForXml(workbook, nameTOcodes, tablecodeTOName, sheetListNames);
		if (errormsg != null && !"".equals(errormsg)) {
			return errormsg;
		}

		// 遍历多表 之所以这样做，遍历次数为行次数，若xml解析时，放入值需要循环 column*row次

		FileOutputStream os = new FileOutputStream(path);
		// 把创建的内容写入到输出流中，并关闭输出流
		workbook.write(os);
		os.close();
		return msg;
	}

	/**
	 * 
	 * @param workbook        excel模板
	 * @param nameTOcodes     每个表的 字段的 名称和编码键值对 映射Map<表名,Map<字段名称，字段编码>>
	 *                        第二个map是这里加工的，传入值为new hashmap即可
	 * @param tablecodeTOName HashMap<表编码, 表名称>
	 * @return
	 * @throws Exception
	 */
	public static String createExcelTemplateForXml(XSSFWorkbook workbook, Map<String, Map<String, String>> nameTOcodes,
			HashMap<String, String> tablecodeTOName, Map<String, List<String>> sheetListNames) throws Exception {

		// String patch="C:/Users/zhilijuan/Desktop/excelout.xml";
		// 创建工作薄
		try {
			InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("excelOutTmplate.xml");
			// 创建一个文档构建工厂
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			// 通过工厂生产DocumentBuilder对象
			DocumentBuilder builder = factory.newDocumentBuilder();
			// 将指定file的内容解析 返回一个Document的对象
			// Document doc=builder.parse("file:///" + patch);
			Document doc = builder.parse(in);
			Element element = doc.getDocumentElement();// 获取根元素
			// System.out.println(element);
			NodeList nodeList = doc.getElementsByTagName("table");
			// System.out.println(nodeList.getLength());
			int len = nodeList.getLength();
			for (int i = 0; i < len; i++) {
				Node node = nodeList.item(i);
				String tablecode = node.getAttributes().getNamedItem("tablecode").getNodeValue();
				String tablename = node.getAttributes().getNamedItem("tablename").getNodeValue();
				if (tablecode == null || tablename == null) {
					return "XML格式不对，所有的属性内容都需要维护！";
				}
				// 放入表编码和表名称
				tablecodeTOName.put(tablecode, tablename);

				// 如果这个子节点，不在统计的表名中
				if (!nameTOcodes.containsKey(tablecode)) {
					continue;
				}
				// 组装每个叶签内容的键值对
				Map<String, String> nameTocode = nameTOcodes.get(tablecode);

				// 存放每个叶签 字段的顺序
				List<String> sheetListName = sheetListNames.get(tablecode);

				// 根据xml名称写入叶签名称
				XSSFSheet sheet = workbook.createSheet(tablename);
				XSSFRow row = sheet.createRow(0);// 第一行
				int len2 = nodeList.item(i).getChildNodes().getLength();
				int rownum = 0;
				for (int j = 0; j < len2; j++) {
					Node node1 = nodeList.item(i).getChildNodes().item(j);
					if (node1.getNodeType() == 1) {
						String nodeName = node1.getNodeName();
						String content = node1.getFirstChild().getNodeValue();
						if (nodeName == null || content == null) {
							return "XML格式不对，所有的属性内容都需要维护！";
						}
						// 放入值
						nameTocode.put(content, nodeName);

						// 放入值 (名称)
						sheetListName.add(content);

						// 创建每个叶签的列名
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
	 * @param listvo       需普通处理的数据
	 * @param templateName 模板文件名称
	 * @param datafilename 输出的数据文件名称
	 * @param startrow     从第几列开始填入数据
	 * @param sepcilValues 需特殊处理的单元格 内容为 String[3] 分别存储 行号、列号、具体的单元格数据
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
			POIFSFileSystem ps = new POIFSFileSystem(fs); // 使用POI提供的方法得到excel的信息
			HSSFWorkbook wb = new HSSFWorkbook(ps);

			HSSFSheet sheet = wb.getSheetAt(0); // 获取到工作表，因为一个excel可能有多个工作表
			HSSFRow row = sheet.getRow(0);

			int begainRow = 0;
			if ("".equals(row) || row == null) {
				begainRow = 0;
			} else {
				begainRow = sheet.getLastRowNum();
				begainRow = begainRow + 1;
			}
			// 如果指定开始列
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
			fontDefault.setFontName("微软雅黑");

			HSSFFont font = wb.createFont();
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			font.setFontHeightInPoints((short) 10);
			font.setFontName("微软雅黑");

			HSSFCellStyle cellStyle1 = wb.createCellStyle();
			cellStyle1.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
			cellStyle1.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
			cellStyle1.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
			cellStyle1.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
			cellStyle1.setAlignment(HSSFCellStyle.ALIGN_RIGHT);// 居右
			cellStyle1.setFont(fontDefault);

			HSSFCellStyle cellStyle2 = wb.createCellStyle();
			cellStyle2.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
			cellStyle2.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
			cellStyle2.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
			cellStyle2.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
			cellStyle2.setAlignment(HSSFCellStyle.ALIGN_LEFT); // 居左
			cellStyle2.setFont(fontDefault);

			HSSFCellStyle cellStyle3 = wb.createCellStyle();
			cellStyle3.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
			cellStyle3.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
			cellStyle3.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
			cellStyle3.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
			cellStyle3.setAlignment(HSSFCellStyle.ALIGN_RIGHT); // 居右
			cellStyle3.setFont(font);

			HSSFCellStyle cellStyle4 = wb.createCellStyle();
			cellStyle4.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
			cellStyle4.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
			cellStyle4.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
			cellStyle4.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
			cellStyle4.setAlignment(HSSFCellStyle.ALIGN_LEFT); // 居左
			cellStyle4.setFont(font);

			HSSFCellStyle cellStyle5 = wb.createCellStyle();
			cellStyle5.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
			cellStyle5.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
			cellStyle5.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
			cellStyle5.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
			cellStyle5.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
			cellStyle5.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平居中
			cellStyle5.setFont(fontDefault);

			String idx_name = "";// 业务条线 每次合并需比对的值
			List<Integer> merger = new ArrayList();// 每次合并的开始的行号
			while (i < listvo.size()) {
				Boolean isSum = false;
				Boolean isCportTile = false;
				// 处理行
				row = sheet.getRow((short) (begainRow + i));
				if (row == null) {
					row = sheet.createRow((short) (begainRow + i)); // 在现有行号后追加数据
				}
				Map<Integer, Object> data = (Map) listvo.get(i);

				if (templateName.equals("A")) {// c表第二列业务条线合并
					if (i == 0) {// 初始化处理
						idx_name = (String) data.get(0);
						merger.add(begainRow + i);
					} else if (((idx_name != null)
							&& (!idx_name.equals((String) (data.get(0) == null ? "" : data.get(0)))))
							|| (idx_name == null && idx_name != data.get(0))) {
						merger.add(begainRow + i);// 不相等就要记录新的合并点
						idx_name = (String) data.get(0);// 比对的值要变成成新值
					}
				}

				if (templateName.equals("C-1") && ((String) data.get(7)).length() < 12) {
					isCportTile = true; // C表：一级项目和二级项目加粗
				}
				if (data.values().contains("合计") || data.values().toString().contains("小计")) {
					isSum = true;
				}
				for (Map.Entry<Integer, Object> entry : data.entrySet()) {
					if ((templateName.equals("C-1") && (entry.getKey() == 7))) {
						// c报表第七列不输出
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
//						 cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 居中
//						 cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//垂直
//					 }
						cell.setCellValue(entry.getValue() == null ? "" : entry.getValue().toString());
					}

				}

				i++;
			}
			if (templateName.equals("A")) {// A表合并业务条线
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
				HSSFRow row = sheet.getRow(Integer.parseInt(rowStr == null ? "0" : rowStr));// 得到行
				if (null == row) {
					row = sheet.createRow(Integer.parseInt(columnStr == null ? "0" : columnStr));
				}
				HSSFCell cell = row.getCell(Integer.parseInt(columnStr == null ? "0" : columnStr));// 得到列
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
	 * @param listvo       需普通处理的数据
	 * @param templateName 模板文件名称
	 * @param datafilename 输出的数据文件名称
	 * @param startrow     从第几列开始填入数据
	 * @param sepcilValues 需特殊处理的单元格 内容为 String[3] 分别存储 行号、列号、具体的单元格数据
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
			POIFSFileSystem ps = new POIFSFileSystem(fs); // 使用POI提供的方法得到excel的信息
			HSSFWorkbook wb = new HSSFWorkbook(ps);

			HSSFSheet sheet = wb.getSheetAt(0); // 获取到工作表，因为一个excel可能有多个工作表
			HSSFRow row = sheet.getRow(0);

			int begainRow = 0;
			if ("".equals(row) || row == null) {
				begainRow = 0;
			} else {
				begainRow = sheet.getLastRowNum();
				begainRow = begainRow + 1;
			}
			// 如果指定开始列
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
			fontDefault.setFontName("微软雅黑");

			HSSFFont font = wb.createFont();
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			font.setFontHeightInPoints((short) 10);
			font.setFontName("微软雅黑");

			HSSFCellStyle cellStyle1 = wb.createCellStyle();
			cellStyle1.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
			cellStyle1.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
			cellStyle1.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
			cellStyle1.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
			cellStyle1.setAlignment(HSSFCellStyle.ALIGN_RIGHT);// 居右
			cellStyle1.setFont(fontDefault);

			HSSFCellStyle cellStyle2 = wb.createCellStyle();
			cellStyle2.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
			cellStyle2.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
			cellStyle2.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
			cellStyle2.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
			cellStyle2.setAlignment(HSSFCellStyle.ALIGN_LEFT); // 居左
			cellStyle2.setFont(fontDefault);

			HSSFCellStyle cellStyle3 = wb.createCellStyle();
			cellStyle3.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
			cellStyle3.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
			cellStyle3.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
			cellStyle3.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
			cellStyle3.setAlignment(HSSFCellStyle.ALIGN_RIGHT); // 居右
			cellStyle3.setFont(font);
			
			HSSFCellStyle cellStyle4 = wb.createCellStyle();
			cellStyle4.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
			cellStyle4.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
			cellStyle4.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
			cellStyle4.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
			cellStyle4.setAlignment(HSSFCellStyle.ALIGN_LEFT); // 居左
			cellStyle4.setFont(font);
			

			HSSFCellStyle cellStyle5 = wb.createCellStyle();
			cellStyle5.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
			cellStyle5.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
			cellStyle5.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
			cellStyle5.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
			cellStyle5.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
			cellStyle5.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平居中
			cellStyle5.setFont(fontDefault);

			String idx_name = "";// 业务条线 每次合并需比对的值
			List<Integer> merger = new ArrayList();// 每次合并的开始的行号
			while (i < listvo1.size()) {
				//设置列宽
				sheet.setColumnWidth(begainRow + i, 256*30+184);
				Boolean isSum = false;
				Boolean isCportTile = false;
				// 处理行
				row = sheet.getRow((short) (begainRow + i));
				if (row == null) {
					row = sheet.createRow((short) (begainRow + i)); // 在现有行号后追加数据
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
			idx_name = "";// 业务条线 每次合并需比对的值
			merger = new ArrayList();// 每次合并的开始的行号
			while (j < listvo2.size()) {
				//设置列宽
				sheet.setColumnWidth(begainRow2 + j, 256*30+184);
				Boolean isSum = false;
				Boolean isCportTile = false;
				// 处理行
				row = sheet.getRow((short) (begainRow2 + j));
				if (row == null) {
					row = sheet.createRow((short) (begainRow2 + j)); // 在现有行号后追加数据
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
	      Reader is = clob.getCharacterStream();// 得到流
	      BufferedReader br = new BufferedReader(is);
	      String s = br.readLine();
	      StringBuffer sb = new StringBuffer();
	      while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
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
