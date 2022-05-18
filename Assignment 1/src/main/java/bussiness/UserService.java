package bussiness;

import model.Destination;
import model.User;
import model.VacationPackage;
import persistence.PersistenceClass;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

abstract public class UserService {


    static public List<VacationPackage> getFilteredPackages(List<VacationPackage> packages,
                                                      Date after,
                                                      Date before,
                                                      double priceMin,
                                                      double priceMax)
    {

        return packages.stream()
                .filter((e)-> e.getBookedUsers().size()<e.getMaxBooks())
                .filter((e)-> {if (before == null) return true; else return e.getPeriodEnd().before(before);})
                .filter((e)-> {if (after == null) return true; else return e.getPeriodStart().after(after);})
                .filter((e)-> e.getPrice() > priceMin && e.getPrice() < priceMax).collect(Collectors.toList());
    }

    static public List<VacationPackage> getAllPackages(){
        return PersistenceClass.packagePersistenceInstance.queryAll();
    }

    static public List<Destination> getAllDestinations(){
        return PersistenceClass.destinationPersistenceInstance.queryAll();
    }

    static public void update(User user){
        PersistenceClass.userPersistenceInstance.update(user);
    }

}
