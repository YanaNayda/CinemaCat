package com.example.cinemacat

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CalendarView
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isInvisible
import com.example.cinemacat.R.anim.rotate_anim
import com.example.cinemacat.R.anim.shake_anim
import java.util.Calendar


class MainActivity : AppCompatActivity(),View.OnClickListener {

    private var cinema = arrayOf("Your CinemaCat!",
        "CinemaCat Holon","CinemaCat Haifa","CinemaCat Tel Aviv","CinemaCat Jerusalem",
        "CinemaCat Raanana","CinemaCat Beer Sheva"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var touchCat = 0


        val imgPoster = findViewById<ImageView>(R.id.img_main_poster)
        val imgBtnMainPoster = findViewById<ImageView>(R.id.img_btn_main_poster)
        val imgBtnFriends = findViewById<ImageView>(R.id.img_btn_friends)
        val imgBtnWolf = findViewById<ImageView>(R.id.img_btn_wolf)
        val imgBtnCat = findViewById<ImageView>(R.id.img_btn_cat)
        val imgBtnCatAnim = findViewById<ImageView>(R.id.img_btn_cat_anim)
        val txtDialogCat = findViewById<TextView>(R.id.txt_dialog_cat)


        val rotateAnim = AnimationUtils.loadAnimation(this, rotate_anim)
        val shakeAnim = AnimationUtils.loadAnimation(this, shake_anim)

        val btnMorning = findViewById<Button>(R.id.btn_time_morning)
        val btnDay = findViewById<Button>(R.id.btn_time_day)
        val btnEvening = findViewById<Button>(R.id.btn_time_evening)

        val btnPlusAdultTicket = findViewById<Button>(R.id.btn_adult_ticket_plus)
        val btnMinusAdultTicket = findViewById<Button>(R.id.btn_adult_ticket_minus)
        val btnPlusChildTicket = findViewById<Button>(R.id.btn_child_ticket_plus)
        val btnMinusChildTicket = findViewById<Button>(R.id.btn_child_ticket_minus)
        val btnPlusStudent = findViewById<Button>(R.id.btn_student_ticket_plus)
        val btnMinusStudent = findViewById<Button>(R.id.btn_student_ticket_minus)

        val btnReadyOrder = findViewById<Button>(R.id.btn_confirm_order)


        val childPrice = 35
        val adultPrice = 70
        val studentPrice = 45


        val txtTotalPrice = findViewById<TextView>(R.id.txt_total_price_value)
        val txtTotalTicket = findViewById<TextView>(R.id.txt_total_tickets_value)

        var adultTicket = 0
        var childTicket = 0
        var studentTicket = 0

        var totalPrice = 0
        var totalTicket = 0

        var time = ""

        var discount = false


        val txtAdultTicket = findViewById<TextView>(R.id.txt_adult_ticket_count)
        val txtChildTicket = findViewById<TextView>(R.id.txt_child_ticket_count)
        val txtStudentTicket = findViewById<TextView>(R.id.txt_student_ticket_count)


//not working
        // val typeface = ResourcesCompat.getFont(this, butcherman)

        //Spinner
        val spin = findViewById<Spinner>(R.id.spinner_cinema)
        val adapter = ArrayAdapter(this, R.layout.custom_spinner_item, cinema)
        adapter.setDropDownViewResource(R.layout.custom_spinner_item)
        spin.adapter = adapter

        spin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                val selectedItem = parent?.getItemAtPosition(position).toString()
                if (selectedItem != "Your CinemaCat!") {
                    imgBtnCatAnim.setImageResource(R.drawable.img_cat_cool)
                    txtDialogCat.text = "Cool! It's a great place :)"
                    //Toast.makeText(applicationContext, "Selected item: $selectedItem", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }


        //Calendar
        val txt_date = findViewById<TextView>(R.id.txt_date)
        val calendarView = findViewById<CalendarView>(R.id.calendarView)

        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            txt_date.text = "$dayOfMonth/${month + 1}/$year"
            txtDialogCat.text = "You choose a perfect day!"

        }

        val minCalendar = Calendar.getInstance().apply {
            set(2025, Calendar.MARCH, 1)
        }
        val maxCalendar = Calendar.getInstance().apply {
            set(2025, Calendar.APRIL, 30)
        }
        calendarView.minDate = minCalendar.timeInMillis
        calendarView.maxDate = maxCalendar.timeInMillis


// ImageButtons
        imgBtnMainPoster.setOnClickListener {
            imgPoster.setImageResource(R.drawable.img_back_frame_1)
            imgBtnCatAnim.setImageResource(R.drawable.img_cat_cool)
            txtDialogCat.text = getString(R.string.wow_it_s_my_favorite_movie)


            imgBtnFriends.clearColorFilter()
            imgBtnWolf.clearColorFilter()
            imgBtnCat.clearColorFilter()
            imgBtnMainPoster.setColorFilter(ContextCompat.getColor(this, R.color.pink))

        }
        imgBtnFriends.setOnClickListener {
            imgPoster.setImageResource(R.drawable.img_back_frame_2)
            imgBtnCatAnim.setImageResource(R.drawable.img_cat_happy)
            txtDialogCat.text = getString(R.string.it_s_look_like_very_cool)


            imgBtnMainPoster.clearColorFilter()
            imgBtnWolf.clearColorFilter()
            imgBtnCat.clearColorFilter()
            imgBtnFriends.setColorFilter(ContextCompat.getColor(this, R.color.pink))
        }
        imgBtnWolf.setOnClickListener {
            imgPoster.setImageResource(R.drawable.img_back_frame_3)
            imgBtnCatAnim.setImageResource(R.drawable.img_cat_scary)
            txtDialogCat.text = getString(R.string.ssss_soo_scray_wolf)


            imgBtnMainPoster.clearColorFilter()
            imgBtnFriends.clearColorFilter()
            imgBtnCat.clearColorFilter()
            imgBtnWolf.setColorFilter(ContextCompat.getColor(this, R.color.pink))

        }
        imgBtnCat.setOnClickListener {
            imgPoster.setImageResource(R.drawable.img_back_frame_4)
            imgBtnCatAnim.setImageResource(R.drawable.img_cat_heart)
            txtDialogCat.text = getString(R.string.love_you)


            imgBtnMainPoster.clearColorFilter()
            imgBtnFriends.clearColorFilter()
            imgBtnWolf.clearColorFilter()
            imgBtnCat.setColorFilter(ContextCompat.getColor(this, R.color.pink))


        }
        //buttons choose time


        imgBtnCatAnim.setOnClickListener {
            touchCat++

            if (touchCat == 1) {
                txtDialogCat.text = "I’m not a pillow, you know!"
            }
            if (touchCat == 3) {
                txtDialogCat.text = "I’m not your stress ball!"
                imgBtnCatAnim.setImageResource(R.drawable.img_cat_open_mouth)
            }
            if (touchCat == 5) {
                txtDialogCat.text = "Hey, hands off! I need my personal space!"
            }

            if (touchCat == 7) {
                txtDialogCat.text = "Don't squeeze me like that!"
                imgBtnCatAnim.setImageResource(R.drawable.img_cat_scary)

                imgBtnCatAnim.startAnimation(rotateAnim)
                txtDialogCat.startAnimation(shakeAnim)

                rotateAnim.setAnimationListener(object : AnimationListener {
                    override fun onAnimationStart(p0: Animation?) {
                        txtDialogCat.textSize = 20f
                        //  txtDialogCat.typeface = typeface
                        txtDialogCat.setTypeface(null, android.graphics.Typeface.BOLD)
                    }

                    override fun onAnimationEnd(p0: Animation?) {
                        touchCat = 0
                        txtDialogCat.textSize = 16f
                        txtDialogCat.setTypeface(null, android.graphics.Typeface.NORMAL)
                        txtDialogCat.text = "Meow ,Meow ,meow !"
                        imgBtnCatAnim.setImageResource(R.drawable.img_cat_cool)
                    }

                    override fun onAnimationRepeat(p0: Animation?) {
                    }
                })

            }
        }

        btnEvening.setBackgroundResource(R.drawable.btn_background)
        btnDay.setBackgroundResource(R.drawable.btn_background)
        btnMorning.setBackgroundResource(R.drawable.btn_background)

        btnMorning.setOnClickListener {
            btnMorning.setBackgroundResource(R.drawable.btn_background_pressed)
            btnDay.setBackgroundResource(R.drawable.btn_background)
            btnEvening.setBackgroundResource(R.drawable.btn_background)
            time = "10:20"


        }
        btnDay.setOnClickListener {
            btnMorning.setBackgroundResource(R.drawable.btn_background)
            btnDay.setBackgroundResource(R.drawable.btn_background_pressed)
            btnEvening.setBackgroundResource(R.drawable.btn_background)
            time = "14:45"

        }
        btnEvening.setOnClickListener {
            btnMorning.setBackgroundResource(R.drawable.btn_background)
            btnDay.setBackgroundResource(R.drawable.btn_background)
            btnEvening.setBackgroundResource(R.drawable.btn_background_pressed)
            time = "19:30"

        }


        btnPlusAdultTicket.setOnClickListener {
            adultTicket++
            totalTicket++
            totalPrice += adultPrice
            txtAdultTicket.text = adultTicket.toString()

            txtDialogCat.text = "Cool! One more adult is going to the movie!"
            imgBtnCatAnim.setImageResource(R.drawable.img_cat_cool)
            txtTotalPrice.text = "$totalPrice"
            txtTotalTicket.text = "$totalTicket"

        }
        btnMinusAdultTicket.setOnClickListener {
            if (adultTicket > 0) {
                adultTicket--
                totalTicket--
                totalPrice -= adultPrice
                txtAdultTicket.text = adultTicket.toString()
                txtDialogCat.text = "Oh no! Why don't they want to go to the movie?"
                imgBtnCatAnim.setImageResource(R.drawable.img_cat_sad)

            } else {
                txtDialogCat.text = "Oh... You can't take away -1 adult..."
                imgBtnCatAnim.setImageResource(R.drawable.img_cat_right)
            }
            if(totalPrice<0){
                totalPrice=0
                totalTicket=0
            }
            txtTotalPrice.text = "$totalPrice"
            txtTotalTicket.text = "$totalTicket"


        }
        btnPlusChildTicket.setOnClickListener {
            childTicket++
            totalTicket++
            totalPrice += childPrice
            txtChildTicket.text = childTicket.toString()
            txtDialogCat.text = " Wow! Children will love this movie!"
            imgBtnCatAnim.setImageResource(R.drawable.img_cat_cool)
            txtTotalPrice.text = "$totalPrice"
            txtTotalTicket.text = "$totalTicket"

        }

        btnMinusChildTicket.setOnClickListener {
            if (childTicket > 0) {
                childTicket--
                totalTicket--
                totalPrice -= childPrice
                txtChildTicket.text = childTicket.toString()
                txtDialogCat.text = "Oh no! Why don't they want to go to the movie?"
                imgBtnCatAnim.setImageResource(R.drawable.img_cat_sad)
            } else {
                txtDialogCat.text = "Oh... You can't take away -1 child..."
                imgBtnCatAnim.setImageResource(R.drawable.img_cat_right)

            }
            if(totalPrice<0){
                totalPrice=0
                totalTicket=0
            }
            txtTotalPrice.text = "$totalPrice"
            txtTotalTicket.text = "$totalTicket"

        }


        btnPlusStudent.setOnClickListener {
            studentTicket++
            totalTicket++
            totalPrice += studentPrice
            txtStudentTicket.text = studentTicket.toString()
            txtDialogCat.text = " Hey! I hope they don’t skip any lessons..."
            imgBtnCatAnim.setImageResource(R.drawable.img_cat_cool)

            txtTotalPrice.text = "$totalPrice"
            txtTotalTicket.text = "$totalTicket"

        }
        btnMinusStudent.setOnClickListener {
            if (studentTicket > 0) {
                studentTicket--
                totalTicket--
                totalPrice -= studentPrice

                txtStudentTicket.text = studentTicket.toString()
                txtDialogCat.text = "Oh no! Why don't they want to go to the movie?"
                imgBtnCatAnim.setImageResource(R.drawable.img_cat_sad)
            } else {
                txtDialogCat.text = "Oh... You can't take away -1 student..."
                imgBtnCatAnim.setImageResource(R.drawable.img_cat_right)
            }
            if(totalPrice<0){
                totalPrice=0
                totalTicket=0
            }
            txtTotalPrice.text = "$totalPrice"
            txtTotalTicket.text = "$totalTicket"
        }

        fun customAlertDialogOrder() {
            val builder = AlertDialog.Builder(this)
            val inflater = layoutInflater
            val dialogLayout = inflater.inflate(R.layout.alert_dialog_order, null)


            val txtDate = dialogLayout.findViewById<TextView>(R.id.txt_alert_date)
            val txtTime = dialogLayout.findViewById<TextView>(R.id.txt_alert_time)
            val txtCinema = dialogLayout.findViewById<TextView>(R.id.txt_alert_cinema)
            val txtPrice = dialogLayout.findViewById<TextView>(R.id.alert_txt_price)
            val txtAmount = dialogLayout.findViewById<TextView>(R.id.alert_txt_total_amount)
            val txtDiscount = dialogLayout.findViewById<TextView>(R.id.txt_alert_discount)
            val btnReadyOrder = dialogLayout.findViewById<Button>(R.id.btn_order)


            txtPrice.text = buildString {
                append(txtPrice.text.toString())
                if (discount) {
                    append((totalPrice * 0.9).toInt())
                }
                else {
                    append(totalPrice.toString())
                }
            }


            txtCinema.text = buildString {
                append(txtCinema.text.toString())
                append(spin.selectedItem.toString())
            }
            txtDate.text = buildString {
                append(txtDate.text.toString())
                append(txt_date.text.toString())
            }
            txtTime.text = buildString {
                append(txtTime.text.toString())
                append(time.toString())
            }
            txtAmount.text = buildString {
                append(txtAmount.text.toString())
                append(totalTicket.toString())
            }

            if (discount) {
                txtDiscount.text = buildString {
                    append(txtDiscount.text.toString())
                    append("-10%")
                }
            }

            val alertDialog = builder.setView(dialogLayout).create()
            btnReadyOrder.setOnClickListener {
                discount=false
                txt_date.text = ""
                time = ""
                spin.setSelection(0)
                txtDialogCat.text = "Meow ,Meow ,meow !"
                imgBtnCatAnim.setImageResource(R.drawable.img_cat_cool)
                txtTotalPrice.text = "0"
                txtTotalTicket.text = "0"
                txtAdultTicket.text = "0"
                txtChildTicket.text = "0"
                txtStudentTicket.text = "0"
                btnDay.setBackgroundResource(R.drawable.btn_background)
                btnMorning.setBackgroundResource(R.drawable.btn_background)
                btnEvening.setBackgroundResource(R.drawable.btn_background)
                alertDialog.dismiss()
            }
            alertDialog.show()
        }


        fun customAlertDialogDiscount() {
            val builder = AlertDialog.Builder(this)
            val inflater = layoutInflater
            val dialogLayout = inflater.inflate(R.layout.alert_dialog_discount, null)
            val txtMessage = dialogLayout.findViewById<TextView>(R.id.txt_message)
            val btnNoDiscount = dialogLayout.findViewById<Button>(R.id.btn_no_discount)
            val imgCat = dialogLayout.findViewById<ImageView>(R.id.img_cat)
            val btnImgFood = dialogLayout.findViewById<ImageView>(R.id.img_btn_food)
            val animLift = AnimationUtils.loadAnimation(this, R.anim.lift_anim)
            btnImgFood.startAnimation(animLift)
            val alertDialog = builder.setView(dialogLayout).create()
            btnNoDiscount.setOnClickListener {

                alertDialog.dismiss()
                customAlertDialogOrder()
            }
            btnImgFood.setOnClickListener {
                txtMessage.text = "Hey you have your discount!"
                imgCat.setImageResource(R.drawable.img_cat_heart)
                btnImgFood.clearAnimation()
                btnImgFood.visibility = View.GONE
                btnNoDiscount.text = "I ready to order with discount!"
                discount = true
            }
            alertDialog.show()
        }



        btnReadyOrder.setOnClickListener {
                if (adultTicket == 0 && childTicket == 0 && studentTicket == 0) {
                    txtDialogCat.text = "You don't take any tickets!"
                    imgBtnCatAnim.setImageResource(R.drawable.img_cat_cry)
                } else {
                    if (txt_date.text == "") {
                        txtDialogCat.text = "You don't choose a date!"
                        imgBtnCatAnim.setImageResource(R.drawable.img_cat_sad)
                    } else {
                        if (time == "") {
                            txtDialogCat.text = "You don't choose a time!"
                            imgBtnCatAnim.setImageResource(R.drawable.img_cat_sad)
                        } else {
                            if (spin.selectedItemPosition == 0) {
                                txtDialogCat.text = "You don't choose a cinema!"
                                imgBtnCatAnim.setImageResource(R.drawable.img_cat_sad)
                            } else {
                                txtDialogCat.text = "Your order is ready!"
                                imgBtnCatAnim.setImageResource(R.drawable.img_cat_happy)

                                if(discount){
                                    customAlertDialogOrder()
                                }
                                else{
                                    customAlertDialogDiscount()
                                }


                            }
                        }
                    }
                }
            }
        }


    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}

