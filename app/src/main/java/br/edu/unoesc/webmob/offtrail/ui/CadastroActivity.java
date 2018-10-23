package br.edu.unoesc.webmob.offtrail.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import org.androidannotations.annotations.AfterViews;

import br.edu.unoesc.webmob.offtrail.R;

public class CadastroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
    }

    public void salvarCadastro(View v){

    }

    public void cancelarCadastro(View v){
        startActivity(new Intent(CadastroActivity.this, PrincipalActivity.class));
        finish();
    }
}
