import java.io.*;
import java.util.Scanner;

/**********************************************************************
 * This class unmixes a message with commands saved to a file and
 * prints out the original message before being mixed up
 *
 * @author Dylan Vannatter and Zack Frent
 * @version Winter 2019
 *********************************************************************/
public class UnMix {

    /** message represents the message the user entered */
    private DoubleLinkedList<Character> message;

    /****************************************************************
     * Constructor for the UnMix class that sets message to be a
     * double linked list with characters
     ***************************************************************/
    public UnMix() {
        message = new DoubleLinkedList<Character>();
    }

    /******************************************************************
     * Method that runs the UnMix class and allows the user to UnMix
     * their original message.
     *
     * @param args String[] argument to allow the method to run
     *****************************************************************/
    public static void main(String[] args) {
        UnMix v = new UnMix();
        v.unMixture(args[0], args[1]);
    }

    /******************************************************************
     * Method that scans the command for the input the user enters
     * and checks whether it was a b, r, or a command
     *
     * @param command String[] argument to allow the method to run
     *****************************************************************/
    public String processCommand(String command) {
        Scanner scan = new Scanner(command);
        char charInput;

        try {
            command = scan.next();
            switch (command.charAt(0)) {
                case 'b':
                    insertbefore(scan.next(), scan.nextInt());
                    break;
                case'r':
                    remove(scan.nextInt(), scan.nextInt());
                    break;
                case'a':
                    replace(scan.next().charAt(0),scan.next()
                            .charAt(0));
                    break;
            }
        } catch (Exception e) {
            System.out.println("Error in command!  Problem!!!! " +
                    "in undo commands");
            System.exit(0);
        }
        finally {
            scan.close();
        }

        return message.toString();
    }

    /******************************************************************
     * Method that UnMix's a user message and prints out the original
     * user message
     *
     * @param filename String represents file with undo commands
     * @param userMessage String represents the message from user
     *****************************************************************/
    private void unMixture(String filename, String userMessage) {
        String original = UnMixUsingFile (filename, userMessage);
        System.out.println ("The Original message was: " + original);
    }

    /******************************************************************
     * Method that scans the users files that has the undo commands
     * written to the file
     *
     * @param filename String represents file with undo commands
     * @param userMessage String represents the message from user
     *****************************************************************/
    public String UnMixUsingFile(String filename, String userMessage) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        convertMessage(userMessage);
        while (scanner.hasNext()) {
            String command = scanner.nextLine();
            userMessage = processCommand(command);
        }
        return userMessage;
    }

    /******************************************************************
     * Method that inserts a string at a specific index
     *
     * @param token the string you want to add
     * @param index the index you want to add at
     *****************************************************************/
    private void insertbefore(String token, int index) {
        for(int i = token.length() - 1; i >= 0; i--){
            char insert = token.charAt(i);
            if(insert == '~') {
                insert = ' ';
            }

            message.add(index,insert);
        }

    }

    /******************************************************************
     * Method that removes all the elements of a Double Linked List
     * in between two indexes
     *
     * @param start the first index you want to remove
     * @param stop the last index you want to remove
     *****************************************************************/
    private void remove(int start, int stop) {
        for(int i = stop; i >= start; i--){
            message.remove(i);
        }
    }

    /******************************************************************
     * Method that replaces all of one character with another
     *
     * @param c1 the character you want to replace
     * @param c2 the character you want to replace c1 with
     *****************************************************************/
    private void replace(Character c1, Character c2){
        if(c1 == '~')
            c1 = ' ';

        if (c2 == '~')
            c2 = ' ';

        message.replaceAll(c1,c2);
    }

    /******************************************************************
     * Method converts a user message into a Double Linked List
     *
     * @param userMessage message you want to convert
     *****************************************************************/
    private void convertMessage(String userMessage){
        for (int i = 0; i <= userMessage.length() -1; i++){
            Character c = userMessage.charAt(i);
            message.add(c);
        }
    }
}


