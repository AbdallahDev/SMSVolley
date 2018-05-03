package com.example.user.smsvolley

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var list = ArrayList<String>()

        var cur = contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null)

        cur.moveToFirst()

        while (cur.isAfterLast == false) {
            var n = cur.getString(cur.getColumnIndex(
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            var p = cur.getString(cur.getColumnIndex(
                    ContactsContract.CommonDataKinds.Phone.NUMBER))
            list.add(n + "\n" + p)
            cur.moveToNext()
        }
        var adp = ArrayAdapter(this,
                android.R.layout.simple_list_item_1, list)
        lv.adapter = adp

    }
}
