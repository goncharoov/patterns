package ru.netology.data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataGenerator {
    private DataGenerator() {
    }

    public static class Registration {
        private Registration() {
        }

        public static CardDeliveryOrder generate() {
            Faker faker = new Faker(new Locale("ru"));
            return new CardDeliveryOrder(
                    faker.address().city(),
                    faker.name().name(),
                    faker.phoneNumber().phoneNumber());
        }

        public static String getDayVisit(int plusDays) {
            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate date = today.plusDays(plusDays);
            return formatter.format(date);

        }
    }
}