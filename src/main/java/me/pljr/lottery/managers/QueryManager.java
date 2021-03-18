package me.pljr.lottery.managers;

import me.pljr.lottery.objects.LotteryPlayer;
import me.pljr.pljrapispigot.database.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class QueryManager {
    private final DataSource dataSource;

    public QueryManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public LotteryPlayer loadPlayer(UUID uuid){
        LotteryPlayer lotteryPlayer = new LotteryPlayer(uuid);
        try {
            int wonAmountTotal = 0;
            int wonAmountLast = 0;
            int wonAmountMax = 0;

            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM lottery_players WHERE uuid=?"
            );
            preparedStatement.setString(1, uuid.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                wonAmountTotal = resultSet.getInt("wonAmountTotal");
                wonAmountLast = resultSet.getInt("wonAmountLast");
                wonAmountMax = resultSet.getInt("wonAmountMax");
            }
            lotteryPlayer = new LotteryPlayer(uuid, 0, wonAmountTotal, wonAmountLast, wonAmountMax, 0);
            dataSource.close(connection, preparedStatement, resultSet);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return lotteryPlayer;
    }

    public void savePlayer(LotteryPlayer lotteryPlayer){
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "REPLACE INTO lottery_players VALUES (?,?,?,?)"
            );
            preparedStatement.setString(1, lotteryPlayer.getUniqueId().toString());
            preparedStatement.setInt(2, lotteryPlayer.getWonAmountTotal());
            preparedStatement.setInt(3, lotteryPlayer.getWonAmountLast());
            preparedStatement.setInt(4, lotteryPlayer.getWonAmountMax());
            preparedStatement.executeUpdate();
            dataSource.close(connection, preparedStatement, null);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void setupTables() {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS lottery_players (" +
                            "uuid char(36) NOT NULL PRIMARY KEY," +
                            "wonAmountTotal int NOT NULL," +
                            "wonAmountLast int NOT NULL," +
                            "wonAmountMax int NOT NULL);"
            );
            preparedStatement.executeUpdate();
            dataSource.close(connection, preparedStatement, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
