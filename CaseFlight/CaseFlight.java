package CaseFlight;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import java.util.stream.Collectors;

public class CaseFlight {
    public static void main(String[] args) throws IOException, OverbookException {


        List<Flight> flightList = Arrays.asList(
                new Flight("Warszawa WAW", "Berlin TXL", "01-10-2019", "4-10-2019", 1.5, 51, 200),
                new Flight("Lublin LUZ", "Londyn LHR", "01-02-2020", "12-02-2020", 2, 79, 700),
                new Flight("Poznań POZ", "Nowy Jork JFK", "12-06-2019", "26-06-2019", 12, 100, 2000),
                new Flight("Berlin TXL", "Bangkok BKK", "20-12-2019", "01-01-2020", 13, 14, 3000),
                new Flight("Tokyo HND", "Moskwa SVO", "15-06-2020", "20-06-2020", 6, 65, 1000),
                new Flight("Paryż CDG", "Edynburg EDI", "13- 11-2019", "16-11-2019", 1.5, 33, 500),
                new Flight("Warszawa WAW", "Mediolan MXP", "26-05-2019", "30-05-2019", 1, 54, 400),
                new Flight("Phoenix PHX", "Meksyk MEX", "18-12-2019", "22-12-2019", 1, 5, 900),
                new Flight("Kraków KRK", "Lublin LUZ", "13-12-2019", "15-12-2019", 0.5, 99, 40),
                new Flight("Lublin LUZ", "Kijów KBP", "24-06-2020", "01-07-2020", 1, 84, 300)
        );


        System.out.println("Witaj w systemie rezerwacji biletów lotnicznych.");
        System.out.println("Aby wyszukać lot wybierz najpierw, po którym z następujących atrybutów chcesz wyszukać lot," +
                " wpisując numer, którym jest oznaczona interesująca ciebie opcja:");
        Scanner sc = new Scanner(System.in);
        System.out.println("1.Miejsce wylotu" + '\n' + "2.Data wylotu" + '\n' + "3.Cena");
        int clientsChoice = sc.nextInt();


        if (clientsChoice == 1) {
            System.out.println("Jakie miasto wylotu ciebie interesuje?");
            String depPlace = sc.next();


            flightList.stream()
                    .filter(flight -> flight.departure.substring(0, flight.departure.length() - 4).equalsIgnoreCase(depPlace))
                    .forEach(flight -> System.out.println("Miejsce wylotu: " + flight.departure + '\n' +
                            "Miejsce przylotu: " + flight.destination + '\n' +
                            "Data wylotu: " + flight.depDate + '\n' +
                            "Data powrotu: " + flight.backDate + '\n' +
                            "Czas przelotu: " + flight.flightTime + " h" + '\n' +
                            "Liczba wolnych miejsc: " + (100 - flight.passengers) + '\n' +
                            "Cena: " + flight.calcPrice() + " zł" + '\n'));


        } else if (clientsChoice == 2) {
            System.out.println("Jaka data wylotu ciebie interesuje? Podaj w formacie dd-mm-yyyy.");
            String dateOfDep = sc.next();

            flightList.stream()
                    .filter(flight -> flight.depDate.equals(dateOfDep))
                    .forEach(flight -> System.out.println("Miejsce wylotu: " + flight.departure + '\n' +
                            "Miejsce przylotu: " + flight.destination + '\n' +
                            "Data wylotu: " + flight.depDate + '\n' +
                            "Data powrotu: " + flight.backDate + '\n' +
                            "Czas przelotu: " + flight.flightTime + " h" + '\n' +
                            "Liczba wolnych miejsc: " + (100 - flight.passengers) + '\n' +
                            "Cena: " + flight.calcPrice() + " zł" + '\n'));

        } else if (clientsChoice == 3) {
            System.out.println("Jaki przedział cenowy ciebie interesuje? Podaj najpierw cenę minimalna, a następnie maksymalną.");
            double minPrice = sc.nextDouble();
            double maxPrice = sc.nextDouble();

            flightList.stream()
                    .filter(flight -> flight.calcPrice() >= minPrice && flight.calcPrice() <= maxPrice)
                    .forEach(flight -> System.out.println("Miejsce wylotu: " + flight.departure + '\n' +
                            "Miejsce przylotu: " + flight.destination + '\n' +
                            "Data wylotu: " + flight.depDate + '\n' +
                            "Data powrotu: " + flight.backDate + '\n' +
                            "Czas przelotu: " + flight.flightTime + " h" + '\n' +
                            "Liczba wolnych miejsc: " + (100 - flight.passengers) + '\n' +
                            "Cena: " + flight.calcPrice() + " zł" + '\n'));

        }

        System.out.println("Jeśli udało ci się znaleźć lot, na który chcialbyś kupić bilet, " +
                "wpisz teraz połączone trzyliterowe kody lotniska wylotu oraz przylotu, zgodnie z przykładem: WAWJFK.");
        String getflightId = sc.next().toUpperCase();
        String depCode = getflightId.substring(0, 3);
        String destCode = getflightId.substring(3, 6);


        flightList.stream()
                .filter(flight -> flight.getFlightCode().equals(getflightId))
                .forEach(flight -> System.out.println("Wybrałeś następujący lot:" + '\n' + "Miejsce wylotu: " + flight.departure + '\n' +
                        "Miejsce przylotu: " + flight.destination + '\n' +
                        "Data wylotu: " + flight.depDate + '\n' +
                        "Data powrotu: " + flight.backDate + '\n' +
                        "Czas przelotu: " + flight.flightTime + " h" + '\n' +
                        "Liczba wolnych miejsc: " + (100 - flight.passengers) + '\n' +
                        "Cena: " + flight.calcPrice() + " zł" + '\n'));

        List<Flight> chosen = flightList.stream()
                .filter(flight -> flight.getFlightCode().equals(getflightId))
                .collect(Collectors.toList());

        int passengers = 0;
        System.out.println("Podaj liczbę pasażerów w wieku powyżej 18 lat.");
        passengers += chosen.get(0).howManyAdults();
        System.out.println("Podaj liczbę pasażerów w wieku od 12 do 18 lat.");
        passengers += chosen.get(0).howManyTeens();
        System.out.println("Podaj liczbę pasażerów w wieku od 12 do 2 lat.");
        passengers += chosen.get(0).howManyKids();
        System.out.println("Podaj liczbę pasażerów w wieku poniżej 2 lat.");
        passengers += chosen.get(0).howManyBabies();

        double multi = chosen.get(0).isTherePlace(passengers);

        double thePrice = Math.round(chosen.get(0).calcFinalPrice(chosen.get(0).calcPrice(), passengers, multi));

        System.out.println("Dziękujemy! Ostateczna cena biletu wynosi: " + thePrice + ". Dziękujemy za wybranie linii lotniczych Who says sky is the limit.");


        String visuals = "*";
        for (int i = 0; i <= 35; i++) {
            visuals = visuals.concat("*");
        }
        double idNumbers;
        String nums = "";
        while (nums.length() < 6) {
            idNumbers = Math.random() * 10;
            int x = (int) idNumbers;
            nums = nums.concat(Integer.toString(x));
        }
        String flightId = chosen.get(0).getFlightCode().concat(nums);

        String filePath = "C:\\Users\\Tomek admin\\Desktop\\ticket.txt";
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(filePath);
            fileWriter.write(visuals);
            fileWriter.write(System.lineSeparator());
            fileWriter.write("Identyfikator rezerwacji: " + flightId);
            fileWriter.write(System.lineSeparator());
            fileWriter.write("Miejsce wylotu: " + chosen.get(0).departure);
            fileWriter.write(System.lineSeparator());
            fileWriter.write("Miejsce przylotu: " + chosen.get(0).destination);
            fileWriter.write(System.lineSeparator());
            fileWriter.write("Data wylotu: " + chosen.get(0).depDate);
            fileWriter.write(System.lineSeparator());
            fileWriter.write("Data powrotu: " + chosen.get(0).backDate);
            fileWriter.write(System.lineSeparator());
            fileWriter.write("Cena: " + thePrice + " zł");
            fileWriter.write(System.lineSeparator());
            fileWriter.write(visuals);

        } finally {
            if (fileWriter != null) {
                fileWriter.close();
            }
        }


    }
}


