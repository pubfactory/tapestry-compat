package com.safaribooks.tapestry.compat.transform;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.IncludeJavaScriptLibrary;
import org.apache.tapestry5.func.Worker;
import org.apache.tapestry5.ioc.services.SymbolSource;
import org.apache.tapestry5.plastic.PlasticClass;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

/** New implementation of the IncludeJavaScriptLibrary.  This is a blatent ripoff of the ImportWorker
 * in the new T5.
 * @author ryan
 */
public class IncludeJavaScriptLibraryWorker extends AbstractIncludeWorker {
    
    private final JavaScriptSupport javascriptSupport;
    
    public IncludeJavaScriptLibraryWorker(final JavaScriptSupport javascriptSupport, SymbolSource symbolSource, AssetSource assetSource) {
        super(symbolSource, assetSource);
        this.javascriptSupport = javascriptSupport;
    }
    
    @Override
    protected Worker<Asset> getWorker() {
        return new Worker<Asset>()
        {
            public void work(Asset asset)
            {
                javascriptSupport.importJavaScriptLibrary(asset);
            }
        };
    }
    
    @Override
    protected String[] getPaths(PlasticClass componentClass) {
        IncludeJavaScriptLibrary annotation = componentClass.getAnnotation(IncludeJavaScriptLibrary.class);
        
        if (annotation == null) { return null; }
        
        return annotation.value();
    }
    
}
