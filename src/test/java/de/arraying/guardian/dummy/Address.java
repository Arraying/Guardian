package de.arraying.guardian.dummy;

import de.arraying.guardian.Wrap;
import de.arraying.guardian.Wrappable;

public interface Address extends Wrappable {

    @Wrap("getStreet")
    String getStreet();

    @Wrap("getCity")
    String getCity();

    @Wrap("getCountry")
    Country getCountry();

}
