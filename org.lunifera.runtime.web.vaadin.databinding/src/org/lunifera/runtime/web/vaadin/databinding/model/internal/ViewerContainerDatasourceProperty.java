/**
 * Copyright (c) 2011 - 2015, Lunifera GmbH (Gross Enzersdorf), Loetz KG (Heidelberg)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *         Florian Pirchner - Initial implementation
 */
package org.lunifera.runtime.web.vaadin.databinding.model.internal;

import org.eclipse.core.databinding.property.INativePropertyListener;
import org.eclipse.core.databinding.property.ISimplePropertyListener;
import org.lunifera.runtime.web.vaadin.databinding.properties.AbstractVaadinValueProperty;

import com.vaadin.data.Container;

/**
 */
public class ViewerContainerDatasourceProperty extends
		AbstractVaadinValueProperty {

	public ViewerContainerDatasourceProperty() {
		super();
	}

	@Override
	public INativePropertyListener adaptListener(
			ISimplePropertyListener listener) {
		return null;
	}

	public Object getValueType() {
		return Container.class;
	}

	protected Object doGetValue(Object source) {
		Container.Viewer component = (Container.Viewer) source;
		return component.getContainerDataSource();
	}

	protected void doSetValue(Object source, Object value) {
		Container.Viewer component = (Container.Viewer) source;
		component.setContainerDataSource((Container) value);
	}

}