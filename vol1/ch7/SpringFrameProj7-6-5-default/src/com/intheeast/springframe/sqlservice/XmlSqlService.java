package com.intheeast.springframe.sqlservice;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import com.intheeast.springframe.dao.UserDao;
import com.intheeast.springframe.sqlservice.jaxb.SqlType;
import com.intheeast.springframe.sqlservice.jaxb.Sqlmap;

public class XmlSqlService implements SqlService {

	// --------- SqlProvider ------------
	private SqlReader sqlReader;
	private SqlRegistry sqlRegistry;
		
	public void setSqlReader(SqlReader sqlReader) {
		this.sqlReader = sqlReader;
	}

	public void setSqlRegistry(SqlRegistry sqlRegistry) {
		this.sqlRegistry = sqlRegistry;
	}

	@PostConstruct
	public void loadSql() {
		this.sqlReader.read(this.sqlRegistry);
	}

	public String getSql(String key) throws SqlRetrievalFailureException {
		try {
			return this.sqlRegistry.findSql(key);
		} 
		catch(SqlNotFoundException e) {
			throw new SqlRetrievalFailureException(e);
		}
	}

	// --------- SqlRegistry ------------	
	private Map<String, String> sqlMap = new HashMap<String, String>();

	public String findSql(String key) throws SqlNotFoundException {
		String sql = sqlMap.get(key);
		if (sql == null)  
			throw new SqlRetrievalFailureException(key + "를 이용해서 SQL을 찾을 수 없습니다");
		else
			return sql;

	}

	public void registerSql(String key, String sql) {
		sqlMap.put(key, sql);
	}
	
	// --------- SqlReader ------------
	private String sqlmapFile;

	public void setSqlmapFile(String sqlmapFile) {
		this.sqlmapFile = sqlmapFile;
	}

	public void read(SqlRegistry sqlRegistry) {
		String contextPath = Sqlmap.class.getPackage().getName(); 
		try {
			JAXBContext context = JAXBContext.newInstance(contextPath);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			InputStream is = UserDao.class.getResourceAsStream(sqlmapFile);
			Sqlmap sqlmap = (Sqlmap)unmarshaller.unmarshal(is);
			for(SqlType sql : sqlmap.getSql()) {
				sqlRegistry.registerSql(sql.getKey(), sql.getValue());
			}
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		} 		
	}

}
