package com.railinc.jook.drawers.rapidcases;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.Page;
import org.apache.wicket.PageParameters;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.IPageLink;
import org.apache.wicket.markup.html.link.PageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.Model;
import org.apache.wicket.protocol.http.servlet.ServletWebRequest;
import org.springframework.ws.client.WebServiceTransportException;

import com.railinc.footprints.model.Issue;
import com.railinc.footprints.model.IssueDetail;
import com.railinc.footprints.service.FootprintsIntegrationService;
import com.railinc.footprints.service.ServiceNotAvailableException;

/**
 * Homepage
 */
public class HomePage extends BasePage {

	private static final long serialVersionUID = 1L;

	// Add any page properties or variables here
	private static final String HYPHEN = "-";
	
	private static final String FILTER_OPEN = "open";
	private static final String STATUS_CLOSED = "Closed";
	private static final String STATUS_DELETED = "Deleted";

	private static final Object LINE_BREAK = "<br/>";

	private Label errorMesgLabel;

	private ArrayList<IssueDetail> issueDetailList;

	private DataView<IssueDetail> issueListView;

	private Panel myCasesPanel;
	

    /**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
    public HomePage(final PageParameters parameters) {
    	myCasesPanel = new Panel("myCasesPanel");
    	myCasesPanel.setVisible(false);
    	add(myCasesPanel);
    	
    	errorMesgLabel = new Label("errorMessage", "");
		errorMesgLabel.setVisible(false);
		add(errorMesgLabel);
		
		
		
		issueDetailList = new ArrayList<IssueDetail>();
		issueListView = new DataView<IssueDetail>("issueListView", new ListDataProvider<IssueDetail>(issueDetailList)) {
			@Override
			protected void populateItem(Item<IssueDetail> item) {
				IssueDetail issueDetail = item.getModelObject();
//				Link l = new PageLink<T>(id, page)
					
				item.add(new Label("id", Long.toString(issueDetail.getId())));
				item.add(new Label("title", issueDetail.getTitle()));
				item.add(new Label("status", issueDetail.getStatus()));
				item.add(new Label("lastUpdated", formatDate(issueDetail.getLastUpdatedOn())));
				
			}
			private String formatDate(Date lastUpdatedOn) {
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
				return lastUpdatedOn == null ? " " : sdf.format(lastUpdatedOn);
			}
		};

		issueListView.setVisible(false);
		myCasesPanel.add(issueListView);

		PageLink pl = new PageLink<Object>("pageLink", new IPageLink() {
	        public Page getPage() {
	            return new IssuePage(1234);
	        }
	        public Class getPageIdentity() {
	            return IssuePage.class;
	        }
	    });
		pl.add(new AttributeAppender("onclick", new Model("alert('This is my JS script');return false;"), ";"));
		add(pl);
        getRapidCasesForUser(myCasesPanel);
    }

	@SuppressWarnings("serial")
	private void getRapidCasesForUser(Panel myCasesPanel) {
		try {
			FootprintsIntegrationService fpIntegrationService = getFootprintsService();
			if (fpIntegrationService == null) {
				setErrorMessage("My cases could not be retrieved.");
				return;
			}
			
			ServletWebRequest r = (ServletWebRequest) getRequest();
			String userId = r.getHttpServletRequest().getRemoteUser();
			
			List<Long> allOpenIssueIds = fpIntegrationService.getIssueIds(
					buildCriteria(userId, FILTER_OPEN), 
					Arrays.asList(HYPHEN + Issue.PROPERTY_LASTUPDATEDON));
			StringBuilder issueIdStr = new StringBuilder();
			for (long issueId : allOpenIssueIds){
				issueIdStr.append(Long.toString(issueId)).append(LINE_BREAK);
				IssueDetail issueDetail = getFootprintsService().getIssueDetail(issueId);
				issueDetailList.add(issueDetail);
			}
			myCasesPanel.setVisible(allOpenIssueIds.size() > 0);

		} catch (WebServiceTransportException e) {
			setErrorMessage("My cases could not be retrieved.");
		} catch (ServiceNotAvailableException e) {
			setErrorMessage("My cases could not be retrieved.");
		}
		
		
	}

	private void setErrorMessage(String message) {
		errorMesgLabel.setDefaultModelObject(message);
		errorMesgLabel.setVisible(true);
	}
	
	private Map<String,List<String>> buildCriteria(String userId, String filter) {
		Map<String,List<String>> criteria = new HashMap<String,List<String>>();
		criteria.put(Issue.PROPERTY_SSOUSERID, Arrays.asList(userId));
		// open is default
		criteria.put(HYPHEN + Issue.PROPERTY_STATUS, Arrays.asList(STATUS_CLOSED,STATUS_DELETED));
		return criteria;
	}
}
