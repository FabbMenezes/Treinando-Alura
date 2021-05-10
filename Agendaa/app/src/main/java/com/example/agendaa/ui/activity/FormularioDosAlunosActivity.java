package com.example.agendaa.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.agendaa.R;
import com.example.agendaa.dao.alunoDAO;
import com.example.agendaa.model.Aluno;

import static com.example.agendaa.ui.activity.constantesActivities.CHAVE_ALUNO;

public class FormularioDosAlunosActivity extends AppCompatActivity {

    public static final String TittleAppBarNovoAluno = "Novo aluno";
    private static final String TittleAppBarEditaAluno = "Edita Aluno";
    private EditText campoNome;
    private EditText campoTelefone;
    private EditText campoEmail;
    private final alunoDAO dao = new alunoDAO();
    private Aluno aluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_dos_alunos);
        inicializacaoDosCampos();
        configuraBotaoSalvar();
        carregaInformacaoAluno();

    }

    private void carregaInformacaoAluno() {
        Intent dados = getIntent();
        if (dados.hasExtra(CHAVE_ALUNO)) {
            setTitle(TittleAppBarEditaAluno);
            aluno = (Aluno) dados.getSerializableExtra(CHAVE_ALUNO);
            preencheCampos();
        } else {
            setTitle(TittleAppBarNovoAluno);
            aluno = new Aluno();

        }
    }

    private void preencheCampos() {
        campoNome.setText(aluno.getNome());
        campoTelefone.setText(aluno.getTelefone());
        campoEmail.setText(aluno.getEmail());
    }

    private void configuraBotaoSalvar() {
        View botaoSalvar = findViewById(R.id.activity_formulario_aluno_botao_salvar);
        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalizaFormularioAluno();
            }
        });
    }

    private void finalizaFormularioAluno() {
        preencheAluno();
        if (aluno.temIdValido()) {
            dao.edita(aluno);
        } else {
            dao.salva(aluno);
        }
        dao.edita(aluno);
        finish();
    }

    private void inicializacaoDosCampos() {
        campoNome = findViewById(R.id.activity_formulario_aluno_nome);
        campoTelefone = findViewById(R.id.activity_formulario_aluno_telefone);
        campoEmail = findViewById(R.id.activity_formulario_aluno_email);
    }

    @NonNull
    private void preencheAluno() {
        Toast.makeText(FormularioDosAlunosActivity.this, "Aluno Criado", Toast.LENGTH_SHORT).show();
        String nome = campoNome.getText().toString();
        String telefone = campoTelefone.getText().toString();
        String email = campoEmail.getText().toString();

        aluno.setNome(nome);
        aluno.setTelefone(telefone);
        aluno.setEmail(email);
    }
}