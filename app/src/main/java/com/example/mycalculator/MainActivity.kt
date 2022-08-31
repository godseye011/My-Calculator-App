package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    var lastDot = false
    var lastNumeric = true
    fun onNumeric(view: View) {
        lastNumeric = true
        result.append((view as Button).text)
    }
    fun onDel(view: View) {
        if(result.text.last() == '.' || result.text.last() == '+' || result.text.last() == '-' || result.text.last() == '/' || result.text.last() == '*') {
            lastDot = false
            lastNumeric = true
        }
        result.text = result.text.dropLast(1)
        if(result.text.isEmpty()) {
            lastNumeric = false
        }
    }
    fun onClr(view: View) {
        result.text = ""
        lastDot = false
        lastNumeric = false
    }
    fun limitDec(view: View) {
        if(!lastDot) {
            result.append(".")
            lastDot = true
            lastNumeric = false
        }
    }
    fun onClickOperator(view: View) {
        if(result.text.toString() == "" && (view as Button).text == "-") {
            result.append((view as Button).text)
        }
        else if(lastNumeric && !isOperator(result.text.toString())) {
            lastNumeric = false
            lastDot = false
            result.append((view as Button).text)
        }
        else {
            Toast.makeText(this, "Invalid Choice", Toast.LENGTH_SHORT).show()
        }
    }
    private fun isOperator(value : String) : Boolean{
        if (value.startsWith("-")) {
            return false
        } else if( value.contains("+") || value.contains("-") || value.contains("/") || value.contains(
                "*")) {
            return true
        }
        return false
    }
    fun onEqual(view: View) {
        var prefix = ""
        var resultvalue = result.text.toString()
        if(result.text.startsWith("-")) {
            prefix = "-"
            resultvalue = resultvalue.substring(1)
        }
        try {
             if(resultvalue.contains("-")) {
                 val aftersplit = resultvalue.split("-")
                 var first = aftersplit[0]
                 val second = aftersplit[1]
                 if(!prefix.isEmpty()) {
                     first = prefix + first
                 }

                result.text = removeTrailingZeros((first.toDouble() - second.toDouble()).toString())

             }
            else if(resultvalue.contains("+")) {
                val aftersplit = resultvalue.split("+")
                var first = aftersplit[0]
                var second = aftersplit[1]
                if(!prefix.isEmpty()) {
                    first = prefix + first
                }
                result.text = removeTrailingZeros((first.toDouble() + second.toDouble()).toString())

            }
            else if(resultvalue.contains("*")) {
                val aftersplit = resultvalue.split("*")
                var first = aftersplit[0]
                var second = aftersplit[1]
                 if(!prefix.isEmpty()) {
                     first = prefix + first
                 }

                result.text = removeTrailingZeros((first.toDouble() * second.toDouble()).toString())

            }
            else if(resultvalue.contains("/")) {
                val aftersplit = resultvalue.split("/")
                var first = aftersplit[0]
                var second = aftersplit[1]
                 if(!prefix.isEmpty()) {
                     first = prefix + first
                 }

                result.text = removeTrailingZeros((first.toDouble() / second.toDouble()).toString())

            }
        }catch (e: ArithmeticException) {
            e.printStackTrace()
        }
    }
    private fun removeTrailingZeros(value : String) : String {
        var num = value
        if(value.contains(".0")) {
            num = num.substring(0, num.length - 2)
        }
        return num
    }



}