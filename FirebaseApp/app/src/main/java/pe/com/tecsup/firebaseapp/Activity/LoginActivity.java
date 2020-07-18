package pe.com.tecsup.firebaseapp.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import pe.com.tecsup.firebaseapp.R;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private View loginPanel;
    private ProgressBar progressBar;
    private SignInButton sign_in_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressBar = findViewById(R.id.proressbar);
        loginPanel = findViewById(R.id.login_panel);

        //iniciando firebase auth
        initFirebaseAuth();

        //iniciando firebaselistener
        initFirebaseAuthStateListener();

        sign_in_button = findViewById(R.id.sign_in_button);
        setGoogleButtonText(sign_in_button,"Iniciar Sesion");

        //Iniciando Google SigIn
        initGoogleSignIn();


    }

    private FirebaseAuth mAtuh;
    private EditText emailInput;
    private EditText passwordInput;

    private void initFirebaseAuth(){
        //iniciando instancia firebaseAuth
        mAtuh = FirebaseAuth.getInstance();

        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);
    }

    public void callLogin(View view){
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Debes completar estos campos",Toast.LENGTH_SHORT).show();
            return;
        }

        loginPanel.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        //inicio sesion de usuario

        mAtuh.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()){
                    loginPanel.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    Log.d(TAG,"Inicio de sesion con correo y contraseña : Completado:"+task.getException());
                    Toast.makeText(LoginActivity.this,"Usuario o Contraseña invalidos",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void callRegister(final View view){
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (email.isEmpty() || password.isEmpty()){
            Toast.makeText(this,"Debes completar estos campos",Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length()<6){
            Toast.makeText(this,"La contraseña es muy debil",Toast.LENGTH_SHORT).show();
            return;
        }

        loginPanel.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        mAtuh.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG,"Usuario creado por email y contraseña:Completado" + task.isSuccessful());
                if (!task.isSuccessful()){
                    loginPanel.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    Log.e(TAG,"Usuario creado por email y contraseña:Failed", task.getException());
                    Toast.makeText(LoginActivity.this,"Registro Fallido",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private  FirebaseAuth.AuthStateListener mAuthListener;

    private void initFirebaseAuthStateListener(){
        //y el método AuthStateListener para que pueda realizar un seguimiento cada vez que el usuario inicia o cierra sesión
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    // El usuario ha iniciado sesión
                    Log.d(TAG,"Estado de autenticacion cambio: signed_in" + user.getUid());
                    Toast.makeText(LoginActivity.this,"Bienvenido " + user.getEmail() ,Toast.LENGTH_SHORT).show();
                    //vamos a MainActivity
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }else{
                    //el usuario cierra sesion
                    Log.d(TAG,"Estado de autenticaion cambio: signed_out");
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAtuh.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAtuh.removeAuthStateListener(mAuthListener);
    }

    protected void setGoogleButtonText(SignInButton signInButton, String buttonText){
        for (int i=0; i<signInButton.getChildCount();i++){
            View v = signInButton.getChildAt(i);
            if (v instanceof TextView){
                TextView tv = (TextView) v;
                tv.setText(buttonText);
                return;
            }
        }

    }

    //Solicitar código utilizado para invocar las interacciones de inicio de sesión de usuario para Google
    private static final int GOOGLE_SIGNIN_REQUEST = 1000;

    //El cliente utiliza para interactuar con las API de Google.
    private GoogleApiClient mGoogleApiClient;

    private void initGoogleSignIn(){
        //configurando botton de inicio con google
        SignInButton mGoogleLoginButton = findViewById(R.id.sign_in_button);
        mGoogleLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginPanel.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);

                //click en booton login con google
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent,GOOGLE_SIGNIN_REQUEST);
            }
        });

        //Configure el inicio de sesión para solicitar la identificación del usuario, la dirección de correo electrónico y el perfil básico. El ID y el perfil básico se incluyen en DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("409980487646-u053fave6u4vjd9esnrgqu3k8nd04e60.apps.googleusercontent.com")
                .requestEmail().build();

        //Cree un GoogleApiClient con acceso a la API de inicio de sesión de Google y las opciones especificadas por gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new  GoogleApiClient.OnConnectionFailedListener(){
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult){
                        // Se produjo un error irresoluble y las API de Google (incluido el inicio de sesión) no estarán disponibles.
                        Log.d(TAG,"Conexion Fallida:" + connectionResult);
                    }
                }).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            Log.d(TAG,"En el activity Result" + requestCode);
            // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
            if (requestCode == GOOGLE_SIGNIN_REQUEST){
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                if (result.isSuccess()){
                    // Google Sign In was successful
                    GoogleSignInAccount account = result.getSignInAccount();
                    Log.d(TAG,"IC: " + account.getId());
                    Log.d(TAG,"DISPLAY NAME: " + account.getDisplayName());
                    Log.d(TAG,"Email: " + account.getEmail());
                    Log.d(TAG,"Foto: " + account.getPhotoUrl());
                    Log.d(TAG,"Token: " + account.getIdToken());

                    //SignIn in firebaseAuthWithGoogle

                    AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
                    mAtuh.signInWithCredential(credential)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Log.d(TAG,"sesion con credenciaes: Completo" + task.isSuccessful());
                                    if (!task.isSuccessful()){
                                        loginPanel.setVisibility(View.VISIBLE);
                                        progressBar.setVisibility(View.GONE);

                                        Log.e(TAG,"Sesion con credenciales: Fallo", task.getException());
                                        Toast.makeText(LoginActivity.this,"Inicio de sesion Fallo",Toast.LENGTH_SHORT
                                        ).show();
                                    }
                                }
                            });
                }else {
                    // Google Sign In failed, hide Progress Bar & Show Login Panel again
                    loginPanel.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    Log.e(TAG,"Sesion con google Fallo");
                }
            }
        }catch (Throwable t){
            try {
                // Google Sign In failed, hide Progress Bar & Show Login Panel again
                loginPanel.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                Log.e(TAG,"on Throwable: " + t.getMessage(),t);
                if (getApplication() != null) Toast.makeText(getApplication(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }catch (Throwable x){}
        }
    }
}