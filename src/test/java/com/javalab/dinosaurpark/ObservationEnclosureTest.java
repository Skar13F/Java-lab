package com.javalab.dinosaurpark.zone;

import com.javalab.dinosaurpark.model.CarnivoreDinosaur;
import com.javalab.dinosaurpark.model.Dinosaur;
import com.javalab.dinosaurpark.model.HerbivoreDinosaur;
import com.javalab.dinosaurpark.model.SatisfactionSurvey;
import com.javalab.dinosaurpark.model.Tourist;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ObservationEnclosureTest {

    @Test
    void shouldEnterTouristAndChargeFee() {
        ObservationEnclosure zone = new ObservationEnclosure(
                "Enclosure", 2, 100, ExperienceType.BASIC, List.of()
        );

        Tourist t1 = new Tourist(1, "Juan");

        zone.visit(t1, new Random(1), 0.2);

        assertEquals(1, zone.getCurrentOccupancy());
        assertTrue(t1.getMoneySpent() > 0,
                "El turista debería haber pagado la entrada");
    }

    @Test
    void shouldNotEnterWhenCapacityIsFull() {
        ObservationEnclosure zone = new ObservationEnclosure(
                "Enclosure", 1, 100, ExperienceType.BASIC, List.of()
        );

        Tourist t1 = new Tourist(1, "Juan");
        Tourist t2 = new Tourist(2, "Pedro");

        zone.visit(t1, new Random(1), 0.0);
        zone.visit(t2, new Random(1), 0.0);

        assertEquals(1, zone.getCurrentOccupancy());
    }

    @Test
    void shouldGenerateSurveyWithinBasicRange() {
        ObservationEnclosure zone = new ObservationEnclosure(
                "Enclosure", 2, 100, ExperienceType.BASIC, List.of()
        );

        Tourist t1 = new Tourist(1, "Juan");

        SatisfactionSurvey survey = zone.conductSurvey(t1, new Random(1));

        assertEquals(t1.getId(), survey.getTouristId());
        assertEquals("Enclosure", survey.getEnclosureName()); // ← corregido
        assertTrue(survey.getScore() >= 1 && survey.getScore() <= 3);
    }

    @Test
    void shouldGenerateSurveyWithinPremiumRange() {
        ObservationEnclosure zone = new ObservationEnclosure(
                "Enclosure", 2, 100, ExperienceType.PREMIUM, List.of()
        );

        Tourist t1 = new Tourist(1, "Juan");

        SatisfactionSurvey survey = zone.conductSurvey(t1, new Random(1));

        assertTrue(survey.getScore() >= 2 && survey.getScore() <= 4);
    }

    @Test
    void shouldGenerateSurveyWithinVIPRange() {
        ObservationEnclosure zone = new ObservationEnclosure(
                "Enclosure", 2, 100, ExperienceType.VIP, List.of()
        );

        Tourist t1 = new Tourist(1, "Juan");

        SatisfactionSurvey survey = zone.conductSurvey(t1, new Random(1));

        assertTrue(survey.getScore() >= 3 && survey.getScore() <= 5);
    }

    @Test
    void shouldReturnDinosaursList() {
        Dinosaur d1 = new CarnivoreDinosaur(1, "Rex", "T-Rex");       // ← corregido
        Dinosaur d2 = new HerbivoreDinosaur(2, "Tri", "Triceratops"); // ← corregido

        ObservationEnclosure zone = new ObservationEnclosure(
                "Enclosure", 2, 100, ExperienceType.BASIC, List.of(d1, d2)
        );

        assertEquals(2, zone.getDinosaurs().size());
    }
}