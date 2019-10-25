import java.util.*;
import java.io.*;
import java.lang.Math;

public class DSAGraph
{
    //PRIVATE CLASSFIELDS
    private UserInterface UI; //To Display Messages
    private DSALinkedList users; 
    private int userCount; //Total count of users in the network
    private int postCount; //Total count of posts for sorting

    //Default Constructor
    public DSAGraph()
    {
        users = new DSALinkedList();
        userCount = 0;
        postCount = 0;
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
            userCount = userCount + 1; //increases userCount after each user is created
            UI.showMessage("New user added: " + userName);
        }
    }

    /*******************************************************
    * SUBMODULE: addFollow()
    * IMPORTS: userName (Object), userName1 (Object)
    * EXPORTS: none
    * ASSERTION: adds a follow from one user to another
    * ******************************************************/
    public void addFollow (Object follower, Object following) //Directed edges
    {
        User userOne, userTwo;

        userOne = getUser(follower); //gets user with userName
        userTwo = getUser(following);
        if(hasUser(follower) && hasUser(following) && (!userOne.equals(userTwo))) //Only adds the edge if both users exist
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
    public void addPost (Object userName, String postData, double clickBait)
    {
        User userOne;
        if (getUser(userName) != null)
        {
            userOne = getUser(userName); //gets user with userName
            String name = userOne.getUserName();
            Post newPost = new Post(postData, name, clickBait);
            userOne.addPost(newPost); //Adds userTwo into linked list of follows for VertexOne
            postCount++;
            UI.showMessage(name + " has added a new post!");
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
        Iterator iter = users.iterator();
        while(iter.hasNext()) //First iterator to find the user
        {
            User curUser = (User)iter.next(); //The next thing found is a user
            DSALinkedList list = curUser.getFollows(); //Gets who the person follows
            DSALinkedList followersList = curUser.getFollowers(); //Gets the person's followers
            if (isFollowing(curUser.getUserName(), user.getUserName()))
            {
                list.removeNode(user);
                if (curUser.getFollowCount() > 0)
                {
                    curUser.remFollowCount();
                }
            }
            if (isFollower(curUser.getUserName(), user.getUserName()))
            {
                followersList.removeNode(user);
                if(curUser.getFollowersCount() > 0)
                {
                    curUser.remFollowerCount();
                }
            }

            DSALinkedList posts = curUser.getPosts(); //Gets all the posts from the user
            Iterator postsIter = posts.iterator();
            while(postsIter.hasNext()) //Second iterator to find follows for each user
            {
                Post postData = (Post)postsIter.next();
                DSALinkedList likeList = postData.getLikes();
                if(isLiking(user.getUserName(),postData))
                {
                    likeList.removeNode(user); 
                    postData.remUserLikeCount();
                }
            }
        } 
        userCount--; //For stats, Overcall user count in the social network
        users.removeNode(user);
        UI.showMessage(user.getUserName() + " has deleted their account!");
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

        DSALinkedList followersList = followingUser.getFollowers();
        if (isFollowing(followingUser.getUserName(), followerUser.getUserName()) && isFollower(followingUser.getUserName(), followerUser.getUserName()))
        {
            list.removeNode(followingUser); //Unfollows the following user
            followersList.removeNode(followerUser); //Removes the follower from the followers list of the following User
            followingUser.remFollowerCount(); //Decrements the follower count by 1 since the user is no longer following the person
            followerUser.remFollowCount(); //Decrements the follow count of the user who was following
        }

        DSALinkedList posts = followingUser.getPosts(); //Gets all the posts from the user
        Iterator postsIter = posts.iterator();

        while(postsIter.hasNext()) //Second iterator to find follows for each user
        {
            Post postData = (Post)postsIter.next();
            DSALinkedList likeList = postData.getLikes();
            if(isLiking(followerUser.getUserName(),postData))
            {
                likeList.removeNode(followerUser); 
                postData.remUserLikeCount();
            }
        }
        UI.showMessage(followerUser.getUserName() + " has unfollowed " + followingUser.getUserName() +"!");
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
        User userOne = getUser(userName1); //get user that has userName1
        DSALinkedList follows = userOne.getFollows();
        return checkList(userName2, follows);
    }

    /***********************************************************************
    * SUBMODULE: isFollower()
    * IMPORTS: userName1 (Object), userName2 (Object)
    * EXPORTS: state (boolean)
    * ASSERTION: Checks if userName 2 is in userOne's followers list
    * **********************************************************************/
    public boolean isFollower(Object userName1, Object userName2)
    {
        User userOne = getUser(userName1); //get user that has userName1
        DSALinkedList followers = userOne.getFollowers();
        return checkList(userName2, followers);
    }

    /***********************************************************************
    * SUBMODULE: isLiking()
    * IMPORTS: userName1 (Object), userName2 (Object), inPost (Object)
    * EXPORTS: state (boolean)
    * ASSERTION: Checks if userName 2 has liked userOne's post
    * **********************************************************************/
    public boolean isLiking(Object checkUser, Post post)
    {
        DSALinkedList likes = post.getLikes();
        return checkList(checkUser, likes);
    }

    /************************************************************************************************
    * SUBMODULE: displayUserInfo()
    * IMPORTS: userName (Object)
    * EXPORTS: none
    * ASSERTION: Displays user's info including Follows, Followers and Posts with who liked each post
    * ***********************************************************************************************/
    public DSAQueue displayUserInfo(Object userName, DSAQueue queue)
    {
        User mainUser = getUser(userName); //Main user that will be displayed

        DSALinkedList followsList = mainUser.getFollows(); //List of follows for the main user
        queue.enqueue("\n--==" + userName + " Info==--"); //Main user's Info displayed starts here
        queue.enqueue("\n" + mainUser.getFollowCount() + " Follows: ");
        exportList(followsList, queue);

        DSALinkedList followersList = mainUser.getFollowers(); //List of followers of the main user
        queue.enqueue("\n\n" + mainUser.getFollowersCount() + " Followers: ");
        exportList(followersList, queue);
        
        DSALinkedList posts = mainUser.getPosts(); //Gets all the posts from the main user
        Iterator postsIter = posts.iterator();
        queue.enqueue("\n\nPosts:\n");
        while(postsIter.hasNext()) //Third iterator for the posts of the main user
        {
            Post curPost = (Post)postsIter.next(); 
            queue.enqueue("\n" + curPost.getPostData() + "\n" + curPost.getLikeCount() + " Likers: "); //Gets whatever the post contains as a String
            DSALinkedList likeList = curPost.getLikes(); //Gets the likes of the curPost
            exportList(likeList, queue);
    
            if(postsIter.hasNext()) //After one post is complete
            {
                queue.enqueue("\n"); // add a comma after each userName in the list
            }
        }
        queue.enqueue("\n");
        return queue;
    }

    /***********************************************************************
    * SUBMODULE: displayAsList()
    * IMPORTS: none
    * EXPORTS: none
    * ASSERTION: Displays an adjacency List
    * **********************************************************************/
    public DSAQueue displayAsList(DSAQueue queue)
    {
        Iterator iter = users.iterator();
        queue.enqueue("\nCURRENT NETWORK STATUS:\n\n"); //Current network status
        while(iter.hasNext()) //First iterator to find the user
        {
            User user = (User)iter.next(); //The next thing found is a user
            queue.enqueue(user.getUserName() +" follows: "); //Current user to indicate all follows related to this user
            DSALinkedList list = user.getFollows(); //List of follows for current user
            exportList(list, queue);
            queue.enqueue("\n"); //After one user is completely shown, move to a new line for the next one
        }
        return queue;
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
                        double cbFactor = curPost.getClickBait(); //Click bait factor
                        double newProb = likeProb*cbFactor;
                        if(newProb >= 1.0)
                        {
                            newProb = 1.0;
                        }
                        if(Math.random() <= newProb) 
                        {
                            curPost.addLike(userTwo); //Adds a like to the post if probability is met
                        }
                        //If the userTwo likes userThree's post AND userOne is following userTwo
                        if(isLiking(userTwo.getUserName(), curPost) && isFollowing(userOne.getUserName(), userTwo.getUserName()))
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

    /*********************************************************
    * SUBMODULE: checkList()
    * IMPORTS: none
    * EXPORTS: queue (DSAqueue)
    * ASSERTION: Checks the list provided if userName 2 is in
    * ********************************************************/
    public boolean checkList(Object checkUser, DSALinkedList list)
    {
        boolean state = false;
        Iterator iter = list.iterator();

        while (iter.hasNext())
        {
            User iterUser = (User)iter.next();
            if(iterUser.getUserName().equals(checkUser)) //If the userName of user one is equal to userName2 which is another user then isFollowing
            {
                state = true;
            }
        }
        return state;
    }

    /************************************************************
    * SUBMODULE: exportList()
    * IMPORTS: list (DSALinkedList)
    * EXPORTS: queue (DSAqueue)
    * ASSERTION: Displays a list with each element separated by ,
    * ***********************************************************/
    public void exportList(DSALinkedList list, DSAQueue queue)
    {
        Iterator iter = list.iterator();
        while (iter.hasNext())
        {
            User user = (User)iter.next();
           
            if(iter.hasNext()) //Only if there is another element/person after
            {
               queue.enqueue(user.getUserName() +", "); // add a comma after each userName in the list
            }
            else
            {
                queue.enqueue(user.getUserName());
            }
        }
    }

    /*************************************************************************************
    * SUBMODULE: exportNetwork()
    * IMPORTS: none
    * EXPORTS: queue (DSAqueue)
    * ASSERTION: exports all follows in network to a queue in same format as a networkFile
    * ************************************************************************************/
    public DSAQueue exportNetwork() //Exports a queue in order for file writing
    {
        DSAQueue queue = new DSAQueue();

        Iterator iter = users.iterator();
        while (iter.hasNext())
        {
            User user = (User)iter.next();
            String userName = user.getUserName();
            queue.enqueue(userName);
            queue.enqueue("\n");
        }

        Iterator followIter = users.iterator();
        while (followIter.hasNext())
        {
            User user = (User)followIter.next();
            String followerName = user.getUserName();
            DSALinkedList list = user.getFollows(); //List of follows for current user
            Iterator linkIter = list.iterator(); 

            while(linkIter.hasNext()) //Second iterator to find follows for each user
            {
                User followingUser = (User)linkIter.next();
                String followingName = followingUser.getUserName();
                queue.enqueue(followingName + ":" + followerName + "\n");
            }
        }
        return queue;
    } 

    /***************************************************************
    * SUBMODULE: displayStats()
    * IMPORTS: none
    * EXPORTS: queue (DSAqueue)
    * ASSERTION: exports stats on the current state of the network
    * **************************************************************/
    public void displayStats(DSAQueue queue)
    {
        User [] userArray; //Array of users for sorting
        Post [] postArray; //Array of posts for sorting
        int topUsers = 5; //array number for the for loop of users to go to when displaying
        int topPosts = 5; //array number for the for loop of posts to go to when displaying
        queue.enqueue("\nCURRENT NETWORK STATISTICS\n");
        userArray = putUserArray(); //Puts all users in linked list into an array
        sortUser(userArray); //Sorts the array in terms of popularity (High - Low)
        if (userCount < topUsers) //In case there aren't 5 users the for loop will go to the amount of users currently
        {
            topUsers = userCount;
        }
        queue.enqueue("\nTop 5 most popular users: \n\n");
        for(int i = 0; i < topUsers; i++)
        {
            User user = (User)userArray[i];
            queue.enqueue((i+1) + ". " + user.getUserName() + " with " + user.getFollowersCount() + " followers\n");
        }
        postArray = putPostArray(); //Puts All posts of network into array
        sortPosts(postArray); //Sorts the Posts in terms of popularity (High - Low)
        if (postCount < topPosts) //In case there aren't 5 posts the for loop will go to the amount of posts currently
        {
            topPosts = postCount;
        }
        queue.enqueue("\nTop 5 most popular posts: \n\n");
        for(int i = 0; i < topPosts; i++)
        {
            Post post = (Post)postArray[i];
            queue.enqueue((i+1) + ". " +post.getPoster() + ": " + post.getPostData() + " has " + post.getLikeCount() + " likes\n");
        }
        //The next section is to display the most popular user and all their information
        User popUser = (User)userArray[0];
        DSALinkedList followsList = popUser.getFollows(); // Gets who they follow
        DSALinkedList followersList = popUser.getFollowers(); //Gets who the followers are
        DSALinkedList postsList = popUser.getPosts(); //Gets all there posts 
        queue.enqueue("\n------======Most Popular User======------\n");
        queue.enqueue("\n" + popUser.getUserName() + "\n");
        queue.enqueue("\n" + popUser.getFollowCount() + " Follows: ");
        exportList(followsList, queue);
        queue.enqueue("\n\n" + popUser.getFollowersCount() + " Followers: ");
        exportList(followersList, queue);
        Iterator postsIter = postsList.iterator();
        queue.enqueue("\n\nPosts:\n");
        while(postsIter.hasNext()) //Iterates over all the posts of the person and displays all related information
        {
            Post curPost = (Post)postsIter.next(); 
            queue.enqueue("\n" + curPost.getPostData() + "\n" + curPost.getLikeCount() + " Likers: "); //Gets whatever the post contains as a String
            DSALinkedList likeList = curPost.getLikes(); //Gets the likes of the curPost
            exportList(likeList, queue);
    
            if(postsIter.hasNext()) //After one post is complete
            {
                queue.enqueue("\n"); // add a comma after each userName in the list
            }
        }
        queue.enqueue("\n");
    }

    /***************************************************************
    * SUBMODULE: putUserArray
    * IMPORTS: none
    * EXPORTS: userArray (Array of Users)
    * ASSERTION: Puts all users in the network into an array
    * **************************************************************/
    public User[] putUserArray()
    {
        User [] userArray = new User[userCount];
        Iterator usersIter = users.iterator(); 
        int i = 0;
        while(usersIter.hasNext()) //Goes through the users in the network
        {
            User user = (User)usersIter.next();
            userArray[i] = user;
            i++;
        }
        return userArray;
    }

    /***************************************************************
    * SUBMODULE: sortUser
    * Wrapper Method for merging but for Users
    * **************************************************************/
    public void sortUser(User[] array)
    {
        mergeSortRecurseU(array, 0, userCount-1);
    }

    /***************************************************************
    * SUBMODULE: mergeSortRecurseU
    * Method for recursively calling merge but for Users
    * **************************************************************/
    private void mergeSortRecurseU(User[] array, int leftIdx, int rightIdx)
    {
        if (leftIdx < rightIdx)
        {
            int midIdx = (leftIdx + rightIdx) / 2;

            mergeSortRecurseU(array, leftIdx, midIdx); //Recurse: Sort left half of the current sub-array
            mergeSortRecurseU(array, midIdx+1, rightIdx); //Recurse: Sort right half of the current sub-array

            mergeUser(array, leftIdx, midIdx, rightIdx); //Merge the left and right sub arrays
        }
    }

    /***************************************************************
    * SUBMODULE: mergeUser
    * Method for merging but for Users
    * **************************************************************/
    private void mergeUser(User[] array, int leftIdx, int midIdx, int rightIdx)
    {
        User tempArr[] = new User[rightIdx - leftIdx + 1];
        int ii = leftIdx; //index for the 'front' of left sub array
        int jj = midIdx + 1; //index for the 'front' of right sub array
        int kk = 0; //index for next free elemen leftValue in tempArr

        while ((ii <= midIdx) && (jj <= rightIdx)) //merge sub arrays into tempArr
        {
            if (array[ii].getFollowersCount() >= array[jj].getFollowersCount())
            {
                tempArr[kk] = array[ii]; //take from left sub-array
                ii = ii + 1;
            }
            else
            {
                tempArr[kk] = array[jj]; //take from right sub-array
                jj = jj + 1;
            }
            kk = kk + 1;
        }
        for (ii = ii; ii <= midIdx; ii++)
        {
            tempArr[kk] = array[ii];
            kk = kk + 1;
        }
        for (jj = jj; jj <= rightIdx; jj++)
        {
            tempArr[kk] = array[jj];
            kk = kk + 1;
        }
        for (kk = leftIdx; kk <= rightIdx; kk++)
        {
            array[kk] = tempArr[kk - leftIdx];
        }
    }

    /***************************************************************
    * SUBMODULE: putPost
    * IMPORTS: none
    * EXPORTS: postArray (Array of Posts)
    * ASSERTION: Puts all posts in the network into an array
    * **************************************************************/
    public Post[] putPostArray()
    {
        Post [] postArray = new Post[postCount];
        Iterator usersIter = users.iterator(); 
        int i = 0;
        while(usersIter.hasNext()) //Goes through the users in the network
        {
            User user = (User)usersIter.next();
            DSALinkedList postList = user.getPosts();
            Iterator postsIter = postList.iterator();
            while(postsIter.hasNext())
            {
                Post post = (Post)postsIter.next();
                postArray[i] = post;
                i++;
            }
        }
        return postArray;
    }

    /***************************************************************
    * SUBMODULE: sortPosts
    * Wrapper Method for merge sort but for post class
    * **************************************************************/
    public void sortPosts(Post[] array)
    {
        mergeSortRecurseP(array, 0, postCount-1);
    }

    /***************************************************************
    * SUBMODULE: mergeSortRecurseP
    * Method for recursively sorting Posts
    * **************************************************************/
    private void mergeSortRecurseP(Post[] array, int leftIdx, int rightIdx)
    {
        if (leftIdx < rightIdx)
        {
            int midIdx = (leftIdx + rightIdx) / 2;

            mergeSortRecurseP(array, leftIdx, midIdx); //Recurse: Sort left half of the current sub-array
            mergeSortRecurseP(array, midIdx+1, rightIdx); //Recurse: Sort right half of the current sub-array

            mergePost(array, leftIdx, midIdx, rightIdx); //Merge the left and right sub arrays
        }
    }

    /***************************************************************
    * SUBMODULE: mergePosts
    * Method for merging but for Posts
    * **************************************************************/
    private void mergePost(Post[] array, int leftIdx, int midIdx, int rightIdx)
    {
        Post tempArr[] = new Post[rightIdx - leftIdx + 1];
        int ii = leftIdx; //index for the 'front' of left sub array
        int jj = midIdx + 1; //index for the 'front' of right sub array
        int kk = 0; //index for next free elemen leftValue in tempArr

        while ((ii <= midIdx) && (jj <= rightIdx)) //merge sub arrays into tempArr
        {
            if (array[ii].getLikeCount() >= array[jj].getLikeCount())
            {
                tempArr[kk] = array[ii]; //take from left sub-array
                ii = ii + 1;
            }
            else
            {
                tempArr[kk] = array[jj]; //take from right sub-array
                jj = jj + 1;
            }
            kk = kk + 1;
        }
        for (ii = ii; ii <= midIdx; ii++)
        {
            tempArr[kk] = array[ii];
            kk = kk + 1;
        }
        for (jj = jj; jj <= rightIdx; jj++)
        {
            tempArr[kk] = array[jj];
            kk = kk + 1;
        }
        for (kk = leftIdx; kk <= rightIdx; kk++)
        {
            array[kk] = tempArr[kk - leftIdx];
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
        private int postCount;
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
            postCount = 0;
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
                UI.showMessage(this.getUserName() + " is now following " + user.getUserName() + "!");
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
            postCount++;
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

        public int getPostCount()
        {
            return this.postCount;
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
        private double clickBait;
        private DSALinkedList likes;
        
        public Post (String inPostData, String inPoster, double inClickBait)
        {
            likes = new DSALinkedList();
            poster = inPoster;
            postData = inPostData;
            likeCount = 0;
            clickBait = inClickBait;
        }

        public void addLike(User user)
        {
           if (!(isLiking(user.getUserName(), this))) //Ensuring a person can only like a post once
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

        public double getClickBait()
        {
            return this.clickBait;
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
}
