package project_1;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileManager {
    private static Path currentPath = Paths.get("").toAbsolutePath();
    private static final Scanner scanner = new Scanner(System.in);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        System.out.println("Консольный файловый менеджер");
        System.out.println("Текущая директория: " + currentPath);
        System.out.println("Введите 'help' для списка команд");

        while (true) {
            System.out.print("\n> ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) continue;

            String[] parts = input.split(" ");
            String command = parts[0];
            String[] arguments = Arrays.copyOfRange(parts, 1, parts.length);

            try {
                switch (command) {
                    case "ls":
                        listFiles(arguments);
                        break;
                    case "cd":
                        changeDirectory(arguments);
                        break;
                    case "mkdir":
                        createDirectory(arguments);
                        break;
                    case "rm":
                        removeFileOrDirectory(arguments);
                        break;
                    case "mv":
                        moveFileOrDirectory(arguments);
                        break;
                    case "cp":
                        copyFile(arguments);
                        break;
                    case "finfo":
                        fileInfo(arguments);
                        break;
                    case "find":
                        findFile(arguments);
                        break;
                    case "help":
                        printHelp();
                        break;
                    case "exit":
                        System.out.println("Выход из файлового менеджера");
                        return;
                    default:
                        System.out.println("Неизвестная команда. Введите 'help' для списка команд");
                }
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }

    private static void listFiles(String[] args) throws IOException {
        boolean detailed = false;
        if (args.length > 0 && args[0].equals("-i")) {
            detailed = true;
        }

        try (Stream<Path> paths = Files.list(currentPath)) {
            List<Path> files = paths.sorted().collect(Collectors.toList());

            if (files.isEmpty()) {
                System.out.println("Директория пуста");
                return;
            }

            if (detailed) {
                System.out.printf("%-50s %-10s %-20s%n", "Имя", "Размер", "Дата изменения");
                System.out.println("------------------------------------------------------------");
                for (Path file : files) {
                    BasicFileAttributes attrs = Files.readAttributes(file, BasicFileAttributes.class);
                    String size = attrs.isDirectory() ? "<DIR>" : String.valueOf(attrs.size());
                    String modified = dateFormat.format(new Date(attrs.lastModifiedTime().toMillis()));
                    System.out.printf("%-50s %-10s %-20s%n", file.getFileName(), size, modified);
                }
            } else {
                files.forEach(file -> System.out.println(file.getFileName()));
            }
        }
    }

    private static void changeDirectory(String[] args) {
        if (args.length == 0) {
            System.out.println("Текущая директория: " + currentPath);
            return;
        }

        String path = args[0];
        Path newPath;

        if (path.equals("..")) {
            newPath = currentPath.getParent();
            if (newPath == null) {
                System.out.println("Вы в корневой директории");
                return;
            }
        } else {
            newPath = currentPath.resolve(path);
        }

        if (Files.exists(newPath) && Files.isDirectory(newPath)) {
            currentPath = newPath.normalize().toAbsolutePath();
            System.out.println("Текущая директория: " + currentPath);
        } else {
            System.out.println("Директория не существует: " + newPath);
        }
    }

    private static void createDirectory(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Укажите имя директории");
            return;
        }

        Path dirPath = currentPath.resolve(args[0]);
        Files.createDirectory(dirPath);
        System.out.println("Директория создана: " + dirPath);
    }

    private static void removeFileOrDirectory(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Укажите имя файла или директории");
            return;
        }

        Path target = currentPath.resolve(args[0]);
        if (!Files.exists(target)) {
            System.out.println("Файл или директория не существует: " + target);
            return;
        }

        if (Files.isDirectory(target)) {
            deleteDirectoryRecursively(target);
            System.out.println("Директория удалена: " + target);
        } else {
            Files.delete(target);
            System.out.println("Файл удален: " + target);
        }
    }

    private static void deleteDirectoryRecursively(Path path) throws IOException {
        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private static void moveFileOrDirectory(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Укажите источник и назначение");
            return;
        }

        boolean force = args.length > 2 && args[args.length - 1].equals("-f");
        Path source = currentPath.resolve(args[0]);
        Path destination = currentPath.resolve(args[1]);

        if (!Files.exists(source)) {
            System.out.println("Источник не существует: " + source);
            return;
        }

        if (Files.exists(destination) && !force) {
            System.out.println("Файл или директория уже существует в точке назначения. Используйте -f для принудительной перезаписи");
            return;
        }

        Files.move(source, destination, StandardCopyOption.REPLACE_EXISTING);
        System.out.println((Files.isDirectory(source) ? "Директория" : "Файл") + " перемещена: " + destination);
    }

    private static void copyFile(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Укажите источник и назначение");
            return;
        }

        boolean force = args.length > 2 && args[args.length - 1].equals("-f");
        Path source = currentPath.resolve(args[0]);
        Path destination = currentPath.resolve(args[1]);

        if (!Files.exists(source)) {
            System.out.println("Источник не существует: " + source);
            return;
        }

        if (Files.isDirectory(source)) {
            System.out.println("Копирование директорий не поддерживается");
            return;
        }

        if (Files.exists(destination) && !force) {
            System.out.println("Файл уже существует в точке назначения. Используйте -f для принудительной перезаписи");
            return;
        }

        Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("Файл скопирован: " + destination);
    }

    private static void fileInfo(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Укажите имя файла");
            return;
        }

        Path filePath = currentPath.resolve(args[0]);
        if (!Files.exists(filePath)) {
            System.out.println("Файл не существует: " + filePath);
            return;
        }

        BasicFileAttributes attrs = Files.readAttributes(filePath, BasicFileAttributes.class);
        System.out.println("Информация о файле: " + filePath.getFileName());
        System.out.println("----------------------------------");
        System.out.println("Путь: " + filePath.toAbsolutePath());
        System.out.println("Тип: " + (attrs.isDirectory() ? "Директория" : "Файл"));
        System.out.println("Размер: " + (attrs.isDirectory() ? "<DIR>" : attrs.size() + " байт"));
        System.out.println("Дата создания: " + dateFormat.format(new Date(attrs.creationTime().toMillis())));
        System.out.println("Дата последнего изменения: " + dateFormat.format(new Date(attrs.lastModifiedTime().toMillis())));
        System.out.println("Дата последнего доступа: " + dateFormat.format(new Date(attrs.lastAccessTime().toMillis())));
    }

    private static void findFile(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Укажите имя файла для поиска");
            return;
        }

        String fileName = args[0];
        System.out.println("Поиск файла '" + fileName + "' в " + currentPath);

        Files.walkFileTree(currentPath, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                if (file.getFileName().toString().equals(fileName)) {
                    System.out.println("Найден: " + file.toAbsolutePath());
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                if (dir.getFileName().toString().equals(fileName)) {
                    System.out.println("Найден: " + dir.toAbsolutePath() + " (директория)");
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private static void printHelp() {
        System.out.println("Доступные команды:");
        System.out.println("ls [-i]                - список файлов в текущей директории (-i для подробной информации)");
        System.out.println("cd [path]              - переход в указанную директорию (cd .. для перехода на уровень выше)");
        System.out.println("mkdir [name]          - создать директорию с указанным именем");
        System.out.println("rm [name]             - удалить файл или директорию");
        System.out.println("mv [source] [dest]    - переместить или переименовать файл/директорию");
        System.out.println("cp [source] [dest]    - скопировать файл");
        System.out.println("finfo [name]          - показать подробную информацию о файле");
        System.out.println("find [name]           - найти файл в текущей директории и поддиректориях");
        System.out.println("help                  - показать эту справку");
        System.out.println("exit                  - выйти из файлового менеджера");
        System.out.println("\nПримечание: для mv и cp можно использовать -f для принудительной перезаписи");
    }
}