
public class Complex {
    private final double real;
    private final double imaginary;

    public Complex(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public double getReal() {
        return real;
    }

    public double getImaginary() {
        return imaginary;
    }

    public Complex add(Complex other) {
        return new Complex(
                real + other.real,
                imaginary + other.imaginary
        );
    }

    public Complex subtract(Complex other) {
        return new Complex(
                real - other.real,
                imaginary - other.imaginary
        );
    }

    public Complex multiply(Complex other) {
        double newReal =
                real * other.real - imaginary * other.imaginary;

        double newImaginary =
                real * other.imaginary + imaginary * other.real;

        return new Complex(newReal, newImaginary);
    }

    public Complex divide(Complex other) {
        double denominator =
                other.real * other.real
                        + other.imaginary * other.imaginary;

        if (denominator == 0) {
            throw new ArithmeticException(
                    "Нельзя делить на ноль"
            );
        }

        double newReal =
                (real * other.real + imaginary * other.imaginary)
                        / denominator;

        double newImaginary =
                (imaginary * other.real - real * other.imaginary)
                        / denominator;

        return new Complex(newReal, newImaginary);
    }

    @Override
    public String toString() {
        if (imaginary == 0) {
            return String.valueOf(real);
        }

        if (real == 0) {
            return imaginary + "i";
        }

        String sign = imaginary > 0 ? " + " : " - ";

        return real + sign + Math.abs(imaginary) + "i";
    }
}
