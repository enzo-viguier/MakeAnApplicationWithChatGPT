package fr.umontpellier.myschool;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {

    private Button signUpAsParent, signUpAsStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUpAsParent = findViewById(R.id.btnSignUpAsParent);
        signUpAsStudent = findViewById(R.id.btnSignUpAsStudent);

        signUpAsParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Rediriger vers l'inscription en tant que parent
                 startActivity(new Intent(SignUpActivity.this, SignUpAsParentActivity.class));
            }
        });

        signUpAsStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Rediriger vers l'inscription en tant qu'étudiant
                startActivity(new Intent(SignUpActivity.this, SignUpAsStudentActivity.class));
            }
        });



    }
}
