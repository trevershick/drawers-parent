package com.railinc.jook.drawers.feedback;

import java.util.List;

import org.apache.click.Control;
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
	private PageLink givePraiseLink;

	public LinkBar(DrawerForm parent) {
		
		
		ideaLink = new PageLink(IdeaForm.class);
		ideaLink.setLabel("Share an idea");
		ideaLink.addStyleClass("linkbar");
		
		reportAProblemLink = new PageLink(ReportAProblem.class);
		reportAProblemLink.setLabel("Report a problem");
		reportAProblemLink.addStyleClass("linkbar");

		givePraiseLink = new PageLink(GivePraise.class);
		givePraiseLink.setLabel("Give praise");
		givePraiseLink.addStyleClass("linkbar");
		
		
		add(ideaLink);
		add(reportAProblemLink);
		add(givePraiseLink);
	}

	@Override
	public void render(HtmlStringBuffer buffer) {
		buffer.append("<hr/>");
		buffer.append("<span>");
		buffer.append("Or ");
		
		List<Control> ctls = getControls();
		for (int i=0;i< ctls.size();i++) {
			if (i>0) {
				buffer.append(", ");
			}
			ctls.get(i).render(buffer);
		}
		
		
		buffer.append("</span>");

	}
	
}
