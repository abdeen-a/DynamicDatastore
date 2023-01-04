package com.controller;


import com.datastores.query.dao.QueryDao;
import com.datastores.register.DatastoreRegistry;
import com.datastores.register.exception.DatastoreRegisterException;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class QueryExectuteController {

    @Autowired
    DatastoreRegistry datastoreRegistry;

        @PostMapping("/api/query")
        public List<Map<String, Object>> executeQuery(@RequestParam("datastoreId")  String datastoreId,
                                                  @RequestParam("query") String query) throws DatastoreRegisterException {

         QueryDao queryDao =  datastoreRegistry.createDao(datastoreId);
         return queryDao.executeQuery(query);
    }

}
