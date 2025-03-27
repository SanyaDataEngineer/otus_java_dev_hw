class Animal {
    protected String name;
    protected double speedRun;
    protected double speedSwim;
    protected int stamina;

    public Animal(String name, double speedRun, double speedSwim, int stamina) {
        this.name = name;
        this.speedRun = speedRun;
        this.speedSwim = speedSwim;
        this.stamina = stamina;
    }

    public double run(int distance) {
        if (stamina >= distance) {
            System.out.println(name + " бежит " + distance + " метров.");
            stamina -= distance;
            return distance / speedRun;
        } else {
            System.out.println(name + " устал и не может бежать!");
            return -1;
        }
    }

    public double swim(int distance) {
        if (speedSwim > 0 && stamina >= calculateStaminaCost(distance)) {
            System.out.println(name + " плывет " + distance + " метров.");
            stamina -= calculateStaminaCost(distance);
            return distance / speedSwim;
        } else {
            System.out.println(name + " устал и не может плыть!");
            return -1;
        }
    }

    public void info() {
        System.out.println("Имя: " + name +
                "\nСкорость бега: " + speedRun + " м/с" +
                "\nСкорость плавания: " + speedSwim + " м/с" +
                "\nВыносливость: " + stamina + " единиц");
    }

    protected int calculateStaminaCost(int distance) {
        return distance * 2;
    }
}

class Cat extends Animal {
    public Cat(String name, double speedRun, int stamina) {
        super(name, speedRun, 0, stamina);
    }

    @Override
    public double swim(int distance) {
        System.out.println(name + " не умеет плавать!");
        return -1;
    }
}

class Dog extends Animal {
    public Dog(String name, double speedRun, double speedSwim, int stamina) {
        super(name, speedRun, speedSwim, stamina);
    }

    @Override
    protected int calculateStaminaCost(int distance) {
        return distance * 2;
    }
}

class Horse extends Animal {
    public Horse(String name, double speedRun, double speedSwim, int stamina) {
        super(name, speedRun, speedSwim, stamina);
    }

    @Override
    protected int calculateStaminaCost(int distance) {
        return distance * 4;
    }
}

public class Main {
    public static void main(String[] args) {

        Cat cat = new Cat("Барсик", 10, 100);
        Dog dog = new Dog("Шарик", 12, 8, 200);
        Horse horse = new Horse("Мустанг", 15, 6, 300);

        System.out.println("\n--- Бег и плавание ---");
        System.out.println("Время бега Барсика на 50 метров: " + cat.run(50));
        System.out.println("Время плавания Шарика на 20 метров: " + dog.swim(20));
        System.out.println("Время бега Мустанга на 150 метров: " + horse.run(150));
        System.out.println("Время плавания Мустанга на 30 метров: " + horse.swim(30));

        System.out.println("\n--- Состояние животных ---");
        cat.info();
        dog.info();
        horse.info();
    }
}