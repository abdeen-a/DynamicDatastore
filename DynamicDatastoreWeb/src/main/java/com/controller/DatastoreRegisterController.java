package com.controller;

import com.datastores.register.*;
import com.datastores.register.datastoredef.DatastoreDef;
import com.datastores.register.exception.DatastoreRegisterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class DatastoreRegisterController {

    @Autowired
    DatastoreRegisterFactory datastoreRegisterFactory;

    @Autowired
    DatastoreRegistry datastoreRegistry;

        @PostMapping("/api/register")
        public String registerDatastore(
                                                  @RequestParam("datastoreType") String datastoreType,
                                                  @RequestParam("jdbcUrl") String jdbcUrl,
                                                  @RequestParam("username") String username,
                                                  @RequestParam("password") String password
                                                  ) throws DatastoreRegisterException {
        System.out.println("Invoking registerDatastore...");
        DatastoreRegisterModel datastoreRegisterModel = datastoreRegisterFactory.getDatastoreRegistrationImpl(datastoreType);
        datastoreRegisterModel.registerDatastore(jdbcUrl, new DatastoreCredentials(username, password));
        datastoreRegistry.addDatastore(datastoreRegisterModel);

        return datastoreRegisterModel.getRegisteredDatastoreDef().getDatastoreId();
    }

    @GetMapping("/api/listdatastores")
    public List<DatastoreDef> listRegisterdDatastores() {
            return new ArrayList<>(datastoreRegistry.listRegisteredDatastores());
    }
}
