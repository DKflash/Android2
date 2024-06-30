package com.guy.class24b_andb_2;

import com.guy.common.DataManagerBase;
import com.guy.common.Question;

import java.util.ArrayList;

public class DataManagerCars extends DataManagerBase {

    @Override
    public ArrayList<Question> getQuestions() {
        ArrayList<Question> questions = new ArrayList<>();

        questions.add(new Question()
                .setImage("https://carwow-uk-wp-3.imgix.net/18015-MC20BluInfinito-scaled-e1707920217641.jpg")
                .setAnswers(new String[]{"Maserati", "Mercedes", "Ford", "Mazda"}));

        questions.add(new Question()
                .setImage("https://www.usnews.com/object/image/00000186-0f0d-da67-a5ef-2f5f87990000/2023-lucid-air-1.jpg")
                .setAnswers(new String[]{"Lucid", "Maserati", "Ferrari", "Mitsubishi"}));

        questions.add(new Question()
                .setImage("")
                .setAnswers(new String[]{"", "", "", ""}));


        return questions;
    }

}