package webapi;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import webapi.DAO.DataDAO;
import webapi.DAO.TargetDAO;
import webapi.Domain.Data;
import webapi.Domain.Target;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.nio.ByteBuffer;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Configuration
public class WebSocketClient implements WebSocket.Listener {

    private DataDAO dataDAO;
    private TargetDAO targetDAO;
    private WebSocket server = null;
    private String url = "wss://iotnet.teracom.dk/app?token=vnoUhgAAABFpb3RuZXQudGVyYWNvbS5ka_j9ctg1JnsNo1n5Rxn3neg=";

    // Send down-link message to device
    // Must be in Json format according to https://github.com/ihavn/IoT_Semester_project/blob/master/LORA_NETWORK_SERVER.md
    public void sendDownLink(String jsonTelegram) {
        server.sendText(jsonTelegram,true);
    }

    @Bean
    CommandLineRunner initClient(DataDAO dataDAO, TargetDAO targetDAO){
        HttpClient client = HttpClient.newHttpClient();
        CompletableFuture<WebSocket> ws = client.newWebSocketBuilder()
                .buildAsync(URI.create(url), this);
        server = ws.join();
        this.dataDAO = dataDAO;
        this.targetDAO = targetDAO;
        System.out.println("Initiated the client");
        return null;
    }

    //onOpen()
    public void onOpen(WebSocket webSocket) {
        // This WebSocket will invoke onText, onBinary, onPing, onPong or onClose methods on the associated listener (i.e. receive methods) up to n more times
        webSocket.request(1);
        System.out.println("WebSocket Listener has been opened for requests.");
    }

    //onError()
    public void onError(WebSocket webSocket, Throwable error) {
        System.out.println("A " + error.getCause() + " exception was thrown.");
        System.out.println("Message: " + error.getLocalizedMessage());
        webSocket.abort();
    };
    //onClose()
    public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
        System.out.println("WebSocket closed!");
        System.out.println("Status:" + statusCode + " Reason: " + reason);
        return new CompletableFuture().completedFuture("onClose() completed.").thenAccept(System.out::println);
    };
    //onPing()
    public CompletionStage<?> onPing(WebSocket webSocket, ByteBuffer message) {
        webSocket.request(1);
        System.out.println("Ping: Client ---> Server");
        System.out.println(message.asCharBuffer().toString());
        return new CompletableFuture().completedFuture("Ping completed.").thenAccept(System.out::println);
    };
    //onPong()
    public CompletionStage<?> onPong(WebSocket webSocket, ByteBuffer message) {
        webSocket.request(1);
        System.out.println("Pong: Client ---> Server");
        System.out.println(message.asCharBuffer().toString());
        return new CompletableFuture().completedFuture("Pong completed.").thenAccept(System.out::println);
    };
    //onText()
    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
        JSONObject json;
        Object command;
        try {
            json = new JSONObject(data.toString());
            command = json.get("cmd");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        if(command.equals("rx")){
            String indented;
            Data dataToSave;
            try {
                indented = json.toString(4);
                String hexString = (String) json.get("data");
                int length = hexString.length();
                if (length % 2 != 0) {
                    throw new IllegalArgumentException("Hex string must have an even number of characters");
                }
                byte[] bytes = new byte[length / 2];
                for (int i = 0; i < length; i += 2) {
                    bytes[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                            + Character.digit(hexString.charAt(i + 1), 16));
                }
                short readTemp = (short) (((bytes[1] & 0b11000000) << 2) + (0b11111111 & bytes[0]));
                readTemp = (short) ((readTemp/10)-20);
                short readHum = (short) (((bytes[2] & 0b10000000) >> 1) + (bytes[1] & 0b00111111));
                short readCO2 = (short) (((bytes[3] & 0b11111100) << 6) + (bytes[2] & 0b01111111));
                System.out.println("Temp: " + readTemp + " , Humidity: " + readHum + " , CO2: " + readCO2);
                dataToSave = new Data(String.valueOf(readTemp), String.valueOf(readHum), String.valueOf(readCO2), "");
                Target target = targetDAO.getTarget();
                if(target!=null){
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("cmd", "tx");
                    jsonObject.put("EUI", "0004A30B00E8207E");
                    jsonObject.put("port", "1");
                    jsonObject.put("confirmed", "true");
                    Short temp = Short.valueOf(target.getTemp());
                    Short humidity = Short.valueOf(target.getHumidity());
                    Short co2 = Short.valueOf(target.getCo2());
                    String dataString = "";
                    String tempString = Integer.toHexString(temp.intValue());
                    String humidityString = Integer.toHexString(humidity.intValue());
                    String co2String = Integer.toHexString(co2.intValue());
                    dataString = tempString + humidityString + co2String;
                    jsonObject.put("data", dataString);
                    System.out.println(jsonObject);
                    sendDownLink(jsonObject.toString());
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            dataDAO.saveNewData(dataToSave);
            System.out.println(indented);
            System.out.println("Received message: " + data);
        }
        webSocket.request(1);
        return new CompletableFuture().completedFuture("onText() completed.").thenAccept(System.out::println);
    }
}
