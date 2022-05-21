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

--2. Queries

getFriendNames :: DB -> UserName -> [RealName]
getPosts 	   :: DB -> To -> [Post]
listGroups 	   :: DB -> UserName -> [Group]
suggestFriends :: DB -> User -> Int -> [User]

---- IMPLEMENTATIONS ----