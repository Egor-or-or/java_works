
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== Калькулятор матриц комплексных чисел ===");

        while (true) {
            System.out.println("\nВыберите операцию:");
            System.out.println("1. Сложение матриц");
            System.out.println("2. Умножение матриц");
            System.out.println("3. Деление матриц");
            System.out.println("4. Транспонирование матрицы");
            System.out.println("5. Вычисление определителя");
            System.out.println("0. Выход");

            int choice = readInt("Ваш выбор: ");

            if (choice == 0) {
                System.out.println("Программа завершена.");
                break;
            }

            try {
                switch (choice) {
                    case 1: {
                        System.out.println("Первая матрица:");
                        ComplexMatrix a = readMatrix();

                        System.out.println("Вторая матрица:");
                        ComplexMatrix b = readMatrix();

                        System.out.println("Результат сложения:");
                        System.out.println(a.add(b));
                        break;
                    }

                    case 2: {
                        System.out.println("Первая матрица:");
                        ComplexMatrix a = readMatrix();

                        System.out.println("Вторая матрица:");
                        ComplexMatrix b = readMatrix();

                        System.out.println("Результат умножения:");
                        System.out.println(a.multiply(b));
                        break;
                    }

                    case 3: {
                        System.out.println("Первая матрица:");
                        ComplexMatrix a = readMatrix();

                        System.out.println("Вторая матрица:");
                        ComplexMatrix b = readMatrix();

                        System.out.println("Результат деления:");
                        System.out.println(a.divide(b));
                        break;
                    }

                    case 4: {
                        System.out.println("Введите матрицу:");
                        ComplexMatrix a = readMatrix();

                        System.out.println("Транспонированная матрица:");
                        System.out.println(a.transpose());
                        break;
                    }

                    case 5: {
                        System.out.println("Введите матрицу:");
                        ComplexMatrix a = readMatrix();

                        System.out.println("Определитель:");
                        System.out.println(a.determinant());
                        break;
                    }

                    default:
                        System.out.println(
                                "Такого пункта меню нет. Попробуйте ещё раз."
                        );
                }
            } catch (IllegalArgumentException | ArithmeticException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }

        scanner.close();
    }

    private static int readInt(String message) {
        while (true) {
            System.out.print(message);

            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Введите целое число.");
            }
        }
    }

    private static double readDouble(String message) {
        while (true) {
            System.out.print(message);

            try {
                return Double.parseDouble(
                        scanner.nextLine().trim().replace(',', '.')
                );
            } catch (NumberFormatException e) {
                System.out.println(
                        "Введите корректное число, например 2.5."
                );
            }
        }
    }

    private static ComplexMatrix readMatrix() {
        int rows;
        int cols;

        while (true) {
            rows = readInt("Количество строк: ");
            cols = readInt("Количество столбцов: ");

            if (rows > 0 && cols > 0) {
                break;
            }

            System.out.println(
                    "Количество строк и столбцов должно быть положительным."
            );
        }

        ComplexMatrix matrix = new ComplexMatrix(rows, cols);

        System.out.println(
                "Для каждого элемента введите действительную "
                        + "и мнимую части отдельно."
        );

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.println(
                        "Элемент [" + (i + 1) + "][" + (j + 1) + "]:"
                );

                double real = readDouble("  Действительная часть: ");
                double imaginary = readDouble("  Мнимая часть: ");

                matrix.set(i, j, new Complex(real, imaginary));
            }
        }

        return matrix;
    }
}
