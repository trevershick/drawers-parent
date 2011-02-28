package com.railinc.jook.drawers.feedback;

import java.util.ArrayList;
import java.util.List;

import org.apache.click.Page;
import org.apache.click.control.Form;
import org.apache.click.util.Bindable;

public abstract class DrawerForm extends Page {
	/**
	 * 
	 */
	private static final long serialVersionUID = 678331435663332563L;

	@Bindable
	protected String title = null;

	@Bindable
	protected Form form = new Form();

	@Bindable
	protected List<String> flash = new ArrayList<String>();

	protected DrawerForm() {
		this.setTemplate("form-template.htm");
	}
	
	public Form getForm() {
		return form;
	}

	public void setForm(Form form) {
		this.form = form;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	
}
