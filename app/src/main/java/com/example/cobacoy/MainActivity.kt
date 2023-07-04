package com.example.cobacoy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.cobacoy.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var lastnumber = false
    var error = false
    var lastdot = false

    private lateinit var expression: Expression

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun onsamadenganClick(view: View) {
        samadengan()
    }



    fun onDigitClick(view: View) {
        if (error){
            binding.operasi.text = (view as Button).text
            error = false
        } else {
            binding.operasi.append((view as Button).text)
        }
        lastnumber = true
        samadengan()
    }

    fun onperatorClick(view: View) {
        if (!error && lastnumber){
            binding.operasi.append((view as Button).text)
            lastdot = false
            lastnumber = false
            samadengan()
        }
    }


    fun onhapusClick(view: View) {
        binding.operasi.text = binding.operasi.text.toString().dropLast(1)
        try {
            val last = binding.operasi.text.toString().last()

            if (last.isDigit()){
                samadengan()
            }
        } catch (e : Exception){
            binding.hasil.text = ""
            binding.hasil.visibility = View.GONE
            Log.e("last char error", e.toString())
        }
    }


    fun onClearClick(view: View) {
        binding.operasi.text = ""
        lastnumber = false
    }

    fun samadengan (){
        if (lastnumber && !error){
            val txt = binding.operasi.text.toString()
            expression = ExpressionBuilder(txt).build()

            try {
                val resault = expression.evaluate()
                binding.hasil.visibility = View.VISIBLE
                binding.hasil.text = resault.toString()
            }catch (ex  : ArithmeticException){
                Log.e("error", ex.toString())
                binding.hasil.text = "error"
                error = true
                lastnumber = false
            }

        }
    }

    fun onclearallClick(view: View) {
        binding.operasi.text = ""
        binding.hasil.text = ""
        lastdot = false
        error = false
        lastnumber = false
        binding.hasil.visibility = View.GONE
    }
}