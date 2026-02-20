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

    private String time;

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
        time = LocalDateTime.now().toString();
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

                Rental newRental = new Rental(foundMember, rObject, time, "", foundCar.getPrice(), 0, foundMember.getLevel(), 0, RentalType.CAR);
                rentalRepository.saveRental(newRental);
                rentalList.add(newRental);
                nameInputR.clear();
                objInputR.clear();

            }

            case MOVIE -> {
                Movie foundMovie = movieRepository.findById(rObject)
                        .orElseThrow(() -> new MovieNotFoundException("Film nummer " +rObject + " finns inte. Försök med en annan film."));

                Rental newRental = new Rental(foundMember, rObject, time, "", foundMovie.getPrice(), 0, foundMember.getLevel(), 0, RentalType.MOVIE);
                rentalRepository.saveRental(newRental);
                rentalList.add(newRental);

                nameInputR.clear();
                objInputR.clear();
            }

            case TOOL -> {
                Tool foundTool = toolRepository.findById(rObject)
                        .orElseThrow(() -> new ToolNotFoundException("Verktyg nummer " +rObject + " finns inte. Försök med ett annat verktyg."));
                Rental newRental = new Rental(foundMember, rObject, time, "", foundTool.getPrice(), 0, foundMember.getLevel(), 0, RentalType.TOOL);
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

    public boolean updateRental(TextField id, TextField daysToRent) {
        Long idR = Long.parseLong(id.getText());
        int days = Integer.parseInt(daysToRent.getText());
        String time = LocalDateTime.now().toString();

        Optional<Rental> opt = rentalRepository.findById(idR);
        if (opt.isPresent()) {
            Rental rental = opt.get();
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

            rental.setEndTime(time);
            rental.setDaysToRent(days);
            rentalRepository.updateRental(rental);
            rentalList.setAll(rentalRepository.readRental());
            id.clear();
            daysToRent.clear();
            return true;
        }
        return false;
    }

    public double getTotalRevenue(){
        double revenue = rentalRepository.getTotalRevenueDB();
        return revenue;
    }

}
