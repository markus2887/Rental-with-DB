package com.ME.OOP2;

import com.ME.OOP2.entity.Car;
import com.ME.OOP2.entity.Member;
import com.ME.OOP2.entity.Rental;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.converter.LocalDateTimeStringConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class RentalService {

    ObservableList<Rental> rentalList = FXCollections.observableArrayList();
    MembershipService mSer = new MembershipService();
    MemberRegistry mReg = new MemberRegistry();
    Inventory inv = new Inventory();
    PricePolicy NormalPriceP = new NormalPricePolicy();
    PricePolicy Level2PriceP = new Level2PricePolicy();


    public RentalService() throws Exception {
    }

    public void rentButtonClicked(TextField nameInputR, TextField carInputR, TextField priceInputR, TableView<Rental> rTable) {
        String name = nameInputR.getText();
        int car = Integer.parseInt(carInputR.getText());
        double price = Double.parseDouble(priceInputR.getText());
        //int lvl = Integer.parseInt(lvlInputR.getText());
        Member m = mSer.searchMemberR(mReg.membersList, name);
        Car c = searchCar(car, inv.carList);

        String time = LocalDateTime.now().toString();

        Rental newRental = new Rental(name, car, time, "", price, 0, m.getLevel(), 0);
        rTable.getItems().add(newRental);
        nameInputR.clear();
        carInputR.clear();
        priceInputR.clear();
    }
    public Car searchCar(int car, ObservableList<Car> cars) {
        Car foundCar = cars.stream()
                .filter(c -> c.getId() == car)
                .findFirst()
                .orElse(null);
        return foundCar;
    }

    public Optional<Rental> findRentalById(int id) {
        return rentalList.stream()
                .filter(m -> m.getId() == id)
                .findFirst();
    }

    public boolean updateRental(TextField id, TextField daysToRent, TableView<Rental> rTable) {
        int idR = Integer.parseInt(id.getText());
        int days = Integer.parseInt(daysToRent.getText());
        String time = LocalDateTime.now().toString();

        Optional<Rental> opt = findRentalById(idR);
        if (opt.isPresent()) {
            Rental m = opt.get();
            if (m.getLevel() == 2) {
                double Level2Price = Level2PriceP.calcPrice(m.getPrice());
                m.setTotalprice(Level2Price * days);
            } else { double NormalPrice = NormalPriceP.calcPrice(m.getPrice());
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
