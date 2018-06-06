package com.example.paul.firestoreexample;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";

    private EditText editTexttitle;
    private EditText editTextDescription;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference noteRef = db.collection("Notebook").document("My first note");
    private TextView textViewData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTexttitle =findViewById(R.id.input_edit_text);
        editTextDescription = findViewById(R.id.description_edit_text);
        textViewData = findViewById(R.id.text_view_data);



    }
    public void submit(View v){

        String title = editTexttitle.getText().toString();
        String description = editTextDescription.getText().toString();

        Map<String, Object> mapNote = new HashMap<>();
        mapNote.put(KEY_TITLE,title);
        mapNote.put(KEY_DESCRIPTION,description);

       noteRef.set(mapNote)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this, "Saved", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }

    public void Load(View v){

        noteRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        if(documentSnapshot.exists()){
                            String title = documentSnapshot.getString(KEY_TITLE);
                            String description = documentSnapshot.getString(KEY_DESCRIPTION);

                            textViewData.setText("Title :" + title+ "\n"+ "Description :" +description);


                        }else {
                            Toast.makeText(MainActivity.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());

                    }
                });

    }
}
