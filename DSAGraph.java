import java.util.*;
import java.io.*;
import java.lang.Math;

public class DSAGraph
{
    //PRIVATE CLASSFIELDS
    private DSALinkedList users;
    private int userCount;

    //Default Constructor
    public DSAGraph()
    {
        users = new DSALinkedList();
        userCount = 0;
    }

    /*******************************************************
    * SUBMODULE: addUser()
    * IMPORTS: userName (Object)
    * EXPORTS: none
    * ASSERTION: adds a new user to the network
    * ******************************************************/
    public void addUser(String userName)
    {
        User user = new User(userName); //Creates new user which contains userName
        if (hasUser(userName) == false)
        {
            users.insertLast(user); //Puts the newly made user in the users linked list
            System.out.println("New user added: " + userName);
            userCount++; //increases userCount after each user is created
        }
    }

    /*******************************************************
    * SUBMODULE: addFollow()
    * IMPORTS: userName (Object), userName1 (Object)
    * EXPORTS: none
    * ASSERTION: adds a follow from one user to another
    * ******************************************************/
    public void addFollow (Object userName1, Object userName2) //Directed edges
    {
        User userOne, userTwo;

        userOne = getUser(userName1); //gets user with userName
        userTwo = getUser(userName2);
        if(hasUser(userName1) && hasUser(userName2) && (!userOne.equals(userTwo))) //Only adds the edge if both users exist
        {  
            userOne.addFollow(userTwo); //Adds userTwo into linked list of follows for UserOne
            userTwo.addFollower(userOne); //Adds userOne into linked list of followers for userTwo
        } 
    }

    /*********************************************************
    * SUBMODULE: addPost()
    * IMPORTS: userName (Object), postData (String)
    * EXPORTS: none
    * ASSERTION: adds a post for a specefied user
    * ********************************************************/
    public void addPost (Object userName, String postData)
    {
        User userOne;
        if (getUser(userName) != null)
        {
            userOne = getUser(userName); //gets user with userName
            String name = userOne.getUserName();
            Post newPost = new Post(postData, name);
            userOne.addPost(newPost); //Adds userTwo into linked list of follows for VertexOne
            System.out.println(name + " has added a new post!");
        }
    }

    /*******************************************************************************************
    * SUBMODULE: addLike()
    * IMPORTS: userName (Object), userName1 (Object), postData (Object)
    * EXPORTS: none
    * ASSERTION: adds a userName like to userName1's post
    * *******************************************************************************************/
    public void addLike(Object userName1, Object userName2, Object postData)
    {
        User liker, poster;
        Post curPost, likePost, foundPost;
        foundPost = null;

        liker = getUser(userName1);
        poster = getUser(userName2);

        likePost = (Post)postData;

        DSALinkedList posts = poster.getPosts(); // Gets all the posts from the user
        Iterator iter = posts.iterator();
        
        while(iter.hasNext()) 
        {
            curPost = (Post)iter.next(); 
            if (curPost.equals(likePost))
            {
                foundPost = likePost; 
            }
        }

        if (foundPost != null)
        {
            foundPost.addLike(liker);
        }
    }

    /*******************************************************************************************
    * SUBMODULE: removeUser()
    * IMPORTS: userName (Object)
    * EXPORTS: none
    * ASSERTION: Removes User from the whole network including followers,following and likes list
    * *******************************************************************************************/
    public void removeUser(Object userName)
    {
        User user = getUser(userName);
        users.removeNode(user);
        Iterator iter = users.iterator();
        while(iter.hasNext()) //First iterator to find the user
        {
            User curUser = (User)iter.next(); //The next thing found is a user
            DSALinkedList list = curUser.getFollows(); //Gets who the person follows
            DSALinkedList followersList = curUser.getFollowers(); //Gets the person's followers
            list.removeNode(user);
            followersList.removeNode(user);
            curUser.remFollowerCount();
            curUser.remFollowCount();

            DSALinkedList posts = curUser.getPosts(); //Gets all the posts from the user
            Iterator postsIter = posts.iterator();
            while(postsIter.hasNext()) //Second iterator to find follows for each user
            {
                Post postData = (Post)postsIter.next();
                DSALinkedList likeList = postData.getLikes();
                likeList.removeNode(user); 
                postData.remUserLikeCount();
            }
            userCount--; //For stats, Overcall user count in the social network
        } 
        System.out.println(user.getUserName() + " has deleted their account!");
    }

    /*********************************************************
    * SUBMODULE: removeFollow()
    * IMPORTS: follower (Object), following (Object)
    * EXPORTS: none
    * ASSERTION: Removes a follow from the follower 
    * ********************************************************/
    public void removeFollow(Object follower, Object following )
    {
        User followerUser = getUser(follower);
        User followingUser = getUser(following);
        DSALinkedList list = followerUser.getFollows();
        list.removeNode(followingUser);

        DSALinkedList followersList = followingUser.getFollowers();
        followersList.removeNode(followerUser);
        followerUser.remFollowerCount();
        followerUser.remFollowCount();

        DSALinkedList posts = followingUser.getPosts(); //Gets all the posts from the user
        Iterator postsIter = posts.iterator();

        while(postsIter.hasNext()) //Second iterator to find follows for each user
        {
            Post postData = (Post)postsIter.next();
            DSALinkedList likeList = postData.getLikes();
            likeList.removeNode(followerUser); 
            postData.remUserLikeCount();
        }
        System.out.println(followerUser.getUserName() + " has unfollowed " + followingUser.getUserName() +"!");
    }


    /*************************************************
    * SUBMODULE: getUser()
    * IMPORTS: userName (Object)
    * EXPORTS: newUser (User)
    * ASSERTION: gets the User with the current name
    * ***********************************************/
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

    /*********************************************************
    * SUBMODULE: hasUser()
    * IMPORTS: userName (Object)
    * EXPORTS: found (boolean)
    * ASSERTION: Checks if a user exists
    * ********************************************************/
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
    
    /*********************************************************
    * SUBMODULE: getUserCount()
    * IMPORTS: none
    * EXPORTS: userCount (Integer)
    * ASSERTION: gets the overall UserCount for social network
    * ********************************************************/
    public int getuserCount()
    {
        return userCount;
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
            User iterUser = (User)iter.next();
            if(iterUser.getUserName() == userName2) //If the userName of user one is equal to userName2 which is another user then isFollowing
            {
                state = true;
            }
        }
        return state;//Boolean True/False
    }

    /***********************************************************************
    * SUBMODULE: isFollower()
    * IMPORTS: userName1 (Object), userName2 (Object)
    * EXPORTS: state (boolean)
    * ASSERTION: Checks if userName 2 is in userOne's followers list
    * **********************************************************************/
    public boolean isFollower(Object userName1, Object userName2)
    {
        boolean state = false; //By default the state is false
        User userOne;
        userOne = getUser(userName1); //get user that has userName1

        DSALinkedList followers = userOne.getFollowers();
        Iterator iter = followers.iterator();

        while (iter.hasNext())
        {
            User iterUser = (User)iter.next();
            if(iterUser.getUserName() == userName2) //If userOne's followers has userName 2 then userName 2 already following
            {
                state = true;
            }
        }
        return state;//Boolean True/False
    }

    /***********************************************************************
    * SUBMODULE: isLiking()
    * IMPORTS: userName1 (Object), userName2 (Object), inPost (Object)
    * EXPORTS: state (boolean)
    * ASSERTION: Checks if userName 2 has liked userOne's post
    * **********************************************************************/
    public boolean isLiking(Object userName1, Object userName2, Post post)
    {
        boolean state = false; //By default the state is false
        User userOne; //User Two is the person getting checked for in the likes
        userOne = getUser(userName1); //userName1 is the person with the post

        DSALinkedList likes = post.getLikes();
        Iterator iter = likes.iterator();

        while (iter.hasNext())
        {
            User iterUser = (User)iter.next();
            if(iterUser.getUserName().equals(userName2)) //If the userName of user one is equal to userName2 which is another user then isFollowing
            {
                state = true;
            }
        }
        return state;//Boolean True/False
    }

    /************************************************************************************************
    * SUBMODULE: displayUserInfo()
    * IMPORTS: userName (Object)
    * EXPORTS: none
    * ASSERTION: Displays user's info including Follows, Followers and Posts with who liked each post
    * ***********************************************************************************************/
    public void displayUserInfo(Object userName)
    {
        User mainUser = getUser(userName); //Main user that will be displayed

        DSALinkedList list = mainUser.getFollows(); //List of follows for the main user
        Iterator followIter = list.iterator();
        System.out.println("\n--==" + userName + " Info==--"); //Main user's Info displayed starts here
        System.out.print("\n" + mainUser.getFollowCount() + " Follows: "); 
        while(followIter.hasNext()) //Iterator to go over who mainUser follows
        {
            User following = (User)followIter.next();
            System.out.print(following.getUserName()); //The userName of the user in the follows LinkedList
            if(followIter.hasNext()) //Only if there is another element/person after
            {
                System.out.print(", "); // add a comma after each userName in the list
            }
        }

        DSALinkedList followersList = mainUser.getFollowers(); //List of followers of the main user
        Iterator followersIter = followersList.iterator();
        System.out.print("\n\n" + mainUser.getFollowersCount() + " Followers: ");
        while(followersIter.hasNext()) //Second iterator to find followers for mainUser
        {
            User follower = (User)followersIter.next();
            System.out.print(follower.getUserName()); //The userName of the user in the followers LinkedList
            if(followersIter.hasNext())
            {
                System.out.print(", "); // add a comma after each userName in the list
            }
        }

        DSALinkedList posts = mainUser.getPosts(); //Gets all the posts from the main user
        Iterator postsIter = posts.iterator();
        System.out.print("\n\nPosts:\n");
        while(postsIter.hasNext()) //Third iterator for the posts of the main user
        {
            Post curPost = (Post)postsIter.next(); 
            System.out.print("\n" + curPost.getPostData() + "\n"); //Gets whatever the post contains as a String
            DSALinkedList likeList = curPost.getLikes(); //Gets the likes of the curPost
            Iterator likesIter = likeList.iterator();
            System.out.print(curPost.getLikeCount() + " Likers: "); //Gets like count and displays each person
            while(likesIter.hasNext())
            {
                User liker = (User)likesIter.next(); //The liker is a user
                System.out.print(liker.getUserName()); //Prints the name of the user who liked the post
                if(likesIter.hasNext())
                {
                    System.out.print(", ");
                }
            }
            if(postsIter.hasNext()) //After one post is complete
            {
                System.out.print("\n"); // add a comma after each userName in the list
            }
        }
        System.out.print("\n");
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
        System.out.println("\nCURRENT NETWORK STATUS:\n"); //Current network status
        while(iter.hasNext()) //First iterator to find the user
        {
            User user = (User)iter.next(); //The next thing found is a user
            System.out.print(user.getUserName() +" follows: "); //Current user to indicate all follows related to this user
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

    /*******************************************************************************
    * SUBMODULE: timeStep()
    * IMPORTS: likeProb (Real), followProb (Real)
    * EXPORTS: none
    * ASSERTION: An update for the graph with imported liking/following probabilities
    * ******************************************************************************/
    public void timeStep(double likeProb, double followProb)
    {

        Iterator usersIter = users.iterator(); 
        while(usersIter.hasNext()) //Goes through the users in the network
        {
            User userOne = (User)usersIter.next(); //The first user [A]->B->C
            //Goes through the users in the network again 
            Iterator usersIterTwo = users.iterator(); 

            while(usersIterTwo.hasNext()) //Goes through all the users from main list again
            {
                User userTwo = (User)usersIterTwo.next(); //The middle user A->[B]->C

                DSALinkedList userTwoList = userTwo.getFollows(); //Gets all the people userTwo is following
                Iterator followerIter = userTwoList.iterator();
                while(followerIter.hasNext())
                {
                    User userThree = (User)followerIter.next(); //The End user A->B->[C]
                    DSALinkedList listPosts = userThree.getPosts();
                    Iterator postIter = listPosts.iterator(); //Iterator for the posts of userThree
                    while(postIter.hasNext())
                    {
                        Post curPost = (Post)postIter.next(); //Gets the post of the userThree
                        if(Math.random() <= likeProb) 
                        {
                            curPost.addLike(userTwo); //Adds a like to the post if probability is met
                        }

                        //If the userTwo likes userThree's post AND userOne is following userTwo
                        if(isLiking(userThree.getUserName(), userTwo.getUserName(), curPost) && isFollowing(userOne.getUserName(), userTwo.getUserName()))
                        { 
                            if(Math.random() <= followProb)
                            {
                                addFollow(userOne.getUserName(),userThree.getUserName()); //userOne follows userThree A->C 
                            }
                        } 
                    }
                }
            }
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
        private int followerCount;
        private int followCount;
        private DSALinkedList follows; //List with all the users current user following
        private DSALinkedList followers; //List with all the users following current user
        private DSALinkedList posts; //List of all posts that current user has put out

        public User (String inUserName)
        {
            follows = new DSALinkedList();
            followers = new DSALinkedList();
            posts = new DSALinkedList();
            userName = inUserName;
            followCount = 0;
            followerCount = 0;
        }

        public String getUserName()
        {
            return this.userName; //returns the userName of the current user 
        } 

        public DSALinkedList getFollows()
        {
            return this.follows; //returns the linked lists that contains the follows to other users 
        }

        public DSALinkedList getFollowers()
        {
            return this.followers;
        }

        public void addFollow(User user)
        {
            if (!(isFollowing(this.getUserName(), user.getUserName()))) //Ensuring a person can only follow a person once
            {
                this.follows.insertLast(user); //Adds edge by adding the user to the list of follows for selected user
                System.out.println(this.getUserName() + " is now following " + user.getUserName() + "!");
                followCount++;
            }
        }

        public void addFollower(User user)
        {
            if (!(isFollower(this.getUserName(), user.getUserName())))
            {
                this.followers.insertLast(user);
                followerCount++;
            }
        }

        public void addPost(Post newPost)
        {
            
            this.posts.insertLast(newPost);
        }

        public DSALinkedList getPosts()
        {
            return this.posts; //returns the linked lists that contains the follows to other users 
        }

        public int getFollowersCount()
        {
            return this.followerCount;
        }

        public int getFollowCount()
        {
            return this.followCount;
        }

        
        public void remFollowerCount()
        {
            followerCount--;
        }

        public void remFollowCount()
        {
            followCount--;
        }
    }


    /*****************************************************************************************************
    * PRIVATE INNER CLASS: Post
    * Class for a Post which stores all info for each post and is stored in a LinkedList within User Class
    * ****************************************************************************************************/
    public class Post 
    {
        private String poster;
        private String postData;
        private int likeCount;
        private DSALinkedList likes;
        
        public Post (String inPostData, String inPoster)
        {
            likes = new DSALinkedList();
            poster = inPoster;
            postData = inPostData;
            likeCount = 0;
        }

        public void addLike(User user)
        {
           if (!(isLiking(this.getPoster(), user.getUserName(), this))) //Ensuring a person can only like a post once
            { 
                this.likes.insertLast(user);
                likeCount++;
            } 
        }

        public String getPoster()
        {
            return this.poster;
        }
        public int getLikeCount()
        {
            return this.likeCount;
        }

        public DSALinkedList getLikes()
        {
            return this.likes;
        }

        public String getPostData()
        {
            return this.postData;
        }

        public void remUserLikeCount()
        {
            likeCount--;
        }
    }
/* Post class
in User have Post posts
where if u wanna retrieve posts u access the post class and get the list from it */
}
