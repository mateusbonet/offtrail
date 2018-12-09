package br.edu.unoesc.webmob.offtrail.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.WindowFeature;

import java.sql.SQLException;

import br.edu.unoesc.webmob.offtrail.R;
import br.edu.unoesc.webmob.offtrail.helper.DatabaseHelper;
import br.edu.unoesc.webmob.offtrail.model.Usuario;

@EActivity(R.layout.activity_login)
@Fullscreen
@WindowFeature(Window.FEATURE_NO_TITLE)
public class LoginActivity extends AppCompatActivity {

    @Bean
    DatabaseHelper dh;
    @ViewById
    EditText editLogin;
    @ViewById
    EditText editSenha;

    public void login(View v) {

        String strLogin = editLogin.getText().toString();
        String strSenha = editSenha.getText().toString();

        if (strLogin != null && strSenha != null &&
                !strLogin.trim().equals("") &&
                !strSenha.trim().equals("")) {

            Usuario u = dh.validaLogin(strLogin, strSenha);

            if(u != null) {
                Intent itPrincipal = new Intent(
                        LoginActivity.this, PrincipalActivity_.class
                );

                //Passando parâmetro para outra tela
                itPrincipal.putExtra("usuario", u);
                startActivity(itPrincipal);
                finish();
            }else{
                Toast.makeText(this, "Usuário e/ou senha estão inválidos!", Toast.LENGTH_LONG).show();
            }

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
