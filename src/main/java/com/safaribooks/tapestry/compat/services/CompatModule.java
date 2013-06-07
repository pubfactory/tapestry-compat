package com.safaribooks.tapestry.compat.services;

import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.services.transform.ComponentClassTransformWorker2;

import com.safaribooks.tapestry.compat.transform.IncludeJavaScriptLibraryWorker;
import com.safaribooks.tapestry.compat.transform.IncludeStylesheetWorker;

 

public class CompatModule {

    @Contribute(ComponentClassTransformWorker2.class)
    public static void provideTransformWorkers(
            OrderedConfiguration<ComponentClassTransformWorker2> configuration)
    {
        configuration.addInstance("IncludeStylesheet", IncludeStylesheetWorker.class);
        configuration.addInstance("IncludeJavaScriptLibrary", IncludeJavaScriptLibraryWorker.class);
    }
}
