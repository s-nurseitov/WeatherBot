package ITStep.Bot;

import ITStep.Image.ImageService;
import ITStep.Weather.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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

@Component
public class Bot extends TelegramLongPollingBot {
    private final static String _TOKEN = "595769930:AAGFeygIEv7CoXqg1IMerLmE_tn1Pn-HQFA";
    private final static String _BOT_USER_NAME = "TrainingWeatherBot";
    private long chatId;

    @Autowired
    WeatherService weatherService;
    @Autowired
    ImageService imageService;

    public void setWeatherService(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    public void setImageService(ImageService imageService) {
        this.imageService = imageService;
    }

    public void onUpdateReceived(Update update) {
        chatId = update.getMessage().getChatId();

        String message = update.getMessage().getText();
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ISO_TIME) +
                " Message from " +
                update.getMessage().getChat().getUserName() +" " +
                update.getMessage().getChat().getFirstName() + " " +
                update.getMessage().getChat().getLastName() + ": " + message);

        if(message == null || message.isEmpty()) return;

        if (message.contains("\u0030\u20E3\u0031\u20E3Astana")) {
            message = "Astana";
        } else if (message.contains("\u0030\u20E3\u0032\u20E3Almaty")) {
            message = "Almaty";
        }

        message = message.toLowerCase();
        if (message.equals("/start")) {
            start();
        }
        else {
            sendWeather(message);
        }
    }

    private void sendWeather(String city) {
        String weather = null;
        try {
            weatherService.LoadCurrentWeather(city);
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
                "\nВлажность: " + weatherService.getHumidity() + "%";
        String url = null;
        try {
            url = imageService.getImageUrlByCity(city);
        } catch (Exception e) {
            sendTextMessage(weather);
            e.printStackTrace();
        }
        sendPhotoMessage(url, weather);
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
