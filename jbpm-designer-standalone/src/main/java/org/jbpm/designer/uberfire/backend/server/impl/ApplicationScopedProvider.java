package org.jbpm.designer.uberfire.backend.server.impl;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.uberfire.commons.cluster.ClusterServiceFactory;
import org.uberfire.io.IOService;
import org.uberfire.io.impl.IOServiceDotFileImpl;
import org.uberfire.io.impl.cluster.IOServiceClusterImpl;
import org.uberfire.backend.repositories.Repository;
import org.uberfire.backend.server.IOWatchServiceNonDotImpl;

import static org.uberfire.backend.server.repositories.SystemRepository.*;

/**
 * This class should contain all ApplicationScoped producers
 * required by the application.
 */
@ApplicationScoped
public class ApplicationScopedProvider {

    @Inject
    private IOWatchServiceNonDotImpl watchService;

    @Inject
    @Named("clusterServiceFactory")
    private ClusterServiceFactory clusterServiceFactory;

    private IOService ioService;

    @PostConstruct
    public void setup() {
        if ( clusterServiceFactory == null ) {
            ioService = new IOServiceDotFileImpl( watchService );
        } else {
            ioService = new IOServiceClusterImpl( new IOServiceDotFileImpl( watchService ), clusterServiceFactory, false );
        }
    }

    @Produces
    @Named("ioStrategy")
    public IOService ioService() {
        return ioService;
    }
}
