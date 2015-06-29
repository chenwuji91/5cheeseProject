package database;

import java.beans.PropertyVetoException;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DbPool {
	public static ComboPooledDataSource ds2=new ComboPooledDataSource();
	public static void init()
	{
		try {
			ds2.setDriverClass("com.mysql.jdbc.Driver");
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ds2.setJdbcUrl("jdbc:mysql://192.168.31.233:3306/suzhou?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull");
		ds2.setUser("admin");
		ds2.setPassword("8887799");	
		//ds2.set
		ds2.setMaxPoolSize(50);
		ds2.setMinPoolSize(5);
		ds2.setInitialPoolSize(8);
		ds2.setMaxStatements(180);
	}
}