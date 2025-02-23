import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Car {
    private String brand;
    private int horsePower;
    private String color;
    private List<CarFunction> carFunctions;

    public Car(String brand, int horsePower, String color) {
        this.brand = brand;
        this.horsePower = horsePower;
        this.color = color;
        this.carFunctions = new ArrayList<>();
    }

    public Car(String brand, int horsePower, String color, List<CarFunction> carFunctions) {
        this.brand = brand;
        this.horsePower = horsePower;
        this.color = color;
        this.carFunctions = carFunctions;
    }

    public void addCarFunction(CarFunction carFunction) {
        this.carFunctions.add(carFunction);
    }

    public void removeCarFunction(CarFunction carFunction) {
        this.carFunctions.remove(carFunction);
    }

    public List<CarFunction> getCarFunctions() {
        return carFunctions;
    }

    public void setCarFunctions(List<CarFunction> carFunctions) {
        this.carFunctions = carFunctions;
    }

    public String getBrand() {
        return brand;
    }

    public int getHorsePower() {
        return horsePower;
    }

    public String getColor() {
        return color;
    }

    public Optional<Double> getPriceForCarFunction(String carFunctionName) {
        return carFunctions.stream()
                .filter(carFunction -> carFunction.getName().equals(carFunctionName))
                .map(CarFunction::getPrice)
                .findFirst();
    }
}


