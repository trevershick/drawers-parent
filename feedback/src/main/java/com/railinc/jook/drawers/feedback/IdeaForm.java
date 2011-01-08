package com.railinc.jook.drawers.feedback;

import org.apache.click.Page;
import org.apache.click.control.Form;
import org.apache.click.control.PageLink;
import org.apache.click.control.Submit;
import org.apache.click.control.TextArea;
import org.apache.click.control.TextField;
import org.apache.click.util.Bindable;

public class IdeaForm extends Page {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3867652277275848298L;

	@Bindable
	protected Form form = new Form();

	@Bindable
	protected LinkBar links = new LinkBar(form);

	@Bindable
	protected String msg;

	public IdeaForm() {
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
			msg = "Your name is " + form.getFieldValue("name");
		} 
		return true;
	}
}
