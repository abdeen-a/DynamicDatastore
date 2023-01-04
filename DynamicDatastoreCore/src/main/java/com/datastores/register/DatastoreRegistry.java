package com.datastores.register;

import com.datastores.query.dao.QueryDao;
import com.datastores.register.datastoredef.DatastoreDef;

import java.util.Collection;

public interface DatastoreRegistry {

    QueryDao createDao(String databaseId);

    DatastoreDef addDatastore(DatastoreRegisterModel datastoreRegisterModel);
    Collection<DatastoreDef> listRegisteredDatastores();
}
