package com.datastores.register;

import com.datastores.register.exception.DatastoreRegisterException;
import com.datastores.register.redshift.RedshiftDatastoreRegister;
import org.springframework.stereotype.Component;

@Component
public class DatastoreRegisterFactory {

    public DatastoreRegisterModel getDatastoreRegistrationImpl(String datastoreType) throws DatastoreRegisterException {
        if ("redshift".equalsIgnoreCase(datastoreType)) {
            return new RedshiftDatastoreRegister();
        }
        throw new DatastoreRegisterException("Unsupported Datastore type" + datastoreType);
    }
}
