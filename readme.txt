George Aziz DSA SocialSim Readme

Date created: 26/10/2019

Date last modified: 27/10/2019

Purpose: To create and investigate social networks with time-step concepts by simulating a social network

Files in project: 
JAVA -

SocialSim.java (File that contains the main)
UserInterface.java  (Provides main menu and sub menus as well as iput validations and prompts for the user when in interactive mode. Used With DSAGraph )
FileManager.java (File IO. WriteFile and ReadFile. Used with DSAGraph)
SimulationMode.java (File reponsible to run simulation mode of program. Used with DSAGraph)
DSALinkedList.java (Data structure and ADT for storing data such as users, posts and likes in social network)
DSAGraph.java (File that contains all functionaility of social network/graph including private inner classes and displays for the network/graph)
DSAQueue.java (Used for displaying everything to the user by iterating over queue in UI. Is used in DSAGraph and UserInterface)

----
UML as PNG and also provided in documentation and report

----
Test Files: 
    100Sample.txt (Made in Excel by me and exported to .txt)
    1000Sample.txt (Made in Excel by me and exported to .txt)
    10000Sample.txt (Made in Excel by me and exported to .txt)
    networkFile.txt (The first networkFile provided to us)
    eventFile.txt (The first eventFile provided to us)
    TSnetwork.txt (Toy Story)
    TSevent1.txt (Toy Story)
    TSevent.2txt (Toy Story)

    UnitTestGraph.java 
    UnitTestLinkedList.java 
    UnitTestQueue.java 

Functionality:  To load in a network from a network file 
                To add/remove users, posts and follows in the social network/graph 
                To be able to run a time step 
                To be able to simulate a real social network through simulation mode and/or interactive mode

TODO: Everything has been completed
