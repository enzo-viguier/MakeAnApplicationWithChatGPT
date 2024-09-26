package fr.umontpellier.myschool;

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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SignUpAsStudentActivity extends AppCompatActivity {

    private EditText firstNameEditText, lastNameEditText, schoolLevelEditText, emailEditText, passwordEditText;
    private Button signupButton;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_student);

        // Références aux champs
        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        schoolLevelEditText = findViewById(R.id.schoolLevelEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        signupButton = findViewById(R.id.signupButton);

        // Initialize Firebase Auth and Database
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = firstNameEditText.getText().toString().trim();
                String lastName = lastNameEditText.getText().toString().trim();
                String schoolLevel = schoolLevelEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                // Validation des champs
                if (TextUtils.isEmpty(firstName)) {
                    Toast.makeText(SignUpAsStudentActivity.this, "Veuillez entrer votre prénom", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(lastName)) {
                    Toast.makeText(SignUpAsStudentActivity.this, "Veuillez entrer votre nom", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(schoolLevel)) {
                    Toast.makeText(SignUpAsStudentActivity.this, "Veuillez entrer votre niveau scolaire", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(SignUpAsStudentActivity.this, "Veuillez entrer un email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(SignUpAsStudentActivity.this, "Veuillez entrer un mot de passe", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(SignUpAsStudentActivity.this, "Le mot de passe doit contenir au moins 6 caractères", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Créer un nouvel utilisateur avec Firebase Authentication
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUpAsStudentActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Inscription réussie
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(SignUpAsStudentActivity.this, "Inscription réussie.", Toast.LENGTH_SHORT).show();

                                    // Sauvegarder les informations de l'étudiant dans la base de données Firebase
                                    String userId = user.getUid();
                                    Map<String, Object> studentData = new HashMap<>();
                                    studentData.put("firstName", firstName);
                                    studentData.put("lastName", lastName);
                                    studentData.put("schoolLevel", schoolLevel);
                                    studentData.put("email", email);

                                    // Enregistrer les données sous l'ID de l'utilisateur
                                    mDatabase.child("students").child(userId).setValue(studentData);

                                    // Rediriger ou afficher un message
                                } else {
                                    // Si l'inscription échoue
                                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                        Toast.makeText(SignUpAsStudentActivity.this, "Cet email est déjà utilisé.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(SignUpAsStudentActivity.this, "Échec de l'inscription : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
            }
        });
    }
}
