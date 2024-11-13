package org.example.demo2.dao;

import jakarta.annotation.Resource;
import jakarta.ejb.Stateless;
import org.example.demo2.model.Client;
import org.example.demo2.model.DatingTable;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Stateless
public class ClientDao{

    @Resource(name = "jdbc/da")
    private DataSource ds;

    public List<Client> findAll() {
        List<Client> students = new ArrayList<>();
        try (Connection conn = ds.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("select * from client")){
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                int age = rs.getInt("age");
                String sex = rs.getString("sex");
                Date birthdate = rs.getDate("birthdate");
                students.add(new Client(
                        id,
                        name,
                        surname,
                        age,
                        sex,
                        birthdate));
            }
            return students;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Client find(int id) {
        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement("select * from client where id = ?")
        ) {
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
//                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                int age = resultSet.getInt("age");
                String sex = resultSet.getString("sex");
                Date birthdate = resultSet.getDate("birthdate");
                return new Client(id,
                        name,
                        surname,
                        age,
                        sex,
                        birthdate);
            }
            return new Client();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void showDates(Client client) {
        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     """
                             SELECT
                             DT.id,
                             CONCAT(C1.name, ' ', C1.surname) AS client1,
                             C1.id AS client1_id,
                             CONCAT(C2.name, ' ', C2.surname) AS client2,
                             C2.id AS client2_id,
                             DT.Date,
                             CNTR.Country_name AS `Country`
                             FROM dating_table DT
                             LEFT JOIN client C1 ON DT.User_1_ID = C1.id
                             LEFT JOIN client C2 ON DT.User_2_ID = C2.id
                             LEFT JOIN country CNTR ON CNTR.ID = DT.Registration_country
                             WHERE DT.User_1_ID = ? OR DT.User_2_ID = ?"""
             )
        ) {
            ps.setInt(1, client.getId());
            ps.setInt(2, client.getId()); // устанавливаем второй параметр

            client.getDates().clear();
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String clientOne = resultSet.getString("client1");
                int userOneId = resultSet.getInt("client1_id");
                String clientTwo = resultSet.getString("client2");
                int userTwoId = resultSet.getInt("client2_id");
                Date date = resultSet.getDate("Date");
                client.getDates().add(new DatingTable(
                        id, clientOne, userOneId, clientTwo, userTwoId, date));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void addDate(Client client, Client secondClient, Date date) {
        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement("INSERT INTO `meeting_agency`.`dating_table` (`User_1_ID`, `User_2_ID`, `Date`, `Registration_country`)\n" +
                     "VALUES (?, ?, ?, ?)")
        ) {
            ps.setInt(1, client.getId());
            ps.setInt(2, secondClient.getId());
            ps.setDate(3, date);
            ps.setInt(4, 1);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Client> findAllExcept(int clientId) {
        List<Client> clients = new ArrayList<>();
        String sql = """
            SELECT
                C.id,
                C.name,
                C.surname,
                TIMESTAMPDIFF(YEAR, C.birthdate, CURDATE()) AS age,
                C.sex,
                C.birthdate
            FROM
                client C
                JOIN hobbylist HL ON HL.User_ID = C.id
                JOIN requirements_list RL ON RL.User_ID = C.id
                JOIN requirements R ON R.ID = RL.requirement_id
            WHERE
                C.id <> ?
            ORDER BY
                C.id
            """;

        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, clientId); // Устанавливаем значение параметра
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Client client = new Client();
                client.setId(rs.getInt("id"));
                client.setName(rs.getString("name"));
                client.setSurname(rs.getString("surname"));
                client.setAge(rs.getInt("age"));
                client.setSex(rs.getString("sex"));
                client.setBirthdate(rs.getDate("birthdate"));
                clients.add(client);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clients;
    }


    public void deleteDate(int id) {
        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement("delete from dating_table where id = ?")
        ) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.print("id of date: " + id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
