package lam.java18.crm.model;

import lombok.Data;

import java.util.List;

@Data
public class ResponseData {
    private boolean isSuccess;
    private String messages;
    private Object data;
    private List<Object> objectList;
}
