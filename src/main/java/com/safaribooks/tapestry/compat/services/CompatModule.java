// Copyright 2007 The Apache Software Foundation
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.safaribooks.tapestry.compat.services;

import org.apache.tapestry5.RenderSupport;
import org.apache.tapestry5.internal.services.RenderSupportImpl;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.services.transform.ComponentClassTransformWorker2;

import com.safaribooks.tapestry.compat.transform.IncludeJavaScriptLibraryWorker;
import com.safaribooks.tapestry.compat.transform.IncludeStylesheetWorker;

 
/** Register services used to support compatability. */
public class CompatModule {
	
	@SuppressWarnings("deprecation")
	public static void bind(ServiceBinder binder) {
		binder.bind(RenderSupport.class, RenderSupportImpl.class);
	}

    @Contribute(ComponentClassTransformWorker2.class)
    public static void provideTransformWorkers(
            OrderedConfiguration<ComponentClassTransformWorker2> configuration)
    {
        configuration.addInstance("IncludeStylesheet", IncludeStylesheetWorker.class);
        configuration.addInstance("IncludeJavaScriptLibrary", IncludeJavaScriptLibraryWorker.class);
    }
}
