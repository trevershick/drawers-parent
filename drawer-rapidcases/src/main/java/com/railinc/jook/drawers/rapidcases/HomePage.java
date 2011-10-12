package com.railinc.jook.drawers.rapidcases;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.PageParameters;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.Model;
import org.apache.wicket.protocol.http.servlet.ServletWebRequest;
import org.springframework.ws.client.WebServiceTransportException;

import com.railinc.footprints.model.Issue;
import com.railinc.footprints.model.PagedResult;
import com.railinc.footprints.service.FootprintsIntegrationService;
import com.railinc.footprints.service.ServiceNotAvailableException;

/**
 * Homepage
 */
public class HomePage extends BasePage {

	private static final long serialVersionUID = 1L;

	// Add any page properties or variables here
	private static final String HYPHEN = "-";
	private static final String STATUS_CLOSED = "Closed";
	private static final String STATUS_DELETED = "Deleted";


	private Label errorMesgLabel;

	private List<Issue> issueDetailList;

	private DataView<Issue> issueListView;

	private WebMarkupContainer myCasesPanel;
	

    /**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
    public HomePage(final PageParameters parameters) {
    	myCasesPanel = new WebMarkupContainer("myCasesPanel");
    	myCasesPanel.setVisible(false);
    	add(myCasesPanel);
    	
    	errorMesgLabel = new Label("errorMessage", "");
		errorMesgLabel.setVisible(false);
		add(errorMesgLabel);
		
		
		
		issueDetailList = new ArrayList<Issue>();
		issueListView = new DataView<Issue>("issueListView", new ListDataProvider<Issue>(issueDetailList)) {
			@Override
			protected void populateItem(Item<Issue> item) {
				Issue issueDetail = item.getModelObject();
				ExternalLink el = new ExternalLink("pageLink", "/drawer-rapidcases/app/issue/issueId/" + issueDetail.getId());
				el.add(new AttributeAppender("class", new Model("jook_popup_link")," "));
				//pl.setParameter("issueId", issueDetail.getId());

				el.add(new Label("id", Long.toString(issueDetail.getId())));
				item.add(el);
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

		
		//BookmarkablePageLink pl = new BookmarkablePageLink<Object>("pageLink",IssuePage.class, new PageParameters("issueId=2576"));
		
//		new IPageLink() {
//	        public Page getPage() {
//	            return new IssuePage(1234);
//	        }
//	        public Class getPageIdentity() {
//	            return IssuePage.class;
//	        }
//	    });
//		
//		pl.add(new AttributeAppender("class", new Model("jook_popup_link")," "));
//		add(pl);
//		
		
        getRapidCasesForUser();
    }

	@SuppressWarnings("serial")
	private void getRapidCasesForUser() {
		try {
			FootprintsIntegrationService fpIntegrationService = getFootprintsService();
			if (fpIntegrationService == null) {
				setErrorMessage("My cases could not be retrieved.");
				return;
			}
			 int pageNumber = 1;
             int pageSize = 5;
            
             
			ServletWebRequest r = (ServletWebRequest) getRequest();
			String userId = r.getHttpServletRequest().getRemoteUser();

			Map<String,List<String>> criteria = new HashMap<String,List<String>>();
			criteria.put(Issue.PROPERTY_SSOUSERID, Arrays.asList(userId));
			criteria.put(HYPHEN + Issue.PROPERTY_STATUS, Arrays.asList(STATUS_CLOSED,STATUS_DELETED));
			
			
			PagedResult<Issue> issueList = fpIntegrationService.getIssueList(criteria, Arrays.asList(HYPHEN + Issue.PROPERTY_LASTUPDATEDON), pageNumber, pageSize);
			issueDetailList.addAll(issueList.getResults());
			
			
			this.issueListView.setVisible(true);
			myCasesPanel.setVisible(issueDetailList.size()>0);
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
