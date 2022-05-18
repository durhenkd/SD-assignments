package bussiness;

import Exceptions.InvalidRegisterInputException;
import model.Agency;
import model.Destination;
import model.VacationPackage;
import persistence.PersistenceClass;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

abstract public class AgencyService {

    public static void delete(Agency agency, Destination d){
        System.out.println("==== " + d.getPackages().size() + " pakages ++++");
        for(VacationPackage p: d.getPackages()){
            p.getBookedUsers().forEach((u)->{u.getBookings().remove(p);PersistenceClass.userPersistenceInstance.update(u);});
            p.getBookedUsers().clear();
            PersistenceClass.packagePersistenceInstance.update(p);
            PersistenceClass.packagePersistenceInstance.delete(p.getId());
        }
        System.out.println("==== Deleting destination id = " + d.getId());
        PersistenceClass.destinationPersistenceInstance.delete(d.getId());
    }
    public static void delete(Agency agency, VacationPackage p){
        agency.getPackages().remove(p);
        p.getBookedUsers().forEach((u)->{u.getBookings().remove(p);PersistenceClass.userPersistenceInstance.update(u);});
        p.getBookedUsers().clear();
        PersistenceClass.agencyPersistenceInstance.update(agency);
        PersistenceClass.packagePersistenceInstance.delete(p.getId());
    }

    public static List<VacationPackage> getPackages(Agency agency, Destination destination){

        if (agency.getPackages().isEmpty()){
            return new ArrayList<>();
        }

        return agency.getPackages().stream()
                .filter((e) -> e.getDestination().equals(destination))
                .toList();
    }

    public static void removePackage(Agency agency, VacationPackage vacationPackage){
        PersistenceClass.packagePersistenceInstance.delete(vacationPackage);
        agency.getPackages().remove(vacationPackage);
    }

    public static void removeAllPackages(Agency agency, Destination destination){
        agency.getPackages().stream()
                .filter((e) -> e.getDestination().equals(destination))
                .forEach(PersistenceClass.packagePersistenceInstance::delete);

        agency.setPackages(agency.getPackages().stream().filter((e) -> !e.getDestination().equals(destination)).toList());
    }

    public static void updatePackage(VacationPackage vacationPackage,
                                  String name,
                                  String price,
                                  LocalDate start,
                                  LocalDate end,
                                  String details,
                                  String maxBooks
    ) throws InvalidRegisterInputException {
        if (name.equals("")) throw new InvalidRegisterInputException("Name shouldn't be empty");
        if (price.equals("")) throw new InvalidRegisterInputException("Should have a price");
        if (start == null) throw new InvalidRegisterInputException("Should have a start date.");
        if (end == null) throw new InvalidRegisterInputException("Should have a end date.");
        if (maxBooks.equals("")) throw new InvalidRegisterInputException("Should have a maximum number of bookings");
        if (!start.isBefore(end)) throw new InvalidRegisterInputException("Start date should be before end date.");
        if (details.length() > 220) throw new InvalidRegisterInputException("Details too Long");

        double _price;
        int _maxBooks;

        try {
            _price = Double.parseDouble(price);
            _maxBooks = Integer.parseInt(maxBooks);
        }catch (Exception e){
            throw new InvalidRegisterInputException("Number values are incorrect");
        }

        Date startDate = Date.from(start.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(end.atStartOfDay(ZoneId.systemDefault()).toInstant());

        vacationPackage.setName(name);
        vacationPackage.setPrice(_price);
        vacationPackage.setPeriodStart(startDate);
        vacationPackage.setPeriodEnd(endDate);
        vacationPackage.setDetails(details);
        vacationPackage.setMaxBooks(_maxBooks);

        PersistenceClass.packagePersistenceInstance.update(vacationPackage);
    }

        public static void addPackage(Agency agency,
                                  String name,
                                  String price,
                                  LocalDate start,
                                  LocalDate end,
                                  String details,
                                  String maxBooks,
                                  Destination destination
    ) throws InvalidRegisterInputException {
        if (name.equals(""))    throw new InvalidRegisterInputException("Name shouldn't be empty");
        if (price.equals(""))   throw new InvalidRegisterInputException("Should have a price");
        if (start == null)      throw new InvalidRegisterInputException("Should have a start date.");
        if (end == null)        throw new InvalidRegisterInputException("Should have a end date.");
        if (maxBooks.equals(""))throw new InvalidRegisterInputException("Should have a maximum number of bookings");
        if (destination == null)throw new InvalidRegisterInputException("Should have a destination");
        if(!start.isBefore(end))throw  new InvalidRegisterInputException("Start date should be before end date.");
        if (details.length()>220)throw new InvalidRegisterInputException("Details too Long");

        double _price;
        int _maxBooks;

        try {
            _price = Double.parseDouble(price);
            _maxBooks = Integer.parseInt(maxBooks);
        }catch (Exception e){
            throw new InvalidRegisterInputException("Number values are incorrect");
        }

        Date startDate = Date.from(start.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(end.atStartOfDay(ZoneId.systemDefault()).toInstant());

        VacationPackage vPackage = new VacationPackage(name, _price, startDate, endDate, details, _maxBooks, "", new ArrayList<>(), destination);

        PersistenceClass.packagePersistenceInstance.persist(vPackage);

        vPackage = PersistenceClass.packagePersistenceInstance.query(
                List.of("name", "price", "maxBooks"),
                List.of(" = '" + name + "'", " = " + _price, " = " + _maxBooks)).get(0);

        agency.getPackages().add(vPackage);
        PersistenceClass.agencyPersistenceInstance.update(agency);
    }

    public static List<Destination> getAllDestinations(){
        return PersistenceClass.destinationPersistenceInstance.queryAll();
    }

    public static Destination addDestination(String name, String country){
        PersistenceClass.destinationPersistenceInstance.persist(new Destination(
                name,
                country,
                new ArrayList<>()
        ));

        return PersistenceClass.destinationPersistenceInstance.query(
                List.of("name","countryName"),
                List.of(" = '" + name + "'", " = '" + country+ "'")
        ).get(0);
    }


}
