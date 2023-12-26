package com.example.tooth_care.Models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class TimeSlot {
    private Map<String, Map<LocalDate, List<LocalTime>>> timeSlots;
    public TimeSlot() {
        timeSlots = new LinkedHashMap<>();
    }


    public void addTimeSlot(String day, LocalDate date, LocalTime startTime, LocalTime endTime) {
        if (!timeSlots.containsKey(day)) {
            timeSlots.put(day, new HashMap<>());
        }

        Map<LocalDate, List<LocalTime>> dayMap = timeSlots.get(day);
        if (!dayMap.containsKey(date)) {
            dayMap.put(date, new ArrayList<>());
        }

        List<LocalTime> timeList = dayMap.get(date);
        while (!startTime.isAfter(endTime)) {
            timeList.add(startTime);
            startTime = startTime.plusHours(1);
        }
    }

    public List<LocalTime> getTimeSlots(String day, LocalDate date) {
        Map<LocalDate, List<LocalTime>> dayMap = timeSlots.getOrDefault(day, Collections.emptyMap());
        return dayMap.getOrDefault(date, Collections.emptyList());
    }


    public void displayTimeSlots() {
        for (Map.Entry<String, Map<LocalDate, List<LocalTime>>> entry : timeSlots.entrySet()) {
            String day = entry.getKey();
            Map<LocalDate, List<LocalTime>> dayMap = entry.getValue();

            for (Map.Entry<LocalDate, List<LocalTime>> dayEntry : dayMap.entrySet()) {
                LocalDate date = dayEntry.getKey();
                List<LocalTime> timeList = dayEntry.getValue();

                //create an array and return
            }
        }
    }
}









