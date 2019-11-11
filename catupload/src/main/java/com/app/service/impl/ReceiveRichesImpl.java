package com.app.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;

import com.app.riches.pojo.RicheTarget;
import com.app.service.ExcelService;
import com.app.service.ExcelService.CallBack;
import com.app.service.ReceiveRiches;

@Service
public class ReceiveRichesImpl implements ReceiveRiches {

	@Override
	public void receiveRichesData(List<RicheTarget> datas) {

		this.receiveRichesData(datas, new ExcelService() {
			
			@Override
			public File getOutFile() {
				return null;
			}
			
			@Override
			public String getName() {
				return null;
			}
			
			@Override
			public File getInPath() {
				return null;
			}
		});
	}

	private void receiveRichesData(List<RicheTarget> datas,ExcelService excelService) {
		try {
			excelService.writeDataToExcel(datas, new CallBack<RicheTarget>() {

				@Override
				public void writeExcel(int index, RicheTarget bean, Sheet sheet) {
					
				}
				
			});
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
