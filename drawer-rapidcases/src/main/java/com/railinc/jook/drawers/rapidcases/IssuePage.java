package com.railinc.jook.drawers.rapidcases;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;

import com.railinc.footprints.model.IssueDetail;
import com.railinc.footprints.service.ServiceNotAvailableException;

/**
 * Homepage
 */
public class IssuePage extends BasePage {

	Label titleLabel = new Label("title","");
	Label statusLabel = new Label("status","");
	Label bodyLabel = new Label("body","");
	Label submittedLabel = new Label("submitted","");

    public IssuePage(PageParameters pp) {
    	this(pp.getAsLong("issueId"));
    }
    /**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
     * @throws ServiceNotAvailableException 
	 */
    public IssuePage(long issueId) {

    	add(titleLabel);
    	add(statusLabel);
    	add(submittedLabel);
    	add(bodyLabel);
    	try {
			IssueDetail detail = this.getFootprintsService().getIssueDetail(issueId);
			this.titleLabel.setDefaultModelObject(detail.getTitle());
			this.statusLabel.setDefaultModelObject(detail.getStatus());
			this.bodyLabel.setDefaultModelObject(detail.getBody());
			this.submittedLabel.setDefaultModelObject(detail.getSubmitted());
		} catch (Exception e) {
			this.titleLabel.setDefaultModelObject("Couldn't load the info.");
		}
  	}
}
