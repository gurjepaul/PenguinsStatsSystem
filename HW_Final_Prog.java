import java.io.FileInputStream;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

/**
 * Write a description of class HW_Final_Prog here.
 * This is a hockey statistics program based on the Pittsburgh Penguins. 
 * Given the choice of 5 Pittsburgh Penguins teams, the user will be able to
 * see the roster of that team along with choices to see advanced statistics 
 * for a specific player and/or which players led that team in a specific 
 * statistics category.
 *
 * @author (Jay Brar)
 * @version (11/18/2018)
 */
////////////////////////////////////////////////////////////////////////////////
public class HW_Final_Prog    
{
    public static void main(String[] args){
        String input, season;

        // Boolean variables that will be used to enter the while-loop as well
        // as check user input
        boolean validChoice = false;

        Scanner keyboard = new Scanner(System.in);

        System.out.println("PITTSBURGH PENGUINS");
        System.out.println("Statistics Program");
        System.out.println();

        while(validChoice == false){
            // do-while loop that will check which team the user will choose
            do{
                // Calls teamMenu which outputs the available teams
                teamMenu();
                input = keyboard.next().toUpperCase();

                // Returns a string based on their selection (Returns null
                // if their selection is invalid)
                season = seasonSelection(input.charAt(0));

                System.out.println();  

                // If season = null (invalid choice), ask them to choose 
                // another letter and restart the loop
                if(season == null){
                    System.out.println("Please enter a valid letter"); 
                    System.out.println();
                }else{
                    // else change the boolean variable to true to break 
                    // the loop
                    validChoice = true;
                }
            }while(validChoice == false);

            Scanner file = null;    

            // try/catch statement that will attempt to open the file 
            try {            
                file = new Scanner(new FileInputStream(season + ".txt"));           
            } 
            catch (IOException e) {
                System.out.println("File not found.");
                System.exit(0);
            } 

            // Calls positionNameInfo() which will open the selected file and 
            // return each players position, first name, and last name in a 2D
            // string array
            String[][] postionNameArray = positionNameInfo(file);

            // Calls playerStatisticsInfo() which will open the selected file 
            // and return each players regular season statistics in a 2D string
            // array
            int[][] statisticsArray = playerStatisticsInfo(file);

            // Closes the file since all the info we need is now in the arrays
            // above
            file.close();

            // Calls roster() which takes in the arrays above and outputs a 
            // formatted roster to the screen
            roster(postionNameArray, statisticsArray);
            System.out.println();

            // do-while loop that will let the user slect what they want to do
            // next with the currently selected team
            do{
                // Calls teamMenu which outputs the available teams
                statisticsMenu(season);
                input = keyboard.next().toUpperCase();

                // Calls statisticsInputChecker() which will return true if  
                // their input is valid
                validChoice = statisticsInputChecker(input.charAt(0));

                System.out.println();  

                // If input isn't valid, output the following lines and restart 
                // the loop (Program will automatically break if input is valid)
                if(validChoice == false){
                    System.out.println("Please enter a valid letter"); 
                    System.out.println();
                }
            }while(validChoice == false);

            // Since we know th input was valid, we just have to call the right 
            // methods based on what the user chose
            if(input.charAt(0) == 'A'){
                // Calls playerStatistics that outputs a specific players stats
                playerStatistics(postionNameArray, statisticsArray, keyboard);
            }else if(input.charAt(0) == 'B'){
                // Calls categoryLeader that outputs category leaders based off
                // of user input
                categoryLeaders(postionNameArray, statisticsArray, keyboard);
            }
            System.out.println();

            // Reset the boolean value to false to see if the user wants to  
            // restart the program
            validChoice = false;

            System.out.print("Restart program? (y/n): ");
            input = keyboard.next().toLowerCase();

            // If input is anyhting other than y, set validChoice to true so  
            // that the while-loop breaks and the program ends
            if(input.charAt(0) != 'y'){
                validChoice = true;
            }

            System.out.println();
        }

        System.exit(0);
    }

    /**
     *  teamMenu method - this method outputs a menu of teams available for the
     *  user to choose from.
     *  Prints to the screen:
     * 
     *  Please select a letter:
     *   A. 1990-91 Pittsburgh Penguins
     *   B. 1991-92 Pittsburgh Penguins
     *   C. 2006-07 Pittsburgh Penguins
     *   D. 2008-09 Pittsburgh Penguins
     *   E. 2017-18 Pittsburgh Penguins
     *   ==> 
     */
    public static void teamMenu(){
        System.out.println("Please select a letter:");
        System.out.println(" A. 1990-91 Pittsburgh Penguins");
        System.out.println(" B. 1991-92 Pittsburgh Penguins");
        System.out.println(" C. 2006-07 Pittsburgh Penguins");
        System.out.println(" D. 2008-09 Pittsburgh Penguins");
        System.out.println(" E. 2017-18 Pittsburgh Penguins");
        System.out.print(" ==> ");
    }

    /**
     *  statisticsMenu method - this method takes in String season and outputs
     *  a menu of choices available to the user.
     *  Prints to the screen:
     *  
     *  Please select a letter:
     *   A. <season> Player Statistics
     *   B. <season> Category Leaders
     *   ==>
     */
    public static void statisticsMenu(String season){
        System.out.println("Please select a letter:");
        System.out.printf(" A. %s Player Statistics%n", season);
        System.out.printf(" B. %s Category Leaders%n", season);
        System.out.print(" ==> ");
    }

    /**
     *  categoryLeadersMenu method - this method outputs a menu of categories 
     *  available for the user to choose from.
     *  Prints to the screen:
     * 
     *  Please select a category:
     *   A. Goals
     *   B. Assists
     *   C. Points
     *   D. Plus-Minus (+/-)
     *   E. Penalty Minutes
     *   ==> 
     */
    public static void categoryLeadersMenu(){
        System.out.println("Please select a category:");
        System.out.println(" A. Goals");
        System.out.println(" B. Assists");
        System.out.println(" C. Points");
        System.out.println(" D. Plus-Minus (+/-)");
        System.out.println(" E. Penalty Minutes");
        System.out.print(" ==> ");
    }

    /**
     *  statisticsMenu method - this method takes in char choice and returns a 
     *  string based off of char choice. This is used for user input. It returns
     *  a year assigned to a season if the input is valid and return null if 
     *  it's invalid.
     */
    public static String seasonSelection(char choice){
        // Set to null since we want to return nothing if input is invalid
        String year = null;

        // Sets year equal to a value if it is one of the valid choices below
        if(choice == 'A'){
            year = "1990-91";
        }else if(choice == 'B'){
            year = "1991-92";
        }else if(choice == 'C'){
            year = "2006-07";
        }else if(choice == 'D'){
            year = "2008-09";
        }else if(choice == 'E'){
            year = "2017-18";
        }

        // Returns a value if input was valid or returns null if not
        return year;
    }

    /**
     *  statisticsInputChecker method - this method checks if user input is 
     *  valid or not. It takes in char choice (user input from main) and 
     *  returns true if its valid and false if its invalid (Connected to 
     *  statisticsMenu).
     */
    public static boolean statisticsInputChecker(char choice){
        // Input isn't if choice doesn't equal A or B
        if((choice != 'A') && (choice != 'B')){
            return false;
        }

        // Input is valid if we make it to this point
        return true;
    }

    /**
     *  categoryInputChecker method - this method checks if user input is valid
     *  or not. It takes in char choice (user input) and returns true if its
     *  valid  and false if its invalid (Connected to categoryLeaders). 
     */
    public static boolean categoryInputChecker(char choice){
        // Input isn't if choice doesn't equal A, B, C, D, or E
        if((choice != 'A') && (choice != 'B') && (choice != 'C')
        && (choice != 'D') && (choice != 'E')){
            return false;
        }

        // Input is valid if we make it to this point
        return true;
    }

    /**
     *  positionNameInfo method - this method takes in the selected team final, 
     *  reads the position, first name, and last name for each player from that 
     *  file and returns a 2D string array with that information.
     */
    public static String[][] positionNameInfo(Scanner file){
        String holder;

        // Declaring an array that will hold all the players information       
        String[][] positionNameArray = new String[20][3];

        // for-loop that goes through each player in the file
        for(int i = 0; i < 20; i++){
            // Player i's position
            positionNameArray[i][0] = file.next();
            // Player i's first name
            positionNameArray[i][1] = file.next();
            // Player i's last name
            positionNameArray[i][2] = file.next();

            // Ends the reading of the current line and goes to the next
            holder = file.nextLine();
        }

        // Array holding every players position, first name, and last name
        return positionNameArray;
    }

    /**
     *  playerStatisticsInfo method - this method takes in the selected team 
     *  final, reads all the regular season statistics for each player from 
     *  that file and returns a 2D string array with that information.
     */
    public static int[][] playerStatisticsInfo(Scanner file){
        // Declaring an array that will hold all the players number+stats
        int[][] statisticsArray= new int[20][7];

        // for-loop that goes through each player in the file
        for(int j = 0; j < 20; j++){
            // Player j's number
            statisticsArray[j][0] = file.nextInt();
            // Player j's number of games played
            statisticsArray[j][1] = file.nextInt();
            // Player j's goals or games started if goalie
            statisticsArray[j][2] = file.nextInt();
            // Player j's assists or winsif goalie
            statisticsArray[j][3] = file.nextInt();
            // Player j's points or losses if goalie
            statisticsArray[j][4] = file.nextInt();
            // Player j's +/- rating or save % if goalie
            statisticsArray[j][5] = file.nextInt();
            // Player j's penalty minutes or goals-against-avg if goalie
            statisticsArray[j][6] = file.nextInt();
        }

        // Array holding every players regular season statistics
        return statisticsArray;
    }
    
    /**
     *  roster method - this method takes in two 2D arrays for player 
     *  information and outputs a formatted roster to the screen based off of
     *  the values in the arrays.
     *  Prints to the screen:
     *  
     *  P         NAME          #
     *  =========================
     *  (Player 1 Position, name, number)
     *  (Player 2 Position, name, number)
     *  . . .
     *  (Player 20 Position, name, number)
     */
    public static void roster(String[][] positionNameArray, 
    int[][] statisticsArray){
        // Will be used later
        int numSpaces = 0;

        // Outputs header
        System.out.printf("P         NAME          #%n");
        System.out.println("=========================");

        // for-loop that goes through every player on the team
        for(int i = 0; i < 20; i++){
            // Outputs the player position
            System.out.print(positionNameArray[i][0] + " ");

            // Lines up the names by adding a space after the position if
            // its only one letter
            if(positionNameArray[i][0].length() == 1){
                System.out.print(" ");
            }

            // Outputs position, first name, last name of player i
            for(int j = 1; j < 3; j++){
                System.out.print(positionNameArray[i][j] + " ");

                // Stores how many characters have been outputted to
                // calculate spaces later
                numSpaces += positionNameArray[i][j].length();
            }

            // for-loop that will line up all the players numbers using 
            // numSpaces from earlier
            for(int spaces = 0; spaces < 18 - numSpaces; spaces++){
                // We want numbers to line up 18 spaces to the right so 
                // we just subtract numSpaces from 18
                System.out.print(" ");
            }

            // Outputs player i's number
            System.out.print(statisticsArray[i][0] + " ");

            // Resets numSpaces to be used for the next player
            numSpaces = 0;
            System.out.println();
        }
    }

    /**
     *  playerStatistics method - this method asks the user for a player number and
     *  then outputs the regular season statistics of that player. Takes in two
     *  2D arrays and Scanner to find the stats of the selected player and to read
     *  user input.
     *  Prints to the screen:
     *  
     *  Postion: <Position>
     *  Name: <Player name>
     *  Number: <Player number>
     *  Games: <Games played>
     *  ... (Outputs more stats that vary depending on if player is goalie or not)
     */
    public static void playerStatistics(String[][] positionNameArray,
    int[][] statisticsArray, Scanner keyboard){
        boolean inputValid = false;
        int playerChoice = 0;

        // do-while loop that will ask the user for the player's number
        do{
            System.out.println("Please enter player number");
            System.out.print(" ==> ");
            int playerNumber = keyboard.nextInt();

            // for-loop that will go through the stats array to check if the
            // player number they inputted is on the roster
            for(int i = 0; i < 20; i++){
                // Will enter this if-statement if number is valid
                if(playerNumber == statisticsArray[i][0]){
                    playerChoice = i;
                    inputValid = true;
                }
            }

            System.out.println();

            // Will repeat loop if player isn't on the roster
            if(inputValid == false){
                System.out.println("Player #" + playerNumber + " not on roster");
            }
        }while(inputValid == false);
        
        // Following lines will output information that same for all players
        
        // Outputs player position
        System.out.println("Position: " 
            + positionNameArray[playerChoice][0]);
        // Outputs player name
        System.out.print("Name: " + positionNameArray[playerChoice][1]);
        System.out.println(" " + positionNameArray[playerChoice][2]);

        // Outputs player number
        System.out.println("Number: " + statisticsArray[playerChoice][0]);
        // Outputs players number of games played
        System.out.println("Games: " + statisticsArray[playerChoice][1]);
        
        // if-else statement that will print different statistics depending on
        // whether the player is a skater or a goalie

        if(playerChoice < 18){ // if the player is a skater
            // Outputs players goals
            System.out.println("Goals: " + statisticsArray[playerChoice][2]);
            // Outputs playes assists
            System.out.println("Assists: " + statisticsArray[playerChoice][3]);
            // Outputs players points
            System.out.println("Points: " + statisticsArray[playerChoice][4]);
            // Outputs players +/- rating
            System.out.println("+/-: " + statisticsArray[playerChoice][5]);
            // Outputs players penalty minutes
            System.out.println("PIM: " + statisticsArray[playerChoice][6]);
        }else{ // if the player is a goalie      
            // Outputs goalies number of games started
            System.out.println("Games Started: " 
                + statisticsArray[playerChoice][2]);
            // Outputs goalies number of wins
            System.out.println("Wins: " + statisticsArray[playerChoice][3]);
            // Outputs goalies number of losses
            System.out.println("Losses: " + statisticsArray[playerChoice][4]);
            // Outputs goalies save percentage
            System.out.println("Save Percentage: " 
                + statisticsArray[playerChoice][5] + "%");
            // Outputs goals goals against average
            System.out.println("Goals Against Average: " 
                + statisticsArray[playerChoice][6]);
        }
    }

    /**
     *  categoryLeaders method - this method asks the user to pick a statistics
     *  category and will then output the player who leads the team in that
     *  category.Takes in two 2D arrays and Scanner to compare the stats of each
     *  player on the team and to read user input.
     *  Prints to the screen:
     *  
     *  Team leader in <category>
     *  Name: <first name><last name>
     *  <category>: <stat>
     */
    public static void categoryLeaders(String[][] positionNameArray,
    int[][] statisticsArray, Scanner keyboard){
        String input;
        int categoryNumber;
        int player = 0;
        int categoryHigh = 0;
        boolean validChoice = false;
        // do-while loop that will find the users desired category
        do{
            // method call that outputs the category menu
            categoryLeadersMenu();
            input = keyboard.next().toUpperCase();
            
            // calls categoryInputChecker that returns a boolean value 
            // depending on if input is valid or not
            validChoice = categoryInputChecker(input.charAt(0));

            System.out.println();  
            
            // Restarts loop if input is invalid
            if(validChoice == false){
                System.out.println("Please enter a valid letter"); 
                System.out.println();
            }
        }while(validChoice == false);
        
        // if-else statements that will set variables used by the method
        // equal to which category the user chose
        
        // categoryNumber is set to which column in the statistics array
        // it is equal to
        if(input.charAt(0) == 'A'){
            categoryNumber = 2;
            input = "Goals";
        }else if(input.charAt(0) == 'B'){
            categoryNumber = 3;
            input = "Assists";
        }else if(input.charAt(0) == 'C'){
            categoryNumber = 4;
            input = "Points";
        }else if(input.charAt(0) == 'D'){
            categoryNumber = 5;
            input = "+/-";
        }else{
            categoryNumber = 6;
            input = "PIM";
        }
        
        // for-loop that will go through all of the skaters and find which 
        // player leads the picked category while storing that value
        for(int i = 0; i < 18; i++){
            if(statisticsArray[i][categoryNumber] > categoryHigh){
                categoryHigh = statisticsArray[i][categoryNumber];
                player = i;
            }
        }
        
        // Outputs the name and stat of the player that leads the team in 
        // the category chosen by the user
        System.out.println("Team Leader in " + input + ":");
        System.out.print("Name: " + positionNameArray[player][1] + " ");
        System.out.println(positionNameArray[player][2]);
        System.out.println(input + ": " + categoryHigh);
    }
}
