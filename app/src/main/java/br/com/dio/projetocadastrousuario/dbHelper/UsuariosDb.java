package br.com.dio.projetocadastrousuario.dbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import br.com.dio.projetocadastrousuario.model.Usuarios;

public class UsuariosDb extends SQLiteOpenHelper {

    private static final String Database = "dbusuarios";
    private static final int version = 1;

    public UsuariosDb(Context context){
        super(context, Database, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String usuario = "CREATE TABLE usuarios(id integer primary key autoincrement NOT NULL, nomeusuario TEXT NOT NULL, email TEXT NOT NULL, telefone INTEGER);";
        db.execSQL(usuario);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String usuario = "DROP TABLE IF EXISTS usuarios";
        db.execSQL(usuario);
    }

    //Aqui para salvar
    public ArrayList<Usuarios> salvarUsuario(Usuarios usuario){
        ContentValues values = new ContentValues();

        values.put("nomeUsuario", usuario.getNomeUsuario());
        values.put("Email", usuario.getEmail());
        values.put("Telefone", usuario.getTelefone());

        getWritableDatabase().insert("usuarios",null,values);

        //lista - mostrar

        public ArrayList<Usuarios>getLista();
            String [] columns = {"id","nomeusuario","email","telefone"};
            Cursor cursor = getWritableDatabase().query("usuarios",columns,null,null,null,null,null,null);
            ArrayList<Usuarios> usuarios = new ArrayList<Usuarios>();

            while (cursor.moveToNext()){
                Usuarios usuario = new Usuarios();
                Usuarios.setId(cursor.getLong(0));
                Usuarios.setNomeUsuario(cursor.getString(1));
                Usuarios.setEmail(cursor.getString(2));
                Usuarios.setTelefone(cursor.getInt(3));

                usuarios.add(usuario);
            }
            return usuarios;
    }
}
