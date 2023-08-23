import java.util.Scanner;

// The Chatbot Aplha!
public class Alpha {
    public static void main(String[] args) throws AlphaException {

        Scanner sc = new Scanner(System.in);

        //End trigger to end the program
        String END = "bye";

        // List trigger word to display a log of stored text
        String LIST = "list";

        String CHECK = "mark";

        String UNCHECK = "unmark";

        String TODO = "todo";

        String DEADLINE = "deadline";

        String EVENT = "event";

        // Intro message
        String intro = "____________________________________________________________\n" +
                " Hello! I'm Alpha\n" +
                " What can I do for you?\n";

        //Outro message
        String end = "____________________________________________________________\n" +
                " Bye. Hope to see you again soon!\n" +
                "____________________________________________________________";

        // Create a List class that performs all list operations
        TaskList taskList = new TaskList();
        System.out.println(intro);
        String input = sc.nextLine();
        while (!input.equals(END)) {
            String[] splitInput = input.split(" "); // Splits string to check for "mark" or "unmark"
            try {
                if (input.equals(LIST)) {
                    taskList.display();
                } else if (splitInput[0].equals(CHECK)) {
                    if (splitInput.length == 1) {
                        throw new MissingIndexException("Missing Index!");
                    } else if (Integer.parseInt(splitInput[1]) > taskList.size()) {
                        throw new InvalidIndexException("Invalid Index!");
                    } else {
                        taskList.mark(Integer.parseInt(splitInput[1]));
                    }

            } else if (splitInput[0].equals(UNCHECK)) {
                    if (splitInput.length == 1) {
                        throw new MissingIndexException("Missing Index!");
                    } else if (Integer.parseInt(splitInput[1]) > taskList.size()) {
                        throw new InvalidIndexException("Invalid Index!");
                    } else {
                        taskList.unmark(Integer.parseInt(splitInput[1]));
                    }

                } else if (splitInput[0].equals(DEADLINE)) {
                if (splitInput.length == 1) {
                    throw new MissingInfoException("Missing Information!", TaskException.TaskType.DEADLINE);
                } else if(input.split("/").length != 2) {
                    throw new InvalidFormatException("Invalid Format!", TaskException.TaskType.DEADLINE);
                } else {
                    String[] splitDeadline = input.split("/");
                    Deadline deadline = new Deadline(splitDeadline[0].split(" ")[1], splitDeadline[1]);
                    taskList.add(deadline);
                }
            } else if (splitInput[0].equals(EVENT)) {
                    if (splitInput.length < 3) {
                        throw new MissingInfoException("Missing Information!", TaskException.TaskType.EVENT);
                    } else if (input.split("/").length != 3 ||
                            input.split("/")[0].split(" ").length == 1){
                        throw new InvalidFormatException("Invalid Format!", TaskException.TaskType.EVENT);
                    } else {
                        String[] splitEvent = input.split("/");
                        Event event = new Event(splitEvent[0].split(" ")[1], splitEvent[1], splitEvent[2]);
                        taskList.add(event);
                    }

            } else if (splitInput[0].equals(TODO)) {
                    if (splitInput.length > 2) {
                        throw new InvalidFormatException("Invalid Format!", TaskException.TaskType.TODO);
                    } else if (splitInput.length == 1) {
                        throw new MissingInfoException("Missing Information!", TaskException.TaskType.TODO);
                    } else {
                        ToDo todo = new ToDo(splitInput[1]);
                        taskList.add(todo);
                    }
                } else {
                    throw new InvalidInputException("Invalid Input!");
            }

            } catch(MissingIndexException e1){
                System.out.println(e1.getMessage() + " Please enter the index of the number you would like to mark.");
            } catch(InvalidIndexException e2){
                System.out.println(e2.getMessage() + " Please enter a valid index. To check all valid indices, " +
                        "type \"list\" and press ENTER");
            } catch(InvalidInputException e3) {
                System.out.println(e3.getMessage() + " Please input something meaningful.");
            } catch(InvalidFormatException | MissingInfoException e5) {
                if (e5.getTask() == TaskException.TaskType.TODO) {
                    System.out.println(e5.getMessage() + " Please enter a todo in the " +
                            "format \"todo YOUR_DESCRIPTION\"");
                } else if (e5.getTask() == TaskException.TaskType.DEADLINE) {
                    System.out.println(e5.getMessage() + " Please enter a deadline in the format " +
                            "\"deadline YOUR_DESCRIPTION /YOUR_TIME\" ");
                } else if (e5.getTask() == TaskException.TaskType.EVENT) {
                    System.out.println(e5.getMessage() + " Please enter an event in the format " +
                            "\"event YOUR_DESCRIPTION " +
                            "/START_TIME /END_TIME\" ");
                }
            } finally {
                input = sc.nextLine();
            }
        }
        System.out.println(end);
    }
}
