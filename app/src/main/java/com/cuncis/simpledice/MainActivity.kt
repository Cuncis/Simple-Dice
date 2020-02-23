package com.cuncis.simpledice

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.item_settings.view.*

class MainActivity : AppCompatActivity() {

    private lateinit var diceViewModel: DiceViewModel
    private var num: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        diceViewModel = ViewModelProviders.of(this).get(DiceViewModel::class.java)
        diceViewModel.number.observe(this, Observer {
            tv_result.text = it.toString()

            num = it

            when (it) {
                1 -> {
                    tv_number1.visibility = View.VISIBLE
                    tv_titleDice1.visibility = View.VISIBLE

                    tv_number2.visibility = View.GONE
                    tv_titleDice2.visibility = View.GONE

                    tv_number3.visibility = View.GONE
                    tv_titleDice3.visibility = View.GONE

                    tv_number4.visibility = View.GONE
                    tv_titleDice4.visibility = View.GONE

                }
                2 -> {
                    tv_number1.visibility = View.VISIBLE
                    tv_titleDice1.visibility = View.VISIBLE

                    tv_number2.visibility = View.VISIBLE
                    tv_titleDice2.visibility = View.VISIBLE

                    tv_number3.visibility = View.GONE
                    tv_titleDice3.visibility = View.GONE

                    tv_number4.visibility = View.GONE
                    tv_titleDice4.visibility = View.GONE
                }
                3 -> {
                    tv_number1.visibility = View.VISIBLE
                    tv_titleDice1.visibility = View.VISIBLE

                    tv_number2.visibility = View.VISIBLE
                    tv_titleDice2.visibility = View.VISIBLE

                    tv_number3.visibility = View.VISIBLE
                    tv_titleDice3.visibility = View.VISIBLE

                    tv_number4.visibility = View.GONE
                    tv_titleDice4.visibility = View.GONE
                }
                4 -> {
                    tv_number1.visibility = View.VISIBLE
                    tv_titleDice1.visibility = View.VISIBLE

                    tv_number2.visibility = View.VISIBLE
                    tv_titleDice2.visibility = View.VISIBLE

                    tv_number3.visibility = View.VISIBLE
                    tv_titleDice3.visibility = View.VISIBLE

                    tv_number4.visibility = View.VISIBLE
                    tv_titleDice4.visibility = View.VISIBLE
                }
            }
        })

        btn_roll.setOnClickListener {
            when (num) {
                1 -> {
                    tv_number1.text = "" + getRandomNumber()
                }
                2 -> {
                    tv_number1.text = "" + getRandomNumber()
                    tv_number2.text = "" + getRandomNumber()
                }
                3 -> {
                    tv_number1.text = "" + getRandomNumber()
                    tv_number2.text = "" + getRandomNumber()
                    tv_number3.text = "" + getRandomNumber()
                }
                4 -> {
                    tv_number1.text = "" + getRandomNumber()
                    tv_number2.text = "" + getRandomNumber()
                    tv_number3.text = "" + getRandomNumber()
                    tv_number4.text = "" + getRandomNumber()
                }
            }
            tv_result.text = getTotal().toString()
        }
    }

    private fun getRandomNumber(): Int {
        return (1..6).random()
    }

    private fun getTotal(): Int {
        var total = 0
        when (num) {
            1 -> {
                tv_number1.text = "" + getRandomNumber()
                total = tv_number1.text.toString().toInt()
            }
            2 -> {
                tv_number1.text = "" + getRandomNumber()
                tv_number2.text = "" + getRandomNumber()
                total = (tv_number1.text.toString().toInt() + tv_number2.text.toString().toInt())
            }
            3 -> {
                tv_number1.text = "" + getRandomNumber()
                tv_number2.text = "" + getRandomNumber()
                tv_number3.text = "" + getRandomNumber()
                total = (tv_number1.text.toString().toInt() + tv_number2.text.toString().toInt() + tv_number3.text.toString().toInt())
            }
            4 -> {
                tv_number1.text = "" + getRandomNumber()
                tv_number2.text = "" + getRandomNumber()
                tv_number3.text = "" + getRandomNumber()
                tv_number4.text = "" + getRandomNumber()
                total = (tv_number1.text.toString().toInt() + tv_number2.text.toString().toInt() + tv_number3.text.toString().toInt()
                        + tv_number4.text.toString().toInt())
            }
        }

        return total
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> dialogSettings()
            R.id.action_about -> dialogAbout()
            R.id.action_exit -> dialogExit()
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun dialogAbout() {
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(true)
        val view = LayoutInflater.from(this).inflate(R.layout.item_about, null)
        builder.setView(view)

        val dialog = builder.create()

        dialog.show()
    }

    private fun dialogSettings() {
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        val view = LayoutInflater.from(this).inflate(R.layout.item_settings, null)
        builder.setView(view)

        val dialog = builder.create()

        val btnPlus = view.btn_plus
        val btnMin = view.btn_min
        val tvSum = view.tv_sum

        tvSum.text = PrefsManager.getValue(this).toString()

        btnPlus.setOnClickListener {
            diceViewModel.number.value = diceViewModel.number.value?.plus(1)
            tvSum.text = diceViewModel.number.value?.toString()
            if (diceViewModel.number.value?.toString().equals("4")) {
                btnPlus.visibility = View.INVISIBLE
            }
            if (!diceViewModel.number.value?.toString().equals("1")) {
                btnMin.visibility = View.VISIBLE
            }
        }

        btnMin.setOnClickListener {
            diceViewModel.number.value = diceViewModel.number.value?.minus(1)
            tvSum.text = diceViewModel.number.value?.toString()
            if (diceViewModel.number.value?.toString().equals("1")) {
                btnMin.visibility = View.INVISIBLE
            }
            if (!diceViewModel.number.value?.toString().equals("4")) {
                btnPlus.visibility = View.VISIBLE
            }
        }

        view.btn_back.setOnClickListener {
            PrefsManager.setPrefValue(this, diceViewModel.number.value)
            dialog.dismiss()
        }

        dialog.show()

    }

    private fun dialogExit() {
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setTitle("Exit")
        builder.setMessage("Do you want to exit?")
        builder.setPositiveButton("Exit") { _, _ ->
            PrefsManager.clearPref(this)
            finish()
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        PrefsManager.clearPref(this)
    }
}
