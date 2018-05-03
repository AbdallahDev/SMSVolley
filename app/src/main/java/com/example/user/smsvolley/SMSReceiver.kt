package com.example.user.smsvolley

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsManager
import android.telephony.SmsMessage
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

/**
 * Created by user on 12/3/2017.
 */

class SMSReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context, p1: Intent) {

        var bundle = p1.extras

        var pdus = bundle.get("pdus") as Array<Any>
        var mobile: String = ""
        var text: String = ""
        var result: String = ""

        for (x in 0..pdus.size - 1) {
            var msg = SmsMessage.createFromPdu(pdus[x] as ByteArray)
            mobile = msg.originatingAddress
            text = msg.displayMessageBody
        }

        Toast.makeText(p0, mobile + text, Toast.LENGTH_LONG).show()

        var url: String = "http://pioneersacademyproject.com/find_item.php?id=" + text
        var rq = Volley.newRequestQueue(p0)
        var jor = JsonObjectRequest(Request.Method.GET, url, null,
                Response.Listener { response ->
                    result = response.getString("name") + "\n" +
                            response.getString("price")
                    var manager = SmsManager.getDefault()
                    manager.sendTextMessage(mobile, null,
                            result, null, null)
                },
                Response.ErrorListener { error -> })

        rq.add(jor)

    }
}
