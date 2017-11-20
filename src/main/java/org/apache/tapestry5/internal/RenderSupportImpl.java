package org.apache.tapestry5.internal;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.FieldFocusPriority;
import org.apache.tapestry5.RenderSupport;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.javascript.Initialization;
import org.apache.tapestry5.services.javascript.InitializationPriority;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.apache.tapestry5.services.javascript.ModuleConfigurationCallback;
import org.apache.tapestry5.services.javascript.StylesheetLink;
import org.apache.tapestry5.services.javascript.StylesheetOptions;

/**
 * @deprecated use {@link JavaScriptSupport} instead.
 */
public class RenderSupportImpl implements RenderSupport {

	final private JavaScriptSupport javaScriptSupport;
	final private AssetSource assetSource; 

	public RenderSupportImpl(final JavaScriptSupport javaScriptSupport, final AssetSource assetSource) {
		super();
		this.javaScriptSupport = javaScriptSupport;
		this.assetSource = assetSource;
	}

	public String allocateClientId(String id) {
		return javaScriptSupport.allocateClientId(id);
	}

	public String allocateClientId(ComponentResources resources) {
		return javaScriptSupport.allocateClientId(resources);
	}

	public void addScript(String format, Object... arguments) {
		javaScriptSupport.addScript(format, arguments);
	}

	public void addScript(InitializationPriority priority, String format,
			Object... arguments) {
		javaScriptSupport.addScript(priority, format, arguments);
	}

	public void addInitializerCall(String functionName, JSONObject parameter) {
		javaScriptSupport.addInitializerCall(functionName, parameter);
	}

	public void addInitializerCall(String functionName, JSONArray parameter) {
		javaScriptSupport.addInitializerCall(functionName, parameter);
	}

	public void addInitializerCall(InitializationPriority priority,
			String functionName, JSONArray parameter) {
		javaScriptSupport.addInitializerCall(priority, functionName, parameter);
	}

	public void addInitializerCall(InitializationPriority priority,
			String functionName, JSONObject parameter) {
		javaScriptSupport.addInitializerCall(priority, functionName, parameter);
	}

	public void addInitializerCall(String functionName, String parameter) {
		javaScriptSupport.addInitializerCall(functionName, parameter);
	}

	public void addInitializerCall(InitializationPriority priority,
			String functionName, String parameter) {
		javaScriptSupport.addInitializerCall(priority, functionName, parameter);
	}

	public RenderSupport importJavaScriptLibrary(Asset asset) {
		javaScriptSupport.importJavaScriptLibrary(asset);
		return this;
	}

	public RenderSupport importStylesheet(Asset stylesheet) {
		javaScriptSupport.importStylesheet(stylesheet);
		return this;
	}

	public RenderSupport importStylesheet(StylesheetLink stylesheetLink) {
		javaScriptSupport.importStylesheet(stylesheetLink);
		return this;
	}

	public RenderSupport importStack(String stackName) {
		javaScriptSupport.importStack(stackName);
		return this;
	}

	public RenderSupport importJavaScriptLibrary(String libraryURL) {
		javaScriptSupport.importJavaScriptLibrary(libraryURL);
		return this;
	}

	public void autofocus(FieldFocusPriority priority,
			String fieldId) {
		javaScriptSupport.autofocus(priority, fieldId);
	}

	public Initialization require(String moduleName) {
		return javaScriptSupport.require(moduleName);
	}

	public void addModuleConfigurationCallback(
			ModuleConfigurationCallback callback) {
		javaScriptSupport.addModuleConfigurationCallback(callback);
	}

	@Override
	public void addScriptLink(Asset... scriptAssets) {
		for (Asset asset : scriptAssets) {
			importJavaScriptLibrary(asset);
		}
	}

	@Override
	public void addScriptLink(String... scriptURLs) {
		for (String scriptURL : scriptURLs) {
			importJavaScriptLibrary(scriptURL);
		}
	}

	@Override
	public void addClasspathScriptLink(String... classpaths) {
		for (String classpath : classpaths) {
			addScriptLink(assetSource.getClasspathAsset(classpath));
		}
	}

	@Override
	public void addStylesheetLink(Asset stylesheet, String media) {
		addStylesheetLink(stylesheet, media);
	}

	@Override
	public void addStylesheetLink(String stylesheetURL, String media) {
		javaScriptSupport.importStylesheet(new StylesheetLink(stylesheetURL, new StylesheetOptions(media)));
	}

	@Override
	public void addScript(String script) {
		javaScriptSupport.addScript(script);
	}

	@Override
	public void addInit(String functionName, JSONArray parameterList) {
		javaScriptSupport.addInitializerCall(functionName, parameterList);
	}

	@Override
	public void addInit(String functionName, JSONObject parameter) {
		javaScriptSupport.addInitializerCall(functionName, parameter);
	}

	@Override
	public void addInit(String functionName, String... parameters) {
		javaScriptSupport.addInitializerCall(functionName, new JSONArray((Object[]) parameters));
	}

}
