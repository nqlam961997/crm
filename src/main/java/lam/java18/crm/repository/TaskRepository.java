package lam.java18.crm.repository;

import lam.java18.crm.model.ResponseData;
import lam.java18.crm.model.TaskModel;
import lam.java18.crm.model.UserModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository extends AbstractRepository<ResponseData>{
    public ResponseData getAll() {
        return executeQuery(connection -> {
            String query = "select * from tasks";

            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet result = statement.executeQuery();

            List<Object> taskModelList = new ArrayList<>();

            while (result.next()) {
                TaskModel taskModel = new TaskModel();
                taskModel.setId(result.getInt("id"));
                taskModel.setName(result.getString(("name")));
                taskModel.setStartDate(getDateTimeFromResultSet("start_date", result));
                taskModel.setEndDate(getDateTimeFromResultSet("end_date", result));
                taskModel.setUserId(result.getInt("user_id"));
                taskModel.setJobId(result.getInt("job_id"));
                taskModel.setStatusId(result.getInt("status_id"));
                taskModelList.add(taskModel);
            }

            ResponseData responseData = new ResponseData();

            if (taskModelList.size() > 0) {
                responseData.setObjectList(taskModelList);
                responseData.setSuccess(true);
                responseData.setMessages("Get all task success");
            } else {
                responseData.setSuccess(false);
                responseData.setMessages("Could not get all task");
            }
            return responseData;
        });
    }

    public ResponseData getOne(int id) {
        return executeQuerySingle(connection -> {
            String query = "select * from tasks where id =?";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, id);

            ResultSet result = statement.executeQuery();

            TaskModel taskModel = new TaskModel();

            if (result.next()) {
                taskModel.setId(result.getInt("id"));
                taskModel.setName(result.getString(("name")));
                taskModel.setStartDate(getDateTimeFromResultSet("start_date", result));
                taskModel.setEndDate(getDateTimeFromResultSet("end_date", result));
                taskModel.setUserId(result.getInt("user_id"));
                taskModel.setJobId(result.getInt("job_id"));
                taskModel.setStatusId(result.getInt("status_id"));
            }

            ResponseData responseData = new ResponseData();

            if (taskModel.getId() > 0) {
                responseData.setData(taskModel);
                responseData.setSuccess(true);
                responseData.setMessages("Get task success");
            } else {
                responseData.setSuccess(false);
                responseData.setMessages("Could not get task");
            }

            return responseData;
        });
    }

    public ResponseData deleteById(int id) {
        int result = executeUpdate(connection -> {
            String query = "delete from tasks where id=?";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, id);

            return statement.executeUpdate();
        });

        ResponseData responseData = new ResponseData();

        if (result == 0) {
            responseData.setMessages("Could not delete task that has id equals to " + id);
            responseData.setSuccess(false);
        } else {
            responseData.setMessages("The task has been deleted");
            responseData.setSuccess(true);
        }

        return responseData;
    }

    public ResponseData save(TaskModel taskModel) {
        int result = executeUpdate(connection -> {
            String query = "insert into tasks(name, start_date, end_date, user_id, job_id, status_id) values(?,?,?,?,?,?)";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, taskModel.getName());
            statement.setTimestamp(2, changeLocalDateTimeIntoTimeStamp(taskModel.getStartDate()));
            statement.setTimestamp(3, changeLocalDateTimeIntoTimeStamp(taskModel.getEndDate()));
            statement.setInt(4, taskModel.getUserId());
            statement.setInt(5, taskModel.getJobId());
            statement.setInt(6, taskModel.getStatusId());

            return statement.executeUpdate();
        });

        ResponseData responseData = new ResponseData();

        if (result == 0) {
            responseData.setMessages("Could not add task");
            responseData.setSuccess(false);
        } else {
            responseData.setMessages("The task has been added");
            responseData.setSuccess(true);
        }

        return responseData;
    }

    public ResponseData update(TaskModel taskModel) {
        int result = executeUpdate(connection -> {
            String query =
                    "update users set email = ?, password = ?, fullname = ?, avatar = ?, role_id = ? where id = ?";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, taskModel.getName());
            statement.setTimestamp(2, changeLocalDateTimeIntoTimeStamp(taskModel.getStartDate()));
            statement.setTimestamp(3, changeLocalDateTimeIntoTimeStamp(taskModel.getEndDate()));
            statement.setInt(4, taskModel.getUserId());
            statement.setInt(5, taskModel.getJobId());
            statement.setInt(6, taskModel.getStatusId());

            return statement.executeUpdate();
        });
        ResponseData responseData = new ResponseData();

        if (result == 0) {
            responseData.setMessages("Could not update task");
            responseData.setSuccess(false);
        } else {
            responseData.setMessages("The user has been task");
            responseData.setSuccess(true);
        }

        return responseData;
    }

    private Timestamp changeLocalDateTimeIntoTimeStamp(LocalDateTime localDateTime) {
        if (localDateTime != null) {
            return Timestamp.valueOf(localDateTime);
        } else {
            return null;
        }
    }

    private LocalDateTime getDateTimeFromResultSet(String columnName, ResultSet result) {
        Timestamp time = null;

        try {
            time = result.getTimestamp(columnName);
        } catch (SQLException e) {
            return null;
        }

        if (time == null) {
            return null;
        } else {
            return time.toLocalDateTime();
        }
    }
}
