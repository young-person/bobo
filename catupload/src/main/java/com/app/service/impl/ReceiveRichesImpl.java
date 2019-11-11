package com.app.service.impl;

import com.app.pojo.RicheTarget;
import com.app.runner.ApplicationRunnerImpl;
import com.app.service.ExcelService;
import com.app.service.ReceiveRiches;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Service
public class ReceiveRichesImpl implements ReceiveRiches {

	@Override
	public void receiveRichesData(List<RicheTarget> datas) {

		this.receiveRichesData(datas, new ExcelService() {
			
			@Override
			public File getOutFile() {
				try {
					return ResourceUtils.getFile(ApplicationRunnerImpl.CAT_CACHE.get("sharePath").getValue());
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				return null;
			}
			
			@Override
			public String getName() {
				return ApplicationRunnerImpl.CAT_CACHE.get("shareName").getValue();
			}
			
			@Override
			public File getInPath() {
				try {
					return ResourceUtils.getFile(ApplicationRunnerImpl.CAT_CACHE.get("sharePath").getValue());
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				return null;
			}
		});
	}

	private void receiveRichesData(List<RicheTarget> datas,ExcelService excelService) {
		try {
			excelService.writeDataToExcel(datas, (index, bean, sheet) -> {

			});
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
