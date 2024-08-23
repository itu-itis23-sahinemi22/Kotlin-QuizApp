package com.emrsa.quizapp

import android.app.AlertDialog
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.emrsa.quizapp.view.answerList
import com.emrsa.quizapp.view.trueFalseEmptyCounter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //USER IS NOT ALLOWED TO GO BACK BY SWIPING
        //INSTEAD WE ASK USER IF HE/SHE WANTS TO QUIT APP
        this@MainActivity.onBackPressedDispatcher.addCallback(this@MainActivity, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val builder = AlertDialog.Builder(this@MainActivity)
                builder.setTitle("Are You Sure?")
                builder.setMessage("Are you sure you want to quit?")
                builder.setPositiveButton("Yes") { dialog, which ->
                    //since activities do not stop if not destroyed,
                    //we will clear counter array by hand
                    trueFalseEmptyCounter[0] = 0
                    trueFalseEmptyCounter[1] = 0
                    trueFalseEmptyCounter[2] = 0
                    for(i in 0..answerList.size-1){//also will clear answerList array
                        answerList[i] = 0
                    }
                    ActivityCompat.finishAffinity(this@MainActivity)
                }
                builder.setNegativeButton("No") { dialog, which ->
                    dialog.dismiss()
                }
                val dialog = builder.create()
                dialog.show()
            }
        })
    }
}