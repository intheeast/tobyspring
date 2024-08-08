package com.intheeast.springframe.sqlservice;

import javax.annotation.PostConstruct;

public class BaseSqlService implements SqlService {

	private SqlReader sqlReader;
	private SqlRegistry sqlRegistry;
		
	public void setSqlReader(SqlReader sqlReader) {
		this.sqlReader = sqlReader;
	}

	public void setSqlRegistry(SqlRegistry sqlRegistry) {
		this.sqlRegistry = sqlRegistry;
	}

	//@PostConstruct : Spring IoC에 의해 호출될이 없음.
	public void loadSql() {
		// this.sqlReader is OxmSqlService.oxmSqlReader
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

}
