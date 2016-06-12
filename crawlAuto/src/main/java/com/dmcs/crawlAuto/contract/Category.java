package com.dmcs.crawlAuto.contract;

import java.util.List;

public class Category {
	private int categoryId;
	private String categoryName;
	private String rootLink;
	private String cateLink;
	private String cateSlug;
	private int status;
	private int active;

	public String getRootLink() {
		return rootLink;
	}

	public void setRootLink(String rootLink) {
		this.rootLink = rootLink;
	}

	public String getCateLink() {
		return cateLink;
	}

	public void setCateLink(String cateLink) {
		this.cateLink = cateLink;
	}

	public String getCateSlug() {
		return cateSlug;
	}

	public void setCateSlug(String cateSlug) {
		this.cateSlug = cateSlug;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	private List<Content> contents;

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public List<Content> getContents() {
		return contents;
	}

	public void setContents(List<Content> contents) {
		this.contents = contents;
	}

}
