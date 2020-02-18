import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.Hashtable;
import java.util.Random;
import java.util.Scanner;
/**********************************************************************
 * This class mixes up a message with commands and saves the undo key
 * to a file.
 *
 * @author Dylan Vannatter and Zack Frent
 * @version Winter 2019
 *********************************************************************/
public class Mix {
    /** message that we are manipulating as a Double Linked List */
    private DoubleLinkedList<Character> message;

    /** undo commands to unscrabble our message */
    private String undoCommands;

    /** holds the clipboards for copying, cutting and pasting */
    private Hashtable<Integer, DoubleLinkedList<Character>> clipBoards;

    /** String that is updated to display the current message */
    private String userMessage;

    /** Scanner used to read in user inputs */
    private Scanner scan;

    /******************************************************************
     * Constructor for the Mix class that sets up the scanner, message
     * and clipboards to allow the mixing to commence.
     *****************************************************************/
    public Mix() {
        scan = new Scanner(System.in);
        message = new DoubleLinkedList<Character>();
        clipBoards = new Hashtable<Integer,
                DoubleLinkedList<Character>>();

        undoCommands = "";
    }

    /******************************************************************
     *Method that runs the Mix class and allows the user to mix up
     * their message.
     *
     * @param args String[] argument to allow the method to run
     *****************************************************************/
    public static void main(String[] args) {
        Mix mix = new Mix();
        mix.userMessage = args[0];
        mix.convertMessage(mix.userMessage);
        System.out.println (mix.userMessage);
        mix.mixture();

    }

    /******************************************************************
     * Method that continuously reads in user input to mix
     * up a provided message
     *****************************************************************/
    private void mixture() {
        do {

            DisplayMessage();
            System.out.print("Command: ");

            // save state
            DoubleLinkedList<Character> currMessage =  new
                    DoubleLinkedList<>();
            String currUndoCommands = undoCommands;

            try {
                String command = scan.next("[Qbrpcxhdaz]");

                switch (command) {
                    case "Q":
                        save(scan.next());
                        System.out.println ("Final mixed up message:"
                                + " \"" + message+"\"");
                        System.exit(0);
                    case "d":
                        deleteAll(scan.next().charAt(0));
                        userMessage = message.toString();
                        break;
                    case "b":
                        insertbefore(scan.next(), scan.nextInt());
                        userMessage = message.toString();
                        break;
                    case "r":
                        remove(scan.nextInt(), scan.nextInt());
                        userMessage = message.toString();
                        break;
                    case "a":
                        replace(scan.next().charAt(0),scan.next()
                                .charAt(0));
                        userMessage = message.toString();
                        break;
                    case "c":
                        copy(scan.nextInt(), scan.nextInt(),
                                scan.nextInt());
                        userMessage = message.toString();
                        break;
                    case "x":
                        cut(scan.nextInt(), scan.nextInt(),
                                scan.nextInt());
                        userMessage = message.toString();
                        break;
                    case "p":
                        paste(scan.nextInt(), scan.nextInt());
                        userMessage = message.toString();
                        break;
                    case "z":
                        randomize();
                        userMessage = message.toString();
                        break;
                    case "h":
                        helpPage();
                        break;
                }
                scan.nextLine();   // should flush the buffer
                System.out.println("For demonstration purposes " +
                        "only:\n" + undoCommands);
            }
            catch (Exception e ) {
                System.out.println ("Error on input, previous " +
                        "state restored.");
                // should completely flush the buffer
                scan = new Scanner(System.in);

                // restore state;
                undoCommands = currUndoCommands;
                message = currMessage ;
            }

        } while (true);
    }

    /******************************************************************
     * Method that removes all the elements of a Double Linked List
     * in between two indexes
     *
     * @param start the first index you want to remove
     * @param stop the last index you want to remove
     *****************************************************************/
    private void remove(int start, int stop) {
        char insert2[];
        for (int i = stop; i >= start; i--) {
            message.remove(i);
        }
        String insert = userMessage.substring(start, stop + 1);
        if (insert.contains(" ")) {
            insert2 = insert.toCharArray();
            insert = "";
            for (int i = 0; i <= insert2.length - 1; i++) {
                if (insert2[i] == ' ')
                    insert2[i] = '~';

                insert = insert + insert2[i];
            }
        }
        undoCommands = "b" + " " + insert + " " + start + "\n" +
                undoCommands;
    }

    /******************************************************************
     * Method that removes all the elements of a Double Linked List
     * in between two indexes and saves it to a clipboard
     *
     * @param start the first index you want to remove
     * @param stop the last index you want to remove
     * @param clipNum clipboard you want the new list to be saved to.
     *****************************************************************/
    private void cut(int start, int stop, int clipNum) {
        copy(start,stop,clipNum);
        remove(start,stop);
    }

    /******************************************************************
     * Method that copies all the elements of a Double Linked List
     * in between two indexes and saves it to a clipboard
     *
     * @param start the first index you want to copy
     * @param stop the last index you want to copy
     * @param clipNum clipboard you want the new list to be saved to.
     *****************************************************************/
    private void copy(int start, int stop, int clipNum) {
        DoubleLinkedList clipBD = new DoubleLinkedList();
        for(int i = start; i <= stop; i++){
            clipBD.add(message.get(i));
        }
        clipBoards.put(clipNum,clipBD);
    }

    /****************************************************************
     * Method that pastes all the elements of a saved clipboard
     * to a desired index.
     *
     * @param index the first index you want to remove
     * @param clipNum clipboard you want the new list to be saved to.
     ****************************************************************/
    private void paste( int index, int clipNum) {
        DoubleLinkedList<Character> list = clipBoards.get(clipNum);
        insertbefore(list.toString(),index);
    }

    /******************************************************************
     * Method that removes all the occurrences of a character in a
     * Double Linked List
     *
     * @param c1 character that you wish to delete all occurrences
     *****************************************************************/
    private void deleteAll(Character c1){
        if(c1 == '~')
            c1 = ' ';
        for(int i = userMessage.length()-1; i>=0;i--) {
            if (userMessage.charAt(i) == c1) {
                message.remove(i);
                String s = "" + c1;
                undoCommands = "b" + " " + s + " " + i + "\n" +
                        undoCommands;
            }
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
        undoCommands = "a" + " " + c2 + " " + c1 + "\n" +
                undoCommands;
    }

    /******************************************************************
     * Method that inserts a string at a specific index
     *
     * @param token the string you want to add
     * @param index the index you want to add at
     *****************************************************************/
    private void insertbefore(String token, int index) {
        int stop = index + token.length() -1;
        if(message.size() == 0){
            index = -1;
        }
        for(int i = token.length() - 1; i >= 0; i--){
            char insert = token.charAt(i);
            if(insert == '~') {
                insert = ' ';
            }
            message.add(index,insert);

        }
        undoCommands = "r" + " " + index + " " +
                + stop + "\n" + undoCommands;
    }

    /******************************************************************
     * Method that calls a random amount of inserts,
     * removes, replaces and deletes
     *****************************************************************/
    private void randomize(){
        for (int i = 0; i <= 10; i++) {
            Random random = new Random();
            int command = random.nextInt(4);
            int randIndex1 = random.nextInt(message.size() - 1);
            int randIndex2 = random.nextInt(message.size() - 1);
            char randChar1 = (char)(random.nextInt(26) + 'a');
            if (command == 0) {
                insertbefore(userMessage.substring(randIndex1), randIndex2);
                userMessage = message.toString();
            }
            if (command == 1) {
                if (randIndex1 < randIndex2) {
                    remove(randIndex1, randIndex2);
                    userMessage = message.toString();

                }
                else {
                    remove(randIndex2, randIndex1);
                    userMessage = message.toString();
                }
            }
            if (command == 2) {
                char c = userMessage.charAt(randIndex1);
                if (c == ' ') {
                    c = '~';
                }
                deleteAll(c);
                userMessage = message.toString();
            }
            if (command == 3) {
                char c = userMessage.charAt(randIndex1);
                if (c == ' '){
                    c = '~';
                }
                replace(c, randChar1);
                userMessage = message.toString();
            }
        }
    }


    /******************************************************************
     * Method that displays the message in a readable format
     *****************************************************************/
    private void DisplayMessage() {
        System.out.print ("Message:\n");
        for (int i = 0; i < userMessage.length(); i++)
            System.out.format ("%3d", i);
        System.out.format ("\n");
        for (char c : userMessage.toCharArray())
            System.out.format("%3c",c);
        System.out.format ("\n");
    }


    /******************************************************************
     * Method that saves undo commands to a file
     *
     * @param filename the file you want to save the commands to
     *****************************************************************/
    public void save(String filename) {

        PrintWriter out = null;
        try {
            out = new PrintWriter(new BufferedWriter(new FileWriter
                    (filename)));

        } catch (IOException e) {
            e.printStackTrace();
        }

        out.println(undoCommands);
        out.close();
    }

    /******************************************************************
     * Method that prints a help page that describes commands
     *****************************************************************/
    private void helpPage() {
        System.out.println("Commands:");
        System.out.println("\tQ filename   means, quit! " + " " +
                "save to filename" );
        System.out.println("\t  ~ is used for a space character" );
        System.out.println("\tr\tis for removing with start and " +
                "stop index" );
        System.out.println("\td\tis for removing all of a specific " +
                "character" );
        System.out.println("\ta\tis for replacing all of one " +
                "character with another" );
        System.out.println("\tc\tis for copying to a clipboard" );
        System.out.println("\tx\tis for copying to a clipboard and " +
                "removing from the message" );
        System.out.println("\tp\tis for pasting whats on a clipboard" );
        System.out.println("\tz\tis for doing a random number of" +
                " mix up commands " );
        System.out.println("\tb\tis for inserting a character at" +
                " a certain index" );
        System.out.println("\th\tmeans to show this help page");
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
