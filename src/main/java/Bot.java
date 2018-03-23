import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Bot extends TelegramLongPollingBot {
    private final static String _TOKEN = "595769930:AAGFeygIEv7CoXqg1IMerLmE_tn1Pn-HQFA";
    private final static String _BOT_USER_NAME = "TrainingWeatherBot";
    public void onUpdateReceived(Update update) {
        Message msg = update.getMessage();
        String txt = msg.getText();
        if (txt.equals("/start")) {
            sendMsg(msg, "Hello, world! This is simple weather bot!");
        }
        if(txt.equals("current-weather")){
            Weather weather = new Weather();
            StringBuffer str = new StringBuffer();
            try {
                weather.GetCurrentWeather();
            } catch (Exception e) {
                e.printStackTrace();
            }
            sendMsg(msg, "Погода в городе Астана на " + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE) + ": " + str);
        }

    }

    public String getBotUsername() {
        return _BOT_USER_NAME;
    }

    public String getBotToken() {
        return _TOKEN;
    }

    private void sendMsg(Message msg, String text) {
        SendMessage s = new SendMessage();
        s.setChatId(msg.getChatId());
        s.setText(text);
        try {
            sendMessage(s);
        } catch (TelegramApiException e){
            e.printStackTrace();
        }
    }
}
