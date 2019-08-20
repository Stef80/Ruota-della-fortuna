package serverRdF.dbComm;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UsersDAOImpl implements UsersDAO {
    private Connection con;
    UsersDAOImpl(Connection c){
        this.con=c;
    }
    @Override
    public boolean addUser(UsersDTO user) throws SQLException {
        String queryAdd = "INSERT INTO "+UserTable+"("+UserIdAttribute+","+UserTipoAttribute+","+UserNameAttribute+","+
                UserSurnameAttribute+","+UserNicknameAttribute+","+UserEmailAttribute+","+UserPasswordAttribute+") " +
                "VALUES ('"+user.getId()+"',"+user.isAdmin()+",'"+user.getName()+"','"+user.getSurname()+"','"
                +user.getNickname()+"','"+user.getEmail()+"','"+user.getPassword()+"');";
        Statement stmt = con.createStatement();
        return stmt.executeUpdate(queryAdd) > 0;
    }

    @Override
    public UsersDTO getUserByEmail(String email) throws SQLException {
        String querySearch = "SELECT * FROM "+UserTable+" WHERE "+UserEmailAttribute+" = '"+email+"';";
        Statement stmt = con.createStatement();
        ResultSet resultSet = stmt.executeQuery(querySearch);
        if(resultSet==null)
            return null;
        return new UsersDTO(resultSet.getString(UserIdAttribute),resultSet.getBoolean(UserTipoAttribute),
                resultSet.getString(UserNameAttribute),resultSet.getString(UserSurnameAttribute),
                resultSet.getString(UserNicknameAttribute), email,
                resultSet.getString(UserPasswordAttribute));
    }

    @Override
    public UsersDTO getUserByNickname(String nickname) throws SQLException {
        String querySearch = "SELECT * FROM "+UserTable+" WHERE "+UserNicknameAttribute+" = '"+nickname+"';";
        Statement stmt = con.createStatement();
        ResultSet resultSet = stmt.executeQuery(querySearch);
        if(resultSet==null)
            return null;
        return new UsersDTO(resultSet.getString(UserIdAttribute),resultSet.getBoolean(UserTipoAttribute),
                resultSet.getString(UserNameAttribute),resultSet.getString(UserSurnameAttribute), nickname,
                resultSet.getString(UserEmailAttribute), resultSet.getString(UserPasswordAttribute));
    }

    @Override
    public UsersDTO getUserById(String id) throws SQLException {
        String querySearch = "SELECT * FROM "+UserTable+" WHERE "+UserIdAttribute+" = '"+id+"';";
        Statement stmt = con.createStatement();
        ResultSet resultSet = stmt.executeQuery(querySearch);
        if(resultSet==null)
            return null;
        return new UsersDTO(id,resultSet.getBoolean(UserTipoAttribute), resultSet.getString(UserNameAttribute),
                resultSet.getString(UserSurnameAttribute), resultSet.getString(UserNicknameAttribute),
                resultSet.getString(UserEmailAttribute), resultSet.getString(UserPasswordAttribute));
    }

    @Override
    public boolean deleteUser(String id) throws SQLException {
        String queryDelete = "DELETE FROM "+UserTable+" WHERE "+UserIdAttribute+" = '"+id+"';";
        Statement stmt = con.createStatement();
        return stmt.executeUpdate(queryDelete) > 0;
    }
}
