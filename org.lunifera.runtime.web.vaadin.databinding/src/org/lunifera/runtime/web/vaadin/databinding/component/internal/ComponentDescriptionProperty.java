/*******************************************************************************
 * Copyright (c) 2012 by committers of lunifera.org

 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Based on ideas of org.eclipse.jface.databinding.swt (EPL)
 * 
 * Contributor:
 * 		Florian Pirchner - porting swt databinding to support vaadin
 * 
 *******************************************************************************/

package org.lunifera.runtime.web.vaadin.databinding.component.internal;

import org.lunifera.runtime.web.vaadin.databinding.properties.AbstractVaadinValueProperty;

import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Component;

/**
 * 
 */
public class ComponentDescriptionProperty extends
		AbstractVaadinValueProperty {
	public String toString() {
		return "ComponentDescriptionProperty"; //$NON-NLS-1$
	}

	public ComponentDescriptionProperty() {
		super();
	}

	public Object getValueType() {
		return String.class;
	}

	protected Object doGetValue(Object source) {
		Component component = (Component) source;
		return component.getDescription();
	}

	protected void doSetValue(Object source, Object value) {
		if(source instanceof AbstractComponent){
			AbstractComponent comp = (AbstractComponent) source;
			comp.setDescription((String) value);
		}else{
			throw new UnsupportedOperationException("setDescription missing in interface!");
		}
	}
}