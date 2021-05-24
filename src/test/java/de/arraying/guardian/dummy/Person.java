package de.arraying.guardian.dummy;

import de.arraying.guardian.Wrap;
import de.arraying.guardian.Wrappable;

public interface Person extends Wrappable {

    @Wrap("getName")
    String getName();

    @Wrap("getAddress")
    Address getLive();

}
