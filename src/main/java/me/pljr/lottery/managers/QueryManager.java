package me.pljr.lottery.managers;

import me.pljr.lottery.Lottery;
import me.pljr.lottery.objects.CorePlayer;
import me.pljr.pljrapi.database.DataSource;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class QueryManager {
    private final Lottery instance = Lottery.getInstance();
    private final DataSource dataSource;

    public QueryManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void loadPlayerSync(UUID uuid){
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
            Lottery.getPlayerManager().setCorePlayer(uuid, new CorePlayer(wonAmountTotal, wonAmountLast, wonAmountMax));
            dataSource.close(connection, preparedStatement, resultSet);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void loadPlayer(UUID uuid){
        Bukkit.getScheduler().runTaskAsynchronously(instance, ()->{
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
                Lottery.getPlayerManager().setCorePlayer(uuid, new CorePlayer(wonAmountTotal, wonAmountLast, wonAmountMax));
                dataSource.close(connection, preparedStatement, resultSet);
            }catch (SQLException e){
                e.printStackTrace();
            }
        });
    }

    public void savePlayerSync(UUID uuid){
        CorePlayer corePlayer = Lottery.getPlayerManager().getCorePlayer(uuid);
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "REPLACE INTO lottery_players VALUES (?,?,?,?)"
            );
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.setInt(2, corePlayer.getWonAmountTotal());
            preparedStatement.setInt(3, corePlayer.getWonAmountLast());
            preparedStatement.setInt(4, corePlayer.getWonAmountMax());
            preparedStatement.executeUpdate();
            dataSource.close(connection, preparedStatement, null);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void savePlayer(UUID uuid){
        CorePlayer corePlayer = Lottery.getPlayerManager().getCorePlayer(uuid);
        Bukkit.getScheduler().runTaskAsynchronously(instance, ()->{
            try {
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "REPLACE INTO lottery_players VALUES (?,?,?,?)"
                );
                preparedStatement.setString(1, uuid.toString());
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
