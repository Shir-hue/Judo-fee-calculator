import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continueProgram = true;

        while (continueProgram) {
            // Athletes onboarding
            System.out.println("Welcome to North Sussex Judo. Please fill up the following");

            String athleteName = getAthleteName(scanner);
            int trainingPlan = getTrainingPlan(scanner);
            Athletes athlete = createAthlete(trainingPlan);

            // Weight and competition details
            double currentWeight = getValidWeight(scanner);
            athlete.setCurrentWeight(currentWeight);

            int useCoaching = getCoachingOption(scanner);
            athlete.setCoachingHours(useCoaching);

            int numCompetitions = getCompetitionCount(scanner, trainingPlan);
            athlete.setNumCompetitions(numCompetitions);

            // Calculate costs
            athlete.calculateCosts();

            // Display formatted details
            displayAthleteDetails(athleteName, athlete);

            // Check for another athlete
            continueProgram = getAnotherAthleteOption(scanner);
        }

        System.out.println("Congratulations! You're now a part of our North Sussex Judo program, beat them all!");
    }

    // Function to get valid athlete name (no numbers)
    private static String getAthleteName(Scanner scanner) {
        System.out.print("Athlete name: ");
        String name = scanner.nextLine();
        while (name.matches(".*\\d.*")) {
            System.out.println("Invalid name. Please enter a name without numbers.");
            System.out.print("Athlete name: ");
            name = scanner.nextLine();
        }
        return name;
    }

    // Function to get valid training plan selection
    private static int getTrainingPlan(Scanner scanner) {
        System.out.println("Training Plans:");
        System.out.println("0. Beginner");
        System.out.println("1. Intermediate");
        System.out.println("2. Elite");
        System.out.print("What's your training plan? (Enter 0, 1, or 2): ");
        String input = scanner.next();

        while (!input.matches("[0-2]")) {
            System.out.println("Invalid input. Please enter 0, 1, or 2.");
            System.out.print("What's your training plan? (Enter 0, 1, or 2): ");
            input = scanner.next();
        }

        return Integer.parseInt(input);
    }

    // Function to create athlete object based on the plan
    private static Athletes createAthlete(int trainingPlan) {
        switch (trainingPlan) {
            case 0:
                return new BeginnerAthlete();
            case 1:
                return new IntermediateAthlete();
            case 2:
                return new EliteAthlete();
            default:
                return null;
        }
    }

    // Function to get valid weight within range
    private static double getValidWeight(Scanner scanner) {
        System.out.print("Enter your current weight (in kilograms): ");
        String input = scanner.next();

        while (!input.matches("\\d+\\.?\\d*") || Double.parseDouble(input) < 50 || Double.parseDouble(input) > 300) {
            System.out.println("Invalid weight. Please enter a weight between 50 and 300 kilograms.");
            input = scanner.next();
        }

        return Double.parseDouble(input);
    }

    private static int getCoachingOption(Scanner scanner) {
        System.out.print("Enter the number of private coaching hours per week (0-5): ");
        String input = scanner.next();

        while (!input.matches("[0-5]")) {
            System.out.println("Invalid input. Please enter a number between 0 and 5.");
            input = scanner.next();
        }

        return Integer.parseInt(input);
    }

    private static int getCompetitionCount(Scanner scanner, int trainingPlan) {
        if (trainingPlan >= 1) {
            System.out.print("Enter the number of competitions entered this month: ");
            String input = scanner.next();

            while (!input.matches("[0-9]+")) {
                System.out.println("Invalid input. Please enter a valid number.");
                input = scanner.next();
            }

            return Integer.parseInt(input);
        } else {
            System.out.println("Only Intermediate and Elite athletes can enter competitions.");
            return 0;
        }
    }

    // Function to display athlete details
    private static void displayAthleteDetails(String name, Athletes athlete) {
        System.out.println("\nAthlete Name: " + name);
        System.out.println("Training Plan: " + athlete.getClass().getSimpleName().replace("Athlete", ""));
        System.out.printf("Training Cost: $%.2f%n", athlete.getBaseFee());
        System.out.printf("Private Coaching Cost: $%.2f%n", athlete.getCoachingCost());
        System.out.printf("Total Monthly Cost: $%.2f%n", athlete.getTotalCost());
    }

    private static boolean getAnotherAthleteOption(Scanner scanner) {
        System.out.print("Do you have another athlete you'd like to process? (Yes/No): ");
        String input = scanner.nextLine().trim();

        while (!input.equalsIgnoreCase("yes") && !input.equalsIgnoreCase("no")) {
            input = scanner.nextLine().trim();
            System.out.println("Invalid input. Please enter 'Yes' or 'No'.");
        }

        return input.equalsIgnoreCase("yes");
    }
}

// Base class for all athletes
abstract class Athletes {
    protected double currentWeight;
    protected int numCompetitions;
    protected int coachingHours;
    protected double totalCost;

    public void setCurrentWeight(double currentWeight) {
        this.currentWeight = currentWeight;
    }

    public void setNumCompetitions(int numCompetitions) {
        this.numCompetitions = numCompetitions;
    }

    public void setCoachingHours(int coachingHours) {
        this.coachingHours = coachingHours;
    }

    public abstract void calculateCosts();
    public abstract double getBaseFee();
    public abstract double getCoachingCost();
    public double getTotalCost() {
        return totalCost;
    }
}

// Subclass for Beginner athletes
class BeginnerAthlete extends Athletes {
    private static final double BASE_FEE = 100.00;
    private static final double COACHING_FEE_PER_HOUR = 9.00;

    @Override
    public void calculateCosts() {
        totalCost = BASE_FEE + (coachingHours * COACHING_FEE_PER_HOUR);
    }

    @Override
    public double getBaseFee() {
        return BASE_FEE;
    }

    @Override
    public double getCoachingCost() {
        return coachingHours * COACHING_FEE_PER_HOUR;
    }
}

// Subclass for Intermediate athletes
class IntermediateAthlete extends Athletes {
    private static final double BASE_FEE = 150.00;
    private static final double COACHING_FEE_PER_HOUR = 12.00;

    @Override
    public void calculateCosts() {
        totalCost = BASE_FEE + (coachingHours * COACHING_FEE_PER_HOUR);
    }

    @Override
    public double getBaseFee() {
        return BASE_FEE;
    }

    @Override
    public double getCoachingCost() {
        return coachingHours * COACHING_FEE_PER_HOUR;
    }
}

// Subclass for Elite athletes
class EliteAthlete extends Athletes {
    private static final double BASE_FEE = 200.00;
    private static final double COACHING_FEE_PER_HOUR = 15.00;

    @Override
    public void calculateCosts() {
        totalCost = BASE_FEE + (coachingHours * COACHING_FEE_PER_HOUR);
    }

    @Override
    public double getBaseFee() {
        return BASE_FEE;
    }

    @Override
    public double getCoachingCost() {
        return coachingHours * COACHING_FEE_PER_HOUR;
    }
}