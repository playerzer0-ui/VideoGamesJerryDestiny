package business;

public class TCProtocol {
    public static final int PORT = 1234;
    public static final String DELIMITER = "%%";
    public static final String HOST = "localhost";
    public static final String EXIT = "EXIT";

    //client
    public static final String USER  = "USER";
    public static final String ORDER  = "ORDER";
    public static final String CANCEL  = "CANCEL";
    public static final String VIEW  = "VIEW";
    public static final String END  = "END";

    //server
    public static final String CONNECTED  = "CONNECTED";
    public static final String MATCH  = "MATCH";
    public static final String CANCELLED  = "CANCELLED";
    public static final String ENDED  = "ENDED";
}
