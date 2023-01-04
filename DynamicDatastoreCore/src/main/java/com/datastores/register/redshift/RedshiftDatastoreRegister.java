package com.datastores.register.redshift;

import com.datastores.register.DatastoreCredentials;
import com.datastores.register.DatastoreRegisterModel;
import com.datastores.register.DatastoreType;
import com.datastores.register.datastoredef.DatastoreDef;
import com.datastores.register.exception.DatastoreRegisterException;
import org.springframework.util.ObjectUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class RedshiftDatastoreRegister implements DatastoreRegisterModel {

    private AtomicInteger counter = new AtomicInteger(0);

    private boolean isRegisteredSuccessfully = false;
    private DatastoreDef datastoreDef = null;
    @Override
    public void registerDatastore(String jdbcUrl, DatastoreCredentials datastoreCredentials) throws DatastoreRegisterException {
        if (datastoreDef != null) {
            throw new DatastoreRegisterException("Datastore already registered");
        }

        try {
            Class dbDriver = Class.forName(DatastoreType.REDSHIFT.getDriverName());
            Connection connection = DriverManager.getConnection(jdbcUrl, datastoreCredentials.getUsername(), datastoreCredentials.getPassword());
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT distinct PG.schemaname, PG.tablename" +
                    // ", PG.column, PG.type" +
                    " FROM PG_TABLE_DEF PG");
            Map<String, List<String>> schemaToTables = new HashMap<>();
            while(rs.next())
            {
                String schemaName = rs.getString("schemaname");
                String tableName = rs.getString("tablename");

                if (shouldExclude(schemaName, tableName)) {
                    continue;
                }
                List<String> tables = schemaToTables.get(schemaName);
                if (tables == null) {
                    tables = new ArrayList<>();
                }
                tables.add(tableName);
                schemaToTables.putIfAbsent(schemaName, tables);
            }

            datastoreDef = new DatastoreDef(generateDatastoreId(), jdbcUrl, schemaToTables, datastoreCredentials, DatastoreType.REDSHIFT);

            isRegisteredSuccessfully = true;
        } catch (ClassNotFoundException e) {
            throw new DatastoreRegisterException("Dependency not included in project classpath", e);
        } catch (SQLException e) {
            throw new DatastoreRegisterException("Could not create connection to database", e);
        }




    }

    private boolean shouldExclude(String schemaName, String tableName) {
        return ObjectUtils.isEmpty(schemaName) || ObjectUtils.isEmpty(tableName) || "pg_catalog".equals(schemaName);
    }

    private String generateDatastoreId() {
        return "redshift-" + counter.getAndIncrement();
    }

    @Override
    public DatastoreDef getRegisteredDatastoreDef() {
        if (!isRegisteredSuccessfully) {
            throw new RuntimeException("Datastore was not successfully registered");
        }
        return datastoreDef;
    }
}
