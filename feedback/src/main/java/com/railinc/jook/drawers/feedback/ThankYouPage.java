package com.railinc.jook.drawers.feedback;

import org.apache.click.Page;
import org.apache.click.util.Bindable;

public class ThankYouPage extends Page {
	@Bindable
	protected String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
