/*******************************************************************************
 * Copyright (c) 2012 by committers of lunifera.org
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Florian Pirchner - initial API and implementation
 *    
 *******************************************************************************/
package org.lunifera.runtime.web.vaadin.standalone.webapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;

import org.lunifera.runtime.web.common.IWebContextRegistry;
import org.lunifera.runtime.web.vaadin.common.OSGiUIProvider;
import org.lunifera.runtime.web.vaadin.standalone.common.Constants;
import org.lunifera.runtime.web.vaadin.standalone.common.IVaadinWebApplication;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.server.UIProvider;

/**
 * A vaadin web application that is not activated by OSGi DS. It can be
 * instantiated by the constructor.
 */
public class EmbeddableVaadinWebApplication implements IVaadinWebApplication {

	private Logger logger = LoggerFactory.getLogger(VaadinWebApplication.class);

	private final BundleContext bundleContext;
	private String alias;
	private String id;
	private String widgetsetName;

	private HttpServiceTracker tracker;
	private UIProviderTracker uiProviderTracker;
	private List<OSGiUIProvider> uiProviders = new ArrayList<OSGiUIProvider>();

	public EmbeddableVaadinWebApplication(BundleContext bundleContext) {
		super();
		this.bundleContext = bundleContext;
	}

	/**
	 * Is called to activate the vaadin application.
	 * 
	 * @param properties
	 */
	public void activate(Map<String, Object> properties) {

		// remove the / from the alias
		alias = (String) properties.get(Constants.PROP_WEBAPP__ALIAS);
		if (alias != null) {
			alias = alias.replace("/", "");
		}

		widgetsetName = (String) properties.get(Constants.PROP_WIDGETSET);

		id = (String) properties.get(Constants.PROP_COMPONENT);

		try {
			uiProviderTracker = new UIProviderTracker(bundleContext, this);
		} catch (InvalidSyntaxException e) {
			new RuntimeException(e);
		}
		uiProviderTracker.open();

		tracker = new HttpServiceTracker(bundleContext, this);
		logger.debug("The alias that will be tracked is:\"" + alias);
		tracker.open();
	}

	/**
	 * Is called to deactivate the vaadin application.
	 * 
	 * @param properties
	 */
	public void deactivate(Map<String, Object> properties) {
		logger.debug("Tracker for alias" + tracker.getAlias() + " was removed.");
		if (uiProviderTracker != null) {
			uiProviderTracker.close();
			uiProviderTracker = null;
		}
		if (tracker != null) {
			tracker.close();
			tracker = null;
		}

		uiProviders = null;
	}

	@Override
	public String getAlias() {
		return alias;
	}

	@Override
	public String getName() {
		return id;
	}

	@Override
	public String getWidgetSetName() {
		return widgetsetName;
	}

	@SuppressWarnings("rawtypes")
	public void updated(Dictionary properties) {
		if (properties.get(Constants.PROP_WIDGETSET) != null) {
			widgetsetName = (String) properties.get(Constants.PROP_WIDGETSET);
		}
	}

	/**
	 * Adds a new {@link UIProvider} to the web application.
	 * 
	 * @param provider
	 */
	public void addOSGiUIProvider(OSGiUIProvider provider) {
		uiProviders.add(provider);
	}

	/**
	 * Removes an {@link UIProvider} from the web application.
	 * 
	 * @param provider
	 */
	public void removeOSGiUIProvider(OSGiUIProvider provider) {
		uiProviders.remove(provider);
	}

	@Override
	public List<OSGiUIProvider> getUiProviders() {
		return Collections.unmodifiableList(uiProviders);
	}

}