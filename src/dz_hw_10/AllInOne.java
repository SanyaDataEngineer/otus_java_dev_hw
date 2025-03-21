package dz_hw_10;

class User {
    // Поля класса
    private String surname;
    private String name;
    private String patronymic;
    private int yearOfBirth;
    private String email;

    public User(String surname, String name, String patronymic, int yearOfBirth, String email) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.yearOfBirth = yearOfBirth;
        this.email = email;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void printInfo() {
        System.out.println("ФИО: " + surname + " " + name + " " + patronymic);
        System.out.println("Год рождения: " + yearOfBirth);
        System.out.println("e-mail: " + email);
    }
}

class Box {
    // Поля класса
    private final double width;
    private final double height;
    private final double depth;
    private String color;
    private boolean isOpen;
    private String item;

    public Box(double width, double height, double depth, String color) {
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.color = color;
        this.isOpen = false;
        this.item = null;
    }

    public void open() {
        if (!isOpen) {
            isOpen = true;
            System.out.println("Коробка открыта.");
        } else {
            System.out.println("Коробка уже открыта.");
        }
    }

    public void close() {
        if (isOpen) {
            isOpen = false;
            System.out.println("Коробка закрыта.");
        } else {
            System.out.println("Коробка уже закрыта.");
        }
    }

    public void repaint(String newColor) {
        if (newColor != null) {
            this.color = newColor;
            System.out.println("Цвет коробки изменён на " + newColor + ".");
        } else {
            System.out.println("Новый цвет не указан.");
        }
    }

    public void putItem(String itemName) {
        if (isOpen && item == null) {
            item = itemName;
            System.out.println("Предмет " + itemName + " положен в коробку.");
        } else if (!isOpen) {
            System.out.println("Нельзя положить предмет в закрытую коробку.");
        } else if (item != null) {
            System.out.println("В коробке уже лежит предмет.");
        }
    }

    public void removeItem() {
        if (isOpen && item != null) {
            System.out.println("Предмет " + item + " вынут из коробки.");
            item = null;
        } else if (!isOpen) {
            System.out.println("Нельзя вынуть предмет из закрытой коробки.");
        } else if (item == null) {
            System.out.println("В коробке нет предмета.");
        }
    }

    public void printInfo() {
        System.out.println("Размеры коробки: ширина=" + width + ", высота=" + height + ", глубина=" + depth);
        System.out.println("Цвет коробки: " + color);
        System.out.println("Открыта: " + isOpen);
        if (item != null) {
            System.out.println("Предмет внутри: " + item);
        } else {
            System.out.println("Предмет отсутствует.");
        }
    }
}

public class AllInOne {
    public static void main(String[] args) {
        User[] users = new User[10];

        users[0] = new User("Иванов", "Иван", "Иванович", 1980, "ivanov@example.com");
        users[1] = new User("Петров", "Петр", "Петрович", 1978, "petrov@example.com");
        users[2] = new User("Сидоров", "Сергей", "Сергеевич", 1990, "sidorov@example.com");
        users[3] = new User("Кузнецова", "Анна", "Павловна", 1969, "kuznetsova@example.com");
        users[4] = new User("Федорова", "Марина", "Александровна", 2001, "fedorova@example.com");
        users[5] = new User("Васильев", "Алексей", "Николаевич", 1956, "vasiliev@example.com");
        users[6] = new User("Миронова", "Ольга", "Владимировна", 1947, "mironova@example.com");
        users[7] = new User("Смирнов", "Дмитрий", "Викторович", 1972, "smirnov@example.com");
        users[8] = new User("Орлова", "Елена", "Игоревна", 1995, "orlova@example.com");
        users[9] = new User("Зайцев", "Андрей", "Андреевич", 2010, "zaytsev@example.com");

        for (User user : users) {
            if (user.getYearOfBirth() <= 1980) { // Текущий год минус 40
                user.printInfo();
            }
        }

        Box box = new Box(20, 30, 15, "Красный");
        box.open();   // Открываем коробку
        box.putItem("Книга");  // Кладем книгу
        box.close();  // Закрываем коробку
        box.repaint("Синий");  // Перекрашиваем коробку
        box.open();   // Снова открываем
        box.removeItem();  // Вынимаем книгу
        box.printInfo();   // Печатаем информацию о коробке
    }
}