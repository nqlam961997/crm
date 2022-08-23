package lam.java18.crm.repository;

import lam.java18.crm.model.JobModel;
import lam.java18.crm.model.ResponseData;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JobRepository extends AbstractRepository<ResponseData>{
    public ResponseData getAll() {
        return executeQuery(connection -> {
            String query = "select * from jobs";

            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet result = statement.executeQuery();

            List<Object> jobModelList = new ArrayList<>();

            while (result.next()) {
                JobModel jobModel = new JobModel();
                jobModel.setId(result.getInt("id"));
                jobModel.setName(result.getString(("name")));
                jobModel.setStartDate(getDateTimeFromResultSet("start_date", result));
                jobModel.setEndDate(getDateTimeFromResultSet("end_date", result));
                jobModelList.add(jobModel);
            }
            ResponseData responseData = new ResponseData();

            if (jobModelList.size() > 0) {
                responseData.setObjectList(jobModelList);
                responseData.setSuccess(true);
                responseData.setMessages("Get all job success");
            } else {
                responseData.setSuccess(false);
                responseData.setMessages("Could not get all job");
            }
            return responseData;
        });
    }

    public ResponseData getOne(int id) {
        return executeQuerySingle(connection -> {
            String query = "select * from jobs where id =?";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, id);

            ResultSet result = statement.executeQuery();

            JobModel jobModel = new JobModel();

            if (result.next()) {
                jobModel.setId(result.getInt("id"));
                jobModel.setName(result.getString(("name")));
                jobModel.setStartDate(getDateTimeFromResultSet("start_date", result));
                jobModel.setEndDate(getDateTimeFromResultSet("end_date", result));
            }

            ResponseData responseData = new ResponseData();

            if (jobModel.getId() > 0) {
                responseData.setData(jobModel);
                responseData.setSuccess(true);
                responseData.setMessages("Get job success");
            } else {
                responseData.setSuccess(false);
                responseData.setMessages("Could not get job");
            }

            return responseData;
        });
    }

    public ResponseData deleteById(int id) {
        int result = executeUpdate(connection -> {
            String query = "delete from jobs where id=?";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, id);

            return statement.executeUpdate();
        });

        ResponseData responseData = new ResponseData();

        if (result == 0) {
            responseData.setMessages("Could not delete job that has id equals to " + id);
            responseData.setSuccess(false);
        } else {
            responseData.setMessages("The job has been deleted");
            responseData.setSuccess(true);
        }

        return responseData;
    }

    public ResponseData save(JobModel jobModel) {
        int result = executeUpdate(connection -> {
            String query = "insert into jobs(name, start_date, end_date) values(?,?,?)";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, jobModel.getName());
            statement.setTimestamp(2, changeLocalDateTimeIntoTimeStamp(jobModel.getStartDate()));
            statement.setTimestamp(3, changeLocalDateTimeIntoTimeStamp(jobModel.getEndDate()));

            return statement.executeUpdate();
        });
        ResponseData responseData = new ResponseData();

        if (result == 0) {
            responseData.setMessages("Could not add job");
            responseData.setSuccess(false);
        } else {
            responseData.setMessages("The job has been added");
            responseData.setSuccess(true);
        }

        return responseData;
    }

    public ResponseData update(JobModel jobModel) {
        int result = executeUpdate(connection -> {
            String query = "update jobs set name = ?, start_date = ?, end_date = ? where id = ?";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, jobModel.getName());
            statement.setTimestamp(2, changeLocalDateTimeIntoTimeStamp(jobModel.getStartDate()));
            statement.setTimestamp(3, changeLocalDateTimeIntoTimeStamp(jobModel.getEndDate()));
            statement.setInt(4, jobModel.getId());

            return statement.executeUpdate();
        });
        ResponseData responseData = new ResponseData();

        if (result == 0) {
            responseData.setMessages("Could not update job");
            responseData.setSuccess(false);
        } else {
            responseData.setMessages("The job has been updated");
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
