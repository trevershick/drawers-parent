package com.railinc.jook.drawers.feedback;

import org.apache.click.Page;
import org.apache.click.control.Form;
import org.apache.click.control.Submit;
import org.apache.click.control.TextArea;
import org.apache.click.control.TextField;
import org.apache.click.util.Bindable;

public class ReportAProblem extends Page {

	/**
	 * 
	 */
	private static final long serialVersionUID = -518770503041992248L;
	@Bindable
	protected Form form = new Form();
	
	@Bindable
	protected LinkBar links = new LinkBar(form);
	
	public ReportAProblem() {
		TextArea idea = new TextArea("idea", "Describe your idea", 40, 4, true);
		TextField summary = new TextField("summary",
				"Sum it up with a short title", 40, true);
		Submit submit = new Submit("submit", "Continue");
			
		form.add(idea);
		form.add(summary);
		form.add(submit);
		form.setListener(this, "onSubmit");

	
	}

	/**
	 * Handle the form submit event.
	 */
	public boolean onSubmit() {
		if (form.isValid()) {
			System.out.println("Yay, valid form");
		} 
		return true;
	}
}
