package ITStep.Bot;

import ITStep.Data.DataDao.Dao;
import ITStep.Data.DataDao.WeatherDao;
import ITStep.Data.model.Request;
import ITStep.Data.model.User;
import ITStep.Data.model.Weather;
import ITStep.Facade.WeatherFacade;
import ITStep.Image.ImageService;
import ITStep.Weather.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import java.sql.Timestamp;
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
    private ImageService imageService;
    @Autowired
    private Dao userDao;
    @Autowired
    private Dao requestDao;
    @Autowired
    private WeatherFacade weatherFacade;
    private Weather currentWeather;

    public void onUpdateReceived(Update update) {
        chatId = update.getMessage().getChatId();

        String message = update.getMessage().getText();
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ISO_TIME) +
                " Message from " +
                update.getMessage().getChat().getUserName() +" " +
                update.getMessage().getChat().getFirstName() + " " +
                update.getMessage().getChat().getLastName() + ": " + message);

        if(message == null || message.isEmpty()) return;

        saveData(update.getMessage());

        message = message.toLowerCase();
        if (message.equals("/start")) {
            sendTextMessage("Hello! This is simple weather bot!");
        }
        else {
            sendWeather(message);
        }
    }

    private void saveData(Message message){
        if(message!=null) {
            Chat chat = message.getChat();
            String text = message.getText().toUpperCase();
            Request request = new Request();
            request.setDate(new Timestamp(System.currentTimeMillis()));
            request.setText(text);
            User user = (User) userDao.findOne(chat.getId());
            if (user == null) {
                user = new User();
                user.setChatId(chat.getId());
                System.out.println(chat.toString());
                user.setFirstName(chat.getFirstName());
                user.setLastName(chat.getLastName());
                user.setName(chat.getUserName());
                user.setCreateDate(new Timestamp(System.currentTimeMillis()));
                userDao.create(user);
            }
            request.setUser(user);
            currentWeather = weatherFacade.getWeather(text);
            request.setWeather(currentWeather);
            requestDao.create(request);
        }
    }

    private void sendWeather(String city) {
        String weather = null;
        if(currentWeather==null){
            sendTextMessage("Город не найден!");
            return;
        }

        weather = "Дата: " + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE) +
                "\nСтрана: " + currentWeather.getCountry() +
                "\nГород: " + currentWeather.getCity() +
                "\nТемпература: " + currentWeather.getTemp() + "\u00b0C" +
                "\nДавление: " + currentWeather.getPressure() +
                "\nВлажность: " + currentWeather.getHumidity() + "%" +
                "\nСкорость ветра: " + currentWeather.getWind() + "км/ч" +
                "\nНаправление ветра: " + currentWeather.getWindDir();
        String url = null;
        try {
            url = imageService.getImageUrlByCity(city);
        } catch (Exception e) {
            sendTextMessage(weather);
            e.printStackTrace();
            return;
        }
        sendPhotoMessage(url, weather);
    }

    private void sendPhotoMessage(String url, String text) {
        System.out.println("Response: " + text);
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
        System.out.println("Response: " + text);
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        message.setReplyMarkup(getKeyboard());
        try {
            sendMessage(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private ReplyKeyboardMarkup getKeyboard(){
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setResizeKeyboard(true);
        List keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add("Astana");
        row.add("Almaty");
        keyboard.add(row);
        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
    }

    public String getBotUsername() {
        return _BOT_USER_NAME;
    }

    public String getBotToken() {
        return _TOKEN;
    }
}
