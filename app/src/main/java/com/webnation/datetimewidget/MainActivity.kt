package com.webnation.datetimewidget

import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DateFormat

class MainActivity : AppCompatActivity() {

    val listFragments = arrayListOf<String>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listFragments.add("Start")
        listFragments.add("End")
        val adapter = SelectTimeFragmentPagerAdapter(listFragments, supportFragmentManager)

        vpPager.adapter = adapter
        vpPager.currentItem = 0
        vpPager.offscreenPageLimit = adapter.count
        tabs.setViewPager(vpPager)
        btnDone.setOnClickListener({ setSaveButton() })


    }

    fun setSaveButton() {
        val fragmentFrom = supportFragmentManager.fragments[0] as FragmentDateTimePicker
        val fragmentTo = supportFragmentManager.fragments[1] as FragmentDateTimePicker
        val dateTimeFrom = fragmentFrom.getDateTimeOfFragment()
        val dateTimeTo = fragmentTo.getDateTimeOfFragment()
        if (dateTimeFrom.isAfter(dateTimeTo)) {

            val dialog = AlertDialog.Builder(this@MainActivity)
                    .setTitle(resources.getString(R.string.error))
                    .setMessage(resources.getString(R.string.from_is_after_to))
                    .setNeutralButton(resources.getString(R.string.ok), DialogInterface.OnClickListener { dialog, id ->
                        dialog.dismiss()
                    })
            dialog.show()

        } else {
            val strDisease1Format = resources.getString(R.string.your_date_range_is)
            var stringDateFrom = ""
            var stringDateTo = ""
            if (!android.text.format.DateFormat.is24HourFormat(this)) {
                stringDateFrom = dateTimeFrom.toString(FragmentDateTimePicker.DATE_FORMATTER_12_HR)
                stringDateTo = dateTimeTo.toString(FragmentDateTimePicker.DATE_FORMATTER_12_HR)

            } else {
                stringDateFrom = dateTimeFrom.toString(FragmentDateTimePicker.DATE_FORMATTER_24_HR)
                stringDateTo = dateTimeTo.toString(FragmentDateTimePicker.DATE_FORMATTER_24_HR)
            }
            val strDateRange1Msg = String.format(strDisease1Format, stringDateFrom, stringDateTo)
            val dialog = AlertDialog.Builder(this@MainActivity)

                    .setTitle(resources.getString(R.string.results))
                    .setMessage(strDateRange1Msg)
                    .setNeutralButton(resources.getString(R.string.ok), DialogInterface.OnClickListener { dialog, id ->
                        dialog.dismiss()
                    })
            dialog.show()
        }


    }

    fun setViewPagerTab(int : Int) {
        vpPager.setCurrentItem(int)
    }



    class SelectTimeFragmentPagerAdapter internal constructor(private val listFragments: ArrayList<String>,
                                                              private val fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

        override fun getCount(): Int {
            return listFragments.size
        }

        // Returns the fragment to display for that page
        override fun getItem(position: Int): Fragment? {
            return newDateTimePickerFragment(listFragments[position])
        }

        // Returns the page title for the top indicator
        override fun getPageTitle(position: Int): CharSequence {
            return listFragments[position]
        }


    }
}
