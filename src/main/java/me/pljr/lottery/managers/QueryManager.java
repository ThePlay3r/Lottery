package me.pljr.lottery.managers;

import me.pljr.lottery.Lottery;
import me.pljr.lottery.objects.CorePlayer;
import me.pljr.pljrapi.database.DataSource;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryManager {
    private final Lottery instance = Lottery.getInstance();
    private final DataSource dataSource;

    public QueryManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void loadPlayerSync(String playerName){
        try {
            int wonAmountTotal = 0;
            int wonAmountLast = 0;
            int wonAmountMax = 0;

            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM lottery_players WHERE username=?"
            );
            preparedStatement.setString(1, playerName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                wonAmountTotal = resultSet.getInt("wonAmountTotal");
                wonAmountLast = resultSet.getInt("wonAmountLast");
                wonAmountMax = resultSet.getInt("wonAmountMax");
            }
            PlayerManager.setCorePlayer(playerName, new CorePlayer(wonAmountTotal, wonAmountLast, wonAmountMax));
            dataSource.close(connection, preparedStatement, resultSet);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void savePlayer(String playerName){
        CorePlayer corePlayer = PlayerManager.getCorePlayer(playerName);
        Bukkit.getScheduler().runTaskAsynchronously(instance, ()->{
            try {
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "REPLACE INTO lottery_players VALUES (?,?,?,?)"
                );
                preparedStatement.setString(1, playerName);
                preparedStatement.setInt(2, corePlayer.getWonAmountTotal());
                preparedStatement.setInt(3, corePlayer.getWonAmountLast());
                preparedStatement.setInt(4, corePlayer.getWonAmountMax());
                preparedStatement.executeUpdate();
                dataSource.close(connection, preparedStatement, null);
            }catch (SQLException e){
                e.printStackTrace();
            }
        });
    }

    public void setupTables() {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS lottery_players (" +
                            "username varchar(16) NOT NULL PRIMARY KEY," +
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
