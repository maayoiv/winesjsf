package com.devproblem.wines;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WineComponentUtils {
	
	public static List<WineComponentTotal> printVarietyBreakdown(Wine w) {
		List<WineComponentTotal> result = new ArrayList<WineComponentTotal>();
		Map<String, Double> breakdown = new HashMap<>();
		Map<String, List<GrapeComponent>> varieties = w.getComponents().stream().collect(Collectors.groupingBy(GrapeComponent::getVariety));			
		for (Map.Entry<String, List<GrapeComponent>> entry: varieties.entrySet()) {			
			breakdown.put(entry.getKey().toString(), entry.getValue().stream().collect(Collectors.summingDouble(GrapeComponent::getPercentage)));
		}
		Iterator<Map.Entry<String, Double>> itr = breakdown.entrySet().stream().sorted(Map.Entry.<String, Double>comparingByValue(Comparator.reverseOrder())).iterator();
		while (itr.hasNext()) {
			Map.Entry<String, Double> entry = itr.next();
			WineComponentTotal total = new WineComponentTotal(entry.getKey().toString(), percentFormatter(entry.getValue())); 
			result.add(total);			
		}
		return result;
	}

	public static List<WineComponentTotal> printYearBreakdown(Wine w) {
		List<WineComponentTotal> result = new ArrayList<WineComponentTotal>();
		Map<Integer, Double> breakdown = new HashMap<>();
		Map<Integer, List<GrapeComponent>> years = w.getComponents().stream().collect(Collectors.groupingBy(GrapeComponent::getYear));			
		for (Map.Entry<Integer, List<GrapeComponent>> entry: years.entrySet()) {			
			breakdown.put(entry.getKey(), entry.getValue().stream().collect(Collectors.summingDouble(GrapeComponent::getPercentage)));
		}
		Iterator<Map.Entry<Integer, Double>> itr = breakdown.entrySet().stream().sorted(Map.Entry.<Integer, Double>comparingByValue(Comparator.reverseOrder())).iterator();
		while (itr.hasNext()) {
			Map.Entry<Integer, Double> entry = itr.next();
			WineComponentTotal total = new WineComponentTotal(entry.getKey().toString(), percentFormatter(entry.getValue())); 
			result.add(total);			
		}
		return result;
	}
	
	public static List<WineComponentTotal> printRegionBreakdown(Wine w) {
		List<WineComponentTotal> result = new ArrayList<WineComponentTotal>();
		Map<String, Double> breakdown = new HashMap<>();
		Map<String, List<GrapeComponent>> varieties = w.getComponents().stream().collect(Collectors.groupingBy(GrapeComponent::getRegion));			
		for (Map.Entry<String, List<GrapeComponent>> entry: varieties.entrySet()) {			
			breakdown.put(entry.getKey().toString(), entry.getValue().stream().collect(Collectors.summingDouble(GrapeComponent::getPercentage)));
		}
		Iterator<Map.Entry<String, Double>> itr = breakdown.entrySet().stream().sorted(Map.Entry.<String, Double>comparingByValue(Comparator.reverseOrder())).iterator();
		while (itr.hasNext()) {
			Map.Entry<String, Double> entry = itr.next();
			WineComponentTotal total = new WineComponentTotal(entry.getKey().toString(), percentFormatter(entry.getValue())); 
			result.add(total);			
		}
		return result;
	}
	
	public static List<WineComponentTotal> printYearAndVarietyBreakdown(Wine w) {
		List<WineComponentTotal> result = new ArrayList<WineComponentTotal>();
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
			WineComponentTotal total = new WineComponentTotal(entry.getKey().toString(), percentFormatter(entry.getValue())); 
			result.add(total);			
		}
		return result;
	}
	
	private static String percentFormatter(Double doubleValue) {
		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(0);
		return nf.format(doubleValue).concat("%");	
	}
}
