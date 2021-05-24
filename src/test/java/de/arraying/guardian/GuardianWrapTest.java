package de.arraying.guardian;

import de.arraying.guardian.dummy.Address;
import de.arraying.guardian.dummy.Country;
import de.arraying.guardian.dummy.Person;
import de.arraying.guardian.invalids.*;
import de.arraying.guardian.real.RealAddress;
import de.arraying.guardian.real.RealCountry;
import de.arraying.guardian.real.RealPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GuardianWrapTest {

    private GuardianContext context;

    @BeforeEach
    void before() {
        context = new GuardianContext();
        context.associate(RealPerson.class, Person.class);
        context.associate(RealAddress.class, Address.class);
        context.associate(RealCountry.class, Country.class);
    }

    @Test
    void exception1() {
        GuardianWrapException exception = new GuardianWrapException("test");
        assertEquals("test", exception.getMessage());
    }
    
    @Test
    void exception2() {
        Exception cause = new Exception("test");
        GuardianWrapException exception = new GuardianWrapException("test", cause);
        assertEquals("test", exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void wrapNull() {
        Object result = context.wrap(null);
        assertNull(result);
    }

    @Test
    void validSingle() {
        Object result = context.wrap(new RealCountry());
        assertTrue(result instanceof Country);
        validateCountry((Country) result);
    }

    @Test
    void validDouble() {
        Object result = context.wrap(new RealAddress());
        assertTrue(result instanceof Address);
        validateAddress((Address) result);
        Country country = ((Address) result).getCountry();
        validateCountry(country);
    }

    @Test
    void validRecursive() {
        Object result = context.wrap(new RealPerson());
        assertTrue(result instanceof Person);
        validatePerson((Person) result);
        Address address = ((Person) result).getLive();
        validateAddress(address);
        Country country = address.getCountry();
        validateCountry(country);
    }

    @Test
    void validVoid() {
        Object result = context.wrap(new RealCountry());
        Country country = (Country) result;
        assertDoesNotThrow(country::playAnthem);
    }

    @Test
    void invalidType() {
        assertThrows(GuardianWrapException.class, () -> context.wrap("abc"));
    }

    @Test
    void invalidBind1() {
        context.associate(RealCountry.class, InvalidCountry.class);
        InvalidCountry country = (InvalidCountry) context.wrap(new RealCountry());
        assertThrows(GuardianWrapException.class, country::getNothing);
    }

    @Test
    void invalidBind2() {
        context.associate(RealCountry.class, InvalidCountry.class);
        InvalidCountry country = (InvalidCountry) context.wrap(new RealCountry());
        assertThrows(GuardianWrapException.class, country::getNonExistent);
    }
    
    @Test
    void invalidInvocation1() {
        context.associate(RealCountry.class, InvalidCountry.class);
        InvalidCountry country = (InvalidCountry) context.wrap(new RealCountry());
        assertThrows(GuardianWrapException.class, country::getException);
    }
    
    @Test
    void invalidInvocation2() {
        context.associate(RealCountry.class, InvalidCountry.class);
        InvalidCountry country = (InvalidCountry) context.wrap(new RealCountry());
        assertThrows(GuardianWrapException.class, country::getPrivate);
    }

    @Test
    void invalidMethod1() {
        context.associate(RealCountry.class, InvalidCountry.class);
        InvalidCountry country = (InvalidCountry) context.wrap(new RealCountry());
        assertThrows(GuardianWrapException.class, country::getPrimitive);
    }

    @Test
    void invalidMethod2() {
        context.associate(RealCountry.class, InvalidCountry.class);
        InvalidCountry country = (InvalidCountry) context.wrap(new RealCountry());
        assertThrows(GuardianWrapException.class, country::getArray);
    }

    @Test
    void invalidMethod3() {
        context.associate(RealCountry.class, InvalidCountry.class);
        InvalidCountry country = (InvalidCountry) context.wrap(new RealCountry());
        assertThrows(GuardianWrapException.class, () -> country.arguments("abc"));
    }

    @Test
    void invalidReturn1() {
        context.associate(RealCountry.class, InvalidCountry.class);
        InvalidCountry country = (InvalidCountry) context.wrap(new RealCountry());
        assertThrows(GuardianWrapException.class, country::getBlank);
    }

    @Test
    void invalidReturn2() {
        context.associate(RealCountry.class, InvalidCountry.class);
        InvalidCountry country = (InvalidCountry) context.wrap(new RealCountry());
        assertThrows(GuardianWrapException.class, country::getPresident);
    }

    private void validateCountry(Country country) {
        assertNotNull(country);
        assertEquals("Country", country.getName());
    }

    private void validateAddress(Address address) {
        assertNotNull(address);
        assertEquals("Street", address.getStreet());
        assertEquals("City", address.getCity());
    }

    private void validatePerson(Person person) {
        assertNotNull(person);
        assertEquals("Name", person.getName());
    }
}
