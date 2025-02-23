
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class ProcessorTest {

    private static final String WINDOW_LOCK = "Window Lock";
    private static final String CRUISE_CONTROL = "Cruise Control";
    private static final String PARKING_SENSORS = "Parking Sensors";
    private static final String VOICE_COMMANDS = "Voice Commands";
    private static final String AC = "AC";

    private final Car bmw = new Car("Bmw", 220, "grey", Arrays.asList(new CarFunction(WINDOW_LOCK, 950), new CarFunction(CRUISE_CONTROL, 850), new CarFunction(VOICE_COMMANDS, 900)));
    private final Car audi = new Car("Audi", 210, "red", Arrays.asList(new CarFunction(PARKING_SENSORS, 700), new CarFunction(VOICE_COMMANDS, 600), new CarFunction(AC, 650)));
    private final Car opel = new Car("Opel", 180, "grey", Arrays.asList(new CarFunction(AC, 400), new CarFunction(WINDOW_LOCK, 420), new CarFunction(PARKING_SENSORS, 450)));
    private final Car vw = new Car("Vw", 200, "red", Arrays.asList(new CarFunction(VOICE_COMMANDS, 880), new CarFunction(PARKING_SENSORS, 720), new CarFunction(AC, 760)));
    private final Car toyota = new Car("Toyota", 190, "grey", Arrays.asList(new CarFunction(CRUISE_CONTROL, 800), new CarFunction(AC, 650), new CarFunction(WINDOW_LOCK, 700)));


    private Processor carProcessor;

    @BeforeEach
    void setUp() {
        carProcessor = new Processor(createListOfCars());
    }

    private List<Car> createListOfCars() {
        return Arrays.asList(bmw, audi, opel, vw, toyota);
    }

    @Test
    void testProcessMethod() {

        // Backup the original System.out
        PrintStream originalOut = System.out;

        // Create a stream to hold the output
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        // Set the new System.out
        System.setOut(ps);

        // Call processStudents
        carProcessor.processCars();

        // Restore the original System.out
        System.setOut(originalOut);

        // Convert the captured output to a string
        String output = baos.toString();

        // Split the output into lines
        String[] lines = output.split(System.lineSeparator());

        // Check the output
        assertEquals("Car: Bmw, HorsePower: 220, Color: grey", lines[0]);
        assertEquals("Average Price: 900.0", lines[1]);
        assertEquals("Price Category: Expensive", lines[2]);

        assertEquals("Car: Audi, HorsePower: 210, Color: red", lines[3]);
        assertEquals("Average Price: 650.0", lines[4]);
        assertEquals("Price Category: Cheap", lines[5]);

        assertEquals("Car: Opel, HorsePower: 180, Color: grey", lines[6]);
        assertEquals("Average Price: 423.3333333333333", lines[7]);
        assertEquals("Price Category: Promo", lines[8]);

        assertEquals("Car: Vw, HorsePower: 200, Color: red", lines[9]);
        assertEquals("Average Price: 786.6666666666666", lines[10]);
        assertEquals("Price Category: Affordable", lines[11]);

        assertEquals("Car: Toyota, HorsePower: 190, Color: grey", lines[12]);
        assertEquals("Average Price: 716.6666666666666", lines[13]);
        assertEquals("Price Category: Cheap", lines[14]);
    }

    @Test
    void methodOne() {
        List<String> expected = Arrays.asList("bmw", "opel", "toyota");
        List<String> actual = carProcessor.methodOne();
        assertEquals(expected, actual);
    }

    @Test
    void methodOneEmptyList() {
        carProcessor = new Processor(new ArrayList<>());
        assertTrue(carProcessor.methodOne().isEmpty());
    }

    @Test
    void methodTwo() {
        List<String> expected = Arrays.asList("BMW");
        List<String> actual = carProcessor.methodTwo();
        assertEquals(expected, actual);
    }

    @Test
    void methodTwoNoHighGrades() {
        carProcessor.cars.get(0).getCarFunctions().get(0).setPrice(890);
        assertTrue(carProcessor.methodTwo().isEmpty());
    }

    @Test
    void methodThree() {
        double expected = (210 + 200) / 2.0;
        double actual = carProcessor.methodThree();
        assertEquals(expected, actual, 0.001);
    }

    @Test
    void methodThreeNoHighGrades() {
        carProcessor.cars.forEach(car -> car.getCarFunctions().forEach(subject -> subject.setPrice(590)));
        assertThrows(CarException.class, () -> carProcessor.methodThree());
    }

    @Test
    void methodFour() {
        // Setup expected result
        Map<String, List<Car>> expected = new HashMap<>();
        expected.put(WINDOW_LOCK, Arrays.asList(toyota, bmw));
        expected.put(CRUISE_CONTROL, Arrays.asList(toyota, bmw));
        expected.put(PARKING_SENSORS, Arrays.asList(audi, vw));
        expected.put(VOICE_COMMANDS, Arrays.asList(audi, vw, bmw));
        expected.put(AC, Arrays.asList(audi, toyota, vw));

        Map<String, List<Car>> actual = carProcessor.methodFour();
        assertEquals(expected, actual);
    }

    @Test
    void methodFourNoHighGrades() {
        carProcessor.cars.forEach(car -> car.getCarFunctions().forEach(subject -> subject.setPrice(490)));
        assertTrue(carProcessor.methodFour().values().stream().allMatch(List::isEmpty));
    }

    @Test
    void methodFive() {
        List<Car> expected = Arrays.asList(opel);

        // Backup the original System.out
        PrintStream originalOut = System.out;

        // Create a stream to hold the output
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        // Set the new System.out
        System.setOut(ps);

        List<Car> actual = carProcessor.methodFive();

        // Restore the original System.out
        System.setOut(originalOut);

        assertEquals(expected, actual);

        // Convert the captured output to a string
        String output = baos.toString();
        assertEquals(opel.getBrand(), output.trim());
    }

    @Test
    void methodFiveNoLowGrades() {
        carProcessor.cars.forEach(car -> car.getCarFunctions().forEach(subject -> subject.setPrice(510)));
        assertTrue(carProcessor.methodFive().isEmpty());
    }

    @Test
    void methodSix() {
        Map<Car, Double> expected = new HashMap<>();
        expected.put(bmw, 900.0);
        expected.put(audi, 650.0);
        expected.put(opel, 423.3333333333333);
        expected.put(vw, 786.6666666666666);
        expected.put(toyota, 716.6666666666666);

        Map<Car, Double> actual = carProcessor.methodSix();
        assertEquals(expected, actual);
    }

    @Test
    void methodSixEmptyMap() {
        carProcessor = new Processor(new ArrayList<>());
        assertTrue(carProcessor.methodSix().isEmpty());
    }
}
