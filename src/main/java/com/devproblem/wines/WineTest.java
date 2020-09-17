package com.devproblem.wines;

import java.text.NumberFormat;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WineTest {

	public static void main(String[] args) {

		Wine w = new Wine("11YVCHAR001", 1000);
		w.setDescription("2011 Yarra Valley Chardonnay");
		w.setTankCode("T25-01");
		w.setProductState("Ready for bottling");
		w.setOwnerName("YV Wines Pty Ltd");
		
		
		w.getComponents().add(new GrapeComponent(80D, 2011, "Chardonnay", "Yarra Valley"));
		w.getComponents().add(new GrapeComponent(10D, 2010, "Chardonnay", "Macedon"));
		w.getComponents().add(new GrapeComponent(5D, 2011, "Pinot Noir", "Mornington"));
		w.getComponents().add(new GrapeComponent(5D, 2010, "Pinot Noir", "Macedon"));
		
		printYearBreakdown(w);
		printVarietyBreakdown(w);
		printRegionBreakdown(w);
		printYearAndVarietyBreakdown(w);
		
	}

	private static void printVarietyBreakdown(Wine w) {
		Map<String, Double> breakdown = new HashMap<>();
		Map<String, List<GrapeComponent>> varieties = w.getComponents().stream().collect(Collectors.groupingBy(GrapeComponent::getVariety));			
		for (Map.Entry<String, List<GrapeComponent>> entry: varieties.entrySet()) {			
			breakdown.put(entry.getKey().toString(), entry.getValue().stream().collect(Collectors.summingDouble(GrapeComponent::getPercentage)));
		}
		Iterator<Map.Entry<String, Double>> itr = breakdown.entrySet().stream().sorted(Map.Entry.<String, Double>comparingByValue(Comparator.reverseOrder())).iterator();
		while (itr.hasNext()) {
			Map.Entry<String, Double> entry = itr.next();
			System.out.println(
					percentFormatter(entry.getValue()) 
					+ " " 
					+ entry.getKey().toString());
		}
	}

	private static void printYearBreakdown(Wine w) {
		Map<Integer, Double> breakdown = new HashMap<>();
		Map<Integer, List<GrapeComponent>> years = w.getComponents().stream().collect(Collectors.groupingBy(GrapeComponent::getYear));			
		for (Map.Entry<Integer, List<GrapeComponent>> entry: years.entrySet()) {			
			breakdown.put(entry.getKey(), entry.getValue().stream().collect(Collectors.summingDouble(GrapeComponent::getPercentage)));
		}
		Iterator<Map.Entry<Integer, Double>> itr = breakdown.entrySet().stream().sorted(Map.Entry.<Integer, Double>comparingByValue(Comparator.reverseOrder())).iterator();
		while (itr.hasNext()) {
			Map.Entry<Integer, Double> entry = itr.next();
			System.out.println(
					percentFormatter(entry.getValue()) 
					+ " " 
					+ entry.getKey().toString());
		}
	}
	
	private static void printRegionBreakdown(Wine w) {
		Map<String, Double> breakdown = new HashMap<>();
		Map<String, List<GrapeComponent>> varieties = w.getComponents().stream().collect(Collectors.groupingBy(GrapeComponent::getRegion));			
		for (Map.Entry<String, List<GrapeComponent>> entry: varieties.entrySet()) {			
			breakdown.put(entry.getKey().toString(), entry.getValue().stream().collect(Collectors.summingDouble(GrapeComponent::getPercentage)));
		}
		Iterator<Map.Entry<String, Double>> itr = breakdown.entrySet().stream().sorted(Map.Entry.<String, Double>comparingByValue(Comparator.reverseOrder())).iterator();
		while (itr.hasNext()) {
			Map.Entry<String, Double> entry = itr.next();
			System.out.println(
					percentFormatter(entry.getValue()) 
					+ " " 
					+ entry.getKey().toString());
		}
	}
	
	private static void printYearAndVarietyBreakdown(Wine w) {
		Map<String, Double> breakdown = new HashMap<>();
		Map<Integer, Map<String, List<GrapeComponent>>> yearVarieties = w.getComponents().stream().collect(Collectors.groupingBy(GrapeComponent::getYear, Collectors.groupingBy(GrapeComponent::getVariety)));
		for (Map.Entry<Integer, Map<String, List<GrapeComponent>>> years : yearVarieties.entrySet()) {
			Map<String, List<GrapeComponent>> varieties = years.getValue();
			for (String variety : varieties.keySet()) {				
				breakdown.put(years.getKey().toString() + " " + variety, 
						varieties.get(variety).stream().collect(Collectors.summingDouble(GrapeComponent::getPercentage)));
			}
		}
		Iterator<Map.Entry<String, Double>> itr = breakdown.entrySet().stream().sorted(Map.Entry.<String, Double>comparingByValue(Comparator.reverseOrder())).iterator();
		while (itr.hasNext()) {
			Map.Entry<String, Double> entry = itr.next();
			System.out.println(
					percentFormatter(entry.getValue()) 
					+ " " 
					+ entry.getKey().toString());
		}
	}
	
	private static String percentFormatter(Double doubleValue) {
		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(0);
		return nf.format(doubleValue).concat("%");	
	}
}
