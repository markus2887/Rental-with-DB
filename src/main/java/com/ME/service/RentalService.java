package com.ME.service;

import com.ME.entity.*;
import com.ME.exception.CarNotFoundException;
import com.ME.repo.MemberRepository;
import com.ME.repo.MemberRepositoryImpl;
import com.ME.repo.RentalRepositoryImpl;
import com.ME.util.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.time.LocalDateTime;
import java.util.Optional;

public class RentalService {

    private ObservableList<Rental> rentalList = FXCollections.observableArrayList();

    private String time;
    MembershipService mSer = new MembershipService();
    PricePolicy NormalPriceP = new NormalPricePolicy();
    PricePolicy Level2PriceP = new Level2PricePolicy();
    MemberRepository mRepo = new MemberRepositoryImpl(HibernateUtil.getSessionFactory());
    RentalRepositoryImpl rentalRepo = new RentalRepositoryImpl(HibernateUtil.getSessionFactory());
    InventoryService inv = new InventoryService();

    public ObservableList<Rental> getRentalList() {
        return rentalList;
    }

    public void loadRental() {
        rentalList.setAll(rentalRepo.readRental());
    }

    public RentalService() throws Exception {
    }

    public void rentButtonClicked(TextField nameInputR, TextField objInputR, ComboBox<RentalType> rentalType, TableView<Rental> rTable, ObservableList<Member> memberList, ObservableList<Car> carList, ObservableList<Movie> movieList, ObservableList<Tool> toolList, Label labelErrorRent) {
        try {
        RentalType typeChoice = rentalType.getValue();
        String name = nameInputR.getText();
        Long rObject = Long.parseLong(objInputR.getText());

        Member foundMember = mSer.searchMemberR(name, memberList);
        Car foundCar = inv.searchCar(rObject, carList);
        Movie foundMovie = inv.searchMovie(rObject, movieList);
        Tool foundTool = inv.searchTool(rObject, toolList);

        time = LocalDateTime.now().toString();

        switch (typeChoice) {
            case CAR -> {
                Rental newRental = new Rental(foundMember, rObject, time, "", foundCar.getPrice(), 0, foundMember.getLevel(), 0, RentalType.CAR);
                rentalRepo.saveRental(newRental);
                rTable.getItems().add(newRental);

                nameInputR.clear();
                objInputR.clear();

            }

            case MOVIE -> {
                Rental newRental = new Rental(foundMember, rObject, time, "", foundMovie.getPrice(), 0, foundMember.getLevel(), 0, RentalType.MOVIE);
                rentalRepo.saveRental(newRental);
                rTable.getItems().add(newRental);

                nameInputR.clear();
                objInputR.clear();
            }

            case TOOL -> {
                Rental newRental = new Rental(foundMember, rObject, time, "", foundTool.getPrice(), 0, foundMember.getLevel(), 0, RentalType.TOOL);
                rentalRepo.saveRental(newRental);
                rTable.getItems().add(newRental);
                rentalList.add(newRental);
                nameInputR.clear();
                objInputR.clear();
            }
        }
        } catch (CarNotFoundException e) {
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
