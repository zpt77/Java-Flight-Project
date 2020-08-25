package CaseFlight;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;


public class Flight {

    String departure;
    String destination;
    String depDate;
    String backDate;
    double flightTime;
    int passengers;
    double basicPrice;

    public Flight(String departure, String destination, String depDate, String backDate,
                  double flightTime, int passengers, double basicPrice) {
        this.departure = departure;
        this.destination = destination;
        this.depDate = depDate;
        this.backDate = backDate;
        this.flightTime = flightTime;
        this.passengers = passengers;
        this.basicPrice = basicPrice;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDepDate() {
        return depDate;
    }

    public void setDepDate(String depDate) {
        this.depDate = depDate;
    }

    public String getBackDate() {
        return backDate;
    }

    public void setBackDate(String backDate) {
        this.backDate = backDate;
    }

    public double getFlightTime() {
        return flightTime;
    }

    public void setFlightTime(double flightTime) {
        this.flightTime = flightTime;
    }

    public int getPassengers() {
        return passengers;
    }

    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }

    public double getBasicPrice() {
        return basicPrice;
    }

    public void setBasicPrice(double basicPrice) {
        this.basicPrice = basicPrice;
    }

    public Calendar flightDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar c = Calendar.getInstance();
        try {
            Date data = dateFormat.parse(depDate);
            c.setTime(data);
        } catch (ParseException ex) {
            System.out.println("Wystąpił błąd.");
        }

        return c;
    }

    public double calcPrice() {


        double b1 = calcB1();
        double b2 = calcB2();
        double b3 = calcB3();

        return Math.round((b1 + b2 + b3) / 3);
    }


    public double calcB1() {

        double multiplier = 1;

        if (flightDate().get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            multiplier = 1.1;
        }
        //System.out.println("1." + basicPrice + " " + multiplier);

        return basicPrice * multiplier;
    }

    public double calcB2() {

        double multiplier = 1;

        Calendar c1 = flightDate();
        Calendar c2 = Calendar.getInstance();
        long diffInMillis = c1.getTimeInMillis() - c2.getTimeInMillis();
        Calendar result = Calendar.getInstance();
        result.setTimeInMillis(diffInMillis);
        long millisInDay = 1000 * 60 * 60 * 24;

        long diffDays = result.getTimeInMillis() / millisInDay;

        if (diffDays > 30) {
            multiplier = 0.9;
        } else if (diffDays < 7) {
            multiplier = 1.2;
        }


        return basicPrice * multiplier;
    }

    public double calcB3() {

        double multiplier = 1;

        if (100 - passengers < 50 && 100 - passengers >= 20) {
            multiplier = 1.1;
        } else if (100 - passengers < 20) {
            multiplier = 1.25;
        }


        return basicPrice * multiplier;
    }

    public double bussinessPrice() {

        return (calcPrice() + businessClass()) / 2;
    }

    public double businessClass() {

        return basicPrice * 1.65;
    }

    public String getFlightCode() {

        String flightCode = departure.substring(departure.length() - 3, departure.length())
                .concat(destination.substring(destination.length() - 3, destination.length()));

        return flightCode;
    }

    public int howManyPassangers() {

        return howManyAdults() + howManyTeens() + howManyBabies() + howManyKids();
    }

    public int howManyAdults() {
        Scanner sc = new Scanner(System.in);
        int adult = sc.nextInt();

        return adult;
    }

    public int howManyTeens() {
        Scanner sc = new Scanner(System.in);
        int teen = sc.nextInt();

        return teen;
    }

    public int howManyKids() {
        Scanner sc = new Scanner(System.in);
        int kid = sc.nextInt();

        return kid;
    }

    public int howManyBabies() {
        Scanner sc = new Scanner(System.in);
        int baby = sc.nextInt();

        return baby;
    }

    public int freeSeats() {

        return 100 - passengers;
    }

    public double isTherePlace(int p) throws OverbookException {

        double multiplier = 1;
        Scanner scpf = new Scanner(System.in);

        if (freeSeats() >= p) {

        } else if (freeSeats() < p && passengers < 110) {
            System.out.println("Brak wolnych miejsc pasażerskich. Proponujemy lot jako stewardessa/steward, ze zniżką 20%" + '\n' +
                    "Czy wyraża Pani/Pan zgodę? Jesli tak proszę wpisać 'tak' jeśli nie proszę wpisać 'nie'");
            if (scpf.nextLine() == "tak") {
                multiplier = 0.8;
            } else if (scpf.nextLine() == "nie") {
                System.out.println("W takim razie proponujemy miejsce na skrzydle samolotu ze zniżką 50%");
                multiplier = 0.5;
            }
        } else if (passengers >= 110) {
            throw new OverbookException("Niestety brak wolnych miejsc. Zapraszamy ponownie.");
        }
        return multiplier;
    }

    public double specialOffer(boolean b) {

        double bonus = 0;
        Scanner scso = new Scanner(System.in);
        System.out.println("Czy będzie Pani/Pan posiadać przy sobie bagaż o wadze 10kg? Koszt 120 zł. " +
                "Proszę wpisać 'tak' jeżeli jest Pani/Pan zainteresowany");
        if ((scso.next() == "tak")) {
            bonus += 120;
        }

        System.out.println("Czy chce Pani/Pan wczesniej zarezerwować posiłek na pokładzie? Koszt 40 zł. " +
                "Proszę wpisać 'tak' jeżeli jest Pani/Pan zainteresowany");
        if ((scso.next() == "tak")) {
            bonus += (40 * howManyPassangers());
        }

        System.out.println("Czy jest Pani/Pan zainteresowany pierwszeństwem przy wsiadaniu do samolotu? Dla klasy ekonomicznej - 15 zł, dla biznesowej - 25 zł. " +
                "Proszę wpisać 'tak' jeżeli jest Pani/Pan zainteresowany");
        if ((scso.next() == ("tak"))) {
            if (b == true) {
                bonus += (25 * howManyPassangers());
            } else {
                bonus += (15 * howManyPassangers());
            }
        }
        return bonus;
    }

    public double calcFinalPrice(double x, int p, double m) throws OverbookException {

        double finalPrice = x;
        boolean bussiness = false;

        Scanner scf = new Scanner(System.in);

        if (m >= 1) {
            System.out.println("Czy jesteś zainteresowany lotem w klasie biznesowej? Jeśli tak wpisz 'tak', jeśli nie - domyślna będzie klasa ekonomiczna");
            if (scf.nextLine() == "tak") {
                bussiness = true;
                finalPrice = bussinessPrice();
            }
            finalPrice += specialOffer(bussiness);

        } else {

        }

            finalPrice *= p;
            finalPrice *= m;
            finalPrice += 50; // opłata serwisowa


            return finalPrice;
        }


    }
