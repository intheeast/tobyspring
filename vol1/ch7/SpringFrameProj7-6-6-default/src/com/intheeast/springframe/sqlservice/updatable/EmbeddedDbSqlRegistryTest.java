package com.intheeast.springframe.sqlservice.updatable;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import com.intheeast.springframe.sqlservice.SqlUpdateFailureException;
import com.intheeast.springframe.sqlservice.UpdatableSqlRegistry;

import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

import java.util.HashMap;
import java.util.Map;

public class EmbeddedDbSqlRegistryTest extends AbstractUpdatableSqlRegistryTest {
	EmbeddedDatabase db;
	
	@Override
	protected UpdatableSqlRegistry createUpdatableSqlRegistry() {
		db = new EmbeddedDatabaseBuilder()
			.setType(H2)
			.addScript("classpath:com/intheeast/springframe/sqlservice/updatable/sqlRegistrySchema.sql")
			.build();
		
		EmbeddedDbSqlRegistry embeddedDbSqlRegistry = new EmbeddedDbSqlRegistry();
		embeddedDbSqlRegistry.setDataSource(db);
		
		return embeddedDbSqlRegistry;
	}
	
	@AfterEach
	public void tearDown() {
		db.shutdown();
	}
	
	@Test
	public void transactionalUpdate() {
		checkFind("SQL1", "SQL2", "SQL3");
		
		Map<String, String> sqlmap = new HashMap<String, String>();
		sqlmap.put("KEY1", "Modified1");
		sqlmap.put("KEY9999!@#$", "Modified9999");
		
		try {
			sqlRegistry.updateSql(sqlmap);
			fail();
		}
		catch(SqlUpdateFailureException e) {}
		
		checkFind("SQL1", "SQL2", "SQL3");
	}
}