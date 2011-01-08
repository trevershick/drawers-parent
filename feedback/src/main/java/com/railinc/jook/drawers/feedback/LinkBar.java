package com.railinc.jook.drawers.feedback;

import org.apache.click.control.Container;
import org.apache.click.control.PageLink;
import org.apache.click.control.Panel;
import org.apache.click.util.HtmlStringBuffer;

public class LinkBar extends Panel {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8837966779025626297L;
	private PageLink reportAProblemLink;
	private PageLink ideaLink;

	public LinkBar(Container onPage) {
		
		
		ideaLink = new PageLink(IdeaForm.class);
		ideaLink.setLabel("Share an idea");
		
		reportAProblemLink = new PageLink(ReportAProblem.class);
		reportAProblemLink.setLabel("Report a problem");

		add(ideaLink);
		add(reportAProblemLink);
	}

	@Override
	public void render(HtmlStringBuffer buffer) {
		buffer.append("<hr/>");
		buffer.append("<span>");
		buffer.append("Or ");
		ideaLink.render(buffer);
		buffer.append(", ");
		reportAProblemLink.render(buffer);
		buffer.append("</span>");

	}
	
}
