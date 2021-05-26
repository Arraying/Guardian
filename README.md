# Guardian

Reflection-based proxy engine to encapsulate sensitive objects in beans.
The core idea is that given some object that contains information relevant in i.e. a script engine but contains sensitive information, you can easily encapsulate it.

## Usage

### Basic

For this example, we will only encapsulate one class, `RealPerson`.

The sensitive class contains some information we want to expose to a script engine, such as the name and address, but for the sake of this example not the age.
```java
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
```
We can now create an interface to act as the object that will be initialized by the engine and can be exposed to the script engine.
```java
public interface Person extends Wrappable {

    @Wrap("getName")
    String whatTheyCallMe();

}
```
Two things are important here:
1. The interface must extend Wrappable, this is not a technical limitation but rather a forced convention.
2. The annotation on each method denotes which source method of the object we are encapsulating to invoke.
For example, we are invoking `RealPerson#getName` and binding it to `Person#whatTheyCallMe`.
   
Next we need to initialize the engine.
```java
GuardianContext context = new GuardianContext();
context.associate(RealPerson.class, Person.class);
```
Finally, we can encapsulate the object:
```java
Person person = context.wrap(new RealPerson());
```

### Recursive

The underlying concepts work recursively. On top of `RealPerson`, also consider `RealAddress`.
On top of the already provided above code, let us define the address as:
```java
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
```
Which shall be encapsulated by:
```java
public interface Address extends Wrappable {

    @Wrap("getStreet")
    String getStreet();

    @Wrap("getCity")
    String getCity();

    @Wrap("getCountry")
    Country getCountry();

}
```

After extending the context as shown below, the behaviour will work as expected:
```java
GuardianContext context = new GuardianContext();
context.associate(RealPerson.class, Person.class);
context.associate(RealAddress.class, Address.class);
```

## Specification

In order to work as expected, these are the specifications.
1. Every encapsulating object must be an interface extending `Wrappable`.
2. Every method must be annotated by `@Wrap` and the referencing method name must be provided, non-null and non-empty.
3. Every referenced method must meet the following criteria:
    1. The method must be `public` or otherwise accessible without the use of `Method#setAccessible`.
    2. The method must return a non-primitive object, be of return type `void` or return `null`.
    3. The method must not return an array of any dimension.
    4. The method must not have any parameters.
    By default, the method will be looked up using zero parameters.
    As such, any method with parameters will simply not be found.
4. If the return value of a represented object returns a type that mismatches the return type of the encapsulating object's method, the following will happen:
    * If the return value is a registered association in the context, the return value itself will also be transformed recursively.
    * If not, an error will be thrown.
    
## Installation

Coming soon.

## Roadmap

The following features are currently planned, and specified in no order:
* Support for primitive data types as well as arrays.
* Methods that return `Collection` or `Map` having their elements (values for `Map`) scanned and if applicable mapped to other encapsulated objects.
* Ability to choose how the reflection is performed and adding [ReflectASM](https://github.com/EsotericSoftware/reflectasm) as an option.
* Adding support for setters. The details of this aren't clear yet.