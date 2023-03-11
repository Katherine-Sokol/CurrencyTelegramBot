package BotAPI;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static BotAPI.Buttons.*;
import static BotAPI.Keyboards.*;

public class TelegramBot extends TelegramLongPollingBot {

    public static final String BOT_USER_NAME = "MyFirstBot";
    public static final String BOT_TOKEN = "6133175980:AAFbHhX-bfoael03v2Lyb6ys4A1UYt3Gh90";

    @Override
    public String getBotUsername() {
        return BOT_USER_NAME;  // для поиска в приложении используем @myowntelegrambot_bot, можно заменить название
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN; //нужно заменить токен, если будем менять имя бота
    }

    @Override
    public void onUpdateReceived(Update update) {
        SendMessage message = new SendMessage();
        if (update.hasMessage() && update.getMessage().hasText()) {
            message.setChatId(update.getMessage().getChatId().toString());
            if (update.getMessage().getText().equals("/start")) {
                message.setText("Ласкаво просимо. Цей бот допоможе відслідковувати актуальні курси валют.");
//                createDefaultKeyboard(message);  - вернуть строку, когда будет описан функционал кнопок и удалить 49 строку
                createStartKeyboard(message);
            }
        } else if (update.hasCallbackQuery()) {
            String callBackData = update.getCallbackQuery().getData();
            message.setChatId(update.getCallbackQuery().getMessage().getChatId().toString());
            switch (callBackData) {
                case GET_INFO_BUTTON:
                    // прописываем метод который вызываеться при нажатии кнопки "Отримати інфо"
                    message.setText("Отримуэмо інфо по курсу валют");
                    createStartKeyboard(message);
                    break;
                case SETTINGS_BUTTON:
                    // прописываем метод который вызываеться при нажатии кнопки "Налаштування"
                    message.setText("Налаштування");
                    createSettingsKeyboard(message);
                    break;
                case DIGITS_AFTER_DECIMAL_POINT_BUTTON:
                    // прописываем метод который вызываеться при нажатии кнопки "К-сть знаків після коми"
                    message.setText("К-сть знаків після коми");
                    createDigitsKeyboard(message);
                    break;
                case BANK_BUTTON:
                    // прописываем метод который вызываеться при нажатии кнопки "Банк"
                    message.setText("Банк");
                    createBankKeyboard(message);
                    break;
                case CURRENCY_RATE_BUTTON:
                    // прописываем метод который вызываеться при нажатии кнопки "Валюти"
                    message.setText("Валюти");
                    createCurrencyKeyboard(message);
                    break;
                case NOTIFICATION_TIME_BUTTON:
                    // прописываем метод который вызываеться при нажатии кнопки "Оберіть час сповіщення"
                    message.setText("Оберіть час сповіщення");
                    createNotificationTimeKeyboard(message);
                    break;
            }
        }
        try {
            execute(message);
        } catch (
                TelegramApiException e) {
            e.printStackTrace();
        }
    }
}