package com.datastores.query.dao;

import com.datastores.register.exception.DatastoreRegisterException;

import java.util.List;
import java.util.Map;

public interface QueryDao {
    List<Map<String, Object>> executeQuery(String query) throws DatastoreRegisterException;
}
