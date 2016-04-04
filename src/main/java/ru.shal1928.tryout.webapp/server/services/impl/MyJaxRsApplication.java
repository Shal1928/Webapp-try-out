package ru.shal1928.tryout.webapp.server.services.impl;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 *
 */
//http://{hostname}:{port}/{context_root_of_Web_ module}/{value_of_@javax.ws.rs.ApplicationPath}}/{value_of_@javax.ws.rs.Path}
@ApplicationPath("rest")
public class MyJaxRsApplication extends Application {
    public Set<Class<?>> getClasses() {
        Set<Class<?>> s = new HashSet<>();
        s.add(HelloWordService.class);
        return s;
    }
}
