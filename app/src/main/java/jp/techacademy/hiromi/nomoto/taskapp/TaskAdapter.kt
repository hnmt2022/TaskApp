package jp.techacademy.hiromi.nomoto.taskapp


import android.content.Context
import  android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*


class TaskAdapter(context: Context): BaseAdapter() {
    private val mLayoutInflater: LayoutInflater
    var mTaskList= mutableListOf<Task>()

    init {
        this.mLayoutInflater = LayoutInflater.from(context)
    }


    override fun getCount(): Int {
        return mTaskList.size

    }

    override fun getItem(position: Int): Any {
        return mTaskList[position]

    }

    override fun getItemId(position: Int): Long {
        return mTaskList[position].id.toLong()

}
//getViewメソッドを実装します。
//
//そのために、他のxmlリソースのViewを取り扱うための仕組みであるLayoutInflaterをプロパティとして定義しておき、
// コンストラクタを新規に追加して取得しておきます。
//getViewメソッドの引数であるconvertViewがnullのときはLayoutInflaterを使ってsimple_list_item_2からViewを取得します
// （エルビス演算子?:は左辺がnullの時に右辺を返します
//simple_list_item_2はタイトルとサブタイトルがあるセルです。
// まずはString型で保持しているmTaskListから文字列を取得しタイトルを設定するように実装しておきます。
// 後でそれぞれタイトルとサブタイトルにタスクの情報を設定するように修正します
//getViewメソッドの引数であるconvertView、すなわち現在表示しようとしている行がnullかどうか判定を行っているのは、
// BaseAdapterにViewを再利用して描画する仕組みがあるため


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView  ?: mLayoutInflater.inflate(android.R.layout.simple_list_item_2, null)

        val textView1 = view.findViewById<TextView>(android.R.id.text1)
        val textView2 = view.findViewById<TextView>(android.R.id.text2)
        // 後でTaskクラスから情報を取得するように変更する
        textView1.text = mTaskList[position].title

        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.JAPANESE)
        val date = mTaskList[position].date
        textView2.text = simpleDateFormat.format(date)

        return view
    }



    }
