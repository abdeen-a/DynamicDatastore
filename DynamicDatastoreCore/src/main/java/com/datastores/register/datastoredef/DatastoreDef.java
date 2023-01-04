package com.datastores.register.datastoredef;

import com.datastores.register.DatastoreCredentials;
import com.datastores.register.DatastoreType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class DatastoreDef {
    private String datastoreId;
    private String jdbcUrl;
    private Map<String, List<String>> tables;
    private DatastoreCredentials datastoreCredentials;
    private DatastoreType datastoreType;
}
