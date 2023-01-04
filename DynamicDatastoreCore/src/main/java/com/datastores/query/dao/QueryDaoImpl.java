package com.datastores.query.dao;

import com.datastores.register.datastoredef.DatastoreDef;
import com.datastores.register.exception.DatastoreRegisterException;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryDaoImpl implements  QueryDao{

    private final DatastoreDef datastoreDef;

    public QueryDaoImpl(DatastoreDef datastoreDef) {
        this.datastoreDef = datastoreDef;
    }

    @Override
    public List<Map<String, Object>> executeQuery( String query) throws DatastoreRegisterException {
        String jdbcUrl = datastoreDef.getJdbcUrl();
        datastoreDef.getDatastoreType();

        List<Map<String, Object>>  results = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(jdbcUrl,
                datastoreDef.getDatastoreCredentials().getUsername(),
                datastoreDef.getDatastoreCredentials().getPassword());
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query)) {


            final ResultSetMetaData meta = rs.getMetaData();
            final int columnCount = meta.getColumnCount();


            while(rs.next())
            {
                Map<String, Object> row = new HashMap<>();
                for (int column = 1; column <= columnCount; ++column)
                {
                    final Object value = rs.getObject(column);
                    String columnName = meta.getColumnName(column);
                    row.put(columnName, String.valueOf(value));
                }
                results.add(row);
            }
        } catch (SQLException e) {
            throw new DatastoreRegisterException("Could not create connection to database", e);
        }

        return results;
    }
}
