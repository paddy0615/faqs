package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
	public static Connection getConnection() {
		// 定义连接
		Connection connection = null;
		try {
			// 加载驱动
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sonicsms_summary", "root", "123456");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}

	@Test
	public void contextLoads() throws Exception {
		//test0();
		//test2();
		//test3();
	}

	/* test0*/
	public static void test0(){
	}

	/* 执行一*/
	public static void test1(){
		/* 执行SQL，改时间
		select * from flightirr_sendingreps_logs where sendingDate between '2018-09-01 00:00:00' and '2018-10-01 00:00:00' and channel='sms'

		insert into sms_sendinghistory201809
			(phoneNum,msgID,country,deliveryDate,pnr,language,jobID,status,remark,vendor)
			select contact,msgID,country,sendingDate,pnr,language,jobID,recordStatus,remark,vendor
			from flightirr_sendingreps_logs where sendingDate between '2018-09-01 00:00:00' and '2018-10-01 00:00:00'
			and channel='sms'

		select * from sms_sendinghistory where deliveryDate between '2018-09-01 00:00:00' and '2018-10-01 00:00:00' and s_event is null
		*/
	}

	/* 执行二：里面有两个步骤（优化版）*/
	public static void test2opt(){
		System.out.println("-------开始执行2");
		Connection connection = null;
		// 预执行加载
		PreparedStatement preparedStatement = null;
		connection = getConnection();
		try {
			int m = 1, l = 1000;
			String sqlString = "";
			//Step 2.
			sqlString = "update sms_sendinghistory201809 a, flightirr_sendingorders b set a.irrType=b.irrType, a.irrnature=b.template, " +
					"a.irrCategory=b.irrCategory, a.reasons=b.reasons, a.flightNo=b.flightNo, a.newFlightNo=b.newFlightNo, " +
					"a.departingFrom=b.departingFrom, a.newDepartingFrom=b.newDepartingFrom, a.arrivingAt=b.arrivingAt, " +
					"a.newArrivingAt=b.newArrivingAt, a.departureDate=b.departureDate, a.newDepartureDate=b.newDepartureDate, " +
					"a.arrivalDate=b.arrivalDate, a.newArrivalDate=b.newArrivalDate, a.orderID=b.id, a.submitDate=b.createdDate, a.account=b.accountNo where a.jobID=b.jobID and a.id >= ? and a.id <= ?";
			//Step 3.
			/*sqlString = "update sms_sendinghistory201809 a, flightirr_sendingtask_logs b set a.s_event=b.caseEvent where a.orderID=b.ordersID and a.id>="+m+" and a.id<="+l;
			 */
			// 使用事务
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(sqlString);
			for(int i=0;i<200;i++){
				System.out.println(i);
				preparedStatement.setInt(1, m);
				preparedStatement.setInt(2, l);
				preparedStatement.addBatch();
				if((i+1)%5==0){
					System.out.println("更新一批");
					//批量处理
					preparedStatement.executeBatch();
					//清除stmt中积攒的参数列表
					preparedStatement.clearBatch();
				}
				m = m + 1000;
				l = l + 1000;
			}
			preparedStatement.executeBatch();
			connection.commit();
			connection.setAutoCommit(true);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("-------结束执行2");
	}

	/* 执行二：里面有两个步骤*/
	public static void test2(){
		System.out.println("-------开始执行2");
		Connection connection = null;
		connection = getConnection();
		// 预执行加载
		PreparedStatement preparedStatement = null;
		try {
			int m = 1, l = 1000;
			String sqlString = "";
			for(int i=0;i<200;i++){
				//Step 2.
				sqlString = "update sms_sendinghistory201809 a, flightirr_sendingorders b set a.irrType=b.irrType, a.irrnature=b.template, " +
				"a.irrCategory=b.irrCategory, a.reasons=b.reasons, a.flightNo=b.flightNo, a.newFlightNo=b.newFlightNo, " +
				"a.departingFrom=b.departingFrom, a.newDepartingFrom=b.newDepartingFrom, a.arrivingAt=b.arrivingAt, " +
				"a.newArrivingAt=b.newArrivingAt, a.departureDate=b.departureDate, a.newDepartureDate=b.newDepartureDate, " +
				"a.arrivalDate=b.arrivalDate, a.newArrivalDate=b.newArrivalDate, a.orderID=b.id, a.submitDate=b.createdDate, a.account=b.accountNo where a.jobID=b.jobID and a.id>="+m+" and a.id<="+l;

				//Step 3.
				/*sqlString = "update sms_sendinghistory201809 a, flightirr_sendingtask_logs b set a.s_event=b.caseEvent where a.orderID=b.ordersID and a.id>="+m+" and a.id<="+l;
				*/
				System.out.println(sqlString);
				try {
					if((i+1)%50==0){
						System.out.println("重新链接");
						if (connection != null) {
							connection.close();
						}
						connection = getConnection();
					}
					preparedStatement = connection.prepareStatement(sqlString);
					preparedStatement.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				m = m + 1000;
				l = l + 1000;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		System.out.println("-------结束执行2");
	}

	/* 执行三*/
	public static void test3(){
		System.out.println("-------开始执行3");
		Connection connection = null;
		// 预执行加载
		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatement1 = null;
		// 结果集
		ResultSet result = null;
		ResultSet result1 = null;
		connection = getConnection();
		try {
			String hql = "select id,phoneNum,jobID,language from sms_sendinghistory201809 where (language is not null and message is null) OR (message='') ";
			preparedStatement = connection.prepareStatement(hql);
			result = preparedStatement.executeQuery();
			int i = 0 ;
			while (result.next()) {
				i++;
				System.out.println(i);
				int orderID = result.getInt("id");
				String phoneNum = result.getString("phoneNum");
				String jobID = result.getString("jobID");
				String language = result.getString("language");
				String hql1 = "select smsBody_can,smsBody_en,smsBody_jap,smsBody_kor,smsBody_put from flightirr_template where jobID='"+jobID+"'";
				try {
					preparedStatement1 = null;
					preparedStatement1 = connection.prepareStatement(hql1);;
					result1 = null;
					result1 = preparedStatement1.executeQuery();
					String msg = "";
					while (result1.next()) {
						if(language.equals("zh-HK")){
							msg = result1.getString("smsBody_can");
						} else if(language.equals("zh-TW")){
							msg = result1.getString("smsBody_can");
						} else if(language.equals("zh_CN") || language.equals("zh-CN")){
							msg = result1.getString("smsBody_put");
						} else if(language.equals("ko-KR") || language.equals("KOR")){
							msg = result1.getString("smsBody_kor");
						} else if(language.equals("ja-JP") || language.equals("JAP")){
							msg = result1.getString("smsBody_jap");
						} else if(language.equals("en-US") || language.equals("en-us")){
							msg = result1.getString("smsBody_en");
						} else if(language.equals("en-SG")){
							msg = result1.getString("smsBody_en");
						}
						String sql = "update sms_sendinghistory201809 set message='"+msg+"' where id="+orderID;
						try {
							preparedStatement = connection.prepareStatement(sql);
							preparedStatement.executeUpdate();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (result1 != null) {
					result1.close();
				}
				if (result != null) {
					result.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		System.out.println("-------结束执行3");
	}

	/* 执行四*/
	public static void test4(){
	 	/* 执行SQL，改时间
		 insert into sms_sendinghistory201809
		 (phoneNum,msgID,country,submitDate,deliveryDate,pnr,jobID,status,vendor,message)
		 select cutomerPhone,msgID_vendor,country,submitDate,deliveryDate,pnr,jobID,receiveStatus,vendor,message
		 from sms_orderlogs where deliveryDate between '2018-09-01 00:00:00' and '2018-10-01 00:00:00'
		*/
	}
}
