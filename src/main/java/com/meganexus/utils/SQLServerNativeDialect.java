package com.meganexus.utils;
import java.sql.Types;

import org.hibernate.dialect.SQLServer2008Dialect;
import org.hibernate.dialect.SQLServer2012Dialect;
import org.hibernate.dialect.SQLServerDialect;
import org.hibernate.type.StandardBasicTypes;


public class SQLServerNativeDialect extends SQLServerDialect{
	 public SQLServerNativeDialect(){
	 super();
	 registerHibernateType(Types.NCHAR, StandardBasicTypes.CHARACTER.getName()); 
     registerHibernateType(Types.NCHAR, 1, StandardBasicTypes.CHARACTER.getName());
     registerHibernateType(Types.NCHAR, 255, StandardBasicTypes.STRING.getName());
     registerHibernateType(Types.NVARCHAR, StandardBasicTypes.STRING.getName());
     registerHibernateType(Types.LONGNVARCHAR, StandardBasicTypes.TEXT.getName());
     registerHibernateType(Types.NCLOB, StandardBasicTypes.CLOB.getName());
	}
}