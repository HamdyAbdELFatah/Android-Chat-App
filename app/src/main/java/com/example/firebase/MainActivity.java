package com.example.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference emp_db = database.getReference("Employee").child("Name");
        Employee employee=new Employee();
        employee.setName("Hamdy");
        employee.setAge(20);
        employee.setCity("Mansoura");
        emp_db.push().setValue(employee);
        text=findViewById(R.id.text);
       remove();
      //  query();
    }

    void getdata(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference posts = database.getReference("Employee").child("Name");

        posts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                   Employee e= d.getValue(Employee.class);
                   text.append(e.getName()+" "+e.getAge()+" "+e.getCity()+"\n");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    void query(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference posts = database.getReference("Employee").child("Name");
        Query query=posts.orderByChild("name").equalTo("Hamdy");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Employee e= d.getValue(Employee.class);
                    Toast.makeText(MainActivity.this, e.getAge()+"", Toast.LENGTH_SHORT).show();
                }
               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    void remove(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference posts = database.getReference("Employee").child("Name");
        Query query=posts.orderByChild("name").equalTo("Hamdy");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Employee e= d.getValue(Employee.class);
                    d.getRef().removeValue();
                    Toast.makeText(MainActivity.this, "Destroyed", Toast.LENGTH_SHORT).show();
                }
                    getdata();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
