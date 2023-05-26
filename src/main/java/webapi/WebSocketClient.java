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
import java.util.Arrays;
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
        System.out.println(data.toString());
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
                short readCO2 = (short) (((bytes[3] & 0b11111100) << 5) + (bytes[2] & 0b01111111));
                System.out.println("Temp: " + readTemp + " , Humidity: " + readHum + " , CO2: " + readCO2);
                dataToSave = new Data(String.valueOf(readTemp), String.valueOf(readHum), String.valueOf(readCO2), "");
                Target target = targetDAO.getTarget();
                System.out.println("Target :" + target);
                if(target!=null && target.getTemp()!=null && target.getHumidity()!=null){
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("cmd", "tx");
                    jsonObject.put("EUI", "0004A30B00E8207E");
                    jsonObject.put("port", "1");
                    jsonObject.put("confirmed", false);
                    short temp = Short.parseShort(target.getTemp());
                    temp = (short) ((temp*10)+200);
                    short humidity = Short.parseShort(target.getHumidity());
                    byte byteArray[] = new byte[3];
                    byteArray[0] = (byte) temp;
                    byteArray[1] = (byte)((temp >> 2) & 0b11000000);
                    byteArray[1] |= (byte) (humidity  & 0b00111111);
                    byteArray[2] = (byte)((humidity << 1) & 0b10000000);

                    String hex[] = new String[3];
                    for (int i = 0; i < byteArray.length;i++) {
                        hex[i] = String.format("%1$2s",Integer.toHexString((int) byteArray[i] & 0xff)).replace(" ","0");
                    }
                    String dataString = String.join("",hex);
                    jsonObject.put("data",dataString);
                    System.out.println("JSON OBJECT: " + jsonObject);
                    sendDownLink(jsonObject.toString());
                } else if(target==null){
                    System.out.println("No targets found");
                }
            } catch (JSONException e) {
                System.out.println("Reached catch block in onText()");
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
