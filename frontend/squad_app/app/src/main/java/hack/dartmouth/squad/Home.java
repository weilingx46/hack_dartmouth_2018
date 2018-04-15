package hack.dartmouth.squad;

import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;


public class Home extends Fragment {
    static Menu curActivity;
    static int type;
    static SharedPreferences sp;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        curActivity = (Menu) getActivity();
        sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        TextView testArea = view.findViewById(R.id.testArea);

        final TextView login_username = view.findViewById(R.id.login_username);
        final TextView login_password = view.findViewById(R.id.login_password);

        Button buttonLogin = view.findViewById(R.id.button_login);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   curActivity.JsonRequestLogin(login_username.getText().toString(), login_password.getText().toString());
               }
           }
        );

        final TextView signup_name = view.findViewById(R.id.signup_name);
        final TextView signup_username = view.findViewById(R.id.signup_username);
        final TextView signup_password = view.findViewById(R.id.signup_password);

        Button buttonSignup = view.findViewById(R.id.button_signup);
        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curActivity.JsonRequestSignup(signup_name.getText().toString(), signup_username.getText().toString(), signup_password.getText().toString());
            }
        });

        // get Menu's (parsed JSON) response variable and display it
        if (((Menu) getActivity()).response != null) {
            testArea.setText(((Menu) getActivity()).response.toString());
        }

        TextView tv_api = view.findViewById(R.id.tv_api);
        tv_api.setText(((Menu) getActivity()).api_data.toString());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

}