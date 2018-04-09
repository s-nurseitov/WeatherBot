package ITStep;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public class Main {
    public static void main(String[] args) {
        ApiContextInitializer.init(); // Инициализируем апи
        ApplicationContext ctx = new ClassPathXmlApplicationContext("bean-config.xml");
        TelegramBotsApi botapi = new TelegramBotsApi();
        try {
            botapi.registerBot(ctx.getBean(TelegramLongPollingBot.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
