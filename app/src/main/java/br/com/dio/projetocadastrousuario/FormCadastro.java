package br.com.dio.projetocadastrousuario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

public class FormCadastro extends AppCompatActivity {

    //criar objetos
    private EditText edit_nome,edit_email,edit_senha,edit_telefone;
    private Button btn_cadastrar;
    String[] mensagens = {"Preencha todos os campos","Cadastro realizado com sucesso"};
    String usuarioID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cadastro);

        //esconder barra de ação
        getSupportActionBar().hide();
        //iniciar componentes
        IniciarComponentes();

        //validação campos
        btn_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //capturar os dados digitados
                String nome = edit_nome.getText().toString();
                String email = edit_email.getText().toString();
                String senha = edit_senha.getText().toString();
                String telefone = edit_telefone.getText().toString();

                //verificar campos vários e exibir mensagem caso os campos não esteja preenchido
                if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || telefone.isEmpty()){
                    Snackbar snackbar = Snackbar.make(view,mensagens[0], Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }else{
                    //Cadastrar usuário no Firebase
                    CadastrarUsuario(view);

                }
            }
        });
    }

    private void CadastrarUsuario(View view){

        String email = edit_email.getText().toString();
        String senha = edit_senha.getText().toString();

        //iniciar o firebase para cadastrar os usuários
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            //responsavel pela autenticacao do usuario
            public void onComplete(@NonNull Task<AuthResult> task) {

                SalvarDadosUsuario();

            //Tratativa de excessões com mensagem de erro
                if (task.isSuccessful()){
                    Snackbar snackbar = Snackbar.make(view,mensagens[1], Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }else{
                    String erro;
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        erro = "Digite uma senha com no mínimo 6 caracteres";
                    }catch (FirebaseAuthUserCollisionException e){
                        erro = "Esta conta já foi cadastrada";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        erro = "Email inválido";
                    }catch (Exception e){
                        erro = "Erro ao cadastrar usuário";
                    }

                    Snackbar snackbar = Snackbar.make(view,erro, Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();

                }
            }
        });
    }
    //Salvar dados do usuário no Firebase
    private void SalvarDadosUsuario(){
        String nome = edit_nome.getText().toString();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String,Object> usuarios = new HashMap<>();
        usuarios.put("nome", nome);

        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Usuarios").document(usuarioID);
        documentReference.set(usuarios).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("db","Sucesso ao Salvar os dados");
            }
        })
         .addOnFailureListener(new OnFailureListener() {
             @Override
             public void onFailure(@NonNull Exception e) {
                 Log.d("db_error","Erro ao Salvar os dados" + e.toString());
             }
         });
    }

    //associar os objetos aos ids
    private void IniciarComponentes(){
        edit_nome = findViewById(R.id.edit_nome);
        edit_email = findViewById(R.id.edit_email);
        edit_senha = findViewById(R.id.edit_senha);
        edit_telefone = findViewById(R.id.edit_telefone);
        btn_cadastrar  =findViewById(R.id.btn_cadastrar);
    }
}