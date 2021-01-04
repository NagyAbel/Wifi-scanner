package com.nagyabel.devicedescovery

import android.app.DownloadManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.stealthcopter.networktools.SubnetDevices
import com.stealthcopter.networktools.SubnetDevices.OnSubnetDeviceFound
import com.stealthcopter.networktools.subnet.Device
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.NullPointerException


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //#181B2C
        var num = 0
        var name = "Scanning"
        var devicenr = 0
        // Asynchronously
        // Asynchronously
        var addressses =  mutableListOf("")
        var url = "https://api.maclookup.app/v2/macs/00:00:00:00:00:00?apiKey=01ev6met0wdgtcez0yv2z7qqh201ev6mfcbetxhatp740m1xenypfidlevbfylzq"
        fun updateMac()
        {
            val queue = Volley.newRequestQueue(this)

            for (i in addressses)
           {
            num += 1
            url = "https://api.maclookup.app/v2/macs/"+i+"/company/name?apiKey=01ev6met0wdgtcez0yv2z7qqh201ev6mfcbetxhatp740m1xenypfidlevbfylzq"

            val stringRequest = StringRequest(Request.Method.GET, url,
                Response.Listener<String> { response ->
                    // Display the first 500 characters of the response string.
                    textView4.append(i+"["+response +"]"+ "\n")
                    num -= 1
                    if (num <= 0)
                    {
                        button.isEnabled = true
                    }else
                    {
                        button.isEnabled = false
                    }
                },
               Response.ErrorListener {
                   //textView4.append(i+"\n")
                   num -= 1
                   if (num <= 0)
                   {
                       button.isEnabled = true
                   }else
                   {
                       button.isEnabled = false
                   }

               })

             queue.add(stringRequest)


           }


        }

// Access the RequestQueue through your singleton class.

        button.setOnClickListener()
        {

                button.isEnabled = false


            addressses = mutableListOf("")
            textView2.text = "Connected devices: -"
            textView4.text = ""
            devicenr = 0
            SubnetDevices.fromLocalAddress().findDevices(object : OnSubnetDeviceFound {
                override fun onDeviceFound(device: Device?) {
                    this@MainActivity.runOnUiThread(java.lang.Runnable {
                        if (device?.mac != null)
                        {
                            devicenr = devicenr + 1
                            textView2.text = "Connected devices: " + devicenr.toString()
                            addressses.add(device?.mac.toString())
                            // textView4.append(device?.mac+ "\n")
                        }

                    })

                }


                override fun onFinished(devicesFound: ArrayList<Device?>?) {
                    // Stub: Finished scanning
                    this@MainActivity.runOnUiThread(java.lang.Runnable {
                        // textinfo.text = devicesFound.toString()
                        textView2.text = "Connected devices: " + devicenr.toString()
                        updateMac()

                    })

                }
            })
        }




    }



}