// Entities.Player vs. Entities.Bot
                System.out.println("Select bot difficulty (1-10):");
                        int botDifficulty = scanner.nextInt();

                        if (botDifficulty >= 1 && botDifficulty <= 10) {
                        System.out.println("Entities.Game started: " + username + " vs. Entities.Bot (Difficulty: " + botDifficulty + ")");
                        } else {
                        System.out.println("Invalid bot difficulty. Please select a number between 1 and 10.");
                        }
                        String[] difficulty = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
                        String machine = "Machine " + difficulty[botDifficulty-1];
                        System.out.println("Entities.Game started: " + username + " vs." + machine);

                        System.out.println("Select an option:");
                        System.out.println("1. Set Entities.Board Dimensions");
                        System.out.println("2. Use Last Entities.Game's Settings");
                        System.out.println("Continue: Use Default Settings");
                        String choice = scanner.nextLine();
                        int length = 0;
                        int height = 0;
                        int piecesToConnect = 0;
                        switch (choice) {
                        case "1":
                        length = Player.getBoardLength(scanner);
                        height = Player.getBoardHeight(scanner);
                        boolean valid = false;
                        while (!valid) {
                        piecesToConnect = Player.getRequiredPieces(scanner);
                        if (piecesToConnect <= length && piecesToConnect <= height) {
                        valid = true;
                        }
                        }
                        break;
                        case "2":
                        try {
                        File file = new File(SETTINGS_FILE);
                        Scanner fileScanner = new Scanner(file);


                        while (fileScanner.hasNextLine()) {
                        String line = fileScanner.nextLine();
                        String[] settings = line.split("■");
                        length = Integer.parseInt(settings[0]);
                        height = Integer.parseInt(settings[1]);
                        piecesToConnect = Integer.parseInt(settings[2]);
                        }
                        fileScanner.close();
                        } catch (IOException e) {
                        e.printStackTrace();
                        }
                        break;
                        case "t"://for testing purposes only; delete for submission
                        length = 4;
                        height = 4;
                        piecesToConnect = 3;
                        break;
default:
        length = 7;
        height = 6;
        piecesToConnect = 4;
        break;
        }

        try {
        FileWriter fileWriter = new FileWriter(SETTINGS_FILE, false); // Open the file in overwrite mode
        PrintWriter printWriter = new PrintWriter(fileWriter);

        String userData = length + "■" + height + "■" + piecesToConnect;
        printWriter.println(userData);

        printWriter.close();
        } catch (IOException e) {
        e.printStackTrace();
        }

        Board board = new Board(length, height, piecesToConnect);
        WinChecker winChecker = new WinChecker(piecesToConnect, board.getBoard());
        Board prev = new Board(length, height, piecesToConnect);
        ;
        char currentPlayer = 'X';
        while (true) {
//                        prev = board;
        board.displayBoard();
        if (currentPlayer == 'X') {
        System.out.println(username + ", enter the column to drop your piece:");
        } else {
        System.out.println("Entities.Bot chooses column " + 1);
        }
        int column = -1; // Initialize to an invalid value
        boolean validInput = false;

        while (!validInput) {
        String input = scanner.nextLine();
        if (input.equalsIgnoreCase("r")) {
        char otherPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        String winnerName = (otherPlayer == 'X') ? username : machine;
        String loserName = (otherPlayer == 'X') ? machine : username;
        System.out.println("Entities.Player " + winnerName + " wins! (Entities.Player " + loserName + " quit)");
        winner(winnerName, loserName);
        return; // Exit the game
        }
        if (input.equalsIgnoreCase("q")) {
        board = prev;
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        }
        try {
        column = Integer.parseInt(input) - 1;
        validInput = true; // Input is valid; exit the loop
        } catch (NumberFormatException e) {
        System.out.println("Invalid input. Please enter a valid integer for the column.");
        }
        }

        if (winChecker.checkWin(currentPlayer)) {
        board.displayBoard();
        System.out.println("Entities.Player " + currentPlayer + " wins!");
        break;
        }

        if (board.makeMove(column, currentPlayer)) {
        if (winChecker.checkWin(currentPlayer)) {
        board.displayBoard();
        String winnerName = (currentPlayer == 'X') ? username : machine;
        String loserName = (currentPlayer == 'X') ? machine : username;
        System.out.println("Entities.Player " + winnerName + " wins!");
        winner(winnerName, loserName);
        break;
        } else if (board.isFull()) {
        board.displayBoard();
        System.out.println("It's a tie!");
        break;
        }
        prev = board;
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        } else {
        System.out.println("Invalid move. Please try again.");
        }
        }

//        BOT PLAYGAME

//    public static void playGame(Board board, String username, String secondPlayer, char currentPlayer, WinChecker winChecker, int d) {
//        while (true) {
//            board.displayBoard();
//            if (currentPlayer == 'X') {
//                System.out.println(username + ", enter the column to drop your piece:");
//            } else {
//                System.out.println(secondPlayer + ", enter the column to drop your piece:");
//            }
//            // Use a try-catch block to handle non-integer input
//            int column = -1; // Initialize to an invalid value
//            boolean validInput = false;
//
//            while (!validInput) {
//                String input = scanner.nextLine();
//                if (input.equalsIgnoreCase("r")) {
//                    char otherPlayer = (currentPlayer == 'X') ? 'O' : 'X';
//                    String winnerName = (otherPlayer == 'X') ? username : secondPlayer;
//                    String loserName = (otherPlayer == 'X') ? secondPlayer : username;
//                    System.out.println("Entities.Player " + winnerName + " wins! (Entities.Player " + loserName + " quit)");
//                    Main.winner(winnerName, loserName);
//                    return; // Exit the game
//                }
//
//
//
//                try {
//                    column = Integer.parseInt(input) - 1;
////                    System.out.println("MOVE: " + column);
//                    moves.add(column);
//                    System.out.println(moves);
//                    validInput = true; // Input is valid; exit the loop
//                } catch (NumberFormatException e) {
//                    //#TODO: stop this error message from triggering with a u for undoing a  move
//                    System.out.println("Invalid input. Please enter a valid integer for the column.");
//                }
////
//            }
//
//            if (winChecker.checkWin(currentPlayer)) {
//                board.displayBoard();
//                System.out.println("Entities.Player " + currentPlayer + " wins!");
//                break;
//            }
//
//            if (board.makeMove(column, currentPlayer)) {
//                if (winChecker.checkWin(currentPlayer)) {
//                    board.displayBoard();
//                    String winnerName = (currentPlayer == 'X') ? username : secondPlayer;
//                    String loserName = (currentPlayer == 'X') ? secondPlayer : username;
//                    System.out.println("Entities.Player " + winnerName + " wins!");
//                    Main.winner(winnerName, loserName);
//                    break;
//                } else if (board.isFull()) {
//                    board.displayBoard();
//                    System.out.println("It's a tie!");
//                    break;
//                }
//                String input = scanner.nextLine();
//                System.out.println("Are you sure? Press 'u' to undo. Press anything to continue.");
//
//                //#TODO: make this more elegant; make 3 undo limit.
//                if (input.equalsIgnoreCase("u")) {
//                    board = reconstruct(board, moves);
//                    currentPlayer = (currentPlayer == 'X') ? 'X' : 'O';
//                } else {
//                    currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
//                }
//            }
//            else {
//                System.out.println("Invalid move. Please try again.");
//            }
//        }
//    }
