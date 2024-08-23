package com.emrsa.quizapp.model

class Question(val question_number : Int,
               val answer : Int,
               val question : String,
               val a1 : String,
               val a2 : String,
               val a3 : String,
    )
//basic question model with
//question_number -> question number
//answer -> 1 OR 2 OR 3 (if 1, true answer is a1; if 2, true answer is a2...)
//question -> question text
// a1,a2,a3 -> option texts