
module HW2(FeatureStruct, FeatureTerm(Simple, Struct), emptyfs, getpath, addpath, delpath, union, intersect) where

data FeatureTerm = Simple String | Struct FeatureStruct
data FeatureStruct = FS [(String, FeatureTerm)]

emptyfs     :: FeatureStruct
getpath     :: FeatureStruct -> [String] -> Maybe FeatureTerm
addpath     :: FeatureStruct -> [String] -> FeatureTerm -> FeatureStruct
delpath     :: FeatureStruct -> [String] -> FeatureStruct
union       :: FeatureStruct -> FeatureStruct -> Maybe FeatureStruct
intersect   :: FeatureStruct -> FeatureStruct -> FeatureStruct

-- DO NOT MODIFT ABOVE

instance Show FeatureTerm where
    -- Implement it here
    show (Simple string) = "\"" ++ string ++ "\""
    show (Struct featureStruct) =(show featureStruct)

instance Show FeatureStruct where
    -- Implement it here
    show (FS []) = "[]"
    show (FS a) = "[" ++ (showList1 a) ++ "]"

instance Eq FeatureStruct where
    -- Implement it here
    (==) a b  = (show a == show b)


showList1 [(string,(Simple str))] = string ++ "=" ++(show (Simple str))
showList1 [(string,(Struct fs))] = string ++  "=>" ++ (show (Struct fs))
showList1 ((string,(Simple str)):xs) = string ++ "=" ++ (show (Simple str)) ++ ", " ++ (showList1 xs)
showList1 ((string,(Struct fs)):xs) = string ++  "=>" ++ (show (Struct fs)) ++ ", " ++ (showList1 xs)

emptyfs = FS []

goDeeper (Simple a) paths = Just (Simple a)
goDeeper (Struct a) [] = Just (Struct a)
goDeeper (Struct a) paths = getpath a paths

getpath (FS []) _ = Nothing
getpath (FS ((a,b):xs) ) [] = Nothing
getpath (FS ((str,featureTerm):xs)) (path:paths) =  if (str==path)
                                                    then goDeeper featureTerm paths
                                                    else getpath (FS xs) (path:paths)


addpath (FS list) paths ft = FS (addPathToList list paths ft)

addPathToList:: [(String,FeatureTerm)] -> [String] -> FeatureTerm -> [(String,FeatureTerm)]
addPathToList _ [] _ = []
addPathToList [] [path] ft = [(path,ft)]
addPathToList [] (path:paths) ft = [(path,Struct (FS((addPathToList [] paths ft))))]
addPathToList ((str,featureTerm):xs) (path:paths) ft =  if(str==path)
                                                        then ([(path,(addDeeper featureTerm paths ft))]++xs)
                                                        else
                                                            if (str<path)
                                                            then ((str,featureTerm):(addPathToList (xs) (path:paths) ft))
                                                            else ( (path,ft) :(str,featureTerm):xs)

addDeeper:: FeatureTerm -> [String] -> FeatureTerm -> FeatureTerm
addDeeper _ [] ft = ft
addDeeper (Struct (FS a))  (path:paths) ft = Struct (FS (addPathToList a (path:paths) ft))


delpath (FS a) b = FS (deletePathFromList a b)

deletePathFromList:: [(String,FeatureTerm)] -> [String] -> [(String,FeatureTerm)]

deletePathFromList a [] = a
deletePathFromList [] _ = []
deletePathFromList ((str,featureTerm):xs) [path]= if(str==path) then (xs) else ((str,featureTerm):(deletePathFromList xs [path]))
deletePathFromList ((str,featureTerm):xs) (path:paths)= if(str==path)
                                                        then ([(path,(deleteDeeper featureTerm paths))]++xs)
                                                        else
                                                            if(str<path)
                                                            then ((str,featureTerm):deletePathFromList xs (path:paths))
                                                            else ((str,featureTerm):xs)

deleteDeeper:: FeatureTerm -> [String] -> FeatureTerm
deleteDeeper a [] = a
deleteDeeper (Struct (FS a)) paths = Struct (FS (deletePathFromList a paths))


union (FS x) (FS y) =  unionList x y


unionList x y = if (FS (uniteBoth x y) == FS (uniteBoth y x))
                then Just (FS (uniteBoth x y))
                else Nothing

uniteBoth x [] = x
uniteBoth [] y = y
uniteBoth ((x,Struct (FS listOfx)):xs) ((y,Struct (FS listOfy)):ys)=    if (x==y)
                                                                        then ((x,Struct (FS (uniteBoth listOfx listOfy))) : (uniteBoth xs ys))
                                                                        else
                                                                            if(x<y)
                                                                            then ((x,(Struct (FS listOfx))): (uniteBoth xs ((y,Struct (FS listOfy)):ys)))
                                                                            else ((y,(Struct (FS listOfy))): (uniteBoth ((x,Struct (FS listOfx)):ys)) ys)
uniteBoth ((x,ftx):xs) ((y,fty):ys) =   if (x==y)
                                        then ((x,ftx): (uniteBoth xs ys))
                                        else
                                            if (x<y)
                                            then ((x,ftx): uniteBoth xs ((y,fty):ys))
                                            else ((y,fty): uniteBoth ((x,ftx):xs) ys)











intersect (FS x) (FS y) = FS (intersectLists x y)

intersectLists _ [] = []
intersectLists [] _ = []
intersectLists ((x,ftx):xs) ((y,fty):ys) =  if(x<y)
                                            then intersectLists xs ((y,fty):ys)
                                            else
                                                if(y<x)
                                                then intersectLists ((x,ftx):xs) ys
                                                else
                                                    if (arenotEqual ftx fty )
                                                    then ([] ++ (intersectLists xs ys))
                                                    else ([(x,(intersectDeeper ftx fty))] ++ (intersectLists xs ys))

arenotEqual (Simple a)(Simple b) = if (a/=b) then True else False
arenotEqual _ _ = False


intersectDeeper:: FeatureTerm -> FeatureTerm -> FeatureTerm
intersectDeeper (Simple a)(Simple b) = (Simple a)
intersectDeeper (Simple a) (Struct (FS b)) = Struct (FS [])
intersectDeeper (Struct (FS a)) (Simple b) = Struct (FS [])
intersectDeeper (Struct (FS [])) _= Struct (FS [])
intersectDeeper _ (Struct (FS []))= Struct (FS [])
intersectDeeper (Struct (FS a)) (Struct (FS b)) = Struct (FS (intersectLists a b))
