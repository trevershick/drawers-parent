/**
 * Copyright (c) Railinc Corporation, 2009.
 * All rights reserved.
 */
package com.railinc.jook.ssoclient;

import java.io.Serializable;

/**
 * @author sdtxs01
 *
 */
public class Resource implements Comparable<Resource>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5197387139063139295L;
	private String key;
	private String name;
	private String relativeUrl;
	
	public String getKey() {
		return key;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the relativeUrl
	 */
	public String getRelativeUrl() {
		return relativeUrl;
	}
	public void setKey(String elementText) {
		this.key = elementText;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @param relativeUrl the relativeUrl to set
	 */
	public void setRelativeUrl(String relativeUrl) {
		this.relativeUrl = relativeUrl;
	}

	public int compareTo(Resource o) {
		String lhs = name == null ? "" : name.toUpperCase();
		String rhs = o.name == null ? "" : o.name.toUpperCase();
		return lhs.compareTo(rhs);
	}
}
