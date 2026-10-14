
public class ComplexMatrix {
    private final int rows;
    private final int cols;
    private final Complex[][] data;

    public ComplexMatrix(int rows, int cols) {
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException(
                    "Размеры матрицы должны быть положительными"
            );
        }

        this.rows = rows;
        this.cols = cols;
        this.data = new Complex[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] = new Complex(0, 0);
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public Complex get(int row, int col) {
        return data[row][col];
    }

    public void set(int row, int col, Complex value) {
        if (value == null) {
            throw new IllegalArgumentException(
                    "Элемент матрицы не может быть null"
            );
        }

        data[row][col] = value;
    }

    public ComplexMatrix add(ComplexMatrix other) {
        if (rows != other.rows || cols != other.cols) {
            throw new IllegalArgumentException(
                    "Для сложения размеры матриц должны совпадать"
            );
        }

        ComplexMatrix result = new ComplexMatrix(rows, cols);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.set(i, j, data[i][j].add(other.data[i][j]));
            }
        }

        return result;
    }

    public ComplexMatrix multiply(ComplexMatrix other) {
        if (cols != other.rows) {
            throw new IllegalArgumentException(
                    "Число столбцов первой матрицы должно совпадать "
                            + "с числом строк второй"
            );
        }

        ComplexMatrix result =
                new ComplexMatrix(rows, other.cols);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < other.cols; j++) {
                Complex sum = new Complex(0, 0);

                for (int k = 0; k < cols; k++) {
                    Complex product =
                            data[i][k].multiply(other.data[k][j]);

                    sum = sum.add(product);
                }

                result.set(i, j, sum);
            }
        }

        return result;
    }

    public ComplexMatrix transpose() {
        ComplexMatrix result = new ComplexMatrix(cols, rows);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.set(j, i, data[i][j]);
            }
        }

        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sb.append(data[i][j]);

                if (j < cols - 1) {
                    sb.append("\t");
                }
            }

            sb.append("\n");
        }

        return sb.toString();
    }

    private ComplexMatrix minorMatrix(int excludedRow, int excludedCol) {
        ComplexMatrix result =
                new ComplexMatrix(rows - 1, cols - 1);

        int newRow = 0;

        for (int i = 0; i < rows; i++) {
            if (i == excludedRow) {
                continue;
            }

            int newCol = 0;

            for (int j = 0; j < cols; j++) {
                if (j == excludedCol) {
                    continue;
                }

                result.set(newRow, newCol, data[i][j]);
                newCol++;
            }

            newRow++;
        }

        return result;
    }

    public Complex determinant() {
        if (rows != cols) {
            throw new IllegalArgumentException(
                    "Определитель существует только у квадратной матрицы"
            );
        }

        if (rows == 1) {
            return data[0][0];
        }

        if (rows == 2) {
            return data[0][0].multiply(data[1][1])
                    .subtract(data[0][1].multiply(data[1][0]));
        }

        Complex result = new Complex(0, 0);

        for (int j = 0; j < cols; j++) {
            Complex cofactor = minorMatrix(0, j).determinant();

            if (j % 2 == 1) {
                cofactor = new Complex(0, 0).subtract(cofactor);
            }

            Complex term = data[0][j].multiply(cofactor);
            result = result.add(term);
        }

        return result;
    }

    public ComplexMatrix inverse() {
        if (rows != cols) {
            throw new IllegalArgumentException(
                    "Обратная матрица существует только для квадратной матрицы"
            );
        }

        Complex det = determinant();

        double detAbs = Math.hypot(
                det.getReal(), det.getImaginary()
        );

        if (detAbs < 1e-12) {
            throw new ArithmeticException(
                    "Матрица необратима: определитель равен нулю "
                            + "или слишком близок к нулю"
            );
        }

        ComplexMatrix cofactors = new ComplexMatrix(rows, cols);

        if (rows == 1) {
            cofactors.set(0, 0, new Complex(1, 0));
        } else {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    Complex value =
                            minorMatrix(i, j).determinant();

                    if ((i + j) % 2 == 1) {
                        value = new Complex(0, 0).subtract(value);
                    }

                    cofactors.set(i, j, value);
                }
            }
        }

        ComplexMatrix adjugate = cofactors.transpose();
        ComplexMatrix result = new ComplexMatrix(rows, cols);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.set(i, j, adjugate.get(i, j).divide(det));
            }
        }

        return result;
    }

    public ComplexMatrix divide(ComplexMatrix other) {
        if (other.rows != other.cols) {
            throw new IllegalArgumentException(
                    "Делитель должен быть квадратной матрицей"
            );
        }

        if (cols != other.rows) {
            throw new IllegalArgumentException(
                    "Количество столбцов первой матрицы должно "
                            + "совпадать с количеством строк второй"
            );
        }

        return multiply(other.inverse());
    }

}
