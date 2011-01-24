package com.railinc.jook.drawers.documentation;

import java.io.Serializable;

public class UserDocument implements Comparable<UserDocument>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4265926657479135820L;
	private int key;
	private String name;
	private String relativeUrl;
	
	public int getKey() {
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
	public void setKey(int elementText) {
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

	public int compareTo(UserDocument o) {
		String lhs = name == null ? "" : name.toUpperCase();
		String rhs = o.name == null ? "" : o.name.toUpperCase();
		return lhs.compareTo(rhs);
	}
}
