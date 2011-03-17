package com.railinc.jook.drawers.rapidcases;

import org.apache.wicket.markup.html.WebPage;

import com.railinc.footprints.service.FootprintsIntegrationService;

public class BasePage extends WebPage {

	FootprintsIntegrationService footprintsService;

	public FootprintsIntegrationService getFootprintsService() {
		return ((WicketApplication)getApplication()).getFootprintsService();
	}
	
}
