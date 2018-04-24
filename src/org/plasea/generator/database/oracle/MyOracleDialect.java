package org.plasea.generator.database.oracle;

import java.sql.Types;

import org.hibernate.Hibernate;
import org.hibernate.dialect.Oracle10gDialect;
import org.hibernate.type.StandardBasicTypes; 
public class MyOracleDialect extends Oracle10gDialect{  
    public MyOracleDialect() {  
        super();       
        registerHibernateType(Types.LONGVARCHAR, Hibernate.TEXT.getName());
    /*registerHibernateType(Types.LONGVARCHAR, StandardBasicTypes.TEXT.getName());
    registerHibernateType(Types.CHAR, StandardBasicTypes.STRING.getName());          
    registerHibernateType(Types.NVARCHAR, StandardBasicTypes.STRING.getName());        
    registerHibernateType(Types.LONGNVARCHAR, StandardBasicTypes.STRING.getName());  
    registerHibernateType(Types.DECIMAL, StandardBasicTypes.DOUBLE.getName());        
    registerHibernateType(Types.NCLOB, StandardBasicTypes.STRING.getName()); */ 
          
          
    }  
}  
