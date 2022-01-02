import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
public class PhotoBot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            handleTextMessage(update);
        }
        if (update.hasCallbackQuery()){
            System.out.println(update);
            String user_first_name = update.getCallbackQuery().getFrom().getFirstName();
            String user_last_name = update.getCallbackQuery().getFrom().getLastName();
            String user_username = update.getCallbackQuery().getFrom().getUserName();
            long user_id = update.getCallbackQuery().getFrom().getId();
            String message_text = update.getCallbackQuery().getData();

            log(user_first_name, user_last_name, Long.toString(user_id), message_text, "answer");
        }
    }

    private void handleTextMessage(Update update) {
        String user_first_name = update.getMessage().getChat().getFirstName();
        String user_last_name = update.getMessage().getChat().getLastName();
        String user_username = update.getMessage().getChat().getUserName();
        long user_id = update.getMessage().getChat().getId();
        String message_text = update.getMessage().getText();
        if (update.hasMessage() && update.getMessage().hasText()) {
            String chat_id = String.valueOf(update.getMessage().getChatId());
            if (message_text.equals("/markup")) {
                // Set variables
                SendMessage message = new SendMessage(chat_id, "Here is your keyboard");// Create a message object object
                // Create ReplyKeyboardMarkup object
                ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
                // Create the keyboard (list of keyboard rows)
                List<KeyboardRow> keyboard = new ArrayList<>();
                // Create a keyboard row
                KeyboardRow row = new KeyboardRow();
                // Set each button, you can also use KeyboardButton objects if you need something else than text
                KeyboardButton keyboardButton = new KeyboardButton();

                row.add("Row 1 Button 1");
                row.add("Row 1 Button 2");
                row.add("Row 1 Button 3");
                // Add the first row to the keyboard
                keyboard.add(row);
                // Create another keyboard row
                row = new KeyboardRow();
                // Set each button for the second line
                row.add("Row 2 Button 1");
                row.add("Row 2 Button 2");
                row.add("Row 2 Button 3");
                // Add the second row to the keyboard
                keyboard.add(row);
                // Set the keyboard to the markup
                keyboardMarkup.setKeyboard(keyboard);
                // Add it to the message
                message.setReplyMarkup(keyboardMarkup);
                try {
                    execute(message); // Sending our message object to user
                    String answer = message.getText();
                    log(user_first_name, user_last_name, Long.toString(user_id), message_text, answer);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
            if (message_text.equals("/keyboard")) {
                // Set variables
                SendMessage message = new SendMessage(chat_id, "Here is your keyboard");// Create a message object object
                // Create ReplyKeyboardMarkup object
                InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
                List<InlineKeyboardButton> keyboard = new ArrayList<>();
                InlineKeyboardButton button = InlineKeyboardButton.builder()
                        .callbackData("qqq")
                        .text("aaaa")
                        .build();
                InlineKeyboardButton button1 = InlineKeyboardButton.builder()
                        .callbackData("qqqddd")
                        .text("vvv")
                        .build();
                keyboard.add(button);
                keyboard.add(button1);

                inlineKeyboardMarkup.setKeyboard(List.of(keyboard));
                // Add it to the message
                message.setReplyMarkup(inlineKeyboardMarkup);
                try {
                    execute(message); // Sending our message object to user
                    String answer = message.getText();
                    log(user_first_name, user_last_name, Long.toString(user_id), message_text, answer);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else {
                // Unknown command
                SendMessage message = new SendMessage(chat_id, "Привет, " + user_first_name + "\nЯ тебя не понимаю") // Create a message object object
                        ;
                try {
                    execute(message); // Sending our message object to user
                    String answer = message.getText();
                    log(user_first_name, user_last_name, Long.toString(user_id), message_text, answer);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }




    @Override
    public String getBotUsername() {
        return "LizaSTest_bot";
    }

    @Override
    public String getBotToken() {
        return "2092937307:AAHQVVIhJVvA87X0IghJZ_DnN-HgzsUjtfM";
    }


    private void log(String first_name, String last_name, String user_id, String txt, String bot_answer) {

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("date", LocalDateTime.now().toString());
        objectNode.put("first_name", first_name);
        objectNode.put("last_name", last_name);
        objectNode.put("user_id", user_id);
        objectNode.put("message", txt);
        objectNode.put("bot_answer", bot_answer);
        try {
            log.info(objectMapper.writeValueAsString(objectNode));
        } catch (JsonProcessingException e) {
            log.info("\n ----------------------------");
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            log.info(dateFormat.format(date));
            log.info("Message from " + first_name + " " + last_name + ". (id = " + user_id + ") \n Text - " + txt);
            log.info("Bot answer: \n Text - " + bot_answer);
        }
    }
}
