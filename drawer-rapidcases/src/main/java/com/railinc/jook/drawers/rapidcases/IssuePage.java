package com.railinc.jook.drawers.rapidcases;

import org.apache.wicket.markup.html.basic.Label;

import com.railinc.footprints.model.IssueDetail;
import com.railinc.footprints.service.ServiceNotAvailableException;

/**
 * Homepage
 */
public class IssuePage extends BasePage {

	Label titleLabel = new Label("title","");

    /**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
     * @throws ServiceNotAvailableException 
	 */
    public IssuePage(long issueId) {
//    	long issueId = parameters.getAsLong("issueId");
    	add(titleLabel);
    	try {
			IssueDetail detail = this.getFootprintsService().getIssueDetail(issueId);
		} catch (Exception e) {
			this.titleLabel.setDefaultModelObject("Couldn't load the info.");
		}
  	}
}
