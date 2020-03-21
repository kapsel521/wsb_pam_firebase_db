package pl.mkonkel.wsb.firebasedb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.widget.Button
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*
import pl.mkonkel.wsb.firebasedb.model.Note
import timber.log.Timber
import java.util.*

class MainActivity : AppCompatActivity() {
    private val db = FirebaseDatabase.getInstance().reference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        databadeListener()
        button_add_note.setOnClickListener{
            val title = note_title.text.toString()
            val body = note_body.text.toString()
            addNote(title, body)
        }
    }

    private fun addNote(title: String?, message: String?) {
        val note = Note(
            title = title,
            message = message
        )

        val uuid = UUID.randomUUID().toString()

        db.child(NOTE)
            .child(uuid)
            .setValue(note)
            .addOnSuccessListener {
                Timber.i("succesfull note adding")
            }
            .addOnFailureListener {
                Timber.e("Faliure during adding note")
            }

    }

    private fun databadeListener(){
        db.child(NOTE)
            .addValueEventListener(
                object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        Timber.e("request was canceled")
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                    Timber.i( "something was changed")
                    }

                }
            )
    }


//    TODO: Add DatabaseListener
//    Get child("childName") - first branch
//    Get child("branchName") - get element in this branch
//    addValueEventListener
//
//    onDataChane() will be invoked when any data will be modified in whole child branch.
//    You can print changed data in the log using Timber.i(data)

//    TODO: Add Helper method for adding "news" to the DB.
//    child() - go to notes branch
//    child() - go to element in branch (ec. User One notes).
//    child() - value in branch
//    setValue(givenValue)
//
//    You can additional add onSuccess and onFailureListener


    companion object{
        const val NOTE = "note"
    }
}
