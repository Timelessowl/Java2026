package ru.punenko.MySecondTestAppSpringBoot.service;

import org.junit.jupiter.api.Test;
import ru.punenko.MySecondTestAppSpringBoot.model.Positions;

import java.time.Year;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class AnnualBonusServiceImplTest {

    @Test
    void calculate() {
        Positions position = Positions.HR;
        double bonus = 2.0;
        int workDays = 243;
        double salary = 100000.00;

        double result = new AnnualBonusServiceImpl().calculate(position, salary, bonus, workDays);

        double expected = Year.now().isLeap() ? 361481.48148148146 : 360493.8271604938;
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void calculateQuarterly() {
        AnnualBonusServiceImpl service = new AnnualBonusServiceImpl();
        double annualBonus = 120000.00;

        assertThat(service.calculateQuarterly(Positions.TL, annualBonus)).isEqualTo(30000.00);
        assertThat(service.calculateQuarterly(Positions.HR, annualBonus)).isZero();
    }
}
