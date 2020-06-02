package com.vannalamobileapps.flashcardsenglishlessonfree.flames;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText firstName, secondName;
    Button matchButton;
    TextView result;
    AlertDialog.Builder builder;
    String str1, str2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firstName = (EditText) findViewById(R.id.firstname);
        secondName = (EditText) findViewById(R.id.secondname);
        matchButton = (Button) findViewById(R.id.matchbutton);
        result = (TextView) findViewById(R.id.result);
        builder = new AlertDialog.Builder(this);

        matchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    str1 = String.valueOf(firstName.getText());
                    str2 = String.valueOf(secondName.getText());
                    String name1 = removeSpaces(str1);
                    String name2 = removeSpaces(str2);
                    if (name1.isEmpty() || name2.isEmpty()) {
                        firstName.setError("Both names required");
                        secondName.setError("Both names required");
                    } else {
                        try {
                            char ch1[] = name1.toCharArray();
                            char ch2[] = name2.toCharArray();
                            for (int i = 0; i < name1.length(); i++) {
                                for (int j = 0; j < name2.length(); j++) {
                                    if (String.valueOf(ch1[i]).equalsIgnoreCase(String.valueOf(ch2[j]))) {
                                        ch1[i] = '$';
                                        ch2[j] = '$';
                                        break;
                                    }
                                }
                            }
                            String str = "";
                            for (int i = 0; i < ch1.length; i++) {
                                if (ch1[i] != '$') {
                                    str += ch1[i];
                                }
                            }
                            for (int i = 0; i < ch2.length; i++) {
                                if (ch2[i] != '$') {
                                    str += ch2[i];
                                }
                            }
                            char ch=0;
                            if (str.length() > 0) {
                                ch = relation(str.length());
                            }
                            final String resStr = relationShip(ch!=0 ? (ch) : ('f'));
                            result.setText(resStr);
                            builder.setMessage(resStr)
                                    .setTitle("Relationship")
                                    .setCancelable(false)
                                    .setPositiveButton("Share", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Intent share = new Intent(Intent.ACTION_SEND);
                                            share.setType("text/plain");
                                            String shareString = "Names :" + str1 + " , " + str1 + "\n" + resStr + " by Flames";
                                            share.putExtra(Intent.EXTRA_TEXT, shareString);
                                            startActivity(Intent.createChooser(share, "Share via"));
                                            dialogInterface.dismiss();
                                        }
                                    })
                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        } catch (Exception e) {
                            firstName.setError("Invalid Name");
                            secondName.setError("Invalid Name");
                            Toast.makeText(getApplicationContext(), "Invalid name :" + e, Toast.LENGTH_LONG).show();
                        }
                    }

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error :" + e, Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    static char relation(int l) {
        String s = "flames";
        int n;
        while (s.length() > 2) {
            n = l;
            while (s.length() < n) {
                n -= s.length();
            }
            s = s.substring(n) + s.substring(0, n - 1);
        }
        return s.charAt(0);
    }

    static String removeSpaces(String s) {
        String str = s.replaceAll(" ", "");
        return str;
    }

    static String relationShip(char c) {
        String str = "No Match";
        switch (c) {
            case 'f':
                str = "May be lucky you're \nFRIENDS";
                break;
            case 'l':
                str = "May be lucky you're \nLOVERS";
                break;
            case 'a':
                str = "You have some \nAFFECTION ";
                break;
            case 'm':
                str = "Your's relationship gonna end in \nMARRIAGE";
                break;
            case 'e':
                str = "Bad Luck you're \nENEMIES";
                break;
            case 's':
                str = "Oh Good, you're \nSIBLINGS";
                break;
        }
        return str;
    }
}
