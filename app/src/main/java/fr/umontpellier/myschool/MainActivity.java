package fr.umontpellier.myschool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import fr.umontpellier.myschool.SignInActivity;
import fr.umontpellier.myschool.SignUpActivity;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button btnSignUp, btnSignIn, btnSignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialiser Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Références des boutons
        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignOut = findViewById(R.id.btnSignOut);

        // Vérifier si un utilisateur est connecté
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            // Utilisateur non connecté : afficher les boutons Inscription et Connexion
            btnSignUp.setVisibility(View.VISIBLE);
            btnSignIn.setVisibility(View.VISIBLE);
            btnSignOut.setVisibility(View.GONE);

            btnSignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Rediriger vers l'inscription
                    startActivity(new Intent(MainActivity.this, SignUpActivity.class));
                }
            });

            btnSignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Rediriger vers la connexion
                    startActivity(new Intent(MainActivity.this, SignInActivity.class));
                }
            });
        } else {
            // Utilisateur connecté : afficher le bouton Déconnexion
            btnSignUp.setVisibility(View.GONE);
            btnSignIn.setVisibility(View.GONE);
            btnSignOut.setVisibility(View.VISIBLE);

            // Afficher un message de bienvenue avec l'email de l'utilisateur
            // Toast.makeText(this, "Bienvenue " + currentUser.getEmail(), Toast.LENGTH_SHORT).show();

            btnSignOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Déconnecter l'utilisateur
                    mAuth.signOut();
                    // Rafraîchir l'activité après déconnexion
                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                    finish(); // Terminer l'activité pour éviter de revenir en arrière
                }
            });
        }
    }
}
