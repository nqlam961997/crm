package lam.java18.crm.repository;

import lam.java18.crm.model.JobModel;
import lam.java18.crm.model.ResponseData;
import lam.java18.crm.model.StatusModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StatusRepository extends AbstractRepository<ResponseData> {
    public ResponseData getAll() {
        return executeQuery(connection -> {
            String query = "select * from status";

            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet result = statement.executeQuery();

            List<Object> statusList = new ArrayList<>();

            while (result.next()) {
                StatusModel statusModel = new StatusModel();
                statusModel.setId(result.getInt("id"));
                statusModel.setName(result.getString(("name")));
                statusList.add(statusModel);
            }
            ResponseData responseData = new ResponseData();

            if (statusList.size() > 0) {
                responseData.setObjectList(statusList);
                responseData.setSuccess(true);
                responseData.setMessages("Get all status success");
            } else {
                responseData.setSuccess(false);
                responseData.setMessages("Could not get all status");
            }
            return responseData;
        });
    }

    public ResponseData getOne(int id) {
        return executeQuerySingle(connection -> {
            String query = "select * from status where id =?";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, id);

            ResultSet result = statement.executeQuery();

            StatusModel statusModel = new StatusModel();

            if (result.next()) {
                statusModel.setId(result.getInt("id"));
                statusModel.setName(result.getString(("name")));
            }

            ResponseData responseData = new ResponseData();

            if (statusModel.getId() > 0) {
                responseData.setData(statusModel);
                responseData.setSuccess(true);
                responseData.setMessages("Get status success");
            } else {
                responseData.setSuccess(false);
                responseData.setMessages("Could not get status");
            }

            return responseData;
        });
    }

    public ResponseData deleteById(int id) {
        int result = executeUpdate(connection -> {
            String query = "delete from status where id=?";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, id);

            return statement.executeUpdate();
        });

        ResponseData responseData = new ResponseData();

        if (result == 0) {
            responseData.setMessages("Could not delete status that has id equals to " + id);
            responseData.setSuccess(false);
        } else {
            responseData.setMessages("The status has been deleted");
            responseData.setSuccess(true);
        }

        return responseData;
    }

    public ResponseData save(StatusModel statusModel) {
        int result = executeUpdate(connection -> {
            String query = "insert into status(name) values(?)";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, statusModel.getName());

            return statement.executeUpdate();
        });
        ResponseData responseData = new ResponseData();

        if (result == 0) {
            responseData.setMessages("Could not add status");
            responseData.setSuccess(false);
        } else {
            responseData.setMessages("The status has been added");
            responseData.setSuccess(true);
        }

        return responseData;
    }

    public ResponseData update(StatusModel statusModel) {
        int result = executeUpdate(connection -> {
            String query = "update status set name = ? where id = ?";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, statusModel.getName());
            statement.setInt(2, statusModel.getId());

            return statement.executeUpdate();
        });
        ResponseData responseData = new ResponseData();

        if (result == 0) {
            responseData.setMessages("Could not update status");
            responseData.setSuccess(false);
        } else {
            responseData.setMessages("The status has been updated");
            responseData.setSuccess(true);
        }

        return responseData;
    }
}
