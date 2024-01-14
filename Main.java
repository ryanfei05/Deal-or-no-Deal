import java.util.*;

/*
Ryan Fei
Oct 31st, 2021
The following program is a text based application of the hit American game show, "Deal or No Deal". Following the same rules as the game, the game is only slightly modified in that there are 24 initial cases instead of 26. The user is displayed a menu to begin, with an option to quit in the menu.
Program contain coloured text, a main menu, and utilizes time delays.
*/

class Main {

  //Initializing variables to be used throughout the whole program
  public static Scanner sc = new Scanner(System.in);
  public static int casesOpened;
  public static int[] cases = new int[24];
  public static List<Integer> values = new ArrayList<Integer>();
  public static int userCase;
  public static String caseValue;
  public static List<Integer> scores = new ArrayList<Integer>();
  public static List<String> names = new ArrayList<String>();
  //Initializing coloured string constants
  public static final String reset = "\u001B[0m";
  public static final String black = "\u001B[30m";
  public static final String red = "\u001B[31m";
  public static final String green = "\u001B[32m";
  public static final String yellow = "\u001B[33m";
  public static final String blue = "\u001B[34m";
  public static final String yellowbackground = "\u001B[43m";
  public static final String whitebackground = "\u001B[47m";

  /*
  Method pause()
  Pauses for an amount of time before executing subsequent code, input of seconds
  Pre: s >=0, and s is a double
  Post: Pauses code execution for an input amount of seconds
  */
  public static void pause(double s) {
    try {
      Thread.sleep((long) s * 1000);
    } catch (InterruptedException ex) {
      Thread.currentThread().interrupt();
    }
  }

  /*
  Method clear()
  Clears the console
  Pre: N/A
  Post: Clears the console
  */
  public static void clear() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  /*
  Method introduction()
  Prints an introduction from the banker before the game, asking for the users name
  Pre: N/A
  Post: Prints introduction screen before game
  */
  public static void introduction() {
    String name;

    System.out.println(yellow + "--------------------------------------------------------------------------------" + reset);
    pause(1);
    System.out.println(yellow + "\n[Banker]" + reset + " Welcome Contestant! To the Game of Deal or No Deal! " + reset);
    pause(1);
    System.out.print(yellow + "\n[Banker]" + reset + " Can you tell me your name? " + reset);
    name = sc.nextLine();
    names.add(name);
    pause(1);
    System.out.println(yellow + "\n[Banker]" + reset + " Alright " + name + "! Let's get this game going, shall we?" + reset);
    pause(1);
    System.out.println(yellow + "\n\nAny key continue..." + reset);
    sc.nextLine();
    clear();

  }

  /*
  Method setValues()
  Initializes values for the game, so that the game can be reset after each play
  Pre: N/A
  Post: Randomizes money values and puts said values into cases, plus reset game variables to 0
  */
  public static void setValues() {

    values = Arrays.asList(1, 2, 5, 7, 10, 25, 50, 75, 100, 250, 500, 750, 1000, 2500, 5000, 10000, 25000, 50000, 75000,100000, 250000, 500000, 750000, 1000000);
    List<Integer> valueslist = Arrays.asList(1, 2, 5, 7, 10, 25, 50, 75, 100, 250, 500, 750, 1000, 2500, 5000, 10000, 25000, 50000, 75000, 100000, 250000, 500000, 750000, 1000000);
    Collections.shuffle(valueslist);
    for (int i = 0; i < cases.length; i++) {
      cases[i] = valueslist.get(i);
    }
    casesOpened = 0;
    userCase = 0;
  }

  /*
  Method selectCase()
  Prompts user to remove a case, then printing out the case value and removing it from values ArrayList and cases Array
  Pre: N/A
  Post: Case value is removed from values ArrayList and case is removed from cases Array, decided by User Input
  */
  public static void selectCase() {
    int removeCase;

    System.out.print("\nSelect a Case to Remove! [1-24] ");
    while (true) {
      try {
        removeCase = Integer.parseInt(sc.nextLine());
        if (1 <= removeCase && removeCase <= 24 && removeCase != userCase && cases[removeCase - 1] != 0) {
          break;
        }
        System.out.println("Select an Available Case");
      } catch (Exception e) {
        System.out.println("Invalid Input");
      }
    }
    caseValue = ("\nCase " + removeCase + " had a value of " + red + "$" + cases[removeCase - 1] + reset);
    values.set(values.indexOf(cases[removeCase - 1]), 0);
    cases[removeCase - 1] = 0;
  }

  /*
  Method caseInterface()
  Prints out the text interface for the cases
  Pre: N/A
  Post: Prints out text interface for the cases
  */
  public static void caseInterface() {
    String caseGraphic;

    System.out.println(yellow + "--------------------------------------------------------------------------------" + reset);
    System.out.println("                                 CASES");

    for (int i = 1; i <= 24; i++) {
      caseGraphic = "[" + i + "]\t\t   ";
      if (i < 10) {
        caseGraphic = "[ " + i + "]\t\t   ";
      }
      if (i == userCase) {
        caseGraphic = green + caseGraphic;
      }
      if (cases[i - 1] == 0) {
        caseGraphic = red + caseGraphic;
      }
      System.out.print(caseGraphic + reset);

      if (i % 6 == 0) {
        System.out.print("\n");
      }
    }
  }

  /*
  Method valueInterface()
  Prints out the value interface for the remaining dollar values left in cases
  Pre: N/A
  Post: Prints out value interface for values of cases
  */
  public static void valueInterface() {
    System.out.println("                         Money Available in Cases");
    for (int i = 1; i <= 24; i++) {
      if (values.get(i - 1) != 0) {
        System.out.print("$" + values.get(i - 1) + ", ");

      }
      if (i % 15 == 0) {
        System.out.print("\n");
      }

    }
    System.out.println();
  }

  /*
  Method switchValue()
  Prints out money won if User switches final case
  Pre: N/A
  Post: Prints out money value won if User switches final case, and adds it to scores ArrayList
  */
  public static void switchValue() {
    for (int i = 0; i < cases.length; i++) {
      if (cases[i] != 0 && i != userCase - 1) {
        System.out.print(green + "$" + cases[i] + reset + "!");
        scores.add(cases[i]);
      }
    }
  }

  /*
  Method userInput()
  Given a maximum int, makes sure that next User Input is between 1 and the max int, inclusive, by continiously asking for input until criteria is met
  Pre: Max bound is an integer
  Post: Returns the int from user input, that is between 1 and the max int, inclusive
  */
  public static int userInput(int max) {
    int input;
    while (true) {
      try {
        input = Integer.parseInt(sc.nextLine());
        if (1 <= input && input <= max) {
          break;
        }
        System.out.println("Invalid Input");
      } catch (Exception e) {
        System.out.println("Invalid Input");
      }
    }
    return input;
  }

  /*
  Method bankOffer()
  Calculates money offer to give to user
  Pre: N/A
  Post: Returns integer offer calculated to give to the user
  */
  public static int bankOffer() {
    int offer = 0;

    for (int i = 0; i < cases.length; i++) {
      offer += cases[i];

    }
    offer /= 25 - casesOpened;
    //Mean case value is multiplied by a constant, which is determined by how many rounds have been played already
    offer *= (double) (5 + casesOpened) / 40;

    return (int) offer;

  }

  /*
  Method showUserCase()
  Prints out the value for the first case the user selected
  Pre: N/A
  Post: Prints out dollar value of users initial case
  */
  public static void showUserCase() {
    System.out.println("Your initial selection, Case " + userCase + " had a value of " + red + "$" + cases[userCase - 1] + reset + ".");

  }

  /*
  Method menu()
  Prints out the main menu interface
  Pre: N/A
  Post: Prints out main menu
  */
  public static void menu() {
    System.out.println(yellow + "--------------------------------------------------------------------------------" + reset);
    System.out.println(red + "                                 Welcome to" + reset);
    System.out.println("                               " + black + yellowbackground + "DEAL" + reset + " OR " + yellow
        + "NO DEAL" + reset);
    System.out.println(yellow + "--------------------------------------------------------------------------------" + reset);
    System.out.println();
    System.out.println("                                 [1] Play");
    System.out.println("                                 [2] Instructions");
    System.out.println("                                 [3] Results");
    System.out.println("                                 [4] Quit");

  }

  /*
  Method instructions()
  Prints out game instructions
  Pre: N/A
  Post: Prints out instructions
  */
  public static void instructions() {
    System.out.println(yellow + "--------------------------------------------------------------------------------" + reset);
    System.out.println("Welcome to Deal or No Deal! (ICS4U1 Version)\nHere are the Instructions to play the game! ");
    pause(1);
    System.out.println(blue + "\n1." + reset + " Pick an initial case [1-24] which you think contains the" + green
        + " most money" + reset + " within it\n(Try and win $1,000,000!)");
    pause(1);
    System.out.println(blue + "2." + reset + " Each round consists of opening various amounts of cases which you think\ncontains the " + red
        + "lowest values" + reset
        + ". The money contained in these cases is revealed and \nthen the case and money is removed from the game");
    pause(1);
    System.out.println(blue + "3." + reset + " At the end of each round, the banker will offer you a deal, and then ask\n whether you accept or don't accept the deal.\n\t" + blue + "a)" + reset + " If you " + green + "accept" + reset + " the deal, the game will " + red + "end" + reset + " and you walk away with the\noffer value\n\t" + blue + "b)" + reset + " If you " + red
    + "don't accept" + reset + " the deal, the next round will " + green + "continue " + reset + "and you will \nselect more cases to remove, banking (good pun) on your chance to end up with a \ncase or offer greater than the current deal");
    pause(1);
    System.out.println(blue + "4." + reset + " If you have gone through all the rounds and only 2 cases remain, the banker \nwill ask if you wish to keep the case you initially picked, or switch your case\nwith the other remaining case. You will walk away with the money in the case \nyou picked.");

    pause(2);
    System.out.println(yellow + "\nAny key to return to Main Menu" + reset);
    sc.nextLine();
    clear();

  }

  /*
  Method results()
  Prints out results of every game (order, name, and final money won)
  Pre: N/A
  Post: Prints table of each game result, organized in the order that the games were played
  */
  public static void results() {
    int spacing = 16;
    System.out.println(yellow + "--------------------------------------------------------------------------------" + reset);
    System.out.println("[Order] \t\t[Name] \t\t\t[Money Won]\t\t");

    for (int i = 1; i <= names.size(); i++) {
      System.out.print("  " + i);
      System.out.print("\t\t\t\t" + names.get(i - 1));
      //Creates even spacing between every game stat, so values are in line
      while (names.get(i - 1).length() < spacing) {
        System.out.print(" ");
        spacing--;
      }
      spacing = 16;
      System.out.print(green + "$" + scores.get(i - 1) + reset);
      System.out.println();
    }
    System.out.println(yellow + "\n\nAny key to return to Main Menu" + reset);
    sc.nextLine();
    clear();

  }

  /*
  Method dealOrNoDeal()
  Runs main game
  Pre: N/A
  Post: Single playthrough of game is complete, game stats saved to the Results
  */
  public static void dealOrNoDeal() {
    int Deal;

    introduction();
    setValues();
    caseInterface();
    //User Input for initial case
    System.out.print("\nSelect Your Case! [1-24] ");
    userCase = userInput(24);
    casesOpened++;
    clear();

    caseInterface();
    System.out.println();
    valueInterface();
    //Loops until some money value is won by User
    while (true) {
      selectCase();
      casesOpened++;
      clear();
      caseInterface();
      System.out.println();
      valueInterface();
      System.out.println(caseValue);
      //Bank Offer is given if it is the end of a round
      if (casesOpened == 7 || casesOpened == 12 || casesOpened == 16 || casesOpened == 19 || casesOpened == 21 || casesOpened == 22) {
        System.out.println(yellow + "\n[Banker]" + reset + " The Bank Offers You: " + green + "$" + bankOffer() + reset);
        System.out.println("\n                              " + black + yellowbackground + "Deal or No Deal?" + reset);
        System.out.println("                                 [1] Deal");
        System.out.println("                                 [2] No Deal");
        Deal = userInput(2);
        //Breaks from loop if offer is accepted
        if (Deal == 1) {
          pause(1);
          System.out.println("\nYou accept the Bankers Offer, winning: " + green + "$" + bankOffer() + reset + "!");
          scores.add(bankOffer());
          showUserCase();
          break;
        //Continues game otherwise
        } else if (Deal == 2) {
          pause(1);
          System.out.println("\nYou reject the Bankers Offer");
        }

      }
      //Final case choice is given for last 2 cases
      if (casesOpened == 23) {
        System.out.println(yellow + "\n[Banker]" + reset + " Do you wish to keep your initial case, or switch cases?\n");
        System.out.println("                                 [1] Keep");
        System.out.println("                                 [2] Switch");
        Deal = userInput(2);
        //User wins initial case, or switched case
        if (Deal == 1) {
          System.out.println("\nYou keep your initial case, winning: " + green + "$" + cases[userCase - 1] + reset + "!");
          scores.add(cases[userCase - 1]);

        } else if (Deal == 2) {
          System.out.print("\nYou switch cases, winning: ");
          switchValue();
          System.out.println();
        }
        //Breaks from loop after final case chosen
        break;
      }

    }
    System.out.println(yellow + "\nAny key to return to Main Menu" + reset);
    sc.nextLine();
    clear();
  }

  /*
  main method
  Runs main game program
  Pre: N/A
  Post: Program is cleared and exited
  */
  public static void main(String[] args) {
    int choice;

    while (true) {
      menu();
      setValues();
      choice = userInput(4);
      clear();

      if (choice == 1) {
        dealOrNoDeal();

      } else if (choice == 2) {
        instructions();
      } else if (choice == 3) {
        results();
      } else if (choice == 4) {
        System.out.println(yellow + "                            Thanks for Playing!" + reset);
        pause(3);
        break;
      }

    }

    sc.close();
  }
}