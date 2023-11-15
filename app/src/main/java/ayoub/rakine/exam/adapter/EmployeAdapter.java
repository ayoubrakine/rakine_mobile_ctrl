package ayoub.rakine.exam.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ayoub.rakine.exam.R;
import ayoub.rakine.exam.beans.Employe;

public class EmployeAdapter  extends BaseAdapter {
    private List<Employe> employes;
    private LayoutInflater inflater;

    public EmployeAdapter(List<Employe> employes, Context context) {
        this.employes = employes;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return employes.size();
    }

    @Override
    public Object getItem(int position) {
        return employes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return employes.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_employe, parent, false);
        }

        TextView id = convertView.findViewById(ayoub.rakine.exam.R.id.id);
        TextView nom = convertView.findViewById(R.id.nom);
        TextView prenom = convertView.findViewById(R.id.prenom);

        TextView dateNaissance = convertView.findViewById(R.id.dateNaissance);
        TextView service = convertView.findViewById(R.id.service);

        Employe employe = employes.get(position);

        id.setText(String.valueOf(employe.getId()));

        nom.setText(employe.getNom());
        prenom.setText(employe.getPrenom());

        service.setText(employe.getService().getNom());
        dateNaissance.setText(employe.getDateNaissance().toString());

        return convertView;
    }

}
