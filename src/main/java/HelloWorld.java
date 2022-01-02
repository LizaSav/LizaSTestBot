import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class HelloWorld {
    @SneakyThrows
    public static void main(String[] args) throws TelegramApiException {
//        BotScript botScript = SheetsQuickstart.getScriptFromGoogleTables();

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        // Register our bot
        try {
            telegramBotsApi.registerBot(
//                    new HubBot(botScript)
                    new EchoBot()
            );
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
