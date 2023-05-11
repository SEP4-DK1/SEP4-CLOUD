package webapi;

public class Data {
    private String temp;
    private String humidity;
    private String co2;
    private String timestamp;

    public Data(String temp, String humidity, String co2, String timestamp){
        this.temp = temp;
        this.humidity = humidity;
        this.co2 = co2;
        this.timestamp = timestamp;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getCo2() {
        return co2;
    }

    public void setCo2(String co2) {
        this.co2 = co2;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
