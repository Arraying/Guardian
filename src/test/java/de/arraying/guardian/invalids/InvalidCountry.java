package de.arraying.guardian.invalids;

import de.arraying.guardian.Wrap;
import de.arraying.guardian.Wrappable;
import de.arraying.guardian.dummy.Address;
import de.arraying.guardian.dummy.Blank;

public interface InvalidCountry extends Wrappable {

    String getNothing();

    @Wrap("get")
    String getNonExistent();

    @Wrap("getNameWithException")
    String getException();

    @Wrap("getSomething")
    String getPrivate();

    @Wrap("isPrimitive")
    boolean getPrimitive();

    @Wrap("getCities")
    String[] getArray();

    @Wrap("imprison")
    String arguments(String in);

    @Wrap("getName")
    Blank getBlank();

    @Wrap("getPresident")
    Address getPresident();
}
