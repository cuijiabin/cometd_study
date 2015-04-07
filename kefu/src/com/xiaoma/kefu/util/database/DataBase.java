package com.xiaoma.kefu.util.database;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.stereotype.Component;

/**
 * 执行原生sql工具类
 * *********************************
* @Description: TODO
* @author: wangxingfei
* @createdAt: 2015年4月2日下午3:34:45
**********************************
 */
@Component
public class DataBase implements Serializable{
	
	private static final long serialVersionUID = 3008729996186951077L;
	private static Logger log = Logger.getLogger(DataBase.class);
	private static SessionFactory sessionFactory;
	
	//用sessionFactory 获取连接池连接
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		DataBase.sessionFactory = sessionFactory;
	}
	
//	private static SessionFactory getSessionFactory() {
//		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath*:application-context.xml");
//		sessionFactory = (SessionFactory) ac.getBean("sessionFactory");
//		return  sessionFactory;
//	}


	/**
	 * 查询数据库,返回数据 (最多1W行)
	* @Description: TODO
	* @param strSQL
	* @return
	 */
	public static DataSet Query(String strSQL) {
//		if(sessionFactory==null) sessionFactory = getSessionFactory();
		DataSource dataSource = SessionFactoryUtils.getDataSource(sessionFactory);
		DataSet ds = new DataSet();
		Statement stat = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			// 打印查询语句
			log.debug(strSQL);
			// 查询
			stat = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = stat.executeQuery(strSQL);
			// 填充Dataset对象
			DataSet.FillDataSet(ds, rs);
			ds.SQLNoPaging = strSQL;
		} catch (Exception ex) {
			System.out.println(strSQL);
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			closeAllOpenObject(rs, stat, conn);
		}

		return ds;
	}
	
	


	/**
	 * 分页查询，返回数据集
	 * 
	 * @param strSQL
	 *            SQL查询语句
	 * @param iPageIndex
	 *            当前第几页
	 * @param iPageSize
	 *            分页大小
	 * @return
	 */
	public static DataSet Query(String strSQL, int iPageIndex, int iPageSize) {
		if(iPageIndex<1){
			iPageIndex=1;
		}
		int iStartIdx = (iPageIndex - 1) * iPageSize;
		String strSQLPage = String.format(strSQL+" limit %1$d, %2$d ", iStartIdx, iPageSize);
		log.debug(strSQLPage);
		DataSet dsDataSet = Query(strSQLPage);
		dsDataSet.SQLNoPaging = strSQL;
		return dsDataSet;
	}


	/**
	 * 查询返回JSonArray对象
	 * 
	 * @param strSQL
	 * @return JSonArray
	 */
	public static JSONArray QueryJSONArray(String strSQL) {
		DataSource dataSource = SessionFactoryUtils.getDataSource(sessionFactory);
		JSONArray jsResult = new JSONArray();
		Statement stat = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			// 打印查询语句
			log.debug(strSQL);
			stat = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = stat.executeQuery(strSQL);

			DataSet.FillJSON(jsResult, rs);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			closeAllOpenObject(rs, stat, conn);
		}

		return jsResult;
	}

	/**
	 * 将数据集转换成为 JSArray
	 * 
	 * @param dsDataSet
	 * @return
	 */
	public static JSONArray convertToJSArray(DataSet dsDataSet) {
		return DataSet.ConvertDataSetToJSONArrary(dsDataSet);
	}

	/**
	 * 关闭数据库的所有连接对象
	 * 
	 * @param rs
	 *            数据集
	 * @param stat
	 *            java.sql.statment
	 * @param conn
	 *            数据库连接
	 */
	private static void closeAllOpenObject(java.sql.ResultSet rs, Statement stat, Connection conn) {
		if (rs != null) {
			try {
				rs.close();
				rs = null;
			} catch (SQLException e) {
			}
		}
		if (stat != null) {
			try {
				stat.close();
				stat = null;
			} catch (SQLException e) {
			}
		}
		if (conn != null) {
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
			}
		}
	}

	/**
	 * 执行更新语句
	 * 
	 * @param strSQL
	 * @return int
	 */
	public static void Excute(String strSQL) throws SQLException {
		DataSource dataSource = SessionFactoryUtils.getDataSource(sessionFactory);
		Statement stat = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			stat = conn.createStatement();
			// 打印查询语句
			log.debug(strSQL);
			stat.execute(strSQL);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new SQLException ("SQL语句执行异常。"); 
		} finally {
			closeAllOpenObject(rs, stat, conn);
		}
	}


	/**
	 * 获取单值
	 * 
	 * @param strSQL
	 * @return
	 */
	public static String getSingleResult(String strSQL) {
		DataSet ds = DataBase.Query(strSQL);
		if (ds.RowCount > 0) {
			return ds.getRow(0).getString(0);
		}
		return null;
	}



	/**
	 * 查询数据库,返回所有行
	* @Description: TODO
	* @param strSQL
	* @return
	 */
	public static DataSet QueryNoLimit(String strSQL) {
		DataSource dataSource = SessionFactoryUtils.getDataSource(sessionFactory);
		DataSet ds = new DataSet();
		Statement stat = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			// 查询
			stat = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = stat.executeQuery(strSQL);
			// 填充Dataset对象
			DataSet.FillDataSetNoLimit(ds, rs);
			ds.SQLNoPaging = strSQL;
		} catch (Exception ex) {
			System.out.println(strSQL);
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			closeAllOpenObject(rs, stat, conn);
		}

		return ds;
	}

}
