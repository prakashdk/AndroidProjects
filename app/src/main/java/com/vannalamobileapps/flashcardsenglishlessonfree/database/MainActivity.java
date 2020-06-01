package com.vannalamobileapps.flashcardsenglishlessonfree.database;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    EditText name,amount,search_name;
    TextView textView;
    Button button,button1,button2;
    AlertDialog.Builder builder;
    AlertDialog alert;
    String searchValue;
    CreateDB db=new CreateDB(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher_action_bar);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        //getSupportActionBar().hide();
        String s=new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        name=(EditText)findViewById(R.id.name);
        amount=(EditText)findViewById(R.id.amount);
        button=(Button)findViewById(R.id.submit);
        button1=(Button)findViewById(R.id.display);
        button2=(Button)findViewById(R.id.delete);
        //button2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.delete,0,0,0);
        textView=(TextView)findViewById(R.id.date);
        textView.setText(s);
        builder=new AlertDialog.Builder(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data1=String.valueOf(name.getText());
                String data2=String.valueOf(amount.getText());
                try {
                    if(!(data1.isEmpty())&&!(data2.isEmpty())){
                        String date=new SimpleDateFormat("dd/MM/yyyy").format(new Date());
                        db.add(data1,data2,date);
                        name.setText("");
                        amount.setText("");
                        Toast.makeText(getApplicationContext(),"stored",Toast.LENGTH_LONG).show();
                    }
                    else{
                        name.setError("Name is required");
                        amount.setError("Amount is required");
                    }
                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_LONG).show();
                }

            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String content=db.getColumn();
                    AlertDialog.Builder dbBuilder=new AlertDialog.Builder(MainActivity.this);
                    TextView textView=new TextView(MainActivity.this);
                    textView.setText(content);
                    textView.setTextSize(20);
                    dbBuilder.setView(textView);
                    dbBuilder.show();
                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Unable to show",Toast.LENGTH_LONG).show();
                }
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    builder.setMessage("this can remove all your data in the database")
                    .setTitle("Are you sure want to clear ?").setCancelable(true)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            db.deleteAll();
                            Toast.makeText(getApplicationContext(),"Deleted",Toast.LENGTH_LONG).show();
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    alert=builder.create();
                    alert.show();

                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Error has occurred",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        switch (id){
            case R.id.item1:
                search_name=new EditText(MainActivity.this);
                search_name.setHint("Q Search...");
                builder=new AlertDialog.Builder(MainActivity.this);
                builder.setView(search_name).setCancelable(false).setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AlertDialog.Builder searchBuilder=new AlertDialog.Builder(MainActivity.this);
                        searchValue=String.valueOf(search_name.getText());
                        TextView textView=new TextView(MainActivity.this);
                        if(searchValue.isEmpty()){
                            dialogInterface.cancel();
                        }
                        else{
                            textView.setText(db.searchByName(searchValue));
                            textView.setTextSize(20);
                            searchBuilder.setView(textView);
                            searchBuilder.show();
                        }

                    }
                }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                AlertDialog alertDialog=builder.create();
                alertDialog.show();
                return true;
            case R.id.item2:
                builder=new AlertDialog.Builder(MainActivity.this);
                final View replaceView=getLayoutInflater().inflate(R.layout.replace_menu,null);
                builder.setView(replaceView).setCancelable(false).setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText find=(EditText)replaceView.findViewById(R.id.find);
                        EditText replace=(EditText)replaceView.findViewById(R.id.replace);
                        EditText index=(EditText)replaceView.findViewById(R.id.index);
                        db.replace(String.valueOf(index.getText()),String.valueOf(find.getText()),String.valueOf(replace.getText()));
                        Toast.makeText(getApplicationContext(),"Replaced",Toast.LENGTH_LONG).show();
                    }
                }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                builder.show();
                return true;

            case R.id.item3:
                final EditText remove_id=new EditText(MainActivity.this);
                remove_id.setHint("Serial no..");
                remove_id.setInputType(2);
                builder.setView(remove_id).setCancelable(false).setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.remove(String.valueOf(remove_id.getText()));
                        Toast.makeText(getApplicationContext(),"Removed",Toast.LENGTH_LONG).show();
                    }
                }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.show();
                return true;

            case R.id.item4:
                Intent share=new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                String string=db.getColumn();
                //share.putExtra(android.content.Intent.EXTRA_SUBJECT,"Subject Test");
                share.putExtra(Intent.EXTRA_TEXT,string);
                startActivity(Intent.createChooser(share,"Share via"));
                //Toast.makeText(getApplicationContext(),"On the way",Toast.LENGTH_LONG).show();
                return true;
            case R.id.item5:
                Toast.makeText(getApplicationContext(),"Contact Developer to Update settings",Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
