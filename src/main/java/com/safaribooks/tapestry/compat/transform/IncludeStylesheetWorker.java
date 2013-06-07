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

package com.safaribooks.tapestry.compat.transform;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.IncludeStylesheet;
import org.apache.tapestry5.func.Worker;
import org.apache.tapestry5.ioc.services.SymbolSource;
import org.apache.tapestry5.plastic.PlasticClass;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

/** New implementation of the IncludeSytlesheetWorker.  This is a blatent ripoff of the ImportWorker
 * in the new T5.
 * @author ryan
 */
public class IncludeStylesheetWorker extends AbstractIncludeWorker {

    private final JavaScriptSupport javascriptSupport;

    public IncludeStylesheetWorker(final JavaScriptSupport javascriptSupport, SymbolSource symbolSource, AssetSource assetSource) {
        super(symbolSource, assetSource);
        this.javascriptSupport = javascriptSupport;
    }

    @Override
    protected Worker<Asset> getWorker() {
        return new Worker<Asset>()
        {
            @Override
            public void work(Asset asset)
            {
                javascriptSupport.importStylesheet(asset);
            }
        };
    }
    
    @Override
    protected String[] getPaths(PlasticClass componentClass) {
        IncludeStylesheet annotation = componentClass.getAnnotation(IncludeStylesheet.class);
        
        if (annotation == null) { return null; }
        
        return annotation.value();
    }
   

}
