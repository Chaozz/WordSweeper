Point Breakdown
===============

Task 2 is the first iteration of your overall group project. Based on
your status as a client or server team, you will have different
expectations, as outlined below. This project is worth 20% of your total
grade.

Reports
=======

You do not need to complete Individual or Group reports for Task 2.

Source Code Management
======================

Each team must tell me the URL of the Git repository in which their
source code can be found. You need to make this Git repository available
to me; you have several choices:

1.  Create a fusion.wpi.edu project and host it there. Then I can access
    it as a logged in fusion.wpi.edu user if you grant me SCM access as
    a logged in user

2.  Create a public Git repository on some site (i.e., github) and then
    I will retrieve anonymously

3.  Create a private Git repository someplace but the you have to add me
    as a member so I can see the code

Configurations
==============

1.  Server applications must be configured to listen on Port 11425 (as
    is done in my mock client/server in the WordSweeper project) and
    client applications will seek to connect to **localhost** using this
    same port number.

Server
======

-   Be able to execute your server and have a mock client
    (mine actually) connect.

-   Demonstrate that server receives a createGameRequest from the mock
    Client to create a game (no password) and issues a boardResponse
    with the initiating player’s board to the client. Follow the
    protocol where the letters on the board state are comma-separated
    (this is a change based on 9-27-2016 lecture; review the protocol
    in wordsweeper.xsd)

-   Start a second mock Client to connect to your server

-   Demonstrate that server receives a joinGameRequest for the first
    game and issues a boardResponse to both the original client and the
    new participating client

-   Players should have a position on the board that is randomly
    selected from the original virtual board state.

-   **Note: I will run your server twice and generate two separate
    games; they cannot contain the same identical letters (i.e., be sure
    that the letters are randomly generated)**

-   **Note: you do not have to be able to support any moves just yet**

Client
======

-   Be able to execute your client and show that it connects to a mock
    server (mine actually). Note for this Task 2 your client does not
    need to actually send or receive messages from the server

-   Your application shows its “main screen” from which a player has the
    option to (a) create game; (b) join game; (c) start practice game

-   Select “start practice game”

-   Open a new window and show the state of a random practice game. That
    is, it should show sixteen cells in a 4x4 arrangement, each
    containing a random letter or ‘Qu’ as a pair.

-   **Note: I will run your program twice and generate two practice
    games; they cannot contain the same identical letters (i.e., be sure
    that the letters are randomly generated)**

-   **Note: You do not have to be able to make any moves just yet**

Submission
==========

I will need to retrieve your project code using Git. However, you should
also prepare a single zip file containing your Eclipse project which
contains your code. Upload this zip file within the my.wpi.edu
assignment for Task 2.

As I have stated in class, do not program your task within the
WordSweeper project. That is mine to push changes to the protocol to
your teams. You will develop your own Java Project in Eclipse (or
alternatively, in some other IDE).

I need the \*.java files (that is, the source code) not just the
compiled \*.class byte code files.

###notes

-   Assume N players on a game
    When 16N > 3*size2 increase size by 1
  
-   change xml



