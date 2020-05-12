# Design Structures and Algorithms Assignment Readme

Date last modified: 28/10/19

## Purpose: 
- To simulate the spread of information through a social network. In the network, at each timestep (a concept of stepping through one step of time), there will be a probability of “liking” a post, which will expose the liker's followers to the
information. Liking a post also gives a probability of “following” the original poster - making a direct connection to the original source.

- To investiage the run-times of the time-step function,the pattern that are created by time-step, the correlation between likes/follows in a network and how differently sized networks scale.

## Files in project: 
**SocialSim.java**: File that contains the main

**UserInterface.java**: Provides main menu and sub menus as well as iput validations and prompts for the user when in **interactive mode**: Used With DSAGraph 

**FileManager.java**: File IO. WriteFile and ReadFile. Used with DSAGraph

**SimulationMode.java**: File reponsible to run simulation mode of program. Used with DSAGraph

**DSALinkedList.java**: Data structure and ADT for storing data such as users, posts and likes in social network

**DSAGraph.java**: File that contains all functionaility of social network/graph including private inner classes and displays for the network/graph -

**DSAQueue.java**:  Used for displaying everything to the user by iterating over queue in UI. Is used in DSAGraph and UserInterface

## Test Files: 
100Sample.txt , 1000Sample.txt , 10000Sample.txt, networkFile.txt, eventFile.txt, TSnetwork.txt, TSevent1.txt, TSevent.2txt 

UnitTestGraph.java, UnitTestLinkedList.java, UnitTestQueue.java 

## Functionality: 
Usage information: `java SocialSim`

Interactive mode: `java SocialSim -i`

Simulation mode: `java SocialSim -s <network.txt> <events.txt> <likeProb> <followProb>`

## TODO: 
Improve efficiency of time-step function
