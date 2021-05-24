package de.arraying.guardian.real;

public class RealAddress {

    public Integer getHouse() {
        return 123;
    }

    public String getStreet() {
        return "Street";
    }

    public String getCity() {
        return "City";
    }

    public RealCountry getCountry() {
        return new RealCountry();
    }
}
