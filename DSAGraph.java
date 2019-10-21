import java.util.*;
import java.io.*;
import java.lang.Math;

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

    public void addFollow (Object userName, Object userName1) //Directed edges
    {
        User userOne, userTwo;

        userOne = getUser(userName); //gets user with userName
        userTwo = getUser(userName1);
        if(hasUser(userName) && hasUser(userName1) && (!userOne.equals(userTwo))) //Only adds the edge if both users exist
        {  
            userOne.addFollow(userTwo); //Adds userTwo into linked list of follows for VertexOne
            followCount++;
        } 
    }

    public void addLike(Object userName, Object userName1, Object postData)
    {
        User liker, poster;
        Post curPost, likePost, foundPost;
        foundPost = null;

        liker = getUser(userName);
        poster = getUser(userName1);

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

    public void removeUser(Object userName)
    {
        User user = getUser(userName);
        users.removeNode(user);
        Iterator iter = users.iterator();
        while(iter.hasNext()) //First iterator to find the user
        {
            User curUser = (User)iter.next(); //The next thing found is a user
            DSALinkedList list = curUser.getFollows();
            list.removeNode(user);
        } 
        System.out.println(user.getUserName() + " has deleted their account!");
    }

    public void removeFollow(Object follower, Object following )
    {
        User followerUser = getUser(follower);
        User followingUser = getUser(following);
        DSALinkedList list = followerUser.getFollows();
        list.removeNode(followingUser);
        System.out.println(followerUser.getUserName() + " has unfollowed " + followingUser.getUserName() +"!");
    }

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
            Post postData = (Post)postsIter.next();
            System.out.print(postData.getPostData() + postData.getLikeCount()); //The userName of the user in the follows LinkedList
            DSALinkedList likeList = postData.getLikes();
            Iterator likesIter = likeList.iterator();
            while(likesIter.hasNext())
            {
                User liker = (User)likesIter.next();
                System.out.print(liker.getUserName() + ",");
            }
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


    /***********************************************************************
    * SUBMODULE: displayAsList()
    * IMPORTS: none
    * EXPORTS: none
    * ASSERTION: Displays an adjacency List
    * **********************************************************************/
    public void displayAsList()
    {
        Iterator iter = users.iterator();
        System.out.println("\nCURRENT NETWORK STATUS:\n"); //Current user to indicate all follows related to this user
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
        Iterator iter = users.iterator();
        while(iter.hasNext()) //Go through every user in the main linked list
        {
            User curUser = (User)iter.next(); //The next thing found is a user
            DSALinkedList mainFollowList = curUser.getFollows();
            Iterator mainFollowIter = mainFollowList.iterator();
            while(mainFollowIter.hasNext())
            {
                User followingUser = (User)mainFollowIter.next(); 
                DSALinkedList followingPosts = followingUser.getPosts();
                Iterator followingPostIter = followingPosts.iterator();
                while(followingPostIter.hasNext())
                {
                    Post curPost = (Post)followingPostIter.next(); 
                    if(Math.random() <= likeProb)
                    {
                        curPost.addLike(curUser);
                    }
                }
            }
        }

        Iterator usersIter = users.iterator();
        while(usersIter.hasNext()) //Goes through the all the users from main list
        {
            User userOne = (User)usersIter.next(); 
            Iterator usersIterTwo = users.iterator(); //Iterator for the edges of the main vertex

            while(usersIterTwo.hasNext()) //Goes through all the users from main list again
            {
                User userTwo = (User)usersIterTwo.next();
                if (isFollowing(userOne.getUserName(), userTwo.getUserName()))
                {
                    DSALinkedList userTwoList = userTwo.getFollows();
                    Iterator followerIter = userTwoList.iterator(); //Goes through the following list of the second userIterator
                    while(followerIter.hasNext())
                    {
                        User userThree = (User)followerIter.next(); //Gets the users from the following list
                        DSALinkedList posts = userThree.getPosts(); //Gets the post of the third user who User2->User3
                        Iterator postIter = posts.iterator();
                        while(postIter.hasNext())
                        {
                            Post curPost = (Post)postIter.next(); 
                            if(isLiking(userThree.getUserName(), userTwo.getUserName(), curPost) && isFollowing(userOne.getUserName(), userTwo.getUserName()))
                            { 
                                if(Math.random() <= followProb)
                                {
                                    addFollow(userOne.getUserName(),userThree.getUserName());
                                }
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
        private DSALinkedList follows; //List with all the users current user following
        private DSALinkedList posts; //List of all posts that current user has put out

        public User (String inUserName)
        {
            follows = new DSALinkedList();
            posts = new DSALinkedList();
            userName = inUserName;
        }

        public String getUserName()
        {
            return this.userName; //returns the userName of the current user 
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
                System.out.println(this.getUserName() + " is now following " + user.getUserName() + "!");
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
    }


    /***********************************************************************
    * PRIVATE INNER CLASS: Post
    * Class for a user which is a person/user in the social network
    * **********************************************************************/
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
    }
/* Post class
in User have Post posts
where if u wanna retrieve posts u access the post class and get the list from it */
}

