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
		category.setRootLink(StringUtils.replace(configMapping.getCategoryRootLink(), "{pageNumber}", ""));
		category.setActive(1);
		category.setStatus(0);
		category.setCateSlug(SlugUtils.toSlug(configMapping.getCategoryName()));
		
		List<Content> contentList = new ArrayList<Content>();
		category.setContents(contentList);

		Connection.Response response = null;
		int i = 1;

		while(true) {
			try {
				String link = StringUtils.replace(configMapping.getCategoryRootLink(), "{pageNumber}", i + "");
				response = getJsoupConnection(link).ignoreHttpErrors(true).execute();
				
				if ((response == null || response.statusCode() != 200)) {
					return category;
				}	
				
				Elements elements = response.parse().select(configMapping.getElement());
				if (elements == null) {
					return category;
				}

				elements.forEach(e -> {
					contentList.add(getContentElement(e));
				});
			} catch (IOException e) {
				e.printStackTrace();
			}

			i++;
		}
	}

	private Content getContentElement(Element element) {
		Content contentObj = new Content();
		contentObj.setActive(1);
		contentObj.setStatus(1);

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
