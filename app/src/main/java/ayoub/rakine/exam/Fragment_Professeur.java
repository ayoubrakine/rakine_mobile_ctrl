package ayoub.rakine.exam;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class Fragment_Professeur extends Fragment {

    private Button add ;
    private Button show ;
    private Button byfiliere ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__professeur, container, false);

        add = view.findViewById(R.id.add);
        show = view.findViewById(R.id.show);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddEmploye.class); // Utilisation de getActivity() pour obtenir le contexte de l'activité
                startActivity(intent);
            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Showemploye.class); // Utilisation de getActivity() pour obtenir le contexte de l'activité
                startActivity(intent);
            }
        });


        return view;
    }
}
