import java.util.*;
import java.io.*;

public class DSAGraph
{
    //private classfields
    private DSALinkedList users;
    private int userCount;
    private int followCount;

    public DSAGraph()
    {
        users = new DSALinkedList();
        userCount = 0;
        followCount = 0;
    }

    public void addUser(String userName, Object value)
    {
        User user = new User(userName, value); //Creates new user which contains userName and value
        if (hasUser(userName) == false)
        {
            users.insertLast(user); //Puts the newly made user in the users linked list
        }

        userCount++; //increases userCount after each user is created
    }

    public void addFollow (Object userName, Object userName1) //Directed edges
    {
        User userOne, userTwo;

        userOne = getUser(userName); //gets user with userName
        userTwo = getUser(userName1); //gets user with userName1 

        userOne.addFollow(userTwo); //Adds userTwo into linked list of follows for VertexOne

        followCount++;
        
    }


    public void addPost (Object userName, Object postData)
    {
        User userOne;
        if (getUser(userName) != null)
        {
            userOne = getUser(userName); //gets user with userName

            userOne.addPost(postData); //Adds userTwo into linked list of follows for VertexOne
        }
    }

    public boolean hasUser(Object userName)
    {
        boolean found = false;
        User temp;
        Object tempName;
        Iterator iter = users.iterator();

        while (iter.hasNext())
        {
            temp = (User)iter.next();
            tempName = temp.getUserName();
            if (tempName.equals(userName))
            {
                found = true;
            }
        }
        return found;
    }

    public int getuserCount()
    {
        return userCount;
    }

    public int getEdgeCount()
    {
        return followCount;
    }

    public User getUser(Object userName)
    {
        Iterator iter = users.iterator();
        User newUser = null; //the new user is set as null at the start since no user has been found

        while(iter.hasNext()) //While there is still another user 
        {
            User user = (User)iter.next(); //Typecast the object returned by iterator to User 
            if (userName.equals(user.getUserName())) //If the imported userName is the same as the newly found in iterator userName
            {
                newUser = user; //The new user is the user that matches the same userName
            }
        }
        return newUser; //Returns the user 
    }

    public void displayUserInfo(Object userName)
    {
        User curUser = getUser(userName);
        DSALinkedList list = curUser.getFollows(); //List of follows for current user
        Iterator linkIter = list.iterator();
        System.out.println("\n--==" + userName + " Info==--"); 
        System.out.print("\nFollows: ");
        while(linkIter.hasNext()) //Second iterator to find follows for each user
        {
            User following = (User)linkIter.next();
            System.out.print(following.getUserName()); //The userName of the user in the follows LinkedList
            if(linkIter.hasNext())
            {
                System.out.print(", "); // add a comma after each userName in the list
            }
        }
        System.out.print("\n");

        DSALinkedList posts = curUser.getPosts(); //Gets all the posts from the user
        Iterator postsIter = posts.iterator();
        System.out.print("\nPosts:\n");
        while(postsIter.hasNext()) //Second iterator to find follows for each user
        {
            String postData = (String)postsIter.next();
            System.out.print(postData); //The userName of the user in the follows LinkedList
            if(postsIter.hasNext())
            {
                System.out.print("\n"); // add a comma after each userName in the list
            }
        }
        System.out.print("\n");
    }
    
    public DSALinkedList getFollows(Object userName)
    {
        return getUser(userName).getFollows(); 
    }

    public DSALinkedList getPosts(Object userName)
    {
        return getUser(userName).getPosts(); 
    }

    /***********************************************************************
    * SUBMODULE: isFollowing()
    * IMPORTS: userName1 (Object), userName2 (Object)
    * EXPORTS: state (boolean)
    * ASSERTION: Checks if userOne is following userTwo
    * **********************************************************************/
    public boolean isFollowing(Object userName1, Object userName2)
    {
        boolean state = false; //By default the state is false

        User userOne;

        userOne = getUser(userName1); //get user that has userName1

        DSALinkedList follows = userOne.getFollows();
        Iterator iter = follows.iterator();

        while (iter.hasNext())
        {
            User iterVertex = (User)iter.next();
            if(iterVertex.getUserName() == userName2) //If the userName of user one is equal to userName2 which is another user then isFollowing
            {
                state = true;
            }
        }
        return state;//Boolean True/False
    }


    /***********************************************************************
    * SUBMODULE: displayAsList()
    * IMPORTS: none
    * EXPORTS: none
    * ASSERTION: Displays an adjacency List
    * **********************************************************************/
    public void displayAsList()
    {
        Iterator iter = users.iterator();
        System.out.println("\nADJACENCY LIST: \n");
        while(iter.hasNext()) //First iterator to find the user
        {
            User user = (User)iter.next(); //The next thing found is a user
            System.out.print(user.getUserName() +": "); //Current user to indicate all follows related to this user
            DSALinkedList list = user.getFollows(); //List of follows for current user
            Iterator linkIter = list.iterator(); 

            while(linkIter.hasNext()) //Second iterator to find follows for each user
            {
                User following = (User)linkIter.next();
                System.out.print(following.getUserName()); //The userName of the user in the follows LinkedList
                if(linkIter.hasNext())
                {
                    System.out.print(", "); // add a comma after each userName in the list
                }
            }
            System.out.print("\n"); //After one user is completely shown, move to a new line for the next one
        }
    }

    /***********************************************************************
    * SUBMODULE: displayAsMatrix
    * IMPORTS: none
    * EXPORTS: none
    * ASSERTION: Displays an adjacency matrix
    * **********************************************************************/
    public void displayAsMatrix()
    {
        System.out.println("\nADJACENCY MATRIX: \n");
        System.out.print("   ");
        Iterator iter = users.iterator();
   
        while(iter.hasNext()) //Iterator to have the header row with all users listed
        {
            User rowOne = (User)iter.next();
            System.out.print(rowOne.getUserName() + "  ");
        }
        System.out.println("\n"); //New line for after header row 

        Iterator usersIter = users.iterator();
        
        while(usersIter.hasNext()) //Main user that will be compared to
        {
            User userOne = (User)usersIter.next(); 
            System.out.print(userOne.getUserName() + "  "); //Current main user that is being checked

            Iterator usersIterTwo = users.iterator(); //Iterator for the following of the main user

            while(usersIterTwo.hasNext()) //Iterator for checking if each user listed is linked to the main user
            {
                //This user changes to check if each user is in the linked list of the main
                User userTwo = (User)usersIterTwo.next();
                
                if (isFollowing(userOne.getUserName(), userTwo.getUserName()))
                {
                    System.out.print("1  ");//If user is in follows list then prints 1 for true
                }
                else
                {
                    System.out.print("0  "); //If user isn't in follows list then prints 0 for false
                }
            }
            System.out.println("\n");
        }
    }

    /***********************************************************************
    * PRIVATE INNER CLASS: User
    * Class for a user which is a person/user in the social network
    * **********************************************************************/
    private class User
    {
        //Private Classfields
        private String userName; //Name of person
        private Object value;
        private DSALinkedList follows; //List with all the users current user following
        private DSALinkedList posts; //List of all posts that current user has put out
        private boolean visited; //True/false for BFS and DFS

        public User (String inUserName, Object inValue)
        {
            follows = new DSALinkedList();
            posts = new DSALinkedList();
            userName = inUserName;
            value = inValue;
        }

        public String getUserName()
        {
            return this.userName; //returns the userName of the current user 
        } 

        public Object getValue()
        {
            return this.value; //returns value of the current user 
        }

        public DSALinkedList getFollows()
        {
            return this.follows; //returns the linked lists that contains the follows to other users 
        }

        public void addFollow(User user)
        {
            if (!(isFollowing(this.getUserName(), user.getUserName()))) //Ensuring a person can only follow a person once
            {
                this.follows.insertLast(user); //Adds edge by adding the user to the list of follows for selected user
            }
        }

        public void addPost(Object postData)
        {
            this.posts.insertLast(postData);
        } 

        public DSALinkedList getPosts()
        {
            return this.posts; //returns the linked lists that contains the follows to other users 
        }

        public void setVisited() //Not needed for now
        {
            visited = true;
        }

        public void clearVisited()
        {
            visited = false; 
        }

        public boolean getVisited()
        {
            return visited;
        }

        /*public String toString()
        {
            output = "";
            return output;
        }*/
    }


    /***********************************************************************
    * PRIVATE INNER CLASS: User
    * Class for a user which is a person/user in the social network
    * **********************************************************************/

/* Post class
in User have Post posts
where if u wanna retrieve posts u access the post class and get the list from it */
}