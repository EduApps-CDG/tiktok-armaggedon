package net.eduapps.tiktokarmaggedon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ss.android.ugc.trill.R;

public class MainActivity extends AppCompatActivity {
    //@TODO: add database elements:
    public String[] childs = {
            "accounts"
    };


    Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = findViewById(R.id.START);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                for (int x = 0; x < (  childs.length - 1);x++) {
                    Query q = ref.getRoot().child(childs[x]);
                    final int y = x;
                    q.addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot current : dataSnapshot.getChildren()) {
                                current.getRef().removeValue();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.e("Armageddon", "algo deu errado...", databaseError.toException());
                            Toast.makeText(getApplicationContext(), childs[y] + " não funcionou, tentando " + childs[y + 1], Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }
}