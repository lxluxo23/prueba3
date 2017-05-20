package com.lxluxo23.prueba;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.Toast;

import java.util.ArrayList;


public class Main2Activity extends AppCompatActivity {

    ArrayList<String> lista;
    ArrayAdapter adaptador;

    GridView gv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        try {
            adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1,lista);


            gv1 = (GridView) findViewById(R.id.grid);
            ArrayAdapter<Cursor> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "vehiculo", null, 1);

            SQLiteDatabase bd = admin.getWritableDatabase();
            Cursor fila = bd.rawQuery("select * from auto ", null);

            adapter1.add(fila);
            bd.close();
            gv1.setAdapter(adaptador);
        }
        catch (Exception e){
            Toast.makeText(this, "fallo",
                    Toast.LENGTH_SHORT).show();

        }

    }

    public ArrayList llenar_lv(){


        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "vehiculo", null, 1);
        ArrayList<String> lista = new ArrayList<>();
        SQLiteDatabase database = admin.getWritableDatabase();
        String q = "SELECT * FROM auto";
        Cursor registros = database.rawQuery(q,null);

        if(registros.moveToFirst()){
            do{
                lista.add(registros.getString(1));
            }while(registros.moveToNext());
        }
        return lista;

    }
}






