package jp.techacademy.hiromi.nomoto.taskapp
//タスクのタイトル、内容、日時に該当するtitle、contents、dateを定義します。
// また、ユニークなIDを指定するidを定義し、@PrimaryKeyと付けます。

import java.io.Serializable
import java.util.Date
import io.realm.RealmObject

import io.realm.annotations.PrimaryKey

//モデルクラス（データを表現するもの、今回はタスク）

//* Serializableインターフェイスを実装することで?生成したオブジェクトをシリアライズできるようになる

// シリアライズとは、データを丸ごとファイルに保存したり別のアクティビティに渡すことができるようにすること。*/
open class Task : RealmObject() , Serializable{
    var title: String = ""
    var contents: String = ""
    var date: Date = Date()

    //idをプライマリーキーとして設定
    //@PrimaryKeyはRealmがプライマリーキーと判断するために必要なもの
    @PrimaryKey
    var id: Int = 0
}