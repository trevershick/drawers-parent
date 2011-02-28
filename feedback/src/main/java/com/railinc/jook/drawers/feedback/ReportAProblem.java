package com.railinc.jook.drawers.feedback;

import org.apache.click.control.Submit;
import org.apache.click.control.TextArea;
import org.apache.click.control.TextField;
import org.apache.click.util.Bindable;

public class ReportAProblem extends DrawerForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -518770503041992248L;
	
	@Bindable
	protected LinkBar links = new LinkBar(this);
	
	
	
	public ReportAProblem() {
		form.setId("drw" + String.valueOf(Math.abs(serialVersionUID)));
		TextArea idea = new TextArea("idea", "Description");
		idea.setRequired(true);
		TextField summary = new TextField("summary",
				"Short title", 40, true);
		Submit submit = new Submit("submit", "Continue");
		
		
		
		form.add(idea);
		form.add(summary);
		form.add(submit);
		form.setListener(this, "onSubmit");
		form.setLabelsPosition("top");
		form.setJavaScriptValidation(false);
		this.setTitle("Report a problem");
	}

	/*
	 * Handle the form submit event.
	 */
	public boolean onSubmit() {
		if (form.isValid()) {
			this.flash.add("Your problem has been submitted");
		} 
		return true;
	}
}
