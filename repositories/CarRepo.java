package repositories;


import Utils.DbHelper;
import Utils.DateHelper;
import models.Car;
import models.Transmission;
import models.Type;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CarRepo {
    public static int count() throws Exception {
        Connection conn = DbHelper.getConnection();
        ResultSet res = conn.createStatement().executeQuery("SELECT COUNT(*) FROM car");
        res.next();
        return res.getInt(1);
    }

    public static List<Car> getAll(int pageSize, int page) throws Exception {
        ArrayList<Car> list = new ArrayList<>();

        Connection conn = DbHelper.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM car ORDER BY id ASC LIMIT ? OFFSET ?");
        stmt.setInt(1, pageSize);
        stmt.setInt(2, pageSize * page);
        ResultSet res = stmt.executeQuery();
        while (res.next()) {
            list.add(parseRes(res));
        }
        return list;
    }

    public static Car find(int id) throws Exception {
        Connection conn = DbHelper.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM car WHERE id = ? LIMIT 1");
        stmt.setInt(1, id);

        ResultSet res = stmt.executeQuery();
        if (!res.next()) return null;
        return parseRes(res);
    }

    public static List<Car> find(String text) throws Exception {
        Connection conn = DbHelper.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM car WHERE manufacture LIKE ? ORDER BY id ASC");
        stmt.setString(1, text + "%");

        ResultSet res = stmt.executeQuery();
        ArrayList<Car> list = new ArrayList<>();
        while (res.next()) {
            list.add(parseRes(res));
        }
        return list;
    }

    public static Car create(Car model) throws Exception {
        Connection conn = DbHelper.getConnection();
        String query = "INSERT INTO car (publisher,manufacture,model,price_per_day,avg_fuel_km,transmission,speed_limit,type,seat_num,door_num,inserted_at,updated_at, car_img) VALUES (?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, model.getPublisher());
        stmt.setInt(2, model.getManufacture());
        stmt.setString(3, model.getModel());
        stmt.setDouble(4, model.getPrice_per_day());
        stmt.setDouble(5, model.getAvg_fuel_km());
        stmt.setInt(6, model.getTransmission().getValue());
        stmt.setDouble(7, model.getSpeed_limit());
        stmt.setInt(8, model.getType().getValue());
        stmt.setInt(9, model.getSeat_num());
        stmt.setInt(10, model.getDoor_num());
        stmt.setTimestamp(11, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
        stmt.setTimestamp(12, java.sql.Timestamp.valueOf(DateHelper.toSqlFormat(model.getUpdated_at())));
        stmt.setString(13,model.getCar_img());

        if (stmt.executeUpdate() != 1)
            throw new Exception("ERR_NO_ROW_CHANGE");

        ResultSet res = conn.createStatement().executeQuery("SELECT * FROM products ORDER BY createdAt DESC LIMIT 1");
        res.next();
        return parseRes(res);
    }

    public static Car update(Car model) throws Exception {
        Connection conn = DbHelper.getConnection();
        String query = "UPDATE Car SET publisher = ?, maunfacture = ?, model = ?, price_per_day = ?, avg_fuel_km = ?, transmission = ?, speed_limit = ?, type = ?, seat_num = ?, door_num = ?, inserted_at = ?, updatedAt = CURRENT_TIMESTAMP, car_img = ? WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, model.getPublisher());
        stmt.setInt(2, model.getManufacture());
        stmt.setString(3, model.getModel());
        stmt.setDouble(4, model.getPrice_per_day());
        stmt.setDouble(5, model.getAvg_fuel_km());
        stmt.setInt(6, model.getTransmission().getValue());
        stmt.setDouble(7, model.getSpeed_limit());
        stmt.setInt(8, model.getType().getValue());
        stmt.setInt(9, model.getSeat_num());
        stmt.setInt(10, model.getDoor_num());
        stmt.setTimestamp(11, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
        stmt.setTimestamp(12, java.sql.Timestamp.valueOf(DateHelper.toSqlFormat(model.getUpdated_at())));
        stmt.setString(13,model.getCar_img());
        stmt.setInt(14, model.getId());

        if (stmt.executeUpdate() != 1)
            throw new Exception("ERR_NO_ROW_CHANGE");

        return find(model.getId());
    }

    public static boolean remove(int id) throws Exception {
        String query = "DELETE FROM car WHERE id = ?";
        PreparedStatement stmt = DbHelper.getConnection().prepareStatement(query);
        stmt.setInt(1, id);
        return stmt.executeUpdate() == 1;
    }

    private static Car parseRes(ResultSet res) throws Exception {
        int id = res.getInt("id");
        int publisher=res.getInt("publisher");
        int manufacture=res.getInt("manufacture");
        String model=res.getString("model");
        double price_per_day=res.getDouble("price_per_day");
        double avg_fuel_km=res.getDouble("avg_fuel_km");
        double speed_limit=res.getDouble("speed_limit");
        int seat_num=res.getInt("seat_num");
        int door_num=res.getInt("door_num");
        String car_img=res.getString("car_img");
        Date inserted_at = DateHelper.fromSql(res.getString("inserted_at"));
        Date updated_at = DateHelper.fromSql(res.getString("updated_at"));

         Transmission transmission;
         Type type;




        return new Car(
                id,  publisher, manufacture, model, price_per_day, avg_fuel_km, transmission, speed_limit, type, seat_num, door_num, inserted_at, updated_at, car_img);
    }

}
