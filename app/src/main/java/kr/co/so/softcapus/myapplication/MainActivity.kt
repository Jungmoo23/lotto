package kr.co.so.softcapus.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {

    private val cleanbtn : Button by lazy{
        findViewById<Button>(R.id.cleanbtn)
    }
    private val btn1 : Button by lazy{
        findViewById<Button>(R.id.btn1)
    }

    private val RunButton : Button by lazy{
        findViewById<Button>(R.id.runbtn)
    }
    private val numberPicke : NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberpicker)
    }

    private  val numberTextviewList: List<TextView> by lazy {
        listOf<TextView>(
            findViewById(R.id.textview1),
            findViewById(R.id.textview2),
            findViewById(R.id.textview3),
            findViewById(R.id.textview4),
            findViewById(R.id.textview5),
            findViewById(R.id.textview6),
            )
    }
    private  var didrun = false

    private val pickNumber = hashSetOf<Int>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberPicke.minValue = 1
        numberPicke.maxValue = 45

        initRunButton()
        initAddButton()
        initcleanButton()
    }
    private fun initRunButton(){
        RunButton.setOnClickListener{
            val list = getRandomNumber()
            Log.d("jm",list.toString())
            didrun =true

            list.forEachIndexed{
                index, number ->
                val textview = numberTextviewList[index]
                textview.text = number.toString()
                textview.isVisible = true

                setNumberBackground(number, textview)

            }
        }
    }

    private fun initcleanButton(){

        cleanbtn.setOnClickListener{
            pickNumber.clear()
            numberTextviewList.forEach{
                it.isVisible = false
            }
            didrun =false
        }
    }

    private fun initAddButton(){
        btn1.setOnClickListener{
            if(didrun){
                Toast.makeText(this,"초기화 후에 시도해주세요",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(pickNumber.size >=5){
                Toast.makeText(this,"번호는 5개까지만 선택할 수 있습니다.",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(pickNumber.contains(numberPicke.value)){
                Toast.makeText(this,"이미 선택한 번호 입니다.",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val textView = numberTextviewList[pickNumber.size]
            textView.isVisible = true
            textView.text = numberPicke.value.toString()

            setNumberBackground(numberPicke.value, textView)

            pickNumber.add(numberPicke.value)
        }
    }

    private fun setNumberBackground(number:Int, textView: TextView){
        when(number){
            in 1..9 -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_yellow)
            in 10..19 -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_blue)
            in 20..29 -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_red)
            in 30..39 -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_gray)
            else -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_green)
        }
    }

    private fun getRandomNumber(): List<Int>{
        val numList = mutableListOf<Int>().apply {
            for(i in 1..45){
                if(pickNumber.contains(i)){
                    continue
                }
                this.add(i)
            }
        }
        numList.shuffle()

        val newlist = pickNumber.toList() + numList.subList(0,6 - pickNumber.size)

        return newlist.sorted()

    }

}