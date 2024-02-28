package com.example.helloworld

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashScreenActivity : AppCompatActivity() {


    companion object {
        //NOTE: It is recommended to keep the time less than 3000 ms
        const val ANIMATION_TIME: Long = 1000 //Change time according to your animation.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide()
       // some methods are deprecated
       // window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        //Use Handler to delay the start of next Activity
        // and show animation in the mean while
        Handler(this.mainLooper).postDelayed({

            //This block will be executed after ANIMATION_TIME milliseconds.

            //After ANIMATION_TIME we will start the MainActivity
            startActivity(Intent(this, MainActivity::class.java))

            //To remove this activity from back stack so that
            // this activity will not show when user closes MainActivity
            finish()

        }, ANIMATION_TIME)

    }
}
