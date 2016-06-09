package com.dmcs.crawlAuto.engines;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.dmcs.crawlAuto.contract.Category;
import com.dmcs.crawlAuto.contract.ConfigMapping;
import com.dmcs.crawlAuto.contract.Content;
import com.dmcs.crawlAuto.utils.SlugUtils;

public class CrawlEngine {

	private ConfigMapping configMapping;

	public CrawlEngine(ConfigMapping configMapping) {
		this.configMapping = configMapping;
	}

	public Category doCrawl() {
		Category category = new Category();
		category.setCategoryName(configMapping.getCategoryName());

		List<Content> contentList = new ArrayList<Content>();
		category.setContents(contentList);
		
		try {
			for (int i = 1; i <= configMapping.getCategoryTotalPage();i++) {
				String link = StringUtils.replace(configMapping.getCategoryRootLink(), "{pageNumber}", i + "");
				
				Document doc = getJsoupConnection(link).get();
				Elements elements = doc.select(configMapping.getElement());
				if (elements == null) {
					return null;
				}
				
				elements.forEach(e -> {
					contentList.add(getContentElement(e));
				});
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return category;
	}

	private Content getContentElement(Element element) {
		Content contentObj = new Content();

		try {
			String title = element.select(configMapping.getTitle()).first().text();
			contentObj.setTitle(title);
			contentObj.setSlug(SlugUtils.toSlug(title));

			String imageThumb = element.select(configMapping.getImageThumb()).first().attr("src");
			contentObj.setImageThumb(imageThumb);

			String description = element.select(configMapping.getDescription()).first().text();
			contentObj.setDescription(description);

			String linkDetail = element.select(configMapping.getLinkDetail()).first().attr("href");
			if (!StringUtils.isEmpty(linkDetail)) {
				Document detailDoc;

				detailDoc = getJsoupConnection(linkDetail).get();

				String createdDate = detailDoc.select(configMapping.getCreateDate()).first().text();
				contentObj.setCreateDate(createdDate);

				Element contentContainer = detailDoc.select(configMapping.getContentContainer()).first();
				String content = contentContainer.select(configMapping.getContent()).toString();
				contentObj.setContent(content);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return contentObj;
	}

	private Connection getJsoupConnection(String link) {
		Connection con = Jsoup.connect(link);
		con.userAgent("Mozilla/5.0");

		if (configMapping.getRequestHeader() != null) {
			configMapping.getRequestHeader().forEach((k, v) -> {
				con.header(k, v);
			});
		}

		return con;
	}
}
