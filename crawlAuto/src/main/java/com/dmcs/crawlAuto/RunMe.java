package com.dmcs.crawlAuto;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.dmcs.crawlAuto.contract.ConfigMapping;
import com.dmcs.crawlAuto.engines.CrawlEngine;
import com.dmcs.crawlAuto.engines.JsonEngine;

public class RunMe {
	public static void main(String arg[]) throws Exception {
		if (arg.length == 0) {
			System.out.println("Add mapping file or location path to run!");
			return;
		}

		File file = new File(arg[0]);

		if (!file.exists()) {
			System.out.println("File/Directory path does not exist!");
			return;
		}

		List<String> filePath = new ArrayList<String>();
		if (file.isFile()) {
			filePath.add(arg[0]);
		} else if (file.isDirectory()) {
			Files.walk(Paths.get(arg[0])).filter(Files::isRegularFile).forEach(path -> {
				if (path.toString().endsWith(".json")) {
					filePath.add(path.toString());
				}
			});
		}

		filePath.forEach(path -> {
			JsonEngine jsonEngine = new JsonEngine();
			ConfigMapping configMapping = jsonEngine.readMapping(path);

			if (configMapping != null) {
				CrawlEngine crawlEngine = new CrawlEngine(configMapping);
				crawlEngine.doCrawl();
			}
		});
	}
}
