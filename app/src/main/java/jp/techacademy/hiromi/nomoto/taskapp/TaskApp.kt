package jp.techacademy.hiromi.nomoto.taskapp

import android.app.Application
import io.realm.Realm

//モデル(Task.kt)と接続するRealmデータベースの準備
//Applicationクラスを継承したTaskAppクラスを作成→アプリケーション要素として指定のためマニフェストに追記
class TaskApp: Application() {
  //onCreateをオーバーライド
  //その中で  Realm.init(this)をしてREALMを初期化

    override fun onCreate(){
        super.onCreate()
        Realm.init(this)

        }

}

