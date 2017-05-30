package logic.mapper;

import javafx.util.StringConverter;
import logic.entities.ObservableCustomer;

import java.util.HashMap;
import java.util.Map;


public class ObservableCustomerStringConverter extends StringConverter<ObservableCustomer> {
    //Jesper
    //Code borrowed from  https://stackoverflow.com/questions/10699655/combo-box-key-value-pair-in-javafx-2 and refactored.

    //Creates a HashMap of Strings and ObservableCustomer Objects.
    private Map<String, ObservableCustomer> mappedCustomers = new HashMap<>();

    //Extracts int and String from ObservableCustomer object and returns them as one String for use in ComboBox.
    @Override
    public String toString(ObservableCustomer observableCustomer) {
        mappedCustomers.put(String.valueOf(observableCustomer.getCvr()), observableCustomer);
        mappedCustomers.put(observableCustomer.getName(), observableCustomer);
        return (String.valueOf(observableCustomer.getName() + ", CVR: " + observableCustomer.getCvr()));
    }


    @Override
    public ObservableCustomer fromString(String string) {
        return mappedCustomers.get(string);
    }
}
