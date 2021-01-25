package com.app.medicineauthourity.domain.author;

import com.app.medicineauthourity.data.author.CategoryRepository;
import com.app.medicineauthourity.data.model.Category;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

public class CategoryRepositoryImpl implements CategoryRepository {

    private final DatabaseReference mDatabase;

    public CategoryRepositoryImpl() {
        mDatabase = FirebaseDatabase.getInstance().getReference(Category.class.getSimpleName());
    }

    @Override
    public void addCategory(Category category, MutableLiveData<Boolean> success) {
        mDatabase.push().setValue(category).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                success.setValue(task.isSuccessful());
            }
        });
    }

    @Override
    public void retrieveCategories(MutableLiveData<List<Category>> categories) {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Category> retrievedCategories = new ArrayList<>();
                Iterable<DataSnapshot> dataSnapshots = dataSnapshot.getChildren();
                for (DataSnapshot categorySnapshot : dataSnapshots) {
                    String id = categorySnapshot.getKey();
                    Category category = categorySnapshot.getValue(Category.class);
                    category.setId(id);
                    retrievedCategories.add(category);
                }
                categories.setValue(retrievedCategories);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                categories.setValue(null);
            }
        });
    }
}
