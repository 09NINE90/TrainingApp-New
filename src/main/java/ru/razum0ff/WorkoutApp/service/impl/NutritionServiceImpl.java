package ru.razum0ff.WorkoutApp.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.razum0ff.WorkoutApp.entity.NutritionReportEntity;
import ru.razum0ff.WorkoutApp.entity.UserEntity;
import ru.razum0ff.WorkoutApp.entity.WeekNutritionEntity;
import ru.razum0ff.WorkoutApp.repository.NutritionReportRepository;
import ru.razum0ff.WorkoutApp.repository.UserRepository;
import ru.razum0ff.WorkoutApp.repository.WeekNutritionRepository;
import ru.razum0ff.WorkoutApp.service.NutritionReportService;

import java.time.*;
import java.util.*;

@Service
public class NutritionServiceImpl implements NutritionReportService {
    private final NutritionReportRepository repository;

    private final WeekNutritionRepository weekNutritionRepository;
    private final UserRepository userRepository;


    public NutritionServiceImpl(NutritionReportRepository repository, WeekNutritionRepository weekNutritionRepository, UserRepository userRepository) {
        this.repository = repository;
        this.weekNutritionRepository = weekNutritionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<NutritionReportEntity> getNutritionReportByUser(UUID id) {
        UserEntity user = userRepository.findById(id).get();
        return repository.findAllByUserOrderByDateDesc(user);
    }

    @Override
    @Transactional
    public void addNutritionReport(UUID id, NutritionReportEntity nutritionReport) {
        UserEntity user = userRepository.findById(id).get();
        nutritionReport.setUser(user);
        saveNutritionToWeek(nutritionReport, user);
        repository.save(nutritionReport);
    }

    @Override
    public WeekNutritionEntity getLastWeekNutritionReportByUser(UUID id) {
        UserEntity user = userRepository.findById(id).get();
        WeekNutritionEntity entity = weekNutritionRepository.findFirstByUserOrderByWeekStartDesc(user);
        if (entity.getCountDaysOfWeek() > 1){
            setValueByDay(entity);
        }
        return entity;
    }

    void setValueByDay(WeekNutritionEntity entity){
        entity.setSumCarbohydrates((float) Math.ceil(entity.getSumCarbohydrates() / entity.getCountDaysOfWeek()));
        entity.setSumCalories((float) Math.ceil(entity.getSumCalories() / entity.getCountDaysOfWeek()));
        entity.setSumFats((float) Math.ceil(entity.getSumFats() / entity.getCountDaysOfWeek()));
        entity.setSumProteins((float) Math.ceil(entity.getSumProteins() / entity.getCountDaysOfWeek()));
    }

    private void saveNutritionToWeek(NutritionReportEntity nutritionReport, UserEntity user) {
        Date dateOfNutritionReport = nutritionReport.getDate();
        List<Date> dateList = getWeekStartEnd(dateOfNutritionReport);
        Date startOfWeekDate = dateList.get(0);
        Date endOfWeekDate = dateList.get(1);
        Date myDate = dateList.get(2);
        String checkDays = myDate.toString().split(" ")[2];

        float calories = (float) Math.ceil(nutritionReport.getCalories() * 10) / 10;
        float proteins = (float) (Math.ceil(nutritionReport.getProteins() * 10) / 10);
        float fats = (float) (Math.ceil(nutritionReport.getFats() * 10) / 10);
        float carbohydrates = (float) (Math.ceil(nutritionReport.getCarbohydrates() * 10) / 10);

        WeekNutritionEntity weekNutrition;
        WeekNutritionEntity lastWeekNutrition = weekNutritionRepository.findByUserAndWeekStart(user, startOfWeekDate);
        if (lastWeekNutrition == null) {
            weekNutrition = new WeekNutritionEntity();
            addWeek(weekNutrition, user, startOfWeekDate, checkDays, endOfWeekDate, myDate, calories, proteins, fats, carbohydrates);
        } else {
            if (checkDateInWeekRange(lastWeekNutrition.getWeekStart(), lastWeekNutrition.getWeekEnd(), myDate)) {
                int countDays;
                String result;
                List<String> stringList = new ArrayList<>(List.of(lastWeekNutrition.getCheckDays().split(",")));
                if (!stringList.contains(checkDays)) {
                    countDays = lastWeekNutrition.getCountDaysOfWeek() + 1;
                    stringList.add(checkDays);
                    StringJoiner joiner = new StringJoiner(",");
                    for (String str : stringList) {
                        joiner.add(str);
                    }
                    result = joiner.toString();
                } else {
                    countDays = lastWeekNutrition.getCountDaysOfWeek();
                    result = lastWeekNutrition.getCheckDays();
                }
                lastWeekNutrition.setCheckDays(result);
                lastWeekNutrition.setLastDate(myDate);
                lastWeekNutrition.setCountDaysOfWeek((short) countDays);
                lastWeekNutrition.setSumCalories(lastWeekNutrition.getSumCalories() + calories);
                lastWeekNutrition.setSumProteins(lastWeekNutrition.getSumProteins() + proteins);
                lastWeekNutrition.setSumFats(lastWeekNutrition.getSumFats() + fats);
                lastWeekNutrition.setSumCarbohydrates(lastWeekNutrition.getSumCarbohydrates() + carbohydrates);

                weekNutritionRepository.save(lastWeekNutrition);
            } else {
                weekNutrition = new WeekNutritionEntity();
                addWeek(weekNutrition, user, startOfWeekDate, checkDays, endOfWeekDate, myDate, calories, proteins, fats, carbohydrates);
            }
        }
    }
    private boolean checkDateInWeekRange (Date startWeek, Date endWeek, Date myDate){
        return !myDate.before(startWeek) && !myDate.after(endWeek);
    }
    private void addWeek(WeekNutritionEntity weekNutrition, UserEntity user, Date start, String checkDays, Date end, Date lastDate, float sumCalories, float sumProteins, float sumFats, float sumCarbohydrates){
        weekNutrition.setWeekStart(start);
        weekNutrition.setWeekEnd(end);
        weekNutrition.setLastDate(lastDate);
        weekNutrition.setCheckDays(checkDays);
        weekNutrition.setCountDaysOfWeek((short) 1);
        weekNutrition.setUser(user);
        weekNutrition.setSumCalories(sumCalories);
        weekNutrition.setSumProteins(sumProteins);
        weekNutrition.setSumFats(sumFats);
        weekNutrition.setSumCarbohydrates(sumCarbohydrates);
        weekNutritionRepository.save(weekNutrition);
    }

    private List<Date> getWeekStartEnd(Date date) {
        LocalDateTime dateTime = Instant.ofEpochMilli(date.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        LocalDate localDate = dateTime.toLocalDate();
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        LocalDate startOfWeek = localDate.minusDays(dayOfWeek.getValue() - 1);
        LocalDate endOfWeek = startOfWeek.plusDays(6);

        Date startOfWeekDate = Date.from(startOfWeek.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endOfWeekDate = Date.from(endOfWeek.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date myDay = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        List<Date> dateList = new ArrayList<>();
        dateList.add(startOfWeekDate);
        dateList.add(endOfWeekDate);
        dateList.add(myDay);
        return dateList;
    }

}
