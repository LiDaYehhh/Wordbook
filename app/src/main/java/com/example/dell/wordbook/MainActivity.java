package com.example.dell.wordbook;

import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.dell.wordbook.dummy.DummyContent;

public class MainActivity extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener,DetailFragment.OnFragmentInteractionListener {
    Button addbutton;
    Button selectbutton;
    EditText selectword;
    static FragmentManager fragmentmanager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sqlite.wordsdbhelp=new wordsdbhelp(this);
        sqlite.writedb=sqlite.wordsdbhelp.getWritableDatabase();
        sqlite.readdb=sqlite.wordsdbhelp.getReadableDatabase();
        String createtable="create table wordstable ("+
                "word text primary key,"+
                "meaning text,"+
                "sample text)";
        //sqlite.db.execSQL(createtable);
        //sqlite.insert("apple","苹果","this apple is very nice");
        DummyContent.gx();
        super.onCreate(savedInstanceState);
        fragmentmanager=getFragmentManager();
        gxfra();
        setContentView(R.layout.activity_main);
        Log.i("555","ppp");
        addbutton=(Button)findViewById(R.id.addbutton);
        selectbutton=(Button)findViewById(R.id.selectbutton);
        selectword=(EditText)findViewById(R.id.selectword);
        addbutton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(MainActivity.this,addword.class);
                        startActivity(intent);
                    }
                }
        );
        selectbutton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onFragmentInteraction(selectword.getText().toString());
                    }
                }
        );


    }
    public static void gxfra()
    {
        ItemFragment mm=new ItemFragment();
        fragmentmanager.beginTransaction().replace(R.id.fragment,mm).commit();
    }
    public void onFragmentInteraction(String word) {
        Bundle arguments =new Bundle();
        arguments.putString("word",word);
        DetailFragment fragment=new DetailFragment();
        fragment.setArguments(arguments);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.worddetail,fragment)
                .commit();
    }
    public void onListFragmentInteraction(DummyContent.DummyItem item)
    {
        onFragmentInteraction(item.content);
    }
    public void onFragmentInteraction(Uri uri)
    {

    }
}
