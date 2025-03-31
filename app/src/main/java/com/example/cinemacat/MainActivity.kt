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
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cinemacat.R.anim.rotate_anim
import com.example.cinemacat.R.anim.shake_anim
import java.util.Calendar


class MainActivity : AppCompatActivity(),View.OnClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // list for spinner - choice of cinema.
         val cinema = arrayOf(
             getString(R.string.your_cinemacat),
             getString(R.string.cinemacat_holon),
             getString(R.string.cinemacat_haifa),
             getString(R.string.cinemacat_tel_aviv), getString(R.string.cinemacat_jerusalem),
             getString(R.string.cinemacat_raanana), getString(R.string.cinemacat_beer_sheva)
        )


        var touchCat = 0
        val checkBWheelChair = findViewById<CheckBox>(R.id.check_wheel_chair)
        val checkBAudio = findViewById<CheckBox>(R.id.check_audio)
        val checkBSubtitles = findViewById<CheckBox>(R.id.check_subtitles)
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
        val imgBtnExit = findViewById<ImageView>(R.id.img_btn_exit)
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

//ExitAlertDialog
        val builderExit = AlertDialog.Builder(this)//builder for exit
        imgBtnExit.setOnClickListener {
             builderExit.apply {
                setTitle(getString(R.string.exit))
                setMessage(getString(R.string.are_you_sure_you_want_to_exit))
                 //couldn't close it
                setCancelable(false)
                setPositiveButton(getString(R.string.yes)) { _, _ ->
                    finish()
                }
                setNegativeButton(getString(R.string.no)) { dialog, _ ->
                    dialog.dismiss()
                }
                 builderExit.create()
                 builderExit.show()
            }
        }

            //Spinner
            val spin = findViewById<Spinner>(R.id.spinner_cinema)
            val adapter = ArrayAdapter(this, R.layout.custom_spinner_item, cinema)
           //change item's background to custom spinner and custom text in spinner (list)
            adapter.setDropDownViewResource(R.layout.custom_spinner_item)
            spin.adapter = adapter

            spin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    //Take selected item from spinner and check if it chose some item from list
                    val selectedItem = parent?.getItemAtPosition(position).toString()
                    if (selectedItem != "@string/your_cinemacat") {
                        imgBtnCatAnim.setImageResource(R.drawable.img_cat_cool)
                        txtDialogCat.text = getString(R.string.cool_it_s_a_great_place)

                    }
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }





// ImageButtons - change main poster and reaction of cat in cat dialog, also add a tint for an Image that chose
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

        // Animation for cat  - if we tap the image button 4 times - the animation start.
        // If we tap it less - change image and text

            imgBtnCatAnim.setOnClickListener {
                touchCat++

                if (touchCat == 1) {
                    txtDialogCat.text = getString(R.string.i_m_not_a_pillow_you_know)
                    imgBtnCatAnim.setImageResource(R.drawable.img_cat_sad)
                }
                if (touchCat == 2) {
                    txtDialogCat.text = getString(R.string.i_m_not_your_stress_ball)
                    imgBtnCatAnim.setImageResource(R.drawable.img_cat_open_mouth)
                }
                if (touchCat == 3) {
                    txtDialogCat.text = getString(R.string.hey_hands_off_i_need_my_personal_space)
                    imgBtnCatAnim.setImageResource(R.drawable.img_cat_sad)
                }

                if (touchCat == 4) {
                    txtDialogCat.text = getString(R.string.don_t_squeeze_me_like_that)
                    imgBtnCatAnim.setImageResource(R.drawable.img_cat_scary)
                    //start  2 animation
                    imgBtnCatAnim.startAnimation(rotateAnim)
                    txtDialogCat.startAnimation(shakeAnim)

                     // when animation start - text change
                    rotateAnim.setAnimationListener(object : AnimationListener {
                        override fun onAnimationStart(p0: Animation?) {
                            txtDialogCat.textSize = 20f
                            txtDialogCat.setTypeface(null, android.graphics.Typeface.BOLD)
                        }

                        override fun onAnimationEnd(p0: Animation?) {
                            touchCat = 0 // reset counter of taps cat (image button)
                            txtDialogCat.textSize = 16f
                            txtDialogCat.setTypeface(null, android.graphics.Typeface.NORMAL)
                            txtDialogCat.text = getString(R.string.meow_meow_meow)
                            imgBtnCatAnim.setImageResource(R.drawable.img_cat_cool)
                        }

                        override fun onAnimationRepeat(p0: Animation?) {
                        }
                    })

                }

            }

        //Calendar
        val txtDate = findViewById<TextView>(R.id.txt_date)
        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        val selectedDate = Calendar.getInstance()

        //we couldn't take a day in tne past
        val today = Calendar.getInstance()
        calendarView.minDate = today.timeInMillis

        // the max day ? that we can chose
        val maxCalendar = Calendar.getInstance().apply {
            set(2025, Calendar.APRIL, 30) }
        calendarView.maxDate = maxCalendar.timeInMillis

        //When we chose a date of cinema
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDate.set(year, month, dayOfMonth)
            txtDate.text = "$dayOfMonth/${month + 1}/$year"

            txtDialogCat.text = getString(R.string.you_chose_a_perfect_day)

            // Take a date of today for check the time (if  we choose today - need to check the time - we couldn't take a time in the past )
            val isToday = today.get(Calendar.YEAR) == year &&
                    today.get(Calendar.MONTH) == month &&
                    today.get(Calendar.DAY_OF_MONTH) == dayOfMonth
            val currentHour = today.get(Calendar.HOUR_OF_DAY)
            val currentMinute = today.get(Calendar.MINUTE)

            if (isToday) {
                //The numbers - it is a hours and minutes of film in cinema.
                //Need to check if we can order a ticket
                if (currentHour > 19 || (currentHour == 19 && currentMinute >= 30)) {
                    //if it is very late - we could't order a ticket today , and all buttons is not enabled
                    btnMorning.isEnabled = false
                    btnDay.isEnabled = false
                    btnEvening.isEnabled = false
                    btnReadyOrder.isEnabled = false
                    txtDialogCat.text = getString(R.string.we_don_t_have_any_movies_in_this_time)
                    txtDialogCat.setTextColor(ContextCompat.getColor(this, R.color.red))
                } else {
                    //check the hours and the minutes andif it true -btn isEnabled= true
                    btnMorning.isEnabled = currentHour < 10 || (currentHour == 10 && currentMinute < 20)
                    btnDay.isEnabled = currentHour < 14 || (currentHour == 14 && currentMinute < 45)
                    btnEvening.isEnabled = currentHour < 19 || (currentHour == 19 && currentMinute < 30)

                    //The btnReadyOrder button becomes active if at least one of the buttons is active (isEnabled = true).
                    btnReadyOrder.isEnabled = btnMorning.isEnabled || btnDay.isEnabled || btnEvening.isEnabled
                }
            } else {
                //if we take a date in the future - all buttons is enabled
                btnMorning.isEnabled = true
                btnDay.isEnabled = true
                btnEvening.isEnabled = true
                btnReadyOrder.isEnabled = true
                txtDialogCat.setTextColor(ContextCompat.getColor(this, R.color.black))
            }
        }

        //chosen the time
        btnMorning.setOnClickListener {
                btnMorning.setBackgroundResource(R.drawable.btn_background_pressed)
                btnDay.setBackgroundResource(R.drawable.btn_background)
                btnEvening.setBackgroundResource(R.drawable.btn_background)
                imgBtnCatAnim.setImageResource(R.drawable.img_cat_happy)
                time = getString(R.string.time_10_20)
            }
        btnDay.setOnClickListener {
                btnMorning.setBackgroundResource(R.drawable.btn_background)
                btnDay.setBackgroundResource(R.drawable.btn_background_pressed)
                btnEvening.setBackgroundResource(R.drawable.btn_background)
                imgBtnCatAnim.setImageResource(R.drawable.img_cat_happy)
                time = getString(R.string.time_14_45)
        }
        btnEvening.setOnClickListener {
                btnMorning.setBackgroundResource(R.drawable.btn_background)
                btnDay.setBackgroundResource(R.drawable.btn_background)
                btnEvening.setBackgroundResource(R.drawable.btn_background_pressed)
                time = getString(R.string.time_19_30)
                imgBtnCatAnim.setImageResource(R.drawable.img_cat_happy)
        }

        //Add a ticket for adult
            btnPlusAdultTicket.setOnClickListener {
                adultTicket++
                totalTicket++
                totalPrice += adultPrice
                txtAdultTicket.text = "$adultTicket"
                txtDialogCat.text = getString(R.string.cool_one_more_adult_is_going_to_the_movies)
                imgBtnCatAnim.setImageResource(R.drawable.img_cat_cool)
                txtTotalPrice.text = "$totalPrice"
                txtTotalTicket.text = "$totalTicket"
            }
        //Minus a ticket for adult
            btnMinusAdultTicket.setOnClickListener {
                if (adultTicket > 0) {
                    adultTicket--
                    totalTicket--
                    totalPrice -= adultPrice
                    txtAdultTicket.text = "$adultTicket"
                    txtDialogCat.text = getString(R.string.why_don_t_they_want_to_go_to_the_movies)
                    imgBtnCatAnim.setImageResource(R.drawable.img_cat_sad)
                } else {
                    //Couldn't remove a ticket for adult if it 0
                    txtDialogCat.text = getString(R.string.oh_you_can_t_remove_1_adult)
                    imgBtnCatAnim.setImageResource(R.drawable.img_cat_right)
                }
                if (totalPrice < 0) {
                    totalPrice = 0
                    totalTicket = 0
                }
                txtTotalPrice.text = "$totalPrice"
                txtTotalTicket.text = "$totalTicket"

            }

            //Add a ticket for child
            btnPlusChildTicket.setOnClickListener {
                childTicket++
                totalTicket++
                totalPrice += childPrice
                txtChildTicket.text = "$childTicket"
                txtDialogCat.text = getString(R.string.wow_children_will_love_this_movie)
                imgBtnCatAnim.setImageResource(R.drawable.img_cat_cool)
                txtTotalPrice.text = "$totalPrice"
                txtTotalTicket.text = "$totalTicket"

            }

            //Remove a ticket for child
            btnMinusChildTicket.setOnClickListener {
                if (childTicket > 0) {
                    childTicket--
                    totalTicket--
                    totalPrice -= childPrice
                    txtChildTicket.text = "$childTicket"
                    txtDialogCat.text =
                        getString(R.string.oh_no_why_don_t_they_want_to_go_to_the_movies)
                    imgBtnCatAnim.setImageResource(R.drawable.img_cat_sad)
                } else {
                    txtDialogCat.text = getString(R.string.oh_you_can_t_remove_1_child)
                    imgBtnCatAnim.setImageResource(R.drawable.img_cat_right)

                }
                if (totalPrice < 0) {
                    totalPrice = 0
                    totalTicket = 0
                }
                txtTotalPrice.text = "$totalPrice"
                txtTotalTicket.text = "$totalTicket"

            }


        //Plus a ticket for student
            btnPlusStudent.setOnClickListener {
                studentTicket++
                totalTicket++
                totalPrice += studentPrice
                txtStudentTicket.text = "$studentTicket"
                txtDialogCat.text = getString(R.string.hey_i_hope_they_don_t_skip_any_lessons)
                imgBtnCatAnim.setImageResource(R.drawable.img_cat_cool)

                txtTotalPrice.text = "$totalPrice"
                txtTotalTicket.text = "$totalTicket"

            }
        //Minus a ticket for student
            btnMinusStudent.setOnClickListener {
                if (studentTicket > 0) {
                    studentTicket--
                    totalTicket--
                    totalPrice -= studentPrice

                    txtStudentTicket.text = "$studentTicket"
                    txtDialogCat.text =
                        getString(R.string.oh_no_why_don_t_they_want_to_go_to_the_movie)
                    imgBtnCatAnim.setImageResource(R.drawable.img_cat_sad)
                } else {
                    txtDialogCat.text = getString(R.string.oh_you_can_t_remove_1_student)
                    imgBtnCatAnim.setImageResource(R.drawable.img_cat_right)
                }
                if (totalPrice < 0) {
                    totalPrice = 0
                    totalTicket = 0
                }
                txtTotalPrice.text = "$totalPrice"
                txtTotalTicket.text = "$totalTicket"
            }


        //Custom Dialog with use alert_dialog_order with text, image and button for comfirm
            fun customAlertDialogOrder() {
                val builderAlertOrder = AlertDialog.Builder(this)
                val inflater = layoutInflater
                val dialogLayout = inflater.inflate(R.layout.alert_dialog_order, null)

                val txtAlertDate = dialogLayout.findViewById<TextView>(R.id.txt_alert_date)
                val txtTime = dialogLayout.findViewById<TextView>(R.id.txt_alert_time)
                val txtCinema = dialogLayout.findViewById<TextView>(R.id.txt_alert_cinema)
                val txtPrice = dialogLayout.findViewById<TextView>(R.id.alert_txt_price)
                val txtAmount = dialogLayout.findViewById<TextView>(R.id.alert_txt_total_amount)
                val txtDiscount = dialogLayout.findViewById<TextView>(R.id.txt_alert_discount)
                val btnAlertReadyOrder = dialogLayout.findViewById<Button>(R.id.btn_order)
                val txtAddInfo = dialogLayout.findViewById<TextView>(R.id.txt_add_info)
            //if we have a discount - change a price
                txtPrice.text = buildString {
                    append(txtPrice.text.toString())
                    if (discount) {
                        append((totalPrice * 0.9).toInt())
                    } else {
                        append(totalPrice.toString())
                    }
                }
            //set text for confirm order
                txtCinema.text = buildString {
                    append(txtCinema.text.toString())
                    append(spin.selectedItem.toString())
                }
                txtAlertDate.text = buildString {
                    append(txtAlertDate.text.toString())
                    append(txtDate.text.toString())
                }

                txtTime.text = buildString {
                    append(txtTime.text.toString())
                    append(time.toString())
                }
                txtAmount.text = buildString {
                    append(txtAmount.text.toString())
                    append(totalTicket.toString())
                }
            //if have an a discount
                if (discount) {
                    txtDiscount.text = buildString {
                        append(txtDiscount.text.toString())
                        append("-10%")
                    }
                }
            //check of CheckBox
                if(checkBWheelChair.isChecked || checkBAudio.isChecked || checkBSubtitles.isChecked){
                    txtAddInfo.text = buildString {
                        append(txtAddInfo.text.toString())
                        append(getString(R.string.we_will_contact_you_for_further_assistance))

                    }
                }
                else{
                    txtAddInfo.text= getString(R.string.space)
                }
            //if we ordered the ticket - we need to restart all
                val alertDialog = builderAlertOrder.setView(dialogLayout).create()
                btnAlertReadyOrder.setOnClickListener {
                    discount = false
                    txtDate.text = getString(R.string.space)
                    time = getString(R.string.space)
                    spin.setSelection(0)
                    txtDialogCat.text = getString(R.string.meow_meow_meow)
                    imgBtnCatAnim.setImageResource(R.drawable.img_cat_cool)
                    childTicket = 0
                    adultTicket = 0
                    studentTicket = 0
                    totalPrice = 0
                    totalTicket = 0
                    txtTotalPrice.text = "$totalPrice"
                    txtTotalTicket.text = "$totalTicket"
                    txtAdultTicket.text = "$adultTicket"
                    txtChildTicket.text = "$childTicket"
                    txtStudentTicket.text = "$studentTicket"
                    btnDay.setBackgroundResource(R.drawable.btn_background)
                    btnMorning.setBackgroundResource(R.drawable.btn_background)
                    btnEvening.setBackgroundResource(R.drawable.btn_background)
                    alertDialog.dismiss()
                    checkBWheelChair.isChecked = false
                    checkBAudio.isChecked = false
                    checkBSubtitles.isChecked = false

                    Toast.makeText(this,
                        getString(R.string.order_is_ready_see_you_soon), Toast.LENGTH_SHORT).show()
                }
                alertDialog.show()
            }
        //first alert dialog - we need to tap the button with food for taking a discount
        //Also, use the custom AlertDialog
            fun customAlertDialogDiscount() {
                val inflater = layoutInflater
                val dialogLayout = inflater.inflate(R.layout.alert_dialog_discount, null)
                val txtMessage = dialogLayout.findViewById<TextView>(R.id.txt_message)
                val btnNoDiscount = dialogLayout.findViewById<Button>(R.id.btn_no_discount)
                val imgCat = dialogLayout.findViewById<ImageView>(R.id.img_cat)
                val btnImgFood = dialogLayout.findViewById<ImageView>(R.id.img_btn_food)
                val animLift = AnimationUtils.loadAnimation(this, R.anim.lift_anim)
            //start animation
                btnImgFood.startAnimation(animLift)
                val alertDialog = AlertDialog.Builder(this).setView(dialogLayout).create()
            //if don't take a discount - close customAlertDialogDiscount and open customAlertDialogOrder
                btnNoDiscount.setOnClickListener {
                    alertDialog.dismiss()
                    customAlertDialogOrder()
                }
            // change text if we tap the button with food
                btnImgFood.setOnClickListener {
                    txtMessage.text = getString(R.string.hey_you_have_your_discount)
                    imgCat.setImageResource(R.drawable.img_cat_heart)
                    btnImgFood.clearAnimation()
                    btnImgFood.visibility = View.GONE
                    btnNoDiscount.text = getString(R.string.i_m_ready_to_order_with_a_discount)
                    discount = true
                }
                alertDialog.show()
            }

        //Check if all fields are filled and change errors and image if not
            btnReadyOrder.setOnClickListener {
                if (adultTicket == 0 && childTicket == 0 && studentTicket == 0) {
                    txtDialogCat.text = getString(R.string.you_haven_t_taken_any_tickets)
                    imgBtnCatAnim.setImageResource(R.drawable.img_cat_cry)
                } else {
                    if (txtDate.text == getString(R.string.space)) {
                        txtDialogCat.text = getString(R.string.you_haven_t_chosen_a_date)
                        imgBtnCatAnim.setImageResource(R.drawable.img_cat_sad)
                    } else {
                        if (time == getString(R.string.space)) {
                            txtDialogCat.text = getString(R.string.you_haven_t_chosen_a_time)
                            imgBtnCatAnim.setImageResource(R.drawable.img_cat_sad)
                        } else {
                            if (spin.selectedItemPosition == 0) {
                                txtDialogCat.text = getString(R.string.you_haven_t_chosen_a_cinema)
                                imgBtnCatAnim.setImageResource(R.drawable.img_cat_sad)
                            } else {
                                txtDialogCat.text = getString(R.string.your_order_is_ready)
                                imgBtnCatAnim.setImageResource(R.drawable.img_cat_happy)

                                if (discount) {
                                    customAlertDialogOrder()
                                } else {
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

