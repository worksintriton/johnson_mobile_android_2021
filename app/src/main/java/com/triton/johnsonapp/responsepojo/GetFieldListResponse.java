package com.triton.johnsonapp.responsepojo;

import java.util.List;

public class GetFieldListResponse {


    /**
     * Status : Success
     * Message : field management
     * Data : [{"_id":"61aa033385104f58378b3b1d","cat_id":"61a8b8752d9a15335c1e511f","field_name":"Field 1","field_type":"String","field_value":"","field_length":"10","field_comments":"filed Length should be 10 digit","field_update_reason":"","date_of_create":"12/3/2021, 5:14:51 PM","date_of_update":"12/3/2021, 5:14:51 PM","created_by":"Admin","updated_by":"Admin","__v":0},{"_id":"61aa033a85104f58378b3b20","cat_id":"61a8b8752d9a15335c1e511f","field_name":"Field 2","field_type":"Textarea","field_value":"","field_length":"100","field_comments":"filed Length should be 10 digit","field_update_reason":"","date_of_create":"12/3/2021, 5:14:58 PM","date_of_update":"12/3/2021, 5:14:58 PM","created_by":"Admin","updated_by":"Admin","__v":0},{"_id":"61aa034285104f58378b3b23","cat_id":"61a8b8752d9a15335c1e511f","field_name":"Field 3","field_type":"Number","field_value":"","field_length":"3","field_comments":"filed Length should be 10 digit","field_update_reason":"","date_of_create":"12/3/2021, 5:15:06 PM","date_of_update":"12/3/2021, 5:15:06 PM","created_by":"Admin","updated_by":"Admin","__v":0},{"_id":"61aa034985104f58378b3b26","cat_id":"61a8b8752d9a15335c1e511f","field_name":"Field 4","field_type":"Dropdown","field_value":"","field_length":"1","field_comments":"filed Length should be 10 digit","field_update_reason":"","date_of_create":"12/3/2021, 5:15:13 PM","date_of_update":"12/3/2021, 5:15:13 PM","created_by":"Admin","updated_by":"Admin","__v":0},{"_id":"61aa034f85104f58378b3b29","cat_id":"61a8b8752d9a15335c1e511f","field_name":"Field 5","field_type":"Date&time","field_value":"","field_length":"1","field_comments":"filed Length should be 10 digit","field_update_reason":"","date_of_create":"12/3/2021, 5:15:18 PM","date_of_update":"12/3/2021, 5:15:18 PM","created_by":"Admin","updated_by":"Admin","__v":0},{"_id":"61aa035585104f58378b3b2c","cat_id":"61a8b8752d9a15335c1e511f","field_name":"Field 6","field_type":"File upload","field_value":"","field_length":"1","field_comments":"filed Length should be 10 digit","field_update_reason":"","date_of_create":"12/3/2021, 5:15:25 PM","date_of_update":"12/3/2021, 5:15:25 PM","created_by":"Admin","updated_by":"Admin","__v":0},{"_id":"61aa035d85104f58378b3b2f","cat_id":"61a8b8752d9a15335c1e511f","field_name":"Field 7","field_type":"Signature","field_value":"","field_length":"1","field_comments":"filed Length should be 10 digit","field_update_reason":"","date_of_create":"12/3/2021, 5:15:32 PM","date_of_update":"12/3/2021, 5:15:32 PM","created_by":"Admin","updated_by":"Admin","__v":0},{"_id":"61aa040585104f58378b3b32","cat_id":"61a8b8752d9a15335c1e511f","field_name":"Field 8","field_type":"Dropdown","field_value":"","field_length":"1","field_comments":"filed Length should be 10 digit","field_update_reason":"","date_of_create":"12/3/2021, 5:18:21 PM","date_of_update":"12/3/2021, 5:18:21 PM","created_by":"Admin","updated_by":"Admin","__v":0},{"_id":"61aa041285104f58378b3b35","cat_id":"61a8b8752d9a15335c1e511f","field_name":"Field 9","field_type":"Textarea","field_value":"","field_length":"10","field_comments":"filed Length should be 10 digit","field_update_reason":"","date_of_create":"12/3/2021, 5:18:33 PM","date_of_update":"12/3/2021, 5:18:33 PM","created_by":"Admin","updated_by":"Admin","__v":0},{"_id":"61aa041885104f58378b3b38","cat_id":"61a8b8752d9a15335c1e511f","field_name":"Field 10","field_type":"String","field_value":"","field_length":"10","field_comments":"filed Length should be 10 digit","field_update_reason":"","date_of_create":"12/3/2021, 5:18:40 PM","date_of_update":"12/3/2021, 5:18:40 PM","created_by":"Admin","updated_by":"Admin","__v":0},{"_id":"61aa041c85104f58378b3b3b","cat_id":"61a8b8752d9a15335c1e511f","field_name":"Field 11","field_type":"String","field_value":"","field_length":"10","field_comments":"filed Length should be 10 digit","field_update_reason":"","date_of_create":"12/3/2021, 5:18:44 PM","date_of_update":"12/3/2021, 5:18:44 PM","created_by":"Admin","updated_by":"Admin","__v":0},{"_id":"61aa041e85104f58378b3b3e","cat_id":"61a8b8752d9a15335c1e511f","field_name":"Field 12","field_type":"String","field_value":"","field_length":"10","field_comments":"filed Length should be 10 digit","field_update_reason":"","date_of_create":"12/3/2021, 5:18:46 PM","date_of_update":"12/3/2021, 5:18:46 PM","created_by":"Admin","updated_by":"Admin","__v":0},{"_id":"61aa042185104f58378b3b41","cat_id":"61a8b8752d9a15335c1e511f","field_name":"Field 13","field_type":"String","field_value":"","field_length":"10","field_comments":"filed Length should be 10 digit","field_update_reason":"","date_of_create":"12/3/2021, 5:18:49 PM","date_of_update":"12/3/2021, 5:18:49 PM","created_by":"Admin","updated_by":"Admin","__v":0},{"_id":"61aa042385104f58378b3b44","cat_id":"61a8b8752d9a15335c1e511f","field_name":"Field 14","field_type":"String","field_value":"","field_length":"10","field_comments":"filed Length should be 10 digit","field_update_reason":"","date_of_create":"12/3/2021, 5:18:51 PM","date_of_update":"12/3/2021, 5:18:51 PM","created_by":"Admin","updated_by":"Admin","__v":0}]
     * Code : 200
     */

    private String Status;
    private String Message;
    private int Code;
    /**
     * _id : 61aa033385104f58378b3b1d
     * cat_id : 61a8b8752d9a15335c1e511f
     * field_name : Field 1
     * field_type : String
     * field_value :
     * field_length : 10
     * field_comments : filed Length should be 10 digit
     * field_update_reason :
     * date_of_create : 12/3/2021, 5:14:51 PM
     * date_of_update : 12/3/2021, 5:14:51 PM
     * created_by : Admin
     * updated_by : Admin
     * __v : 0
     */

    private List<DataBean> Data;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean {
        private String _id;
        private String cat_id;
        private String field_name;
        private String field_type;
        private String field_value;
        private String field_length;
        private String field_comments;
        private String field_update_reason;
        private String date_of_create;
        private String date_of_update;
        private String created_by;
        private String updated_by;
        private int __v;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCat_id() {
            return cat_id;
        }

        public void setCat_id(String cat_id) {
            this.cat_id = cat_id;
        }

        public String getField_name() {
            return field_name;
        }

        public void setField_name(String field_name) {
            this.field_name = field_name;
        }

        public String getField_type() {
            return field_type;
        }

        public void setField_type(String field_type) {
            this.field_type = field_type;
        }

        public String getField_value() {
            return field_value;
        }

        public void setField_value(String field_value) {
            this.field_value = field_value;
        }

        public String getField_length() {
            return field_length;
        }

        public void setField_length(String field_length) {
            this.field_length = field_length;
        }

        public String getField_comments() {
            return field_comments;
        }

        public void setField_comments(String field_comments) {
            this.field_comments = field_comments;
        }

        public String getField_update_reason() {
            return field_update_reason;
        }

        public void setField_update_reason(String field_update_reason) {
            this.field_update_reason = field_update_reason;
        }

        public String getDate_of_create() {
            return date_of_create;
        }

        public void setDate_of_create(String date_of_create) {
            this.date_of_create = date_of_create;
        }

        public String getDate_of_update() {
            return date_of_update;
        }

        public void setDate_of_update(String date_of_update) {
            this.date_of_update = date_of_update;
        }

        public String getCreated_by() {
            return created_by;
        }

        public void setCreated_by(String created_by) {
            this.created_by = created_by;
        }

        public String getUpdated_by() {
            return updated_by;
        }

        public void setUpdated_by(String updated_by) {
            this.updated_by = updated_by;
        }

        public int get__v() {
            return __v;
        }

        public void set__v(int __v) {
            this.__v = __v;
        }
    }
}
