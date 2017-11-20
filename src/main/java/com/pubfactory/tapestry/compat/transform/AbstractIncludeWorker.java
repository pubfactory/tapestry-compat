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

import java.util.Locale;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.func.F;
import org.apache.tapestry5.func.Mapper;
import org.apache.tapestry5.func.Worker;
import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.ioc.services.SymbolSource;
import org.apache.tapestry5.model.MutableComponentModel;
import org.apache.tapestry5.plastic.ComputedValue;
import org.apache.tapestry5.plastic.FieldHandle;
import org.apache.tapestry5.plastic.InstanceContext;
import org.apache.tapestry5.plastic.MethodAdvice;
import org.apache.tapestry5.plastic.MethodInvocation;
import org.apache.tapestry5.plastic.PlasticClass;
import org.apache.tapestry5.plastic.PlasticField;
import org.apache.tapestry5.plastic.PlasticMethod;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.TransformConstants;
import org.apache.tapestry5.services.transform.ComponentClassTransformWorker2;
import org.apache.tapestry5.services.transform.TransformationSupport;

/** 
 * Base class used by the JS and stylesheet include annotation workers.  This is based off
 * of the T5 ImportWorker implementation in 5.3.
 * @author ryan
 */
public abstract class AbstractIncludeWorker implements ComponentClassTransformWorker2 {

    private final SymbolSource symbolSource;

    private final AssetSource assetSource;

    public AbstractIncludeWorker(SymbolSource symbolSource, AssetSource assetSource) {
        this.symbolSource = symbolSource;
        this.assetSource = assetSource;
    }
    
    protected abstract Worker<Asset> getWorker();
    
    /** Null means there isn't an annotation. */
    protected abstract String[] getPaths(PlasticClass componentClass);
 
    private final Mapper<String, String> expandSymbols = new Mapper<String, String>()
            {

                @Override
                public String map(String element)
                {
                    return symbolSource.expandSymbols(element);
                }
            };

            private String[] expandPaths(String[] paths)
            {
                return F.flow(paths).map(expandSymbols).toArray(String.class);
            }

            private void initializeAssetsFromPaths(final Resource baseResource,
                    final String[] expandedPaths, final PlasticField assetsField)
            {
                assetsField.injectComputed(new ComputedValue<Asset[]>()
                {

                    @Override
                    public Asset[] get(InstanceContext context)
                    {
                        ComponentResources resources = context.get(ComponentResources.class);

                        return convertPathsToAssetArray(baseResource, resources.getLocale(), expandedPaths);
                    }
                });
            }

            private Asset[] convertPathsToAssetArray(final Resource baseResource, final Locale locale, String[] assetPaths)
            {
                return F.flow(assetPaths).map(new Mapper<String, Asset>()
                {

                    @Override
                    public Asset map(String assetPath)
                    {
                        return assetSource.getAsset(baseResource, assetPath, locale);
                    }
                }).toArray(Asset.class);
            }

            private void addMethodAssetOperationAdvice(PlasticMethod method, final FieldHandle access,
                    final Worker<Asset> operation)
            {
                method.addAdvice(new MethodAdvice()
                {

                    @Override
                    public void advise(MethodInvocation invocation)
                    {
                        Asset[] assets = (Asset[]) access.get(invocation.getInstance());

                        F.flow(assets).each(operation);

                        invocation.proceed();
                    }
                });
            }

            @Override
            public void transform(PlasticClass componentClass, TransformationSupport support, MutableComponentModel model) {
                String[] paths = getPaths(componentClass);
                if (paths != null && paths.length != 0) {

                    PlasticMethod method = componentClass.introduceMethod(TransformConstants.SETUP_RENDER_DESCRIPTION);

                    String[] expandedPaths = expandPaths(paths);

                    PlasticField assetListField = componentClass.introduceField(Asset[].class,
                            "importedAssets_" + method.getDescription().methodName);

                    initializeAssetsFromPaths(model.getBaseResource(), expandedPaths, assetListField);

                    addMethodAssetOperationAdvice(method, assetListField.getHandle(), getWorker());
                    
                    model.addRenderPhase(SetupRender.class);

                }
            }
}
