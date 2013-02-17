/**
 * Copyright (c) 2012 Committers of lunifera.org.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Florian Pirchner - initial API and implementation
 */
package org.lunifera.runtime.web.http.tests;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.equinox.http.servlet.ExtendedHttpService;
import org.knowhowlab.osgi.testing.assertions.BundleAssert;
import org.knowhowlab.osgi.testing.assertions.ServiceAssert;
import org.lunifera.runtime.web.http.IHttpApplication;
import org.lunifera.runtime.web.jetty.IHandlerProvider;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.cm.ConfigurationAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Activator implements BundleActivator {

	private static final Logger logger = LoggerFactory
			.getLogger(Activator.class);

	private static Activator instance;
	public static BundleContext context;

	private ConfigurationAdmin cmAdmin;
	private List<IHttpApplication> httpApplications = new ArrayList<IHttpApplication>();
	private List<ExtendedHttpService> httpServices = new ArrayList<ExtendedHttpService>();
	private List<IHandlerProvider> handlerProviders = new ArrayList<IHandlerProvider>();

	/**
	 * @return the instance
	 */
	public static Activator getInstance() {
		return instance;
	}

	/**
	 * @return the applications
	 */
	public List<IHttpApplication> getHttpApplications() {
		return httpApplications;
	}

	/**
	 * @return the httpServices
	 */
	public List<ExtendedHttpService> getHttpServices() {
		return httpServices;
	}

	/**
	 * @return the httpServices
	 */
	public List<IHandlerProvider> getHandlerProvider() {
		return handlerProviders;
	}

	/**
	 * Returns the configuration admin service.
	 * 
	 * @return
	 */
	public ConfigurationAdmin getConfigurationAdmin() {
		return cmAdmin;
	}

	@Override
	public void start(BundleContext context) throws Exception {
		Activator.context = context;
		instance = this;

		BundleAssert.setDefaultBundleContext(context);
		ServiceAssert.setDefaultBundleContext(context);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		Activator.context = null;
		instance = null;
	}

	/**
	 * OSGi-DS component
	 */
	public static final class Component {

		/**
		 * Called by OSGi-DS
		 * 
		 * @param httpService
		 */
		public void addHttpService(ExtendedHttpService httpService) {
			Activator.getInstance().httpServices.add(httpService);
		}

		/**
		 * Called by OSGi-DS
		 * 
		 * @param httpService
		 */
		public void removeHttpService(ExtendedHttpService httpService) {
			Activator.getInstance().httpServices.remove(httpService);
		}

		/**
		 * Called by OSGi-DS
		 * 
		 * @param provider
		 */
		public void addHandlerProvider(IHandlerProvider provider) {
			Activator.getInstance().handlerProviders.add(provider);
		}

		/**
		 * Called by OSGi-DS
		 * 
		 * @param provider
		 */
		public void removeHandlerProvider(IHandlerProvider provider) {
			Activator.getInstance().handlerProviders.remove(provider);
		}

		/**
		 * Called by OSGi-DS
		 * 
		 * @param httpApplication
		 */
		public void addHttpApplication(IHttpApplication httpApplication) {
			Activator.getInstance().httpApplications.add(httpApplication);
		}

		/**
		 * Called by OSGi-DS
		 * 
		 * @param httpApplication
		 */
		public void removeHttpApplication(IHttpApplication httpApplication) {
			Activator.getInstance().httpApplications.remove(httpApplication);
		}

		/**
		 * Called by OSGi-DS
		 * 
		 * @param httpApplication
		 */
		public void setCMAdmin(ConfigurationAdmin cmAdmin) {
			Activator.getInstance().cmAdmin = cmAdmin;
		}

		/**
		 * Called by OSGi-DS
		 * 
		 * @param httpApplication
		 */
		public void unsetCMAdmin(ConfigurationAdmin cmAdmin) {
			Activator.getInstance().cmAdmin = null;
		}

	}
}
