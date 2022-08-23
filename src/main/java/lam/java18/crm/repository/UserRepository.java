package lam.java18.crm.repository;

import lam.java18.crm.model.ResponseData;
import lam.java18.crm.model.RoleModel;
import lam.java18.crm.model.UserModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserRepository extends AbstractRepository<ResponseData>{
    public ResponseData getAll() {
        return executeQuery(connection -> {
            String query = "select * from users";

            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet result = statement.executeQuery();

            List<Object> userModelList = new ArrayList<>();

            while (result.next()) {
                UserModel userModel = new UserModel();
                userModel.setId(result.getInt("id"));
                userModel.setEmail(result.getString(("email")));
                userModel.setPassword(result.getString("password"));
                userModel.setFullName(result.getString("fullname"));
                userModel.setAvatar(result.getString("avatar"));
                userModel.setRoleId(result.getInt("role_id"));
                userModelList.add(userModel);
            }

            ResponseData responseData = new ResponseData();

            if (userModelList.size() > 0) {
                responseData.setObjectList(userModelList);
                responseData.setSuccess(true);
                responseData.setMessages("Get all user success");
            } else {
                responseData.setSuccess(false);
                responseData.setMessages("Could not get all user");
            }
            return responseData;
        });
    }

    public ResponseData getOne(int id) {
        return executeQuerySingle(connection -> {
            String query = "select * from users where id =?";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, id);

            ResultSet result = statement.executeQuery();

            UserModel userModel = new UserModel();

            if (result.next()) {
                userModel.setId(result.getInt("id"));
                userModel.setEmail(result.getString(("email")));
                userModel.setPassword(result.getString("password"));
                userModel.setFullName(result.getString("fullname"));
                userModel.setAvatar(result.getString("avatar"));
                userModel.setRoleId(result.getInt("role_id"));
            }

            ResponseData responseData = new ResponseData();

            if (userModel.getId() > 0) {
                responseData.setData(userModel);
                responseData.setSuccess(true);
                responseData.setMessages("Get user success");
            } else {
                responseData.setSuccess(false);
                responseData.setMessages("Could not get user");
            }

            return responseData;
        });
    }

    public ResponseData deleteById(int id) {
        int result = executeUpdate(connection -> {
            String query = "delete from users where id=?";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, id);

            return statement.executeUpdate();
        });

        ResponseData responseData = new ResponseData();

        if (result == 0) {
            responseData.setMessages("Could not delete user that has id equals to " + id);
            responseData.setSuccess(false);
        } else {
            responseData.setMessages("The user has been deleted");
            responseData.setSuccess(true);
        }

        return responseData;
    }

    public ResponseData save(UserModel userModel) {
        int result = executeUpdate(connection -> {
            String query = "insert into users(email, password, fullname, avatar, role_id) values(?,?,?,?,?)";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, userModel.getEmail());
            statement.setString(2, userModel.getPassword());
            statement.setString(3, userModel.getFullName());
            statement.setString(4, userModel.getAvatar());
            statement.setInt(5, userModel.getRoleId());

            return statement.executeUpdate();
        });

        ResponseData responseData = new ResponseData();

        if (result == 0) {
            responseData.setMessages("Could not add user");
            responseData.setSuccess(false);
        } else {
            responseData.setMessages("The user has been added");
            responseData.setSuccess(true);
            responseData.setData(userModel);
        }

        return responseData;
    }

    public ResponseData update(UserModel userModel) {
        int result = executeUpdate(connection -> {
            String query =
                    "update users set email = ?, password = ?, fullname = ?, avatar = ?, role_id = ? where id = ?";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, userModel.getEmail());
            statement.setString(2, userModel.getPassword());
            statement.setString(3, userModel.getFullName());
            statement.setString(4, userModel.getAvatar());
            statement.setInt(5, userModel.getRoleId());
            statement.setInt(6, userModel.getId());

            return statement.executeUpdate();
        });
        ResponseData responseData = new ResponseData();

        if (result == 0) {
            responseData.setMessages("Could not update user");
            responseData.setSuccess(false);
        } else {
            responseData.setMessages("The user has been updated");
            responseData.setSuccess(true);
        }

        return responseData;
    }

    public ResponseData findByEmailAndPassword(String email, String password) {
        return executeQuerySingle(connection -> {
            String query = "select * from users where email = ? and password = ?";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet result = statement.executeQuery();

            UserModel userModel = new UserModel();

            if (result.next()) {
                userModel.setId(result.getInt("id"));
                userModel.setEmail(result.getString(("email")));
                userModel.setPassword(result.getString("password"));
                userModel.setFullName(result.getString("fullname"));
                userModel.setAvatar(result.getString("avatar"));
                userModel.setRoleId(result.getInt("role_id"));
            }

            ResponseData responseData = new ResponseData();

            if (userModel.getId() > 0) {
                responseData.setData(userModel);
                responseData.setSuccess(true);
                responseData.setMessages("Login success");
            } else {
                responseData.setSuccess(false);
                responseData.setMessages("The email or password is not correct! Please try again");
            }

            return responseData;
        });
    }

    public ResponseData findByEmail(String email) {
        return executeQuerySingle(connection -> {
            String query = "select * from users where email = ?";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, email);

            ResultSet result = statement.executeQuery();

            UserModel userModel = new UserModel();

            if (result.next()) {
                userModel.setId(result.getInt("id"));
                userModel.setEmail(result.getString(("email")));
                userModel.setPassword(result.getString("password"));
                userModel.setFullName(result.getString("fullname"));
                userModel.setAvatar(result.getString("avatar"));
                userModel.setRoleId(result.getInt("role_id"));
            }

            ResponseData responseData = new ResponseData();

            if (userModel.getId() > 0) {
                responseData.setData(userModel);
                responseData.setSuccess(true);
                responseData.setMessages("Get user success");
            } else {
                responseData.setSuccess(false);
                responseData.setMessages("The email or password is not correct! Please try again");
            }

            return responseData;
        });
    }

    public boolean existedByEmail(String email) {
        ResponseData responseData = findByEmail(email);
        return responseData.isSuccess();
    }
}
