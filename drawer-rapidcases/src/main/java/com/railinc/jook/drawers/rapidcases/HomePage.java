package com.railinc.jook.drawers.rapidcases;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;

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
	

    /**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
    public HomePage(final PageParameters parameters) {

    	Panel myCasesPanel = new Panel("myCasesPanel");
        getRapidCasesForUser(myCasesPanel);
        //add(myCasesPanel);
    }

	@SuppressWarnings("serial")
	private void getRapidCasesForUser(Panel myCasesPanel) {
		Label errorMesgLabel = new Label("errorMessage", "");
		add(errorMesgLabel);
		try {
			FootprintsIntegrationService fpIntegrationService = getFootprintsService();
			if (fpIntegrationService == null) {
				remove(errorMesgLabel);
				add(new Label("errorMessage", "My cases could not be retrieved."));
				return;
			}
			
			List<Long> allOpenIssueIds = fpIntegrationService.getIssueIds(
					buildCriteria(getUserId(), FILTER_OPEN), 
					Arrays.asList(HYPHEN + Issue.PROPERTY_LASTUPDATEDON));
			StringBuilder issueIdStr = new StringBuilder();
			List<IssueDetail> issueDetailList = new ArrayList<IssueDetail>();
			for (long issueId : allOpenIssueIds){
				issueIdStr.append(Long.toString(issueId)).append(LINE_BREAK);
				IssueDetail issueDetail = getFootprintsService().getIssueDetail(issueId);
				issueDetailList.add(issueDetail);
			}
			//add(new MultiLineLabel("issueIdList", issueIdStr.toString()));
			DataView<IssueDetail> issueListView = new DataView<IssueDetail>("issueListView", new ListDataProvider<IssueDetail>(issueDetailList)) {

				@Override
				protected void populateItem(Item<IssueDetail> item) {
					IssueDetail issueDetail = item.getModelObject();
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
			add(issueListView);
			
		} catch (ServiceNotAvailableException e) {
			remove(errorMesgLabel);
			add(new Label("errorMessage", "My cases could not be retrieved."));
		}
		
	}
	
	private String getUserId() {
		return "ITSXS01";
	}

	private Map<String,List<String>> buildCriteria(String userId, String filter) {
		Map<String,List<String>> criteria = new HashMap<String,List<String>>();
		criteria.put(Issue.PROPERTY_SSOUSERID, Arrays.asList(userId));
		// open is default
		criteria.put(HYPHEN + Issue.PROPERTY_STATUS, Arrays.asList(STATUS_CLOSED,STATUS_DELETED));
		return criteria;
	}
}
