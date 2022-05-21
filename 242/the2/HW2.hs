
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
instance Show FeatureStruct where
    -- Implement it here
instance Eq FeatureStruct where
    -- Implement it here

