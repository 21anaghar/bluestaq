import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Elevator {
    private int currentFloor;
    private int topFloor;
    private String upOrDown;
    private List<Integer> buttonPresses;

    // Constructor
    public Elevator(int topFloor) {
        this.currentFloor = 1;  // Assumes elevator is at ground floor to start
        this.topFloor = topFloor;
        this.upOrDown = "stopped";
        this.buttonPresses = new ArrayList<>();
    }

    // Add a new buttonPress
    public void addButtonPress(int destFloor) {
        if (destFloor < 1 || destFloor > topFloor) {
            System.out.println("Requested floor " + destFloor + " is not supported.");
            return;
        }
        
        if (buttonPresses.contains(destFloor)) {
            System.out.println("Floor " + destFloor + " is already in requested.");
        } else {
            buttonPresses.add(destFloor);
            System.out.println("Destination added for floor " + destFloor);
        }
    }

    //Process all buttonPressess 
    public void proceesButtonPresses() {
        while (!buttonPresses.isEmpty()) {
            if (upOrDown.equals("stopped")) {
                goUpOrDown();
            }
            if (upOrDown.equals("up")) {
                processUpButtonPresses();
            } else if (upOrDown.equals("down")) {
                processDownButtonPresses();
            }
        }
        System.out.println("All buttonPresses are processed. Elevator is at floor " + currentFloor);
    }

    // Determine initial direction based on the which destination floor is nearest
    private void goUpOrDown() {
        int closestUp = Integer.MAX_VALUE;
        int closestDown = Integer.MIN_VALUE;

        for (int floor : buttonPresses) {
            if (floor > currentFloor && floor < closestUp) {
                closestUp = floor;
            } else if (floor < currentFloor && floor > closestDown) {
                closestDown = floor;
            }
        }
        if (closestUp == Integer.MAX_VALUE) {
             if (closestDown == Integer.MIN_VALUE) { //randomly choose up. This case should never happen
                 upOrDown = "up";                 
             } else {
                upOrDown = "down";
             } 
        } else {
             if (closestDown == Integer.MIN_VALUE) { 
                 upOrDown = "up";                 
             } else {
                if (Math.abs(currentFloor-closestUp) > Math.abs(currentFloor-closestDown)) {
                    upOrDown = "down";
                } else {
                    upOrDown = "up";
                }
             }
        } 
    }

    // Process buttonPresses in the up direction
    private void processUpButtonPresses() {
        Collections.sort(buttonPresses);

        for (int floor : new ArrayList<>(buttonPresses)) {
            if (floor >= currentFloor) {
                while (currentFloor < floor) {
                    currentFloor++;
                    System.out.println("Elevator moving up to floor " + currentFloor);
                }
                System.out.println("Arrived at floor " + currentFloor);
                buttonPresses.remove((Integer) currentFloor);
            }
        }
        if (buttonPresses.isEmpty()) {
             upOrDown = "stopped";
        } else {
             upOrDown = "down";
        }
    }

    // Process buttonPresses in the downward direction
    private void processDownButtonPresses() {
        buttonPresses.sort(Collections.reverseOrder());

        for (int floor : new ArrayList<>(buttonPresses)) {
            if (floor <= currentFloor) {
                while (currentFloor > floor) {
                    currentFloor--;
                    System.out.println("Elevator moving down to floor " + currentFloor);
                }
                System.out.println("Arrived at floor " + currentFloor);
                buttonPresses.remove((Integer) currentFloor);
            }
        }
        if (buttonPresses.isEmpty()) {
             upOrDown = "stopped";
        } else {
             upOrDown = "up";
        }
    }

    public static void main(String[] args) {
        Elevator elevator = new Elevator(10); 

        // Add multiple buttonPresses
        elevator.addButtonPress(5);
        // Process all queued buttonPresses
        elevator.proceesButtonPresses();

        elevator.addButtonPress(2);
        elevator.addButtonPress(10);
        elevator.addButtonPress(5);

        // Process all queued buttonPresses
        elevator.proceesButtonPresses();
    }
}

