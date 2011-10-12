package com.railinc.jook.drawers.rapidcases;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

import com.railinc.footprints.service.FootprintsIntegrationService;

/**
 * Application object for your web application. If you want to run this application without deploying, run the Start class.
 * 
 * @see com.railinc.jook.drawers.rapidcases.Start#main(String[])
 */
public class WicketApplication extends WebApplication
{ 
	//@SpringBean(name="footprints")
	FootprintsIntegrationService footprintsService;
	
    public FootprintsIntegrationService getFootprintsService() {
		return footprintsService;
	}

	public void setFootprintsService(FootprintsIntegrationService footprintsService) {
		this.footprintsService = footprintsService;
	}

	/**
     * Constructor
     */
	public WicketApplication()
	{
		mountBookmarkablePage("issue", IssuePage.class);
		mountBookmarkablePage("home", HomePage.class);
	}
	
//	public void init() {
//		super.init();
//		addComponentInstantiationListener(new SpringComponentInjector(this));
//	}

	
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	public Class<HomePage> getHomePage()
	{
		return HomePage.class;
	}
	

}
