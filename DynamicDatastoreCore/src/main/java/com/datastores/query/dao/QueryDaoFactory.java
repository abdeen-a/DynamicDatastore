package com.datastores.query.dao;

import com.datastores.register.datastoredef.DatastoreDef;
import org.springframework.stereotype.Component;

@Component
public class QueryDaoFactory {
    public QueryDao createDao(DatastoreDef datastoreDef) {
        return new QueryDaoImpl(datastoreDef);
    }
}
