package com.datastores.register;

import com.datastores.query.dao.QueryDao;
import com.datastores.query.dao.QueryDaoFactory;
import com.datastores.register.datastoredef.DatastoreDef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DatastoreRegistryInMemoryImpl implements DatastoreRegistry {
    @Autowired
    private QueryDaoFactory queryDaoFactory;

    private Map<String, DatastoreDef> inMemoryCache = new ConcurrentHashMap<>();

    @Override
    public QueryDao createDao(String databaseId) {

        DatastoreDef datastoreDef=  inMemoryCache.get(databaseId);
        return queryDaoFactory.createDao(datastoreDef);
    }

    @Override
    public DatastoreDef addDatastore(DatastoreRegisterModel datastoreRegisterModel) {
        DatastoreDef datastoreDef = datastoreRegisterModel.getRegisteredDatastoreDef();
        inMemoryCache.put(datastoreDef.getDatastoreId(), datastoreDef);
        return datastoreDef;
    }

    @Override
    public Collection<DatastoreDef> listRegisteredDatastores() {
        return inMemoryCache.values();
    }
}
