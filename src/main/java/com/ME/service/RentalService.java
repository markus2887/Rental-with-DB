package com.ME.service;

import com.ME.entity.*;
import com.ME.exception.*;
import com.ME.repo.*;
import com.ME.util.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import org.hibernate.SessionFactory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

public class RentalService {

    private final RentalRepository rentalRepository;
    private final MemberRepository memberRepository;
    private final CarRepository carRepository;
    private final MovieRepository movieRepository;
    private final ToolRepository toolRepository;

    public RentalService(RentalRepository rentalRepository, MemberRepository memberRepository, CarRepository carRepository, MovieRepository movieRepository, ToolRepository toolRepository) {
        this.rentalRepository = rentalRepository;
        this.memberRepository = memberRepository;
        this.carRepository = carRepository;
        this.movieRepository = movieRepository;
        this.toolRepository = toolRepository;
    }

    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    private ObservableList<Rental> rentalList = FXCollections.observableArrayList();

    private LocalDateTime time;

    PricePolicy NormalPriceP = new NormalPricePolicy();
    PricePolicy LevelTwoPriceP = new LevelTwoPricePolicy();
    PricePolicy LevelThreePriceP = new LevelThreePricePolicy();


    public ObservableList<Rental> getRentalList() {
        return rentalList;
    }

    public void loadRental() {
        rentalList.setAll(rentalRepository.readRental());
    }

    public void rentButtonClicked(TextField nameInputR, TextField objInputR, ComboBox<RentalType> rentalType, TableView<Rental> rTable, ObservableList<Member> memberList, ObservableList<Car> carList, ObservableList<Movie> movieList, ObservableList<Tool> toolList, Label labelErrorRent) {
        try {
        RentalType typeChoice = rentalType.getValue();
        String name = nameInputR.getText();
        time = LocalDateTime.now();
        Long rObject = Long.parseLong(objInputR.getText());

        Member foundMember = memberRepository.findByName(name)
                .orElseThrow(() -> new MemberNotFoundException("Medlemmen " +name +" finns inte."));

        Optional<Rental> foundRental = rentalRepository.findByRentalObjectId(rObject, typeChoice.name());
        if (foundRental.isPresent()) {
            throw new ItemAlreadyHiredException("Tyvärr är nummer " + rObject + " redan uthyrd! Beklagar.");
        }

        switch (typeChoice) {
            case CAR -> {
                Car foundCar = carRepository.findById(rObject)
                        .orElseThrow(() -> new CarNotFoundException("Bil nummer " +rObject + " finns inte. Försök med en annan bil."));

                Rental newRental = new Rental(foundMember, rObject, time, null, foundCar.getPrice(), 0, foundMember.getLevel(), 0, RentalType.CAR);
                rentalRepository.saveRental(newRental);
                rentalList.add(newRental);
                nameInputR.clear();
                objInputR.clear();

            }

            case MOVIE -> {
                Movie foundMovie = movieRepository.findById(rObject)
                        .orElseThrow(() -> new MovieNotFoundException("Film nummer " +rObject + " finns inte. Försök med en annan film."));

                Rental newRental = new Rental(foundMember, rObject, time, null, foundMovie.getPrice(), 0, foundMember.getLevel(), 0, RentalType.MOVIE);
                rentalRepository.saveRental(newRental);
                rentalList.add(newRental);

                nameInputR.clear();
                objInputR.clear();
            }

            case TOOL -> {
                Tool foundTool = toolRepository.findById(rObject)
                        .orElseThrow(() -> new ToolNotFoundException("Verktyg nummer " +rObject + " finns inte. Försök med ett annat verktyg."));
                Rental newRental = new Rental(foundMember, rObject, time, null, foundTool.getPrice(), 0, foundMember.getLevel(), 0, RentalType.TOOL);
                rentalRepository.saveRental(newRental);
                rentalList.add(newRental);
                nameInputR.clear();
                objInputR.clear();
            }
        }
        } catch (MemberNotFoundException | CarNotFoundException | MovieNotFoundException | ToolNotFoundException | ItemAlreadyHiredException e) {
            labelErrorRent.setText(e.getMessage());
            labelErrorRent.setTextFill(Color.RED);
        }
    }

    public boolean updateRental(TextField id) {
        Long idR = Long.parseLong(id.getText());
        //int days = Integer.parseInt(daysToRent.getText());
        LocalDateTime time = LocalDateTime.now();

        Optional<Rental> opt = rentalRepository.findById(idR);
        if (opt.isPresent()) {
            Rental rental = opt.get();
            rental.setEndTime(time);
            long minutes = Duration.between(rental.getStartTime(), rental.getEndTime()).toMinutes();
            double hours = minutes / 60.0;
            double days= hours /24;
            days = Math.round(days * 10000.0) / 10000.0;

            rental.setDaysToRent(days);
            if (rental.getLevel() > 3) { rental.setLevel(3); }
            if (rental.getLevel() < 1) { rental.setLevel(1); }

            switch (rental.getLevel()) {
                case 1 -> {
                    double normalPrice = NormalPriceP.calcPrice(rental.getPrice());
                    rental.setTotalPrice(normalPrice * days);
                }
                case 2 -> {
                    double levelTwoPrice = LevelTwoPriceP.calcPrice(rental.getPrice());
                    rental.setTotalPrice(levelTwoPrice * days);
                }
                case 3 -> {
                    double levelThreePrice = LevelThreePriceP.calcPrice(rental.getPrice());
                    rental.setTotalPrice(levelThreePrice * days);
                }
            }

            rentalRepository.updateRental(rental);
            rentalList.setAll(rentalRepository.readRental());
            id.clear();
            return true;
        }
        return false;
    }

    public double getTotalRevenue(){
        double revenue = rentalRepository.getTotalRevenueDB();
        return revenue;
    }

}
