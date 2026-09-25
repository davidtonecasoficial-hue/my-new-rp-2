package com.meliodas.sbudaapp

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.view.View
import android.view.animation.OvershootInterpolator
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class MainActivity : AppCompatActivity() {

    private val fullMessage = "i got you Sbuda, you are my son now"
    private val typeHandler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnBig = findViewById<FrameLayout>(R.id.btnBig)
        val cardMessage = findViewById<CardView>(R.id.cardMessage)
        val tvMessage = findViewById<TextView>(R.id.tvMessage)

        btnBig.setOnClickListener {
            vibrate()
            animateButtonPress(btnBig)
            revealMessage(cardMessage, tvMessage)
        }
    }

    /** Efeito "pulo" no botão ao ser clicado (scale down + overshoot up). */
    private fun animateButtonPress(view: View) {
        view.animate()
            .scaleX(0.88f)
            .scaleY(0.88f)
            .setDuration(90)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    view.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(280)
                        .setInterpolator(OvershootInterpolator(3f))
                        .setListener(null)
                        .start()
                }
            })
            .start()
    }

    /** Mostra o card com fade + slide-up, depois "escreve" a mensagem letra a letra. */
    private fun revealMessage(card: CardView, textView: TextView) {
        typeHandler.removeCallbacksAndMessages(null)
        textView.text = ""

        if (card.visibility != View.VISIBLE) {
            card.visibility = View.VISIBLE
            card.alpha = 0f
            card.translationY = 40f
            card.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(320)
                .setInterpolator(OvershootInterpolator(1.2f))
                .start()
        }

        // Efeito de "máquina de escrever"
        var index = 0
        val typeSpeedMs = 35L
        val typer = object : Runnable {
            override fun run() {
                if (index <= fullMessage.length) {
                    textView.text = fullMessage.substring(0, index)
                    index++
                    typeHandler.postDelayed(this, typeSpeedMs)
                }
            }
        }
        typeHandler.post(typer)
    }

    private fun vibrate() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val manager = getSystemService(VibratorManager::class.java)
                manager?.defaultVibrator?.vibrate(
                    VibrationEffect.createOneShot(35, VibrationEffect.DEFAULT_AMPLITUDE)
                )
            } else {
                @Suppress("DEPRECATION")
                val vibrator = getSystemService(VIBRATOR_SERVICE) as? Vibrator
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator?.vibrate(
                        VibrationEffect.createOneShot(35, VibrationEffect.DEFAULT_AMPLITUDE)
                    )
                } else {
                    @Suppress("DEPRECATION")
                    vibrator?.vibrate(35)
                }
            }
        } catch (e: Exception) {
            // Dispositivo sem vibrador ou permissão — ignora silenciosamente.
        }
    }

    override fun onDestroy() {
        typeHandler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }
}
