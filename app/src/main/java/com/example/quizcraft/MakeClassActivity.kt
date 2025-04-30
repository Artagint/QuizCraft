package com.example.quizcraft

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class MakeClassActivity : AppCompatActivity(){
    // Have the user pick a file and it gets actually uploaded when the "Done" button is pressed
    private val pickFileLauncher = registerForActivityResult(GetContent()){ uri: Uri? ->
        if (uri == null){
            Toast.makeText(this, "Please upload a file", Toast.LENGTH_SHORT).show()
        }
        else{
            pickedFileUri = uri
            Toast.makeText(this, "File ready to upload: ${uri.lastPathSegment}", Toast.LENGTH_SHORT).show()
        }
    }

    lateinit var classNameInput: EditText
    lateinit var uploadFileButton: Button
    lateinit var doneButton: Button
    var pickedFileUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_class)

        classNameInput    = findViewById(R.id.classNameInput)
        uploadFileButton  = findViewById(R.id.uploadFileButton)
        doneButton        = findViewById(R.id.doneButton)

        /*
         * Here we just have the user pick a file, then when "Done" button is clicked, upload it to the DB.
         * I also want the user to be able to upload any file type (pdf, docx,...etc...)
         */
        uploadFileButton.setOnClickListener{
            pickFileLauncher.launch("*/*")
        }
        doneButton.setOnClickListener{
            val className = classNameInput.text.toString().trim()
            val fileUri   = pickedFileUri

            if (className.isEmpty()){
                Toast.makeText(this, "Please enter a class name", Toast.LENGTH_SHORT).show()
            }
            else if (fileUri == null){
                Toast.makeText(this, "Please upload a file", Toast.LENGTH_SHORT).show()
            }
            else{
                /*
                 * Here I check if the class already exists or not. If it does then let the user know to
                 * upload under a different class name, if it doesn't then make the new class and upload
                 * the file for it
                 */
                val firestore    = FirebaseFirestore.getInstance()
                val classDocRef  = firestore.collection("classes").document(className)

                classDocRef.get()
                    .addOnSuccessListener { snapshot ->
                        if (snapshot.exists()){
                            Toast.makeText(
                                this,
                                "“$className” already exists, please choose another name.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        else {
                            // create the class document, then upload the file
                            classDocRef.set(mapOf(
                                "createdAt" to FieldValue.serverTimestamp()
                            ))
                                .addOnSuccessListener {
                                    uploadToFirebase(className, fileUri)
                                }
                                .addOnFailureListener{ e ->
                                    Toast.makeText(
                                        this,
                                        "Couldn’t create class: ${e.message}",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                        }
                    }
                    .addOnFailureListener{ e ->
                        Toast.makeText(
                            this,
                            "Error checking class: ${e.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
            }
        }
    }

    // Upload the class name and file name to the DB, the filename is known by what class name it is linked with
    private fun uploadToFirebase(className: String, fileUri: Uri){
        val filename   = fileUri.lastPathSegment ?: "file"
        val storageRef = FirebaseStorage.getInstance()
            .getReference("classes/$className/$filename")

        storageRef.putFile(fileUri).addOnSuccessListener{taskSnapshot->
            taskSnapshot.storage.downloadUrl.addOnSuccessListener{downloadUrl->
                FirebaseFirestore.getInstance().collection("classes").document(className)
                    .collection("files").add(mapOf(
                        "name"       to filename,
                        "url"        to downloadUrl.toString(),
                        "uploadedAt" to FieldValue.serverTimestamp()
                    ))
                    .addOnSuccessListener{
                        Toast.makeText(this, "Upload complete!", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    .addOnFailureListener{e->
                        Toast.makeText(this, "Save metadata failed: ${e.message}", Toast.LENGTH_LONG).show()
                    }
            }
        }
            .addOnFailureListener{e->
                Toast.makeText(this, "Upload failed: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }
}
