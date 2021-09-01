/**
* @Company weipu
* @Title: ImportExcelTest.java 
* @Package org.bana.common.util.file210330 
* @author liuwenjie   
* @date 2021年3月30日 上午9:15:14 
* @version V1.0   
*/
package org.bana.common.util.file210330;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.bana.common.util.exception.BanaUtilException;
import org.bana.common.util.jdbc.DbUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: ImportExcelTest
 * @Description:
 * @author liuwenjie
 */
public class ImportExcelTest {

	private static final Logger LOG = LoggerFactory.getLogger(ImportExcelTest.class);

	private static final String KEY_ORDER_TOTAL = "订单数据库金额";

	// private boolean onlyJob = true;

	private String filePath = "/Users/liuwenjie/文件资料/微谱/2021/21041604/";
	// private String fileName = "31 20210327 WP-20071989-BC-01.xlsx";

	private String[] rowTitle = new String[] { "备注", "我方子公司", "客户名称", "合同号", "合同金额", "修正合同金额",
			"订单序号(对应sheet\"医药订单all\"里的序号）", "订单号", "累计百分比（截至1月底）", "订单金额（最终订单金额）", "修正订单金额", "行业群", "包材", "药品", "技术",
			"项目里程碑(旧)", "收入确认百分比(旧)", "里程碑完成时间", "项目里程碑（新）", "备注（规格/温度/放置方式）", "标准工作量", "收入确认百分比(新)", "里程碑完成日期(新)",
			"收入确认(旧)", "收入确认(新)", "是否为收款节点", "应收款金额", "收款期数", "ERP认款编号", "实际收款日期", "实际收款金额", "付款方名称", "开票日期", "发票号",
			"开票金额", "发票抬头", "责任人", "分配时间", "财务审核", "事业部审核" };

	private List<String> rowTitleList = Arrays.asList(rowTitle);

	private String[] targetRowTitle = new String[] { "订单号", "订单金额（最终订单金额）", "修正订单金额", "行业群", "项目里程碑（新）",
			"备注（规格/温度/放置方式）", "里程碑完成日期(新)", "是否为收款节点", "应收款金额", "收款期数", "实际收款日期", "实际收款金额" };

	private String orderName = "订单号";
	private String orderTotal = "订单金额（最终订单金额）";
	private String orderTotalChange = "修正订单金额";

	private String[] orderTitle = new String[] { orderName, orderTotal, orderTotalChange };
	private String[] milestoneTitle = new String[] { "行业群", "项目里程碑（新）", "备注（规格/温度/放置方式）", "里程碑完成日期(新)", "是否为收款节点",
			"应收款金额", "收款期数", "实际收款日期", "实际收款金额" };

	// 查询验证
	private String checkInvoiceSql = "select * from dbo.invoiceInfo where inumid = ? ";
	private String checkJobSql = "select * from dbo.biomedicaljob where inumid = ? and typeid=1 ";
	private String checkJobNameSql = "select * from BiomedicalProject bp join  BiomedicalConfigure bc on bp.PID=bc.PID  join IndustryCategoryInfo ici on bp.sampleType_three=ici.Id where AbbreviateName=? and CJobName=?";
	private String checkPayNumsql = "select * from BiomedicalOrderPaymentInfo where Inumid = ?";

	// 更新脚本
	// 更新订单
	private String updateOrder = "update InvoiceInfo set total=? where inumid=?"; // 订单号

	// 增加变更记录
	private String insertOrderChange = "insert into InvoiceinfoBalance(inumid,contranumber,model,AreaSaleId,TeamSaleId,CustomerSource,Saleid,SaleName,paymentAccount,ddltitleid,balanceState,OccupiedAmount,originalPayment,guaranteePayment,submit_time,adminid,state,total,modifytotal,SubmitUserId,audit_time,nowremovePayment,removePayment,CreditType,TotalAmount,AvailableAmount,CDOccupiedAmount,customerId,customerName,refund,refund_guarantee,refund_deposit)\n"
			+ "select a.inumid,a.commNumber ,b.BusinessDivisionId,b.AreaId,b.TeamId,c.CustomerSource,a.salesid,a.salesName,a.cname,14,7,0,0,0,GETDATE(),a.salesid,1,\n"
			+ "?,\n" // '申请金额(增加多少钱）-int类型'
			+ "?,\n" // '申请变更的总金额（包括以前的金额+增加的钱）-int类型'
			+ "a.salesid,GETDATE(),0,0,a.CreditType,0,0,0,a.CustomerId,a.cname,0,0,0\n" + "from Invoiceinfo a\n"
			+ "left join AdminInfo b on b.aId=a.salesid \n" + "left join CustomerInfo c on c.Cid=a.CustomerId\n"
			+ "where inumid=?"; // '订单号'
	// 更新job
	private String insertJobSql = "insert into dbo.biomedicaljob "
			+ " (inumid, jobname,typeid, isNode,   term , instalmentAmount,remarks , createTime , experimentContent, connectTime,prelimWorkload, sort, state,configureid,actualworkload ) "
			// 订单号 job名字 1 是否分期 期数 分期金额 备注 创建日期 动态备注 里程碑完成时间 工时 index 固定值 jobId
			+ " values " + " (?,?,1,?,?,?,?,?,'',?,?,?,'已关联',?,?)";

	private String payInfo = "分项内容-导入041501";

	// 更新对账
	private String insertPaySql = "insert into BiomedicalOrderPaymentInfo(Inumid " + "      ,InstalmentNumber"
			+ "      ,InstalmentAmount" + "      ,ExperimentContent" + "      ,ReceivedPayment"
			+ "      ,RefundReceivedPayment" + "      ,GuaranteePayment" + "      ,RefundGuaranteePayment"
			+ "      ,RemoveGuaranteePayment" + "      ,RefundTotal" + "      ,CreditType" + "      ,CreateTime"
			+ "      ,GuaranteeDueDate)" + "values(?,?,?,'" + payInfo + "',?,0,0,0,0,0,0,GETDATE(),null)";
	// 订单号 期数 金额

	private String updateIsPayment = "update BiomedicalOrderPaymentInfo set IsPayment=ReceivedPayment+GuaranteePayment"
			+ "  where inumid=?";

	// private String updatePrestSql = "update a set
	// InstalmentCount=c,PaymentStatus='0/'+CONVERT(varchar(50),c)\n"
	// + " from InvoiceInfo a\n"
	// + " join (select count(*) as c,Inumid from BiomedicalOrderPaymentInfo where
	// ExperimentContent='" + payInfo + " ' group by Inumid) b \n"
	// + " on b.Inumid=a.inumid\n"
	// + " ";

	private String updatePrestSql = "update a set InstalmentCount=c,PaymentStatus=CONVERT(varchar(50),d)+'/'+CONVERT(varchar(50),c)\n"
			+ "from InvoiceInfo a\n"
			+ "join (select count(*) as c,Inumid from BiomedicalOrderPaymentInfo where ExperimentContent='" + payInfo
			+ " '  group by Inumid) b \n" + "on b.Inumid=a.inumid\n"
			+ "join (select count(*) as d,Inumid from BiomedicalOrderPaymentInfo where ExperimentContent='" + payInfo
			+ " ' and ReceivedPayment+GuaranteePayment=InstalmentAmount  group by Inumid) c \n"
			+ "on c.Inumid=a.inumid";

	private List<Map<String, String>> successMapList = new ArrayList<>();
	private List<Map<String, String>> failMapList = new ArrayList<>();

	// private String insertPaymentSql = "insert into db."

	private String executeInsert(ImportData data, Connection connection) throws SQLException {
		StringBuffer msg = new StringBuffer();
		String orderNum = data.getOrderNum();
		List<Map<String, Object>> millStoneList = data.getMillStoneList();
		if (millStoneList.size() == 0) {
			throw new RuntimeException("订单" + orderNum + "没有读取到里程碑excel");
		}
		PreparedStatement insertjobPrest = connection.prepareStatement(insertJobSql, ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		PreparedStatement insertPayPrest = connection.prepareStatement(insertPaySql, ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		PreparedStatement updateIsPaymentPrest = connection.prepareStatement(updateIsPayment);

		int sort = 0;
		Date now = new Date(System.currentTimeMillis());
		for (Map<String, Object> mileStone : millStoneList) {
			sort++;
			String hangye = (String) mileStone.get("行业群");
			String name = (String) mileStone.get("项目里程碑（新）");
			Map<String, Object> result = getJobID(hangye, name, connection);
			int jobId = (Integer) result.get("cid");
			double work = (Double) result.get("work");
			System.out.print("insert sql " + hangye + ":" + name + ":" + jobId + ":" + work);
			// 订单号
			insertjobPrest.setString(1, orderNum);
			insertjobPrest.setString(2, name);
			// 是否为收款节点
			String isPay = (String) mileStone.get("是否为收款节点");
			insertjobPrest.setInt(3, "是".equals(isPay) ? 1 : 0);
			// 期数
			String term = (String) mileStone.get("收款期数");
			System.out.print("期数:" + term);
			if (term == null) {
				insertjobPrest.setObject(4, null);
			} else {
				insertjobPrest.setInt(4, Integer.valueOf(term));
			}
			// 分期金额
			String payNum = (String) mileStone.get("应收款金额");
			if (payNum == null) {
				insertjobPrest.setObject(5, null);
			} else {
				insertjobPrest.setInt(5, Integer.valueOf(payNum));
			}

			// 备注（规格/温度/放置方式）
			String marks = (String) mileStone.get("备注（规格/温度/放置方式）");
			insertjobPrest.setString(6, marks);
			// insertjobPrest.setString(8, marks);
			// 创建日期
			insertjobPrest.setDate(7, now);

			// 里程碑完成时间 里程碑完成日期(新)
			java.util.Date connectTime = (java.util.Date) mileStone.get("里程碑完成日期(新)");
			if (connectTime != null) {
				insertjobPrest.setDate(8, new Date(connectTime.getTime()));
			} else {
				insertjobPrest.setDate(8, null);
			}

			// 工时
			insertjobPrest.setDouble(9, work);
			if (connectTime != null) {
				insertjobPrest.setDouble(12, work);
			} else {
				insertjobPrest.setDouble(12, 0);
			}

			// sort
			insertjobPrest.setInt(10, 1000 + sort);
			// jobId
			insertjobPrest.setInt(11, jobId);

			insertjobPrest.addBatch();

			// 实际收款金额
			String receivedPay = (String) mileStone.get("实际收款金额");

			// 构建执行期数的文件

			if (term != null && payNum != null && !data.isHasPayInfo()) {
				insertPayPrest.setString(1, orderNum);
				insertPayPrest.setInt(2, Integer.valueOf(term));
				insertPayPrest.setInt(3, Integer.valueOf(payNum));
				if (receivedPay == null) {
					insertPayPrest.setObject(4, 0);
				} else {
					insertPayPrest.setInt(4, Integer.valueOf(receivedPay));
				}
				insertPayPrest.addBatch();
			}
			//

			System.out.println();
		}

		int[] executeBatch = insertjobPrest.executeBatch();

		System.out.println("执行完成insert job sql" + Arrays.toString(executeBatch));
		if (!data.isHasPayInfo()) {
			int[] executeBatch2 = insertPayPrest.executeBatch();
			System.out.println("执行完成insert pay sql" + Arrays.toString(executeBatch2));

			// 更新分期状态
			updateIsPaymentPrest.setString(1, orderNum);
			int executeUpdate = updateIsPaymentPrest.executeUpdate();
			System.out.println("执行update isPayment的脚本" + executeUpdate);
		} else {
			msg.append("已存在对账信息，对账信息未导入；");
		}

		// 执行更新订单数据

		// updateIsPaymentPrest.setString(1, orderNum);
		Integer dataTotal = (Integer) data.getOrderMap().get(KEY_ORDER_TOTAL);
		String excelTotalObj = (String) data.getOrderMap().get(orderTotal);
		if (excelTotalObj == null) {
			throw new RuntimeException("订单金额为空");
		}
		Integer excelTotal = Integer.parseInt(excelTotalObj);
		if (excelTotal > dataTotal) { // 金额变动信息
			PreparedStatement updateOrderPrest = connection.prepareStatement(updateOrder);
			PreparedStatement insertOrderChangePre = connection.prepareStatement(insertOrderChange);
			updateOrderPrest.setInt(1, excelTotal);
			updateOrderPrest.setString(2, orderNum);
			int executeUpdate2 = updateOrderPrest.executeUpdate();
			System.out.println("执行update 修改订单金额的脚本" + executeUpdate2);
			// 增加金额变动信息
			insertOrderChangePre.setInt(1, dataTotal); // 原来的金额
			insertOrderChangePre.setInt(2, excelTotal);
			insertOrderChangePre.setString(3, orderNum);
			insertOrderChangePre.executeUpdate();
			msg.append("excel金额和erp中不一致，修改了订单金额，并增加了一条金额变更记录");
		}
		return msg.toString();
	}

	public void checkData(ImportData importData, Connection connection) throws SQLException {
		String orderNum = importData.getOrderNum();

		PreparedStatement orderPrest = connection.prepareStatement(checkInvoiceSql);
		orderPrest.setString(1, orderNum);
		// 执行转移
		ResultSet rs = orderPrest.executeQuery();
		//
		int count = 0;
		while (rs.next()) {
			count++;
			String inumid = rs.getString("inumid");
			// 判断订单中的总金额是否一样
			int total = rs.getInt("total");
			String excelTotalObj = (String) importData.getOrderMap().get(orderTotal);
			if (excelTotalObj == null) {
				throw new RuntimeException("订单金额为空");
			}
			Integer excelTotal = Integer.parseInt(excelTotalObj);
			if (excelTotal < total) {
				throw new RuntimeException("订单金额数据库金额大于excel金额,不能进行刷新，excel中=" + excelTotal + "，数据库中=" + total);
			}
			importData.getOrderMap().put(KEY_ORDER_TOTAL, total);
			// 判断订单中是否存在job数据
			PreparedStatement jobPrest = connection.prepareStatement(checkJobSql);
			jobPrest.setString(1, orderNum);
			ResultSet jobRS = jobPrest.executeQuery();
			int jobCount = 0;
			while (jobRS.next()) {
				jobCount++;
			}
			if (jobCount >= 1) {
				throw new RuntimeException("订单已经存在job，" + jobCount + "不进行导入，订单num=" + orderNum);
			}
			jobRS.close();
			// 判断订单的对账内容是否已经存在
			// if(!onlyJob) {
			PreparedStatement payPrest = connection.prepareStatement(checkPayNumsql);
			payPrest.setString(1, orderNum);
			ResultSet payRS = payPrest.executeQuery();
			int payCount = 0;
			while (payRS.next()) {
				payCount++;
			}
			if (payCount >= 1) {
				importData.setHasPayInfo(true);
				// throw new RuntimeException("订单已经存在对账信息，不进行导入，订单num=" + orderNum);
			}
			payRS.close();
			// }

		}
		rs.close();
		System.out.println("执行结束总数量为" + count);
		if (count < 1) {
			throw new RuntimeException("没有查到订单" + orderNum);
		}
	}

	@Test
	public void importDir() throws Exception {
		File dir = new File(filePath);
		for (File file : dir.listFiles()) {
			if (file.getName().endsWith(".xlsx") && !file.getName().startsWith("~")) {
				try {
					importFile(file);
				} catch (Exception e) {
					LOG.error("导入异常" + file.getName(), e);
					// 记录一条失败的订单
					Map<String, String> failMsg = new HashMap<>();
					failMsg.put("orderNum", "整个文件");
					failMsg.put("msg", e.getMessage());
					failMsg.put("fileName", file.getName());
					failMapList.add(failMsg);
				}
			}
		}
		saveResultFile();
	}

	public void importFile(File file) throws Exception {
		List<Integer> indexList = Arrays.asList(targetRowTitle).stream().map(item -> rowTitleList.indexOf(item))
				.collect(Collectors.toList());
		System.out.println(indexList);
		// File file1 = new File(filePath + fileName);
		FileInputStream fis = new FileInputStream(file);
		Workbook workBook = WorkbookFactory.create(fis);
		Sheet sheet = workBook.getSheetAt(0);
		// 获取列头
		Row rowT = sheet.getRow(0);
		for (int i = 0; i < rowTitle.length; i++) {
			Cell cell = rowT.getCell(i);
			String stringCellValue = cell.getStringCellValue().trim();
			if (!stringCellValue.equals(rowTitle[i].trim())) {
				String fileName = file.getName();
				String msg = fileName + "存在和执行的列不一致的表头，应该是 [" + rowTitle[i].trim() + "] 实际是 【" + stringCellValue + "】";
				System.out.println(msg);
				throw new RuntimeException(msg);
			}
		}
		int currentRowNum = 1;
		// 获取列数据
		boolean hasRecordValue = true;
		Map<String, ImportData> dataMap = new HashMap<String, ImportExcelTest.ImportData>();
		while (hasRecordValue) {
			Row row = sheet.getRow(currentRowNum++);
			LOG.debug("=====获取数据行当前行数: " + currentRowNum);
			if (row == null) {
				hasRecordValue = false;
				break;
			}
			Map<String, Object> rowMap = new HashMap<String, Object>();
			for (int targetIndex : indexList) {
				Cell cell = row.getCell(targetIndex);
				Object cellValue = null;
				if (cell != null) {
					cellValue = getCellValue(cell);
				}
				rowMap.put(rowTitle[targetIndex], cellValue);
				// System.out.print(rowTitle[targetIndex] + " : " + cellValue + " ");
			}
			// 处理当前行数据归类
			Object orderNum = rowMap.get(orderName);
			if (orderNum == null) {
				throw new RuntimeException("异常，没有找到订单号" + currentRowNum);
			}
			ImportData orderData = dataMap.get(orderNum);
			if (orderData == null) {
				orderData = new ImportData();
				dataMap.put(orderNum.toString(), orderData);
				orderData.setOrderNum(orderNum.toString());
				// 第一行要生成一个里程碑数据
				Map<String, Object> orderData2 = getOrderData(rowMap);
				orderData.setOrderMap(orderData2);
			}
			orderData.getMillStoneList().add(getMillStoneMap(rowMap));
		}

		LOG.debug("读取excel完成，完成到" + currentRowNum);
		fis.close();
		printDataMap(dataMap);
		doUpdate(dataMap, file.getName());
	}

	/**
	 * @Description:
	 * @author liuwenjie
	 * @throws IOException
	 * @date 2021年3月30日 下午5:23:35
	 */
	private void saveResultFile() throws IOException {
		if (successMapList.size() > 0) {
			File file = new File(filePath + "success.csv");
			// successMsg.put("orderNum", entry.getKey());
			// successMsg.put("fileName",fileName);
			StringBuffer sb = new StringBuffer();
			sb.append("文件名,订单编号,结果\n");
			for (Map<String, String> map : successMapList) {
				sb.append(map.get("fileName")).append(",").append(map.get("orderNum")).append(",")
						.append(map.get("msg")).append("\n");
			}
			FileUtils.write(file, sb.toString(), "utf-8");
		}
		File file2 = new File(filePath + "error.csv");
		if (failMapList.size() > 0) {
			// failMsg.put("orderNum", entry.getKey());
			// failMsg.put("msg", e.getMessage());
			// failMsg.put("fileName",fileName);
			StringBuffer sb = new StringBuffer();
			sb.append("文件名,订单编号,失败原因\n");
			for (Map<String, String> map : failMapList) {
				sb.append(map.get("fileName")).append(",").append(map.get("orderNum")).append(",")
						.append(map.get("msg")).append("\n");
			}
			FileUtils.write(file2, sb.toString(), "utf-8");
		}

	}

	private void doUpdate(Map<String, ImportData> dataMap, String fileName) throws SQLException {
		Set<Entry<String, ImportData>> entrySet = dataMap.entrySet();
		DataSource dataSource = DbUtil.getDataSource();
		Connection connection = dataSource.getConnection();
		// 目标数据库的插入语句
		connection.setAutoCommit(false);

		for (Entry<String, ImportData> entry : entrySet) {
			try {
				checkData(entry.getValue(), connection);
				String executeMsg = executeInsert(entry.getValue(), connection);
				// 记录一条失败的订单
				Map<String, String> successMsg = new HashMap<>();
				successMsg.put("orderNum", entry.getKey());
				successMsg.put("fileName", fileName);
				successMsg.put("msg", executeMsg);
				successMapList.add(successMsg);
			} catch (RuntimeException e) {
				LOG.error("导入异常", e);
				// 记录一条失败的订单
				Map<String, String> failMsg = new HashMap<>();
				failMsg.put("orderNum", entry.getKey());
				failMsg.put("msg", e.getMessage());
				failMsg.put("fileName", fileName);
				failMapList.add(failMsg);
				continue;
			}

		}
		PreparedStatement updatePrest = connection.prepareStatement(updatePrestSql, ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		int executeUpdate = updatePrest.executeUpdate();
		System.out.println("执行update成功" + executeUpdate);

		connection.commit();
		connection.close();
	}

	private Map<String, Object> getJobID(String hangye, String name, Connection connection) throws SQLException {
		PreparedStatement jobSearch = connection.prepareStatement(checkJobNameSql);
		jobSearch.setString(1, hangye);
		jobSearch.setString(2, name);
		ResultSet rs = jobSearch.executeQuery();
		Map<String, Object> map = new HashMap<>();
		if (rs.next()) {
			int int1 = rs.getInt("cid");
			map.put("cid", int1);
			double work = rs.getDouble("cworkload");
			map.put("work", work);
			return map;
		} else {
			throw new RuntimeException("无法查询里程碑名称对应的id" + hangye + ":" + name);
		}
	}

	private void printDataMap(Map<String, ImportData> data) {
		Set<Entry<String, ImportData>> entrySet = data.entrySet();
		for (Entry<String, ImportData> entry : entrySet) {
			System.out.println("订单号：" + entry.getKey());
			System.out.println(entry.getValue());
		}
	}

	private Object getCellValue(Cell cell) {
		if (cell == null) {
			System.out.println(cell);
		}
		CellType cellTypeEnum = cell.getCellTypeEnum();

		switch (cellTypeEnum) {
			case STRING:
				return cell.getStringCellValue();
			case BOOLEAN://// 布尔值
				return cell.getBooleanCellValue();
			case NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {// 返回指定格式的时间字符串
					return cell.getDateCellValue();
				}
				// if("date".equalsIgnoreCase(columnConfig.getType())){
				// return cell.getDateCellValue();
				// }
				// Map<String, String> columnStyle = columnConfig.getStyle();
				// if(columnStyle != null){
				// String format = columnStyle.get("dataFormat");
				// if(!StringUtils.isBlank(format)){
				// DecimalFormat df = new DecimalFormat(format);
				// return df.format(cell.getNumericCellValue());
				// }
				// }
				return getNumberFormat().format(cell.getNumericCellValue());
			case ERROR:// 错误
				throw new BanaUtilException("存在错误的表格格式");
			case BLANK:// 空白
				return null;
			default:
				throw new BanaUtilException("存在无法解析的表单类型");
		}
	}

	private NumberFormat nf;

	private NumberFormat getNumberFormat() {
		if (nf == null) {
			nf = NumberFormat.getInstance();
			nf.setGroupingUsed(false);// true时的格式：1,234,567,890
		}
		return nf;
	}

	private Map<String, Object> getOrderData(Map<String, Object> rowMap) {
		Map<String, Object> orderMap = new HashMap<>();
		for (String string : orderTitle) {
			orderMap.put(string, rowMap.get(string));
		}
		return orderMap;
	}

	private Map<String, Object> getMillStoneMap(Map<String, Object> rowMap) {
		Map<String, Object> millStoneMap = new HashMap<>();
		for (String string : milestoneTitle) {
			millStoneMap.put(string, rowMap.get(string));
		}
		return millStoneMap;
	}

	public static class ImportData {
		private String orderNum;

		private boolean hasPayInfo = false;

		private Map<String, Object> orderMap;

		private List<Map<String, Object>> millStoneList = new ArrayList<>();

		public String getOrderNum() {
			return orderNum;
		}

		public void setOrderNum(String orderNum) {
			this.orderNum = orderNum;
		}

		public Map<String, Object> getOrderMap() {
			return orderMap;
		}

		public void setOrderMap(Map<String, Object> orderMap) {
			this.orderMap = orderMap;
		}

		public List<Map<String, Object>> getMillStoneList() {
			return millStoneList;
		}

		public void setMillStoneList(List<Map<String, Object>> millStoneList) {
			this.millStoneList = millStoneList;
		}

		public boolean isHasPayInfo() {
			return hasPayInfo;
		}

		public void setHasPayInfo(boolean hasPayInfo) {
			this.hasPayInfo = hasPayInfo;
		}

		@Override
		public String toString() {
			return "ImportData [orderNum=" + orderNum + ", hasPayInfo=" + hasPayInfo + ", orderMap=" + orderMap
					+ ", millStoneList=" + mileList(millStoneList) + "]";
		}

		private String mileList(List<Map<String, Object>> millStoneList) {
			StringBuffer sb = new StringBuffer("[\n");
			for (Map<String, Object> map : millStoneList) {
				sb.append(map.toString()).append(",\n");
			}
			return sb.toString();
		}

	}
}
