package ru.punenko.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import ru.punenko.MySecondTestAppSpringBoot.model.Positions;

import java.time.Year;

@Service
public class AnnualBonusServiceImpl implements AnnualBonusService {
    @Override
    public double calculate(Positions positions, double salary, double bonus, int workDays) {
        int daysInYear = Year.now().length();
        return salary * bonus * daysInYear * positions.getPositionCoefficient() / workDays;
    }

    @Override
    public double calculateQuarterly(Positions positions, double annualBonus) {
        return positions.isManager() ? annualBonus / 4 : 0;
    }
}
