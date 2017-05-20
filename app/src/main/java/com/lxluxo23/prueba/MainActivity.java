package com.lxluxo23.prueba;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {

    private EditText et1,et2,et3,et4,et5;
    private Spinner sp1,sp2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);



        et1=(EditText) findViewById(R.id.editText);
        et2=(EditText) findViewById(R.id.editText2);
        et3=(EditText) findViewById(R.id.editText3);
        et4=(EditText) findViewById(R.id.editText4);
        et5=(EditText) findViewById(R.id.editText5);
        sp1=(Spinner) findViewById(R.id.spinner);
        sp2=(Spinner) findViewById(R.id.spinner2);

        sp1 = (Spinner) findViewById(R.id.spinner);
        String []opciones={"gasolina","diesel","gas","electricidad","hidrogeno","etanol"};
        ArrayAdapter adapter1 = new ArrayAdapter(this,android.R.layout.simple_spinner_item, opciones);
        sp1.setAdapter(adapter1);

        String c="";
        for (int i=1800;i<2018;i++){

            c=String.valueOf(i);
            adapter.add(c);
        }

        sp2.setAdapter(adapter);

    }


    public void ingresar (View view)
    {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"vehiculo",null,1);
        SQLiteDatabase bd= admin.getWritableDatabase();

        String uno,dos,tres;

        uno=et1.getText().toString();
        dos=et2.getText().toString();
        tres=et3.getText().toString();

        String patente=uno+""+dos+""+tres;
        String marca = et5.getText() . toString();
        String modelo = et4.getText() . toString();
        String año = sp1.getSelectedItem().toString();
        String combustible = sp2.getSelectedItem().toString();


        ContentValues registro = new ContentValues();

        registro.put("patente",patente);
        registro.put("modelo",modelo);
        registro.put("marca",marca);
        registro.put("año",año);
        registro.put("combustible",combustible);

        bd.insert("auto",null,registro);
        bd.close();
        et1.setText("");
        et2.setText("");
        et3.setText("");
        et4.setText("");
        et5.setText("");

        Toast.makeText (this,"se insertaron llos datos ",Toast.LENGTH_SHORT).show();
    }

    public void buscar(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"vehiculo", null, 1);

        SQLiteDatabase bd = admin.getWritableDatabase();


        String uno,dos,tres;

        uno=et1.getText().toString();
        dos=et2.getText().toString();
        tres=et3.getText().toString();

        String patente=uno+""+dos+""+tres;


        Cursor fila = bd.rawQuery("select modelo,marca,año,combustible from auto where patente='" + patente+"'", null);
        if (fila.moveToFirst()) {
            et4.setText(fila.getString(0));
            et5.setText(fila.getString(1));

        } else
            Toast.makeText(this, "el auto no existe",
                    Toast.LENGTH_SHORT).show();
        bd.close();

    }
   public void eliminar (View v){

       AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"vehiculo",null,1);
       SQLiteDatabase bd = admin.getWritableDatabase();

       String uno,dos,tres;

       uno=et1.getText().toString();
       dos=et2.getText().toString();
       tres=et3.getText().toString();

       String patente=uno+""+dos+""+tres;
       int cant = bd.delete("auto", "patente=" + patente, null);
       bd.close();
       if (cant == 1) {
           Toast.makeText(this, "registros eliminados", Toast.LENGTH_SHORT).show();
       }
       else
           Toast.makeText(this, "No existe el auto",
                   Toast.LENGTH_SHORT).show();


   }

    public void lanzar (View v)
    {
        Intent i = new Intent(this, Main2Activity.class);
        startActivity(i);
    }
}

