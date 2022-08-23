package lam.java18.crm.repository;

import lam.java18.crm.model.ResponseData;
import lam.java18.crm.model.RoleModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RoleRepository extends AbstractRepository<ResponseData> {

    public ResponseData getAll() {
        return executeQuery(connection -> {
            String query = "select * from roles";

            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet result = statement.executeQuery();

            List<Object> roleModelList = new ArrayList<>();

            while (result.next()) {
                RoleModel roleModel = new RoleModel();
                roleModel.setId(result.getInt("id"));
                roleModel.setName(result.getString(("name")));
                roleModel.setDescription(result.getString("description"));
                roleModelList.add(roleModel);
            }
            ResponseData responseData = new ResponseData();

            if (roleModelList.size() > 0) {
                responseData.setObjectList(roleModelList);
                responseData.setSuccess(true);
                responseData.setMessages("Get all role success");
            } else {
                responseData.setSuccess(false);
                responseData.setMessages("Could not get all role");
            }
            return responseData;
        });
    }

    public ResponseData getOne(int id) {
        return executeQuerySingle(connection -> {
            String query = "select * from roles where id =?";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, id);

            ResultSet result = statement.executeQuery();

            RoleModel roleModel = new RoleModel();

            if (result.next()) {
                roleModel.setId(result.getInt("id"));
                roleModel.setName(result.getString(("name")));
                roleModel.setDescription(result.getString("description"));
            }

            ResponseData responseData = new ResponseData();

            if (roleModel.getId() > 0) {
                responseData.setData(roleModel);
                responseData.setSuccess(true);
                responseData.setMessages("Get role success");
            } else {
                responseData.setSuccess(false);
                responseData.setMessages("Could not get role");
            }

            return responseData;
        });
    }

    public ResponseData deleteById(int id) {
        int result = executeUpdate(connection -> {
            String query = "delete from roles where id=?";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, id);

            return statement.executeUpdate();
        });

        ResponseData responseData = new ResponseData();
        if (result == 0) {
            responseData.setMessages("Could not delete role that has id equals to " + id);
            responseData.setSuccess(false);
        } else {
            responseData.setMessages("The role has been deleted");
            responseData.setSuccess(true);
        }

        return responseData;
    }

    public ResponseData save(RoleModel roleModel) {
        int result = executeUpdate(connection -> {
            String query = "insert into roles(name, description) values(?,?)";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, roleModel.getName());
            statement.setString(2, roleModel.getDescription());

            return statement.executeUpdate();
        });
        ResponseData responseData = new ResponseData();

        if (result == 0) {
            responseData.setMessages("Could not add role");
            responseData.setSuccess(false);
        } else {
            responseData.setMessages("The role has been added");
            responseData.setSuccess(true);
        }

        return responseData;
    }

    public ResponseData update(RoleModel roleModel) {
        int result = executeUpdate(connection -> {
            String query = "update roles set name = ?, description = ? where id = ?";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, roleModel.getName());
            statement.setString(2, roleModel.getDescription());
            statement.setInt(3, roleModel.getId());

            return statement.executeUpdate();
        });
        ResponseData responseData = new ResponseData();

        if (result == 0) {
            responseData.setMessages("Could not update role");
            responseData.setSuccess(false);
        } else {
            responseData.setMessages("The role has been updated");
            responseData.setSuccess(true);
        }

        return responseData;
    }
}
