package com.datastores.register;

public enum DatastoreType {
    REDSHIFT("com.amazon.redshift.jdbc42.Driver"),
    BIG_QUERY("");

    DatastoreType(String driverName) {
        this.driverName = driverName;
    }
    String driverName;

    public String getDriverName() {
        return driverName;
    }
}
