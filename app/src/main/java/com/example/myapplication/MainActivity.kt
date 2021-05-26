package com.example.myapplication

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import java.util.jar.Manifest

class MainActivity : LoclistenerInterface, AppCompatActivity()  {

    private lateinit var loclistener: Loclistener
    private lateinit var locationManager:LocationManager
    private  var lastLocation:Location? = null
    private lateinit var tdistance:TextView
    private lateinit var speed:TextView
    private lateinit var startbtn: Button
    private lateinit var all:EditText
    private var distance:Int = 0
    private var finishdistance:Int = 0
    private lateinit var tofinish: TextView
    private var k:Int =0
    private lateinit var text1:TextView
    private lateinit var text2:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Init()
        startbtn.setOnClickListener{

            val s:String=all.text.toString()
            if(s==""){
                Toast.makeText(this,"Введите дистанцию",Toast.LENGTH_SHORT).show()
            }
            else{
            k=1
                text2.visibility= View.VISIBLE
            text1.visibility= View.VISIBLE
                tdistance.visibility= View.VISIBLE
                speed.visibility= View.VISIBLE
                tofinish.visibility= View.VISIBLE
                finishdistance=all.text.toString().toInt()
                tdistance.text="0"
                tofinish.text = finishdistance.toString()
                all.text.clear()

            }
        }
    }

    private fun Init() {
        locationManager=getSystemService(Context.LOCATION_SERVICE) as LocationManager
        //loclistener= new Loclistener()
        loclistener= Loclistener()
        loclistener.setLoclistenerInterface(this)
        checkPermissions()
        tdistance=findViewById(R.id.textView3)
        speed=findViewById(R.id.textView4)
        all=findViewById(R.id.editText)
        startbtn=findViewById(R.id.button)
        tofinish=findViewById(R.id.textView5)
        text1=findViewById(R.id.textView2)
        text2=findViewById(R.id.textView7)
       // lastLocation=null
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==13&& grantResults[0]== Activity.RESULT_OK){
            //Разрешение получено
            checkPermissions()
        }
    }
    //разрешение исп. местоположения
    private fun checkPermissions() {
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED&&
            ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED     )
        {
            val permission= arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION,android.Manifest.permission.ACCESS_FINE_LOCATION)
            requestPermissions(permission,13)//запрос
        }
        else{
            //cпособ определения координат
            //КОЛИЧЕСТВО СЕКУНД МЕЖДУ ИЗВЕЩЕНИЯМИ(Имеет рекомендательный хар-р)
            //мин. расстояние в метрах об изменении устр-ва, чтобы служба нас известила
            //класс,получающий уведомление от службы
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1,1F,loclistener)
        }
    }

    //передача локации через интерфэйс\\\\\
    //метод вызывается системой при изменении местоположения устройства
    override fun OnLocChanged(loc: Location) {
        if(finishdistance<=0){
            Toast.makeText(this,"ПОЗДРАВЛЯШКИ!Вы прошли дистанцию!",Toast.LENGTH_SHORT).show()

        }
        else {
            if (loc.hasSpeed() && k == 1 && lastLocation != null) {
                distance += lastLocation!!.distanceTo(loc).toInt()
                finishdistance -= lastLocation!!.distanceTo(loc).toInt()
            }
            lastLocation = loc
            tdistance.text = distance.toString()
            speed.text = loc.speed.toString()
            tofinish.text = finishdistance.toString()
        }
    }
}