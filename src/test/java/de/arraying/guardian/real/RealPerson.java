package de.arraying.guardian.real;

public class RealPerson {

    public Integer getAge() {
        return 19;
    }

    public String getName() {
        return "Name";
    }

    public RealAddress getAddress() {
        return new RealAddress();
    }

}
