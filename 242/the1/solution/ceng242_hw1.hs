module HW1 where

--Data
------

type RealName = String
type UserName = String
type GroupName = String
type Message = String

data Post    = Post UserName Message deriving (Show, Eq)
data To 	 = UserID UserName | GroupID GroupName deriving (Show, Eq)
data User    = User UserName RealName [UserName] [Post] deriving (Show, Eq)
data Group   = Group GroupName [UserName] [Post] deriving (Show, Eq)
data DB		 = DB [User] [Group] deriving (Show, Eq)

--1. Commands

newUser      :: DB -> User -> DB
addFriend    :: DB -> UserName -> UserName -> DB
sendPost 	 :: DB -> UserName -> Message -> [To] -> DB
newGroup 	 :: DB -> GroupName -> DB
addMember 	 :: DB -> GroupName -> UserName -> DB
removeMember :: DB -> GroupName -> UserName -> DB

--2. Queries:

getFriendNames :: DB -> UserName -> [RealName]
getPosts 	   :: DB -> To -> [Post]
listGroups 	   :: DB -> UserName -> [Group]
suggestFriends :: DB -> User -> Int -> [User]

---- IMPLEMENTATIONS ----
inListUser:: [User] -> String -> Bool
inListUser [] _ = False
inListUser ((User usr rln friends posts):xs) user = if user==usr then True else inListUser xs user

inListGroup:: [Group] -> String -> Bool
inListGroup [] _ = False
inListGroup ((Group grp members posts):xs) group = if grp==group then True else inListGroup xs group

isMember::[UserName]-> String -> Bool
isMember [] _ = False
isMember (x:xs) userName = if x==userName then True else isMember xs userName

findRealName::[User]->String->String
findRealName [] _ = "NoUserFound"
findRealName ((User usr rln friends posts):xs) userName = if usr==userName then rln else findRealName xs userName

findFriends::[User] -> [String] -> [String]
findFriends users [] = []
findFriends users (x:xs) = findRealName users x : findFriends users xs

findUserFriends:: [User]->String ->[String]
findUserFriends [] _ = []
findUserFriends ((User usr rln friends posts):xs) userName = if userName==usr
													  then friends
													  else findUserFriends xs userName

addUserFriend [] _ _ = []
addUserFriend ((User usr rln friends posts):xs) user1 user2 = if usr == user1
                                                              then (User usr rln (user2:friends) posts): addUserFriend xs user1 user2
															  else
															      if usr == user2
																  then (User usr rln (user1:friends) posts) : addUserFriend xs user1 user2
																  else (User usr rln friends posts) : addUserFriend xs user1 user2
																 
addUserMember::[Group] -> String -> String -> [Group]
addUserMember [] _ _ = []
addUserMember ((Group grp members posts):xs) groupName userName = if grp==groupName
																  then 
																	  if isMember members userName
																	  then (Group grp members posts): addUserMember xs groupName userName
																	  else (Group grp (userName:members) posts): addUserMember xs groupName userName
																  else
																	  (Group grp members posts): addUserMember xs groupName userName
															  
removeUserMember::[Group] -> String -> String -> [Group]
removeUserMember [] _ _ = []
removeUserMember ((Group grp members posts):xs) groupName userName = if grp == groupName
																  then 
																       if isMember members userName
																	   then (Group grp (filter (/=userName) members) posts): removeUserMember xs groupName userName
																	   else (Group grp members posts): removeUserMember xs groupName userName
																  else 
																       (Group grp members posts): removeUserMember xs groupName userName

checkMessage::[Post]->Post->Bool
checkMessage [] _ = False
checkMessage (x:xs) post = if (x==post) then True else (checkMessage xs post)

addPostUser::User->Post->User
addPostUser (User usr rln friends posts) message = (User usr rln friends (message:posts))

addPostGroup::Group->Post->Group
addPostGroup (Group grp members posts) message = (Group grp members (message:posts))
																   
messageUser::[User]->String->String->[UserName]->[User]
messageUser _ _ _ [] = []
messageUser users sender message (recpt:recpts) = if (checkMessage (getUserPosts (findUserString users recpt)) (Post sender message))
                                                  then (findUserString users recpt): (messageUser users sender message recpts)
												  else  (addPostUser (findUserString users recpt) (Post sender message)): (messageUser users sender message recpts)
																   
messageGroup::[Group]->String->String->[GroupName]->[Group]
messageGroup _ _ _ [] = []
messageGroup groups sender message (recpt:recpts) = if (checkMessage (getGroupPosts(findGroupString groups recpt)) (Post sender message))
													then (findGroupString groups recpt): (messageGroup groups sender message recpts)
													else (addPostGroup (findGroupString groups recpt) (Post sender message)):(messageGroup groups sender message recpts)
														
predicate::To -> Bool
predicate (UserID _) = True
predicate (GroupID _) = False

getUserPosts::User->[Post]
getUserPosts (User usr rln friends posts) = posts

getUserFriends::User->[UserName]
getUserFriends (User usr rln friends posts) = friends

getGroupPosts::Group->[Post]
getGroupPosts (Group grp members posts)= posts

getGroupMembers::Group->[UserName]
getGroupMembers (Group grp members posts)= members

findUser::[User]->To->User
findUser [] _ = (User "" "" [] [])
findUser (x:xs) (GroupID _) = (User "" "" [] [])
findUser [(User usr rln friends posts)] (UserID userName) = if usr==userName then (User usr rln friends posts) else (User "" "" [] [])
findUser ((User usr rln friends posts):xs) (UserID userName) = if usr==userName then (User usr rln friends posts) else findUser xs (UserID userName)

findUserString::[User]->String->User
findUserString [] _ = (User "" "" [] [])
findUserString [(User usr rln friends posts)] userName = if usr==userName then (User usr rln friends posts) else (User "" "" [] [])
findUserString ((User usr rln friends posts):xs) userName = if usr==userName then (User usr rln friends posts) else findUserString xs userName

findGroup::[Group]->To->Group
findGroup [] _ = (Group "" [] [])
findGroup (x:xs) (UserID _) = (Group "" [] [])
findGroup [(Group grp members posts)] (GroupID groupName) = if grp==groupName then (Group grp members posts) else (Group "" [] [])
findGroup ((Group grp members posts):xs) (GroupID groupName) = if grp==groupName then (Group grp members posts) else findGroup xs (GroupID groupName)

findGroupString::[Group]->String->Group
findGroupString [] _ = (Group "" [] [])
findGroupString [(Group grp members posts)] groupName = if grp==groupName then (Group grp members posts) else (Group "" [] [])
findGroupString ((Group grp members posts):xs) groupName = if grp==groupName then (Group grp members posts) else findGroupString xs groupName

findInGroups::[Group]->UserName->[Group]
findInGroups [] _ = []
findInGroups ((Group grp members posts):xs) userName = if isMember members userName then (Group grp members posts): findInGroups xs userName else findInGroups xs userName

addGroupMembers::[Group]->[String]->[UserName]
addGroupMembers _ [] = []
addGroupMembers groups (x:xs) = (getGroupMembers(findGroupString groups x))++ (addGroupMembers groups xs)

filterList::[UserName]->[UserName]
filterList [] = []
filterList (x:xs) = if (count x (x:xs))>1 then filterList xs else x:filterList xs

count :: Eq a => a -> [a] -> Int
count x =  length . filter (==x)

findMutuals::[User]->User->Int->[User]
findMutuals [] _ _ = []
findMutuals ((User usr1 rln1 friends1 posts1):xs) (User usr rln friends posts) limit = if (usr1==usr) || (isMember friends usr1)
																					   then findMutuals xs (User usr rln friends posts) limit
																					   else
																					       if (compareLists friends1 friends >= limit )
																						   then (User usr1 rln1 friends1 posts1):findMutuals xs (User usr rln friends posts) limit
																						   else findMutuals xs (User usr rln friends posts) limit

compareLists::[String]->[String]->Int
compareLists _ [] = 0
compareLists friends (x:xs) = 	if (isMember friends x)
								then 1+ compareLists friends xs
								else compareLists friends xs

newUser (DB users groups) (User usr rln friends posts) = if inListUser users usr then (DB users groups) else (DB ((User usr rln friends posts):users) groups)

addFriend (DB users groups) user1 user2 = (DB (addUserFriend users user1 user2) groups)

notRecptUser::[User]->[String]->[User]
notRecptUser [] _ = []
notRecptUser ((User usr rln friends posts):xs) recpts = if isMember recpts usr then notRecptUser xs recpts else (User usr rln friends posts):(notRecptUser xs recpts)

notRecptGroup::[Group]->[String]->[Group]
notRecptGroup [] _ = []
notRecptGroup ((Group grp members posts):xs) recpts = if isMember recpts grp then notRecptGroup xs recpts else (Group grp members posts):(notRecptGroup xs recpts)

sendPost (DB users groups) user1 mess to = (DB ((messageUser users user1 mess userRecpts) ++ (notRecptUser users userRecpts)) ((messageGroup groups user1 mess groupRecpts)++ (notRecptGroup groups groupRecpts)))
											where
												userRecpts = filterList([usr | (UserID usr )<- to ] ++ filterList (addGroupMembers groups [grp | GroupID grp <- to]))
												groupRecpts = filterList([grp | (GroupID grp )<- to])

newGroup (DB users groups) groupName = if inListGroup groups groupName then (DB users groups) else (DB users ((Group groupName [] []):groups))

addMember (DB users groups) groupName userName = (DB users (addUserMember groups groupName userName))

removeMember (DB users groups) groupName userName = (DB users (removeUserMember groups groupName userName))

getFriendNames (DB users groups) userName = findFriends users (findUserFriends users userName)

getPosts (DB users groups) user = if predicate user then getUserPosts (findUser users user) else getGroupPosts (findGroup groups user)

listGroups (DB users groups) userName = findInGroups groups userName


suggestFriends (DB users groups) (User usr rln friends posts) num = findMutuals users (User usr rln friends posts) num

