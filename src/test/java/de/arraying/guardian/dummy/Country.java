package de.arraying.guardian.dummy;

import de.arraying.guardian.Wrap;
import de.arraying.guardian.Wrappable;

public interface Country extends Wrappable {

    @Wrap("getName")
    String getName();

    @Wrap("playAnthem")
    void playAnthem();
}
