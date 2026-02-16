package com.ME.service;

import com.ME.entity.*;
import com.ME.exception.CarNotFoundException;
import com.ME.exception.MemberNotFoundException;
import com.ME.repo.*;
import com.ME.util.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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

    MemberRepository memberRepo = new MemberRepositoryImpl(sessionFactory);
    MembershipService mSer = new MembershipService(memberRepo);



    //MembershipService mSer = new MembershipService();
    //MemberRepository mRepo = new MemberRepositoryImpl(HibernateUtil.getSessionFactory());
    //RentalRepositoryImpl rentalRepo = new RentalRepositoryImpl(HibernateUtil.getSessionFactory());
    //InventoryService inv = new InventoryService();
    PricePolicy NormalPriceP = new NormalPricePolicy();
    PricePolicy Level2PriceP = new Level2PricePolicy();


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
        Long rObject = Long.parseLong(objInputR.getText());

        /* Gamla metoderna
        Member foundMember = mSer.searchMemberR(name, memberList);
        Car foundCar = inv.searchCar(rObject, carList);
        Movie foundMovie = inv.searchMovie(rObject, movieList);
        Tool foundTool = inv.searchTool(rObject, toolList);
        */

            Member foundMember = memberRepository.findByName(name)
                    .orElseThrow(() -> new MemberNotFoundException(
                    "Medlemmen hittades inte tyvärr med namn: " + name
            ));

            /*
            if (foundMember.isPresent()) {
                throw new MemberNotFoundException("Medlem med namn " + name + " finns inte");
            }
            Optional<Car> foundCar = carRepository.findById(rObject);
            Optional<Movie> foundMovie = movieRepository.findById(rObject);
            Optional<Tool> foundTool = toolRepository.findById(rObject);
             */

        time = LocalDateTime.now().toString();

        switch (typeChoice) {
            case CAR -> {
                Car foundCar = carRepository.findById(rObject)
                        .orElseThrow(() -> new CarNotFoundException(
                                "Bil nummer " +rObject + " Hittades inte"
                        ));

                Rental newRental = new Rental(foundMember, rObject, time, "", foundCar.getPrice(), 0, foundMember.getLevel(), 0, RentalType.CAR);
                rentalRepository.saveRental(newRental);
                rTable.getItems().add(newRental);

                nameInputR.clear();
                objInputR.clear();

            }

            case MOVIE -> {
                Rental newRental = new Rental(foundMember, rObject, time, "", foundMovie.get().getPrice(), 0, foundMember.getLevel(), 0, RentalType.MOVIE);
                rentalRepository.saveRental(newRental);
                rTable.getItems().add(newRental);

                nameInputR.clear();
                objInputR.clear();
            }

            case TOOL -> {
                Rental newRental = new Rental(foundMember, rObject, time, "", foundTool.get().getPrice(), 0, foundMember.getLevel(), 0, RentalType.TOOL);
                rentalRepository.saveRental(newRental);
                rTable.getItems().add(newRental);
                rentalList.add(newRental);
                nameInputR.clear();
                objInputR.clear();
            }
        }
        } catch (MemberNotFoundException | CarNotFoundException e) {
            labelErrorRent.setText(e.getMessage());
        }
    }

    public Optional<Rental> findRentalById(Long id) {
        return rentalList.stream()
                .filter(m -> m.getId() == id)
                .findFirst();
    }

    public boolean updateRental(TextField id, TextField daysToRent, TableView<Rental> rTable) {
        Long idR = Long.parseLong(id.getText());
        int days = Integer.parseInt(daysToRent.getText());
        String time = LocalDateTime.now().toString();

        Optional<Rental> opt = findRentalById(idR);
        if (opt.isPresent()) {
            Rental m = opt.get();
            if (m.getLevel() == 2) {
                double Level2Price = Level2PriceP.calcPrice(m.getPrice());
                m.setTotalprice(Level2Price * days);
            }
            else { double NormalPrice = NormalPriceP.calcPrice(m.getPrice());
                m.setTotalprice(NormalPrice * days);
            }

            m.setEndTime(time);
            m.setDaysToRent(days);
            rTable.refresh();
            id.clear();
            daysToRent.clear();
            return true;
        }
        return false;
    }

    public void showRevenue(double totalRevenue, Label labelRevenue){
        for (Rental rental : rentalList) {
            totalRevenue = totalRevenue + rental.getTotalPrice();
        }
        labelRevenue.setText(Double.toString(totalRevenue));
    }

}
