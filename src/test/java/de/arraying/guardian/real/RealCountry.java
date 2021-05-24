package de.arraying.guardian.real;

public class RealCountry {

    public String getName() {
        return "Country";
    }

    public String getNameWithException() {
        throw new RuntimeException();
    }

    public String getNuclearLaunchCodes() {
        return "abc123";
    }

    private String getSomething() {
        return "Something";
    }

    public boolean isPrimitive() {
        return true;
    }

    public String[] getCities() {
        return new String[0];
    }

    public void playAnthem() {}

    public String imprison(String name) {
        return name;
    }

    public RealPerson getPresident() {
        return new RealPerson();
    }
}
