package com.railinc.jook.drawers.feedback;

import org.apache.click.control.Submit;
import org.apache.click.control.TextArea;
import org.apache.click.control.TextField;
import org.apache.click.util.Bindable;

public class IdeaForm extends DrawerForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3867652277275848298L;

	@Bindable
	protected LinkBar links = new LinkBar(this);

	@Bindable
	protected String msg;

	public IdeaForm() {
		TextArea idea = new TextArea("idea", "Describe your idea");
		idea.setRequired(true);

		TextField summary = new TextField("summary",
				"Sum it up with a short title", 40, true);
		Submit submit = new Submit("submit", "Continue");

		form.add(idea);
		form.add(summary);
		form.add(submit);
		form.setListener(this, "onSubmit");
		form.setLabelsPosition("top");
		form.setId("drw" +Math.abs( serialVersionUID));
		setTitle("Share an idea");
	}

	/**
	 * Handle the form submit event.
	 */

	public boolean onSubmit() {
		if (form.isValid()) {
			ThankYouPage page = getContext().createPage(ThankYouPage.class);
			page.setMessage("Thank you for your idea! We'll take a look.");
			setForward(page);
		}
		return true;
	}
}
