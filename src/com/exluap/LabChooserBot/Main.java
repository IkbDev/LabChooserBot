package com.exluap.LabChooserBot;

import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

/**
 * com.exluap.LabChooserBot
 * Created by exluap <nickzaytsew@gmail.com> (C) 11.10.16
 * https://exluap.com
 * https://twitter.com/exluap
 */
public class Main {
    public static void main(String[] args) throws TelegramApiRequestException {
        BotConfig.BOT_TOKEN = args[0];
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();


        telegramBotsApi.registerBot(new BotMainFunc());

    }
}
