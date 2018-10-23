package br.edu.unoesc.webmob.offtrail.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.WindowFeature;

import br.edu.unoesc.webmob.offtrail.R;

@EActivity(R.layout.activity_login)
@Fullscreen
@WindowFeature(Window.FEATURE_NO_TITLE)
public class LoginActivity extends AppCompatActivity {

    @ViewById
    EditText editLogin;
    @ViewById
    EditText editSenha;

    public void login(View v) {

        String strLogin = editLogin.getText().toString();
        String strSenha = editSenha.getText().toString();

        if (strLogin != null && strSenha != null &&
                !strLogin.trim().equals("") &&
                !strSenha.trim().equals("") &&
                strLogin.equals("mateus") &&
                strSenha.equals("mateus")) {


            Intent itPrincipal = new Intent(
                    LoginActivity.this, PrincipalActivity.class
            );
            startActivity(itPrincipal);
            finish();

        } else {
            Toast.makeText(this, "Usuário e/ou senha estão inválidos!", Toast.LENGTH_LONG).show();
            editLogin.setText("");
            editSenha.setText("");
            editLogin.requestFocus();
        }

    }

    public void sair(View v) {

        finish();
        System.exit(0);
    }
}
