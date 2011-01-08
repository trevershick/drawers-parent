package com.railinc.jook.drawers.feedback;

import org.apache.click.Page;
import org.apache.click.control.AbstractLink;
import org.apache.click.control.Form;
import org.apache.click.control.PageLink;
import org.apache.click.util.Bindable;

public class GivePraise extends Page {

	/**
	 * 
	 */
	private static final long serialVersionUID = -518770503041992248L;
	@Bindable
	protected Form form = new Form();
	
	@Bindable
	protected AbstractLink ideaLink;
	
	public GivePraise() {
		
		ideaLink = new PageLink(IdeaForm.class);
		ideaLink.setLabel("Share an idea");
	}

}
