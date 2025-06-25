package dz_hw_13;

interface Transportable {

    boolean move(Person person, Terrain terrain, int distance);
}

enum Terrain {
    FOREST,
    PLAIN,
    SWAMP
}

abstract class Transport implements Transportable {
    protected String type;

    public Transport(String type) {
        this.type = type;
    }

    @Override
    public abstract boolean move(Person person, Terrain terrain, int distance);
}

class Person {
    public String name;
    private Transport currentTransport;

    public Person(String name) {
        this.name = name;
    }

    public void setTransport(Transport transport) {
        this.currentTransport = transport;
    }

    public void removeTransport() {
        this.currentTransport = null;
    }

    public boolean move(Terrain terrain, int distance) {
        if (currentTransport != null) {

            return currentTransport.move(this, terrain, distance);
        } else {

            System.out.println(name + " идет пешком " + distance + " метров по " + terrain);
            return true;
        }
    }

    public void printStatus() {
        if (currentTransport == null) {
            System.out.println(name + " сейчас не использует транспорт.");
        } else {
            System.out.println(name + " использует " + currentTransport.type);
        }
    }
}

class Car extends Transport {
    private int fuel;

    public Car(int fuel) {
        super("Car");
        this.fuel = fuel;
    }

    @Override
    public boolean move(Person person, Terrain terrain, int distance) {
        switch (terrain) {
            case FOREST:
            case SWAMP:
                System.out.println(person.name + " не может проехать на автомобиле через " + terrain);
                return false;
            case PLAIN:
                if (fuel >= distance) {
                    fuel -= distance;
                    System.out.println(person.name + " проезжает на автомобиле " + distance + " метров по равнине.");
                    return true;
                } else {
                    System.out.println(person.name + " не может проехать на автомобиле из-за недостатка топлива.");
                    return false;
                }
            default:
                throw new IllegalArgumentException("Неизвестный тип местности: " + terrain);
        }
    }
}

class Horse extends Transport {
    private int strength;

    public Horse(int strength) {
        super("Horse");
        this.strength = strength;
    }

    @Override
    public boolean move(Person person, Terrain terrain, int distance) {
        switch (terrain) {
            case SWAMP:
                System.out.println(person.name + " не может проехать на лошади через болото.");
                return false;
            case FOREST:
            case PLAIN:
                if (strength >= distance) {
                    strength -= distance;
                    System.out.println(person.name + " проезжает на лошади " + distance + " метров по " + terrain);
                    return true;
                } else {
                    System.out.println(person.name + " не может проехать на лошади из-за недостатка сил.");
                    return false;
                }
            default:
                throw new IllegalArgumentException("Неизвестный тип местности: " + terrain);
        }
    }
}

class Bicycle extends Transport {
    private int driverStrength;

    public Bicycle(int driverStrength) {
        super("Bicycle");
        this.driverStrength = driverStrength;
    }

    @Override
    public boolean move(Person person, Terrain terrain, int distance) {
        switch (terrain) {
            case SWAMP:
                System.out.println(person.name + " не может проехать на велосипеде через болото.");
                return false;
            case FOREST:
            case PLAIN:
                if (driverStrength >= distance) {
                    driverStrength -= distance;
                    System.out.println(person.name + " проезжает на велосипеде " + distance + " метров по " + terrain);
                    return true;
                } else {
                    System.out.println(person.name + " не может проехать на велосипеде из-за недостатка сил.");
                    return false;
                }
            default:
                throw new IllegalArgumentException("Неизвестный тип местности: " + terrain);
        }
    }
}

class AllTerrainVehicle extends Transport {
    private int fuel;

    public AllTerrainVehicle(int fuel) {
        super("All-Terrain Vehicle");
        this.fuel = fuel;
    }

    @Override
    public boolean move(Person person, Terrain terrain, int distance) {
        if (fuel >= distance) {
            fuel -= distance;
            System.out.println(person.name + " проезжает на вездеходе " + distance + " метров по " + terrain);
            return true;
        } else {
            System.out.println(person.name + " не может проехать на вездеходе из-за недостатка топлива.");
            return false;
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Person alex = new Person("Alex");

        Car car = new Car(100);
        Horse horse = new Horse(80);
        Bicycle bicycle = new Bicycle(60);
        AllTerrainVehicle atv = new AllTerrainVehicle(120);

        alex.printStatus();
        alex.move(Terrain.PLAIN, 50);

        alex.setTransport(car);
        alex.printStatus();
        alex.move(Terrain.FOREST, 70);
        alex.move(Terrain.PLAIN, 50);

        alex.removeTransport();
        alex.setTransport(horse);
        alex.printStatus();
        alex.move(Terrain.SWAMP, 20);
        alex.move(Terrain.PLAIN, 30);

        alex.removeTransport();
        alex.setTransport(bicycle);
        alex.printStatus();
        alex.move(Terrain.SWAMP, 10);
        alex.move(Terrain.PLAIN, 20);

        alex.removeTransport();
        alex.setTransport(atv);
        alex.printStatus();
        alex.move(Terrain.SWAMP, 40);
    }
}