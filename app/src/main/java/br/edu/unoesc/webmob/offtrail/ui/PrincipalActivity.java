package br.edu.unoesc.webmob.offtrail.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ListView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.WindowFeature;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.List;

import br.edu.unoesc.webmob.offtrail.R;
import br.edu.unoesc.webmob.offtrail.adapter.TrilheiroAdapter;
import br.edu.unoesc.webmob.offtrail.helper.DatabaseHelper;
import br.edu.unoesc.webmob.offtrail.model.Usuario;
import br.edu.unoesc.webmob.offtrail.rest.CidadeClient;
import br.edu.unoesc.webmob.offtrail.rest.Endereco;

@EActivity(R.layout.activity_principal)
@Fullscreen
@WindowFeature(Window.FEATURE_NO_TITLE)
public class PrincipalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @ViewById
    ListView lstTrilheiros;

    @Bean
    TrilheiroAdapter trilheiroAdapter;

    @Pref
    Configuracao_ configuracao;

    //Injeção do cliente rest
    @RestService
    CidadeClient cidadeClient;

    ProgressDialog pd;

    @Bean
    DatabaseHelper dh;

    @AfterViews
    public void inicializar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PrincipalActivity.this, CadastroActivity_.class));
                //finish();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Recuperar dados do usuário
        Usuario u = (Usuario)getIntent().getSerializableExtra("usuario");
        Toast.makeText(this,"Seja bem-vindo! - " + u.getEmail(),Toast.LENGTH_LONG).show();

        //Mudar cor de fundo da view principal
        View v = toolbar.getRootView();
        //v.setBackgroundColor(configuracao.cor().get());

        Toast.makeText(this,configuracao.parametro().get(),Toast.LENGTH_LONG).show();

        //Escrevendo os parâmetros
        //configuracao.edit().cor().put(Color.BLUE).apply();

    }

    public void atualizarListaTrilheiros(){
        //TODO: (1,00) Implementar a ordenação pelo nome do trilheiro de forma descendente - Ok.
        trilheiroAdapter.updateList();
        trilheiroAdapter.notifyDataSetChanged();
        lstTrilheiros.setAdapter(trilheiroAdapter);
    }

    @Override
    public void onResume(){
        super.onResume();

        atualizarListaTrilheiros();
        //TODO: (1,00) Implementar atualização automática da lista de trilheiros ao sair da tela de cadastro do trilheiro - Ok.
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a PARENT activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            //TODO: (0,75) Implementar uma tela de sobre o sistema com informações gerais - Ok.
            startActivity(new Intent(PrincipalActivity.this, SobreActivity_.class));

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_sincronizar) {
            pd = new ProgressDialog(this);
            pd.setCancelable(false);
            pd.setTitle("Aguarde, consultando...");
            pd.setIndeterminate(true);
            pd.show();
            consultarCidadePorNome();
        } else if (id == R.id.nav_preferencias) {
            //TODO: (0,75) Implementar tela para salvar preferências - Ok.
            startActivity(new Intent(PrincipalActivity.this, PreferenciasActivity_.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @UiThread
    public void operacaoConcluida() {
        Toast.makeText(this, "Operação realizada com sucesso!", Toast.LENGTH_LONG).show();
        pd.dismiss();
    }

    @Background
    public void consultarCidadePorNome(){
        //TODO: (1,50) Implementar a busca de todas as cidades que começam com "São" e gravar na tabela cidade - Ok.
        //Aciona a busca
        List<Endereco> e = cidadeClient.getEndereco("São");

        if (e != null && e.size() > 0) {
            for (int i = 0; i < e.size(); i++) {
                dh.saveCidade(e.get(i).toString());
            }

            operacaoConcluida();
        }
    }
}
