package com.fabianardila.proyectogradouts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import dmax.dialog.SpotsDialog;

public class LoginBibliotecarioActivity extends AppCompatActivity {

    EditText etCorreo, etPass;

    private FirebaseAuth mAuth;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_bibliotecario);

        etCorreo = findViewById(R.id.etCorreo);
        etPass = findViewById(R.id.etPass);

        mAuth = FirebaseAuth.getInstance();
        dialog = new SpotsDialog.Builder().setContext(this).build();
    }

    public void restablecerContrasena(View view) {
        if (view != null) {
            Toast.makeText(this, "Disponible proximamente", Toast.LENGTH_SHORT).show();
        }
    }

    public void iniciarSesion(View view) {
        if (view != null) {
            dialog.setMessage("Ingresando...");
            dialog.show();

            String email = etCorreo.getText().toString();
            String pass = etPass.getText().toString();


            if (validarCampos()) {
                mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            dialog.dismiss();
                            Intent intent = new Intent(LoginBibliotecarioActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            dialog.dismiss();
                            Toast.makeText(LoginBibliotecarioActivity.this, "Inicio de sesion fallido", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }

    private boolean validarCampos() {
        return true;
    }
}
