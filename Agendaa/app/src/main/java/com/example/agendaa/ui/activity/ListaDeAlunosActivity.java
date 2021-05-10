package com.example.agendaa.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.agendaa.R;
import com.example.agendaa.dao.alunoDAO;
import com.example.agendaa.model.Aluno;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static com.example.agendaa.ui.activity.constantesActivities.CHAVE_ALUNO;

public class ListaDeAlunosActivity extends AppCompatActivity {

    public static final String TitleAppBarListaDeAlunos = "Lista de Alunos";
    public static final String TittleAppBarListaDeAlunos = TitleAppBarListaDeAlunos;
    private final alunoDAO dao = new alunoDAO();
    private Aluno aluno;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);
        setTitle(TittleAppBarListaDeAlunos);
        configuraFABNovoAluno();
        dao.salva(new Aluno("Alex", "1144252", "do1@gmail.com"));
        dao.salva(new Aluno("Elda", "1144252", "Elda@gmail.com"));

    }

    private void configuraFABNovoAluno() {
        FloatingActionButton botaoNovoAluno = findViewById(R.id.lista_alunos_fab_novo_aluno);
        botaoNovoAluno.setOnClickListener((view) -> {
            abreFormularioInsereAluno();
        });
    }

    private void abreFormularioInsereAluno() {
        startActivity(new Intent(this, FormularioDosAlunosActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        SetUpList();
    }

    private void SetUpList() {
        ListView listaDeAlunos = findViewById(R.id.activity_lista_alunos_listview);
        final List<Aluno> alunos = dao.todos();
        configuraAdapter(listaDeAlunos, alunos);
        configuraListenerdeCliquePorItem(listaDeAlunos);

    }

    private void configuraListenerdeCliquePorItem(ListView listaDeAlunos) {
        listaDeAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long id) {
                Aluno alunoEscolhido = (Aluno) adapterView.getItemAtPosition(posicao);
                abreFormularioModoEditaAluno(alunoEscolhido);

            }
        });
    }

    private void abreFormularioModoEditaAluno(Aluno aluno) {
        Intent vaiParaFormularioActivity = new Intent(ListaDeAlunosActivity.this, FormularioDosAlunosActivity.class);
        vaiParaFormularioActivity.putExtra(CHAVE_ALUNO, aluno);
        startActivity(vaiParaFormularioActivity);
    }

    private void configuraAdapter(ListView listaDeAlunos, List<Aluno> alunos) {
        listaDeAlunos.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                alunos));
    }
}