package com.dmcs.crawlAuto.contract;

import java.util.Map;

public class ConfigMapping {

	private String databaseConnection;
	private Map<String, String> requestHeader;

	private String categoryName;
	private int categoryTotalPage;
	private String categoryRootLink;

	private String element;
	private String title;
	private String imageThumb;
	private String description;
	private String linkDetail;
	private String imageLink;
	private String content;
	private String contentContainer;
	private String createDate;

	public String getCategoryRootLink() {
		return categoryRootLink;
	}

	public void setCategoryRootLink(String categoryRootLink) {
		this.categoryRootLink = categoryRootLink;
	}

	public String getTitle() {
		return title;
	}

	public String getContentContainer() {
		return contentContainer;
	}

	public void setContentContainer(String contentContainer) {
		this.contentContainer = contentContainer;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImageThumb() {
		return imageThumb;
	}

	public void setImageThumb(String imageThumb) {
		this.imageThumb = imageThumb;
	}

	public String getImageLink() {
		return imageLink;
	}

	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getDatabaseConnection() {
		return databaseConnection;
	}

	public void setDatabaseConnection(String databaseConnection) {
		this.databaseConnection = databaseConnection;
	}

	public String getElement() {
		return element;
	}

	public void setElement(String element) {
		this.element = element;
	}

	public String getLinkDetail() {
		return linkDetail;
	}

	public void setLinkDetail(String linkDetail) {
		this.linkDetail = linkDetail;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Map<String, String> getRequestHeader() {
		return requestHeader;
	}

	public void setRequestHeader(Map<String, String> requestHeader) {
		this.requestHeader = requestHeader;
	}

	public int getCategoryTotalPage() {
		return categoryTotalPage;
	}

	public void setCategoryTotalPage(int categoryTotalPage) {
		this.categoryTotalPage = categoryTotalPage;
	}
}
