package webapi.DAO;

import org.springframework.stereotype.Component;
import webapi.Data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataDAOImpl implements DataDAO{


    public DataDAOImpl(){
    }

    public Connection connect() throws SQLException
    {
        String url = ConnInfo.url;
        String user = ConnInfo.user;
        String password = ConnInfo.password;
        return DriverManager.getConnection(url, user, password);
    }

    @Override
    public Data getLatest() {
        Data data = null;
        String SQL = "select * from *table* ORDER BY timestamp ASC";
        try(Connection conn = connect(); PreparedStatement preparedStatement = conn.prepareStatement(SQL)){
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                data = new Data(rs.getString("temp"), rs.getString("humidity"), rs.getString("co2"), rs.getString("timestamp"));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return data;
    }

    @Override
    public List<Data> getAll() {
        List<Data> dataList = new ArrayList<>();
        String SQL = "select * from *table* ORDER BY timestamp ASC";
        try(Connection conn = connect(); PreparedStatement preparedStatement = conn.prepareStatement(SQL)){
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                dataList.add(new Data(rs.getString("temp"), rs.getString("humidity"), rs.getString("co2"), rs.getString("timestamp")));
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return dataList;
    }

    @Override
    public List<Data> getByTime(String fromDate, String fromTime, String toDate, String toTime) {
        List<Data> dataList = new ArrayList<>();
        String SQL = "select * from *table* ORDER BY timestamp ASC";
        try(Connection conn = connect(); PreparedStatement preparedStatement = conn.prepareStatement(SQL)){
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Date from = stringToDate(fromDate);

            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void saveData(Data data) {
        String SQL = "insert into *table* (temp, humidity, co2, timestamp) values (?, ?, ?, ?)";
        int affectedRows;
        try(Connection conn = connect(); PreparedStatement preparedStatement = conn.prepareStatement(SQL)){
            preparedStatement.setString(1, data.getTemp());
            preparedStatement.setString(2, data.getHumidity());
            preparedStatement.setString(3, data.getCo2());
            preparedStatement.setString(4, data.getTimestamp());
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private Date stringToDate(String date){
        return null;
    }
}
