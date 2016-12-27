package com.exluap.LabChooserBot;

import com.vdurmont.emoji.EmojiParser;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.File;

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
                    " Напиши мне последние 4 цифры твоего студенческого " +
                    "и я верну номера вариантов :memo:" +
                    "Меня разработал :nerd: @exluap"));
        } else if (message.getText().matches("[-+]?\\d+") && message.getText().length() == 4) {
            SendMSG(message.getChatId().toString(),"Твой вариант лабораторной №2 : " + LabWork(Integer.parseInt(message.getText()))); //Преобразование строки в число
            SendMSG(message.getChatId().toString(),"Твой вариант лабораторной №3 : " + LabWork2(Integer.parseInt(message.getText())));
            SendMSG(message.getChatId().toString(),LabWork4(Integer.parseInt(message.getText())));
        } else if (message.getText().equalsIgnoreCase("слава Украине!")) {
            UkraineLifeHack(message.getChatId().toString());
        } else if(!message.getText().matches("[-+]?\\d+") && !message.getText().endsWith("?")){
            SendMSG(message.getChatId().toString(), EmojiParser.parseToUnicode("Прости меня, но я понимаю только цифры. Напиши мне последние 4 цифры студенческого и я скажу тебе твой вариант. Не надо писать студенческий номер полностью" +
                    ", пожалей мою оперативную память, ведь мой хозяин, @exluap, написал меня на Java :sweat:" +
                    ", давай попробуем ввод еще разок?"));
        }else if (message.getText().length() !=4 && !message.getText().endsWith("?")) {
            SendMSG(message.getChatId().toString(), EmojiParser.parseToUnicode("Упс :fearful:! Что-то пошло не так, " +
                    "я насчитал больше 4 символов, быть может ты ввел лишний пробел или написал номер студнческого" +
                    " полностью? Давай попробуем снова? :wink:"));
        } else if (message.getText().endsWith("?")) {
            String[] result = {"Бесспорно", "Это предрешено заранее", "Никаких сомнений", "Определенно да", "Знаки говорят - да", "Спроси позже",
            "Знаешь, такое лучше не рассказывать", "Дай мне подумать, спроси позже", "По моим данным - нет"};

            SendMSG(message.getChatId().toString(), result[(int) (Math.random() * result.length)]);
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

    String LabWork4 (int numbers) {
        numbers = numbers % 10;
        String result = "Твой вариант лаборатоной №4: ";
        switch(numbers) {
            case 1: return result + '1';
            case 2: return result + '2';
            case 3: return result + '3';
            case 4: return result + '4';
            case 5: return result + '5';
            case 6: return result + '6';
            case 0: return result + '4';
            case 7:
            case 8:
            case 9: return result + (numbers-3);
        }
      return null;
    }

    void UkraineLifeHack(String ChatID) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(ChatID);
        File photo = new File("res/Ukraine.jpg");
        sendPhoto.setNewPhoto(photo);
        try {
            sendPhoto(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
