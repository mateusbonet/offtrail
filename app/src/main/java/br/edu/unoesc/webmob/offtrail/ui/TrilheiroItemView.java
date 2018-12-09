package br.edu.unoesc.webmob.offtrail.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.sql.SQLException;

import br.edu.unoesc.webmob.offtrail.R;
import br.edu.unoesc.webmob.offtrail.helper.DatabaseHelper;
import br.edu.unoesc.webmob.offtrail.model.Trilheiro;

import static android.support.v4.content.ContextCompat.startActivity;

@EViewGroup(R.layout.lista_trilheiros)
public class TrilheiroItemView extends LinearLayout {

    @ViewById
    TextView txtNome;

    @ViewById
    TextView txtMoto;

    @ViewById
    ImageView imvFoto;

    @Bean
    DatabaseHelper dh;

    //Variável global
    Trilheiro trilheiro;

    public TrilheiroItemView(Context context) {
        super(context);
    }

    @Click(R.id.imvEditar)
    public void editar(View view){

        //Criar intent para chamar a tela de cadastro
        //Nesta intent passar o objeto Trilheiro
        //TODO: (2,50) Implementar a edição dos dados do trilheiro - Ok.

        Intent intent = new Intent(
                view.getContext(), CadastroActivity_.class
        );

        intent.putExtra("trilheiro", trilheiro);
        view.getContext().startActivity(intent);
    }

    @Click(R.id.imvExcluir)
    public void excluir(){

        AlertDialog.Builder dialogo = new AlertDialog.Builder(getContext());
        dialogo.setTitle("Exclusão");
        dialogo.setMessage("Deseja realmente excluir? - " + trilheiro.getNome());
        dialogo.setCancelable(false);
        dialogo.setNegativeButton("Não", null);
        dialogo.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //TODO: (2,50) Implementar a exclusão do trilheiro e grupo trilheiro - Ok.
                if(dh.excluirTrilheiro(trilheiro)){
                    Toast.makeText(getContext(), "Trilheiro excluído com sucesso!",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getContext(), "Não foi possível excluir o trilheiro selecionado! ",Toast.LENGTH_LONG).show();
                }
            }
        });
        dialogo.show();
    }

    public void bind(Trilheiro t) {
        trilheiro = t;
        txtNome.setText(t.getNome());
        txtMoto.setText(t.getMoto().getModeloMoto() + " - " + t.getMoto().getCilindradasMoto());
        imvFoto.setImageBitmap(BitmapFactory.decodeByteArray(t.getFoto(), 0, t.getFoto().length));
    }
}