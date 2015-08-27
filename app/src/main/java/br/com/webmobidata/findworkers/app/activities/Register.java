package br.com.webmobidata.findworkers.app.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import br.com.webmobidata.findworkers.R;
import br.com.webmobidata.findworkers.app.handleViews.CollapseExpandViews;

public class Register extends AppCompatActivity {

    //Fields fixo para a tela de registro
    ImageView photoProfile;
    CheckBox isEnterprise;

    //Fields para o formulario de pessoa fisica
    EditText namePerson,passwordPerson, confirmPasswordPerson, phonePerson, cpfPerson;
    EditText nameEnterprisePerson, cnpjPerson, addressEnterprisePerson;
    View formRegisterPerson, optionProfile, dataEnterprisePerson;
    AutoCompleteTextView emailPerson;
    RadioGroup radioGroup;
    RadioButton workerAutonomousRadioButton, enterpriseRadioButton;
    CheckBox isWorker;

    //Fields para o formulario de pessoa juridica
    AutoCompleteTextView emailEnterprise;
    EditText nameEnterprise, passwordEnterprise, confirmPasswordEnterprise, phoneEnterprise, cnpjEnterprise;
    View formRegisterEnterprise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setReferenceUIs();
        final CollapseExpandViews collapseExpandViews = new CollapseExpandViews();

        photoProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickImageIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickImageIntent.setType("image/*");
                pickImageIntent.putExtra("crop", "true");
                pickImageIntent.putExtra("outputX", 100);
                pickImageIntent.putExtra("outputY", 100);
                pickImageIntent.putExtra("aspectX", 1);
                pickImageIntent.putExtra("aspectY", 1);
                pickImageIntent.putExtra("scale", true);
                pickImageIntent.putExtra("return-data", true);
                pickImageIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
                startActivityForResult(pickImageIntent, 9990);
            }
        });

        isEnterprise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if (isEnterprise.isChecked()) {
                collapseExpandViews.expandOrCollapse(formRegisterPerson,false);
                collapseExpandViews.expandOrCollapse(formRegisterEnterprise,true);
            }else{
                collapseExpandViews.expandOrCollapse(formRegisterEnterprise,false);
                collapseExpandViews.expandOrCollapse(formRegisterPerson,true);
            }
            }
        });

        isWorker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isWorker.isChecked()){
                    optionProfile.setVisibility(View.VISIBLE);
                    if(workerAutonomousRadioButton.isChecked()){
                        collapseExpandViews.expandOrCollapse(cpfPerson,true);
                        collapseExpandViews.expandOrCollapse(dataEnterprisePerson,false);
                    }else if(enterpriseRadioButton.isChecked()){
                        collapseExpandViews.expandOrCollapse(dataEnterprisePerson,true);
                        collapseExpandViews.expandOrCollapse(cpfPerson,false);
                    }
                }else{
                    optionProfile.setVisibility(View.GONE);
                    collapseExpandViews.expandOrCollapse(cpfPerson, false);
                    collapseExpandViews.expandOrCollapse(dataEnterprisePerson,false);
                }
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int radioButtonID) {
                if(radioButtonID == workerAutonomousRadioButton.getId()){
                    collapseExpandViews.expandOrCollapse(dataEnterprisePerson,false);
                    collapseExpandViews.expandOrCollapse(cpfPerson,true);
                }else{
                    collapseExpandViews.expandOrCollapse(cpfPerson,false);
                    collapseExpandViews.expandOrCollapse(dataEnterprisePerson,true);
                }
            }
        });

    }

    private void setReferenceUIs() {
        //Fields fixo
        photoProfile = (ImageView) findViewById(R.id.photoProfile);
        isEnterprise = (CheckBox) findViewById(R.id.isEnterprise);

        //Fields formulario pessoa fisica
        namePerson = (EditText) findViewById(R.id.namePerson);
        passwordPerson = (EditText) findViewById(R.id.passwordPerson);
        confirmPasswordPerson = (EditText) findViewById(R.id.confirmPasswordPerson);
        phonePerson = (EditText) findViewById(R.id.phonePerson);
        cpfPerson= (EditText) findViewById(R.id.cpfPerson);
        nameEnterprisePerson =(EditText) findViewById(R.id.nameEnterprisePerson);
        cnpjPerson = (EditText) findViewById(R.id.cnpjPerson);
        addressEnterprisePerson =(EditText) findViewById(R.id.addressEnterprisePerson);
        formRegisterPerson = findViewById(R.id.formRegisterPerson);
        optionProfile = findViewById(R.id.optionProfile);
        dataEnterprisePerson = findViewById(R.id.dataEnterprisePerson);
        emailPerson = (AutoCompleteTextView) findViewById(R.id.emailPerson);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        isWorker = (CheckBox) findViewById(R.id.isWorker);
        workerAutonomousRadioButton = (RadioButton) findViewById(R.id.workerAutonomousRadioButton);
        enterpriseRadioButton =(RadioButton) findViewById(R.id.enterpriseRadioButton);

        //Fields formulario pessoa juridica
        nameEnterprise = (EditText) findViewById(R.id.nameEnterprise);
        passwordEnterprise = (EditText) findViewById(R.id.passwordEnterprise);
        confirmPasswordEnterprise = (EditText) findViewById(R.id.confirmPasswordEnterprise);
        phoneEnterprise = (EditText) findViewById(R.id.phoneEnterprise);
        cnpjEnterprise = (EditText) findViewById(R.id.cnpjEnterprise);
        emailEnterprise = (AutoCompleteTextView) findViewById(R.id.emailEnterprise);
        formRegisterEnterprise = findViewById(R.id.formRegisterEnterprise);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 9990) {
            if (data != null) {
                Bundle extras = data.getExtras();
                Bitmap selectedBitmap = extras.getParcelable("data");
                photoProfile.setImageBitmap(selectedBitmap);
            }
        }
    }
}
