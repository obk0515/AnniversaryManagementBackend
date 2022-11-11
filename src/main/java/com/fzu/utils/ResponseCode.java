package com.fzu.utils;

@SuppressWarnings(value = "all")
public enum ResponseCode {
    SUCCESS(0, "SUCCESS"),
    DATA_INADEQUATE(-5, "The data is inadequate"),
    ERROR(-1, "ERROR"),
    DATA_IS_NULL(-1099, "The data is NULL"),
    CREATE_SUCCESS(2, "CREATE"),
    CREATE_ERROR(-2, "CREATE ERROR"),
    DELETE_SUCCESS(3, "DELETE_SUCCESS"),
    CREATE_TABLE_ERROR(-1005, " CREATE_TABLE_ERROR"),
    CREATE_DATABASE_ERROR(-1006, "CREATE_DATABASE_ERROR"),
    DATABASE_ALREADY_EXSIT(-1007, "DATABASE_ALREADY_EXSIT"),
    NOT_DATABASE_PRIVILEGES(-1044, "The User is not access the Database privileges!"),
    NOT_ACCESS_DATABASE(-1045, "Do not access the Database ,because of Username or Password is invalid"),
    CAN_NO_BE_NULL(-1048, "The Filed can not be null."),
    ATABASE_NO_EXSIT(-1049, "The Database is not exsits."),
    FILED_NO_EXSIT(-1054, "The Filed is not exsits."),
    TABLE_NO_EXSIT(-1146, "The Table is not exsits."),
    FILED_WRITE_FAIL(-1062, "The Filed is Duplicate,Write Fail."),
    FILED_UPDATE_FAIL(-1169, "The Filed is Duplicate,UpdateFail."),
    NO_HANDLE_PRIVILEGES(-1227, "You are not this Handle privileges!"),
    MYSQL_TOO_OLD(-1235, "Your MySQL version is too old,need update."),
    SQL_INCORRECT(-1149, "Your SQL Grammars is Incorrect."),
    AFFAIR_COMMIT_FAIL(-1180, "The Affair Commit Fail."),
    UN_LOGIN(-1009, "UN_LOGIN");

    private final int code;
    private final String desc;

    private ResponseCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }
}
