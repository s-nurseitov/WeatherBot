package ITStep.Bot;

import ITStep.Weather.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    private final static String _TOKEN = "595769930:AAGFeygIEv7CoXqg1IMerLmE_tn1Pn-HQFA";
    private final static String _BOT_USER_NAME = "TrainingWeatherBot";
    private long chatId;

    IWeatherService weatherService = null;
    ApplicationContext ctx = null;
    public Bot() {
        ctx = new ClassPathXmlApplicationContext("bean-config.xml");
    }

    public void onUpdateReceived(Update update) {
        chatId = update.getMessage().getChatId();

        String txt = update.getMessage().getText();
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ISO_TIME) +
                " Message from " +
                update.getMessage().getChat().getUserName() +" " +
                update.getMessage().getChat().getFirstName() + " " +
                update.getMessage().getChat().getLastName() + ": " + txt);

        if(txt == null || txt.isEmpty()) return;

        if (txt.contains("\u0030\u20E3\u0031\u20E3Astana")) {
            txt = "current weather Astana";
        } else if (txt.contains("\u0030\u20E3\u0032\u20E3Almaty")) {
            txt = "current weather Almaty";
        }

        if (txt.equals("/start")) {
            start();
        }
        else if (txt.contains("current weather ")) {
            sendWeather(txt);
        }
    }

    private void sendWeather(String txt) {
        String weather = null;
        String city = txt.substring("current weather ".length());
        weatherService = ctx.getBean(IWeatherService.class);
        try {
            weatherService.GetCurrentWeather(city);
        } catch (Exception e) {
            e.printStackTrace();
            sendTextMessage("Город не найден!");
            return;
        }
        weather = "Дата: " + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE) +
                "\nСтрана: " + weatherService.getCountry() +
                "\nГород: " + weatherService.getCity() +
                "\nТемпература: " + weatherService.getTemp() + "\u00b0C" +
                "\nДавление: " + weatherService.getPressure() +
                "\nВлажность: " + weatherService.getHumidity()+"%";
        String url = null;
        if (txt.contains("Astana")) {
            url = "http://www.astana-hotels.net/images/astana-city/bayterek2.jpg";
        } else if (txt.contains("Almaty")) {
            url = "http://friends.kz/uploads/posts/2013-05/1369821448_1.jpg";
        }
        if (url != null) {
            sendPhotoMessage(url,weather);
        } else {
            sendTextMessage(weather);
        }
    }

    private void sendPhotoMessage(String url, String text) {
        System.out.println("Response: " + text);
        SendMessage s = new SendMessage();
        s.setChatId(chatId);
        s.setText(text);
        SendPhoto photo = new SendPhoto()
                .setChatId(chatId)
                .setPhoto(url)
                .setCaption(text);
        try {
            sendPhoto(photo);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendTextMessage(String text) {
        SendMessage s = new SendMessage();
        s.setChatId(chatId);
        s.setText(text);
        send(s);
    }

    private void send(SendMessage message){
        System.out.println("Response: " + message.getText());
        try {
            sendMessage(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void start() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setResizeKeyboard(true);
        List keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add("\u0030\u20E3\u0031\u20E3Astana");
        row.add("\u0030\u20E3\u0032\u20E3Almaty");
        keyboard.add(row);
        keyboardMarkup.setKeyboard(keyboard);
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setReplyMarkup(keyboardMarkup);
        message.setText("Hello! This is simple weather bot!");
        send(message);
    }

    public String getBotUsername() {
        return _BOT_USER_NAME;
    }

    public String getBotToken() {
        return _TOKEN;
    }
}
