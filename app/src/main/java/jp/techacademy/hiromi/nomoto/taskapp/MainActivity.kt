package jp.techacademy.hiromi.nomoto.taskapp
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import io.realm.RealmChangeListener
import io.realm.Sort
import java.util.*

import android.content.Intent
import androidx.appcompat.app.AlertDialog
import android.app.AlarmManager
import android.app.PendingIntent


const val EXTRA_TASK ="jp.techacademy.hiromi.nomoto.taskapp.TASK"


/*TaskAdapterを保持するプロパティを定義する
onCreateメソッドでTaskAdapterを生成する
TaskAdapterにデータを設定、ListViewにTaskAdapterを設定、再描画するreloadListViewメソッドを追加する
onCreateメソッドでreloadListViewメソッドを呼び出す
reloadListViewメソッドではAdapterにデータを設定しなおしたあとに、notifyDataSetChangedメソッドを呼び出すことでデータが変わったことを伝えてリストを再描画します
*/


class MainActivity : AppCompatActivity() {
    private lateinit var mRealm: Realm
    private val mRealmListener = object : RealmChangeListener<Realm> {
        override fun onChange(element: Realm){
            reloadListView()
        }
    }

    private lateinit var mTaskAdapter: TaskAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab.setOnClickListener { view ->
            val intent = Intent(this, InputActivity::class.java)
            startActivity(intent)
            //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
              //  .setAction("Action", null).show()
        }

        //Realmの設定
        mRealm = Realm.getDefaultInstance()
        mRealm.addChangeListener(mRealmListener)

        //ListViewの設定
        mTaskAdapter = TaskAdapter(this)

        // ListViewをタップしたときの処理
        //listView1.setOnItemClickListener { parent, view, position, id ->
        listView1.setOnItemClickListener{ parent, _, position, _, ->
            //入力・編集する画面に遷移させる
            val task = parent.adapter.getItem(position) as Task
            val intent = Intent(this, InputActivity::class.java)
            intent.putExtra(EXTRA_TASK, task.id)
            startActivity(intent)
        }

        // ListViewを長押ししたときの処理
        //listView1.setOnItemLongClickListener { parent, view, position, id ->
        listView1.setOnItemLongClickListener { parent, _, position, _ ->
            // タスクを削除する
            val task = parent.adapter.getItem(position) as Task
            //ダイアログを表示する
            val builder = AlertDialog.Builder(this)

            builder.setTitle("削除")
            builder.setMessage(task.title + "を削除しますか")

            builder.setPositiveButton("OK"){ _, _, ->

                val results = mRealm.where(Task::class.java).equalTo("id", task.id).findAll()

                mRealm.beginTransaction()
                results.deleteAllFromRealm()
                mRealm.commitTransaction()

                val resultIntent = Intent(applicationContext, TaskAlarmReceiver::class.java)
                val resultPendingIntent = PendingIntent.getBroadcast(
                    this,
                    task.id,
                    resultIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )

                val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
                alarmManager.cancel(resultPendingIntent)



                reloadListView()

            }
            builder.setNegativeButton("CANCEL", null)

            val dialog = builder.create()
            dialog.show()

            true

        }

// アプリ起動時に表示テスト用のタスクを作成する
        //addTaskForTest()

        reloadListView()

    }

    private fun reloadListView() {

        //後でTaskクラスに変更する
        //val taskList = mutableListOf("aaaa", "bbb", "ccc")
// Realmデータベースから、「すべてのデータを取得して新しい日時順に並べた結果」を取得
        val taskRealmResults = mRealm.where(Task::class.java).findAll().sort("date",Sort.DESCENDING)

        // 上記の結果を、TaskListとしてセットする
        mTaskAdapter.mTaskList = mRealm.copyFromRealm(taskRealmResults)


        // TaskのListView用のアダプタに渡す
        //mTaskAdapter.mTaskList = taskList
        listView1.adapter = mTaskAdapter


        // 表示を更新するために、アダプターにデータが変更されたことを知らせる
        mTaskAdapter.notifyDataSetChanged()
    }


override fun onDestroy(){
        super.onDestroy()

        mRealm.close()
    }

//private fun addTaskForTest(){
  //  val task = Task()
   // task.title = "作業"
    //task.contents = "プログラムを書いてPUSHする"
    //task.date = Date()
    //task.id = 0
    //mRealm.beginTransaction()
    //mRealm.copyToRealmOrUpdate(task)
    //mRealm.commitTransaction()

}



