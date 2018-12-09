package br.edu.unoesc.webmob.offtrail.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.LongClick;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

import java.io.ByteArrayOutputStream;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import br.edu.unoesc.webmob.offtrail.R;
import br.edu.unoesc.webmob.offtrail.helper.DatabaseHelper;
import br.edu.unoesc.webmob.offtrail.model.Grupo;
import br.edu.unoesc.webmob.offtrail.model.GrupoTrilheiro;
import br.edu.unoesc.webmob.offtrail.model.Moto;
import br.edu.unoesc.webmob.offtrail.model.Trilheiro;
import br.edu.unoesc.webmob.offtrail.model.Usuario;

@EActivity(R.layout.activity_cadastro)
public class CadastroActivity extends AppCompatActivity {

    @ViewById
    ImageView imvFoto;
    @ViewById
    EditText edtNomeTrilheiro;
    @ViewById
    EditText edtIdadeTrilheiro;
    @ViewById
    Spinner spnMotos;
    @ViewById
    Spinner spnGrupos;
    @Bean
    DatabaseHelper dh;

    Integer codigo;

    @AfterViews
    public void inicializar() {
        // cria o adapter
        ArrayAdapter<Moto> motos = null;
        try {
            motos = new ArrayAdapter<Moto>(this,
                    android.R.layout.simple_spinner_item,
                    dh.getMotoDao().queryForAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // vincula o adaptar ao spinner
        spnMotos.setAdapter(motos);

        // cria o adapter
        ArrayAdapter<Grupo> grupos = null;
        try {
            grupos = new ArrayAdapter<Grupo>(this,
                    android.R.layout.simple_spinner_item,
                    dh.getGrupoDao().queryForAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // vincula o adaptar ao spinner
        spnGrupos.setAdapter(grupos);

        Trilheiro t = (Trilheiro) getIntent().getSerializableExtra("trilheiro");

        if (t != null) {

            codigo = t.getCodigo();
            edtNomeTrilheiro.setText(t.getNome());
            edtIdadeTrilheiro.setText(t.getIdade().toString());

            Bitmap bitmap = BitmapFactory.decodeByteArray(t.getFoto(), 0, t.getFoto().length);
            imvFoto.setImageBitmap(bitmap);

            if (t.getMoto() != null) {
                for (int i = 0; i < motos.getCount(); i++) {
                    if (t.getMoto().getCodigo().equals(motos.getItem(i).getCodigo())) {
                        spnMotos.setSelection(i);
                        break;
                    }
                }
            }

            spnMotos.setSelection(motos.getPosition(t.getMoto()));

            Grupo grp = dh.adquirirGrupoTrilheiro(t).getGrupo();

            if (grp != null) {

                for (int i = 0; i < grupos.getCount(); i++) {
                    if (grp.getCodigo().equals(grupos.getItem(i).getCodigo())) {
                        spnGrupos.setSelection(i);
                        break;
                    }
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
    }

    public void salvarCadastro(View v) {

        if (edtNomeTrilheiro.length() == 0 && edtIdadeTrilheiro.length() == 0) {
            Toast.makeText(this, "Verifique o preenchimento dos campos! ", Toast.LENGTH_LONG).show();
        } else {

            Trilheiro t = new Trilheiro();

            t.setNome(edtNomeTrilheiro.getText().toString());
            t.setIdade(Integer.parseInt(edtIdadeTrilheiro.getText().toString()));
            t.setMoto((Moto) spnMotos.getSelectedItem());

            Bitmap bitmap = ((BitmapDrawable) imvFoto.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            t.setFoto(baos.toByteArray());

            try {
                if (codigo != null) {
                    t.setCodigo(codigo);
                    dh.getTrilheiroDao().update(t);
                } else {
                    dh.getTrilheiroDao().create(t);
                }
            } catch (SQLException e) {
                Toast.makeText(this, "Erro ao salvar os dados! ", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }

            GrupoTrilheiro gt = new GrupoTrilheiro();
            gt.setTrilheiro(t);
            gt.setGrupo((Grupo) spnGrupos.getSelectedItem());
            gt.setDataCadastro(new Date());

            try {
                if (codigo != null) {
                    GrupoTrilheiro grpTrilheiro = dh.adquirirGrupoTrilheiro(t);
                    grpTrilheiro.setGrupo(gt.getGrupo());
                    dh.getGrupoTrilheiroDao().update(grpTrilheiro);
                } else {
                    dh.getGrupoTrilheiroDao().create(gt);
                }

                Toast.makeText(this, "Dados salvo com sucesso! ", Toast.LENGTH_LONG).show();
            } catch (SQLException e) {
                Toast.makeText(this, "Erro ao salvar os dados! ", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
            finish();
        }
    }

    public void cancelarCadastro(View v) {

        finish();

    }

    @LongClick(R.id.imvFoto)
    public void capturarFoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, 100);
        }
    }

    @OnActivityResult(100)
    void onResult(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imvFoto.setImageBitmap(imageBitmap);
        }
    }
}
