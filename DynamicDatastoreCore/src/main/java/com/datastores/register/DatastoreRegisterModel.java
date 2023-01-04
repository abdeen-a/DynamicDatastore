package com.datastores.register;

import com.datastores.register.datastoredef.DatastoreDef;
import com.datastores.register.exception.DatastoreRegisterException;

public interface DatastoreRegisterModel {
    void registerDatastore(String jdbcUrl, DatastoreCredentials datastoreCredentials)
            throws DatastoreRegisterException;
    DatastoreDef getRegisteredDatastoreDef();
}
