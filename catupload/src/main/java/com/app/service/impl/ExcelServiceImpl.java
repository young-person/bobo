package com.app.service.impl;

import java.io.File;

import com.app.service.ExcelService;

/** 
 * @Description: TODO
 * @date 2019年11月12日 下午10:36:55 
 * @ClassName: ExcelServiceImpl 
 */
public class ExcelServiceImpl implements ExcelService{

	/* (non-Javadoc)
	 * @see com.app.service.ExcelService#getInPath()
	 */
	@Override
	public File getInPath() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.app.service.ExcelService#getName()
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.app.service.ExcelService#getOutFile()
	 */
	@Override
	public File getOutFile() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void init() {
		
	}
	public static void main(String[] args) {
		for(int index = 1; index <= 293; index++) {
			System.out.println("private String f"+index+";");
		}
	}

}
