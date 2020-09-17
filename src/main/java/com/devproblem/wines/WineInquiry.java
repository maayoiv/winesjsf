package com.devproblem.wines;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

@ManagedBean(name = "wineInquiry")
@RequestScoped
public class WineInquiry {
	
	private final String VARIETY_VIEW = "View by Variety";
	private final String YEAR_VIEW = "View by Year";
	private final String REGION_VIEW = "View by Region";
	private final String YEAR_VARIETY_VIEW = "View by Year and Variety";
	
	List<Wine> wineRecords;
	List<WineComponentTotal> wineComponentRecords;
	List<String> componentDropDownOptions = new ArrayList<String>();
	String selectedLotCode;
	String selectedComponentDropDown;
	
	public String getSelectedLotCode() {
		return selectedLotCode;
	}

	public void setSelectedLotCode(String selectedLotCode) {
		this.selectedLotCode = selectedLotCode;
	}

	public String getMessage() {
		return "this should be a json string to be parsed on view";
	}
	
	public List<Wine> getWineRecords() {
		wineRecords = new ArrayList<Wine>();
		wineRecords.add(parseJsonWine("11YVCHAR001"));
		wineRecords.add(parseJsonWine("11YVCHAR002"));
		wineRecords.add(parseJsonWine("15MPPN002-VK"));
		return wineRecords;
	}

	public void setWineRecords(List<Wine> wineRecords) {
		this.wineRecords = wineRecords;
	}
	
	public void selectWine() {
		HttpSession session =  (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		session.setAttribute("selectedLotCode", getSelectedLotCode());
	}
	
	public List<WineComponentTotal> getWineComponentRecords() {
		return getWineComponentBreakdown();
	}
	
	public List<WineComponentTotal> getWineComponentBreakdown() {
		getWineRecords();
		if (getSelectedLotCode() == null) {
			HttpSession session =  (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
			selectedLotCode = (String) session.getAttribute("selectedLotCode");
		}
		if (selectedLotCode != null ) {
			Wine selectedWine = getWineFromLotCode();
			if (VARIETY_VIEW.equals(selectedComponentDropDown)) {
				wineComponentRecords = WineComponentUtils.printVarietyBreakdown(selectedWine);
			} else if (YEAR_VIEW.equals(selectedComponentDropDown)) {
				wineComponentRecords = WineComponentUtils.printYearBreakdown(selectedWine);
			} else if (REGION_VIEW.equals(selectedComponentDropDown)) {
				wineComponentRecords = WineComponentUtils.printRegionBreakdown(selectedWine);
			} else if (YEAR_VARIETY_VIEW.equals(selectedComponentDropDown)) {
				wineComponentRecords = WineComponentUtils.printYearAndVarietyBreakdown(selectedWine);
			} else {
				wineComponentRecords = WineComponentUtils.printVarietyBreakdown(selectedWine);
			}
		}
		return wineComponentRecords;
	}

	private Wine parseJsonWine(String fileName) {
		Wine wine = null;
		try {
			Gson gson = new Gson();
			Reader reader = Files.newBufferedReader(Paths.get("C:\\Users\\ASUS\\Documents\\sts-projectwine\\winesjsf\\src\\main\\java\\" + fileName + ".json"));
			wine = gson.fromJson(reader, Wine.class);			
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wine;
	}
	
	private Wine getWineFromLotCode() {
		for (int i = 0; i < wineRecords.size(); i++) {
			Wine wineRecord = wineRecords.get(i);
			if (wineRecord.getLotCode().equals(selectedLotCode)) {
				return wineRecord;
			}
		}
		return null;
	}

	public String getSelectedComponentDropDown() {
		return selectedComponentDropDown;
	}

	public void setSelectedComponentDropDown(String selectedComponentDropDown) {
		this.selectedComponentDropDown = selectedComponentDropDown;
	}

	public List<String> getComponentDropDownOptions() {
		if(componentDropDownOptions.size() == 0) {
			componentDropDownOptions.add(VARIETY_VIEW);
			componentDropDownOptions.add(REGION_VIEW);
			componentDropDownOptions.add(YEAR_VIEW);
			componentDropDownOptions.add(YEAR_VARIETY_VIEW);
		}
		return componentDropDownOptions;
	}
	
	public List<WineComponentTotal> updateComponentList() {
		List<WineComponentTotal> updatedList = getWineComponentBreakdown(); 
		return updatedList;
	}
}
