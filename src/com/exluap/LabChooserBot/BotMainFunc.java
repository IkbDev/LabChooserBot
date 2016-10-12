package com.exluap.LabChooserBot;

import com.vdurmont.emoji.EmojiParser;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * com.exluap.LabChooserBot
 * Created by exluap <nickzaytsew@gmail.com> (C) 11.10.16
 * https://exluap.com
 * https://twitter.com/exluap
 */
public class BotMainFunc extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {
        Func(update);
    }

    @Override
    public String getBotUsername() {
        return BotConfig.BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BotConfig.BOT_TOKEN;
    }


    //Основная работа бота

    void Func(Update update) {
        Message message = update.getMessage();

        if (message.getText().equals("/start")) {
            SendMSG(message.getChatId().toString(), EmojiParser.parseToUnicode("Привет! :hand: Я бот, который поможет тебе узнать твой вариант для лабораторных" +
                    "работ по программированию №2 и №3" +
                    " Напиши мне последние 4 цифры твоего студентческого с текстом `Мои цифры студенческого: ` " +
                    "и я верну номера вариантов :memo:" +
                    "Меня разработал :nerd: @exluap" +
                    " P.S. Помни, я очень чувствителен к регистру!!! так что будь осторожен при наборе команды `Мои цифры студенческого: `"));
        } else if (message.getText().startsWith("Мои цифры студенческого:") ) {
            SendMSG(message.getChatId().toString(),"Твой вариант лабораторной №2 : " + LabWork(Integer.parseInt(splitter(message.getText())))); //Преобразование строки в число
            SendMSG(message.getChatId().toString(),"Твой вариант лабораторной №3 : " + LabWork2(Integer.parseInt(splitter(message.getText()))));
        }

        }

    /*
        Функция отправки сообщения пользователю

        Внимание!! Работает некорректно для большого количества входящих данных
     */

    private void SendMSG(String chatID, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatID);
        sendMessage.setText(text);

        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    //Функция вытаскивания цифр студенческого

    private String splitter(String s) {
        String[] a = s.split(":");
        return a[1].replaceAll(" ","");
    }

    //Лабораторная работа №2

    Integer LabWork(int a) {

        a = a % 100 / 10 % 5 + 1;

        return a;
    }

    //Лабораторная работа №3

    Integer LabWork2(int b) {

        b = b % 100 % 10 % 6 + 1;

        return b;
    }
}
