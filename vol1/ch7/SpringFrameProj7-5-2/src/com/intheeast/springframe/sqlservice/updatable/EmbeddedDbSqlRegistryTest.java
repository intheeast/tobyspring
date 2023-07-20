package com.intheeast.springframe.sqlservice.updatable;

import org.junit.jupiter.api.AfterEach;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import com.intheeast.springframe.sqlservice.UpdatableSqlRegistry;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

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
}