package ceng.ceng351.stackoverflowdb;


import java.sql.*;

/**
 * Created by hakan on 12/16/2016.
 */
public class STACKOVERFLOWDB implements ISTACKOVERFLOWDB{

    private static String user = "e2098812";
    private static String password = "*REDACTED*";
    private static String host = "*REDACTED*";
    private static String database = "db2098812";
    private static int port = 3306;
    private Connection con;


    @Override
    public void initialize() {
        String url = "jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.con =  DriverManager.getConnection(url, this.user, this.password);


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int createTables() {
        int insertNotSucc=0;
        int insertedNum=4;
        String createUserTable="create table user (" +
                "userID char(10)," +
                "username char(30)," +
                "registrationDate date," +
                "lastLoginDate date,"+
                "primary key (userID))";

        String createArticleTable = "create table article (" +
                "articleID char(10)," +
                "userID char(10)," +
                "name char(80)," +
                "description char(130)," +
                "date date,"+
                "rating int," +
                "primary key (articleID),"+
                "foreign key (userID) references user(userID) on delete cascade)";

        String createCommentTable = "create table comment("+
                "commentID char(10),"+
                "articleID char(10),"+
                "userID char(10),"+
                "message char(130),"+
                "date date,"+
                "rating int,"+
                "primary key (commentID),"+
                "foreign key (articleID) references article(articleID) on delete cascade,"+
                "foreign key (userID) references user(userID) on delete cascade) ";

        String createReputationTable = "create table reputation("+
                "reputationID char(10),"+
                "userID char(10),"+
                "weeklyReputation int,"+
                "monthlyReputation int,"+
                "yearlyReputation int,"+
                "alltimeReputation int,"+
                "primary key (reputationID),"+
                "foreign key (userID) references user(userID) on delete cascade)";

        try {
            Statement statement = this.con.createStatement();

            insertNotSucc += statement.executeUpdate(createUserTable);

            insertNotSucc += statement.executeUpdate(createArticleTable);

            insertNotSucc += statement.executeUpdate(createCommentTable);

            insertNotSucc += statement.executeUpdate(createReputationTable);

            statement.close();

        } catch (SQLException e){
            e.printStackTrace();
        }
        return insertedNum-insertNotSucc;
    }

    @Override
    public int dropTables() {

        int result;
        int numberofTablesDropped = 0;

        String dropUser = "drop table user";
        String dropArticle = "drop table article";
        String dropComment = "drop table comment";
        String dropRep = "drop table reputation";

        try {
            Statement statement = this.con.createStatement();

            result = statement.executeUpdate(dropRep);
            if(result == 0) numberofTablesDropped++;

            result = statement.executeUpdate(dropComment);
            if(result == 0) numberofTablesDropped++;

            result = statement.executeUpdate(dropArticle);
            if(result == 0) numberofTablesDropped++;

            result = statement.executeUpdate(dropUser);
            if(result == 0) numberofTablesDropped++;


        } catch (SQLException e) {
            e.printStackTrace();            // TODO
        }

        return numberofTablesDropped;
    }

    String parseStrings(String input)
    {
        if(input.isEmpty()) return "null";
        else return  input;
    }

    @Override
    public int insertUser(User[] users) {
        int success=0;
        for (int i = 0; i < users.length; i++){
            User curUser = users[i];

            String sql = "insert into user values(?,?,?,?)";
            try {

                PreparedStatement statement = this.con.prepareStatement(sql);
                statement.setString(1,parseStrings(curUser.getuserID()));
                statement.setString(2,parseStrings(curUser.getusername()));
                statement.setString(3,parseStrings(curUser.getregistrationDate()));
                statement.setString(4,parseStrings(curUser.getlastLoginDate()));

                int result = statement.executeUpdate();
                if (result==1) success++;
            } catch (SQLException e)
            {
                e.printStackTrace();
                continue;
            }
        }

        return success;
    }

    @Override
    public int insertArticle(Article[] articles) {

        int success=0;
        for (int i = 0; i < articles.length; i++){
            Article curArticle = articles[i];

            String sql = "insert into article values (?,?,?,?,?,?)";
            try {

                PreparedStatement statement = this.con.prepareStatement(sql);
                statement.setString(1,parseStrings(curArticle.getarticleID()));
                statement.setString(2,parseStrings(curArticle.getuserID()));
                statement.setString(3,parseStrings(curArticle.getname()));
                statement.setString(4,parseStrings(curArticle.getdescription()));
                statement.setString(5,parseStrings(curArticle.getdate()));
                statement.setInt(6,curArticle.getrating());
                int result = statement.executeUpdate();
                if (result==1) success++;
            } catch (SQLException e)
            {
                e.printStackTrace();
                continue;
            }
        }

        return success;
    }

    @Override
    public int insertComment(Comment[] comments) {

        int success=0;
        for (int i = 0; i < comments.length; i++){
            Comment curComment = comments[i];

            String sql = "insert into comment values (?,?,?,?,?,?)";
            try {
                PreparedStatement statement = this.con.prepareStatement(sql);

                statement.setString(1,parseStrings(curComment.getcommentID()));
                statement.setString(2,parseStrings(curComment.getarticleID()));
                statement.setString(3,parseStrings(curComment.getuserID()));
                statement.setString(4,parseStrings(curComment.getmessage()));
                statement.setString(5,parseStrings(curComment.getdate()));
                statement.setInt(6,curComment.getrating());



                int result = statement.executeUpdate();
                if (result==1) success++;
            } catch (SQLException e)
            {
                e.printStackTrace();
                continue;
            }
        }
        return success;
    }

    @Override
    public int insertReputation(Reputation[] reputations) {

        int success=0;
        for (int i = 0; i < reputations.length; i++){
            Reputation curReputation = reputations[i];

            String sql = "insert into reputation values (? , ? , ? , ? , ? , ?)";
            try {

                PreparedStatement statement = this.con.prepareStatement(sql);
                statement.setString(1,parseStrings(curReputation.getreputationID()));
                statement.setString(2,parseStrings(curReputation.getuserID()));
                statement.setInt(3,curReputation.getweeklyReputation());
                statement.setInt(4,curReputation.getmonthlyReputation());
                statement.setInt(5,curReputation.getyearlyReputation());
                statement.setInt(6,curReputation.getalltimeReputation());

                int result = statement.executeUpdate();
                if (result==1) success++;
            } catch (SQLException e)
            {
                e.printStackTrace();
                continue;
            }
        }
        return success;
    }

    @Override
    public QueryResult.UsernameDateRatingResult[] getArticleHighestRating() {
        String query="select U.username, A.date, A.rating from user U, (select * from article A where A.articleID not in (select A1.articleID from article A1, article A2 where A1.rating<A2.rating))as A where A.userID=U.userID order by U.username";
        try{
            Statement statement = this.con.createStatement();
            ResultSet rs = statement.executeQuery(query);
            int size=0;

            if(rs.last()) {
                size = rs.getRow();
                rs.beforeFirst();
            }

            QueryResult.UsernameDateRatingResult[] result = new QueryResult.UsernameDateRatingResult[size];

            for(int i=0;rs.next();i++)
            {
                result[i] = new QueryResult.UsernameDateRatingResult(rs.getString("username"),rs.getString("date"),rs.getInt("rating"));
            }

            return result;
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return new QueryResult.UsernameDateRatingResult[0];
    }

    @Override
    public QueryResult.UsernameMessageRatingAlltimereputationResult[] getCommentLowestRating() {
        String query="select U.username, C2.message, C2.rating, R.alltimeReputation from (select * from (select * from comment C1 where C1.commentID not in (select C.commentID from comment C where C.message like \"%mysql%\")) as C2 order by C2.rating limit 1) as C2, user U, reputation R where C2.userID=U.userID and C2.userID=R.userID order by U.username";
        try{
            Statement statement = this.con.createStatement();
            ResultSet rs = statement.executeQuery(query);
            int size=0;

            if(rs.last()) {
                size = rs.getRow();
                rs.beforeFirst();
            }

            QueryResult.UsernameMessageRatingAlltimereputationResult[] result = new QueryResult.UsernameMessageRatingAlltimereputationResult[size];

            for(int i=0;rs.next();i++)
            {
                result[i] = new QueryResult.UsernameMessageRatingAlltimereputationResult(rs.getString("username"),rs.getString("message"),rs.getInt("rating"),rs.getInt("alltimeReputation"));
            }

            return result;
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return new QueryResult.UsernameMessageRatingAlltimereputationResult[0];
    }

    @Override
    public QueryResult.UseridUsernameRegistrationdateWeeklyreputationResult[] getUseridUsernameAfterGivenDate(String Date) {
        String query = "select U.userID, U.username, U.registrationDate, R.weeklyReputation from user U, reputation R where U.userID in (select U1.userID from user U1 where U1.registrationDate > \""+Date+"\" ) and U.userID=R.userID order by U.userID";
        try{
            Statement statement = this.con.createStatement();
            ResultSet rs = statement.executeQuery(query);
            int size=0;

            if(rs.last()) {
                size = rs.getRow();
                rs.beforeFirst();
            }

            QueryResult.UseridUsernameRegistrationdateWeeklyreputationResult[] result = new QueryResult.UseridUsernameRegistrationdateWeeklyreputationResult[size];

            for(int i=0;rs.next();i++)
            {
                result[i] = new QueryResult.UseridUsernameRegistrationdateWeeklyreputationResult(rs.getString("userID"),rs.getString("username"),rs.getString("registrationDate"),rs.getInt("weeklyReputation"));
            }

            return result;
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return new QueryResult.UseridUsernameRegistrationdateWeeklyreputationResult[0];
    }

    @Override
    public QueryResult.UsernameMessageRatingResult[] getUsernameMessageRatingMoreThanGivenRating(String message, int rating) {
        String query ="select U.username, C.message, C.rating from comment C, user U where C.message not like \"%"+message+"%\" and C.rating > "+ rating+" and C.userID=U.userID order by U.username";
        try{
            Statement statement = this.con.createStatement();
            ResultSet rs = statement.executeQuery(query);
            int size=0;

            if(rs.last()) {
                size = rs.getRow();
                rs.beforeFirst();
            }

            QueryResult.UsernameMessageRatingResult[] result = new QueryResult.UsernameMessageRatingResult[size];

            for(int i=0;rs.next();i++)
            {
                result[i] = new QueryResult.UsernameMessageRatingResult(rs.getString("username"),rs.getString("message"),rs.getInt("rating"));
            }

            return result;
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return new QueryResult.UsernameMessageRatingResult[0];
    }

    @Override
    public int MultiplyComment(String date) {
        String query = "update comment set rating = rating*2 where date > \""+date+"\"";
        try{
            Statement statement = this.con.createStatement();
            return statement.executeUpdate(query);
        }catch(SQLException e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public QueryResult.UseridUsernameLastlogindateResult[] getUsernameMessageRatingCommentedByGivenUser(String userID) {
        String query ="select distinct u.userID, u.username, u.lastLoginDate from comment b,user u where not exists ( select articleID from comment a where a.userID=\""+userID+"\" and not exists ( select articleID from comment c where b.userID=c.userID and c.userID<>\""+userID+"\" and a.articleID=c.articleID)) and u.userID = b.userID order by u.userID";
        try{
            Statement statement = this.con.createStatement();
            ResultSet rs = statement.executeQuery(query);
            int size=0;

            if(rs.last()) {
                size = rs.getRow();
                rs.beforeFirst();
            }

            QueryResult.UseridUsernameLastlogindateResult[] result = new QueryResult.UseridUsernameLastlogindateResult[size];

            for(int i=0;rs.next();i++)
            {
                result[i] = new QueryResult.UseridUsernameLastlogindateResult(rs.getString("userID"),rs.getString("username"),rs.getString("lastLoginDate"));
            }

            return result;
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return new QueryResult.UseridUsernameLastlogindateResult[0];
    }

    @Override
    public QueryResult.UsernameMessageRatingResult[] getNameUsernameDateRatingMoreThanGivenArticle(int rating, String articleID) {
        String query = "select U.username, C.message, C.rating from user U, comment C where C.rating > "+rating +" and articleID=\""+articleID+"\" and C.userID=U.userID order by U.username";
        try{
            Statement statement = this.con.createStatement();
            ResultSet rs = statement.executeQuery(query);
            int size=0;

            if(rs.last()) {
                size = rs.getRow();
                rs.beforeFirst();
            }

            QueryResult.UsernameMessageRatingResult[] result = new QueryResult.UsernameMessageRatingResult[size];

            for(int i=0;rs.next();i++)
            {
                result[i] = new QueryResult.UsernameMessageRatingResult(rs.getString("username"),rs.getString("message"),rs.getInt("rating"));
            }

            return result;
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return new QueryResult.UsernameMessageRatingResult[0];
    }

    @Override
    public QueryResult.NameUsernameDateRatingResult[] getUsernameDateRatingNotCommentedByAnyUser() {
        String query= "select A.name, U.username, A.date, A.rating from user U, article A, comment C where C.articleID=A.articleID and U.userID=C.userID and not exists (select C1.userID from comment C1 where C1.articleID=C.articleID and C1.userID!=C.userID) order by A.name";
        try{
            Statement statement = this.con.createStatement();
            ResultSet rs = statement.executeQuery(query);
            int size=0;

            if(rs.last()) {
                size = rs.getRow();
                rs.beforeFirst();
            }

            QueryResult.NameUsernameDateRatingResult[] result = new QueryResult.NameUsernameDateRatingResult[size];

            for(int i=0;rs.next();i++)
            {
                result[i] = new QueryResult.NameUsernameDateRatingResult(rs.getString("name"),rs.getString("username"),rs.getString("date"),rs.getInt("rating"));
            }

            return result;
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return new QueryResult.NameUsernameDateRatingResult[0];
    }

    @Override
    public QueryResult.UsernameDateRatingResult[] UsernameDateRatingHasHighestReputation(String date) {
        String query ="select U1.username, A.date, A.rating from (select U.userID, U.username, R.weeklyReputation from user U, reputation R where U.userID = R.userID and R.weeklyReputation = (select max(weeklyReputation) from reputation)) as U1, (select * from article where date < \""+date+"\") as A where A.userID = U1.userID order by U1.username";
        try{
            Statement statement = this.con.createStatement();
            ResultSet rs = statement.executeQuery(query);
            int size=0;

            if(rs.last()) {
                size = rs.getRow();
                rs.beforeFirst();
            }

            QueryResult.UsernameDateRatingResult[] result = new QueryResult.UsernameDateRatingResult[size];

            for(int i=0;rs.next();i++)
            {
                result[i] = new QueryResult.UsernameDateRatingResult(rs.getString("username"),rs.getString("date"),rs.getInt("rating"));
            }

            return result;
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return new QueryResult.UsernameDateRatingResult[0];
    }

    @Override
    public QueryResult.UsernameDateRatingResult[] UsernameDateRatingDeleteAndSelect(String date) {

        String deleteQuery = "delete from article where date > \""+ date+"\"";
        String query = "select U.username, A.date, A.rating from user U, article A where A.userID = U.userID order by U.username";
        try{
            Statement statement = this.con.createStatement();
            statement.executeUpdate(deleteQuery);
            ResultSet rs = statement.executeQuery(query);
            int size=0;

            if(rs.last()) {
                size = rs.getRow();
                rs.beforeFirst();
            }

            QueryResult.UsernameDateRatingResult[] result = new QueryResult.UsernameDateRatingResult[size];

            for(int i=0;rs.next();i++)
            {
                result[i] = new QueryResult.UsernameDateRatingResult(rs.getString("username"),rs.getString("date"),rs.getInt("rating"));
            }

            return result;
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return new QueryResult.UsernameDateRatingResult[0];
    }
}
