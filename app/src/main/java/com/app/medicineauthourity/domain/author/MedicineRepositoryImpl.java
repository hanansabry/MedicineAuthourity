package com.app.medicineauthourity.domain.author;

import com.app.medicineauthourity.data.author.MedicineRepository;
import com.app.medicineauthourity.data.model.Category;
import com.app.medicineauthourity.data.model.Medicine;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

public class MedicineRepositoryImpl implements MedicineRepository {

    private final FirebaseDatabase mDatabase;

    public MedicineRepositoryImpl() {
        mDatabase = FirebaseDatabase.getInstance();
    }

    @Override
    public void addMedicine(Medicine medicine, MutableLiveData<Boolean> success) {
        DatabaseReference medId = mDatabase.getReference(Medicine.class.getSimpleName()).push();
        medId.setValue(medicine).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    //add this medicine to it's category
                    HashMap<String, Object> medicineNameId = new HashMap<>();
                    medicineNameId.put("id", medId.getKey());
                    medicineNameId.put("name", medicine.getName());
                    mDatabase.getReference(Category.class.getSimpleName())
                            .child(medicine.getCategoryId())
                            .push()
                            .updateChildren(medicineNameId, (databaseError, databaseReference) -> success.setValue(databaseError == null));
                } else {
                    success.setValue(false);
                }
            }
        });
    }

    @Override
    public void updateApproveStatus(Medicine medicine, MutableLiveData<Boolean> updateStatus) {
        HashMap<String, Object> newValues = new HashMap<>();
        newValues.put("approved", medicine.isApproved());
        newValues.put("attrs", medicine.getAttrs());
        mDatabase.getReference(Medicine.class.getSimpleName()).child(medicine.getId())
                .updateChildren(newValues, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        updateStatus.setValue(databaseError == null);
                    }
                });
    }

    @Override
    public void retrieveMedicinesById(String medicineId, MutableLiveData<Medicine> medicine) {
        mDatabase.getReference(Medicine.class.getSimpleName()).child(medicineId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Medicine retrievedMedicine = dataSnapshot.getValue(Medicine.class);
                retrievedMedicine.setId(medicineId);
                medicine.setValue(retrievedMedicine);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                medicine.setValue(null);
            }
        });
    }

    @Override
    public void retrieveMedicineByCode(String barcode, MutableLiveData<Medicine> medicine) {
        mDatabase.getReference(Medicine.class.getSimpleName()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> dataSnapshots = dataSnapshot.getChildren();
                for (DataSnapshot medicineSnapshot : dataSnapshots) {
                    String id = medicineSnapshot.getKey();
                    Medicine retrievedMedicine = medicineSnapshot.getValue(Medicine.class);
                    retrievedMedicine.setId(id);
                    if (retrievedMedicine.getBarQRCode().equals(barcode)) {
                        medicine.setValue(retrievedMedicine);
                        return;
                    }
                }
                medicine.setValue(null);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                medicine.setValue(null);
            }
        });
    }

    @Override
    public void retrieveAllMedicines(MutableLiveData<List<Medicine>> medicines) {
        mDatabase.getReference(Medicine.class.getSimpleName()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Medicine> retrievedMedicines = new ArrayList<>();
                Iterable<DataSnapshot> dataSnapshots = dataSnapshot.getChildren();
                for (DataSnapshot medicineSnapshot : dataSnapshots) {
                    String id = medicineSnapshot.getKey();
                    Medicine medicine = medicineSnapshot.getValue(Medicine.class);
                    medicine.setId(id);
                    retrievedMedicines.add(medicine);
                }
                medicines.setValue(retrievedMedicines);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                medicines.setValue(null);
            }
        });
    }

    @Override
    public void retrieveMedicinesByCategory(String categoryId, MutableLiveData<List<Medicine>> medicines) {
        mDatabase.getReference(Medicine.class.getSimpleName()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Medicine> retrievedMedicines = new ArrayList<>();
                Iterable<DataSnapshot> dataSnapshots = dataSnapshot.getChildren();
                for (DataSnapshot medicineSnapshot : dataSnapshots) {
                    String id = medicineSnapshot.getKey();
                    Medicine medicine = medicineSnapshot.getValue(Medicine.class);
                    medicine.setId(id);
                    if (medicine.getCategoryId().equals(categoryId)) {
                        retrievedMedicines.add(medicine);
                    }
                }
                medicines.setValue(retrievedMedicines);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                medicines.setValue(null);
            }
        });
    }

    @Override
    public void retrieveMedicinesByProduction(String productionType, MutableLiveData<List<Medicine>> medicines) {
        mDatabase.getReference(Medicine.class.getSimpleName()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Medicine> genericMedicines = new ArrayList<>();
                Iterable<DataSnapshot> dataSnapshots = dataSnapshot.getChildren();
                for (DataSnapshot medicineSnapshot : dataSnapshots) {
                    String id = medicineSnapshot.getKey();
                    Medicine medicine = medicineSnapshot.getValue(Medicine.class);
                    medicine.setId(id);
                    if (Medicine.Production.valueOf(medicine.getProduction()) == Medicine.Production.Generic) {
                        genericMedicines.add(medicine);
                    }
                }
                medicines.setValue(genericMedicines);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                medicines.setValue(null);
            }
        });
    }

    @Override
    public void addConflictMedicinesForMedicine(Medicine medicine, String value, MutableLiveData<Boolean> success) {
        DatabaseReference rootRef = mDatabase.getReference(Medicine.class.getSimpleName())
                .child(medicine.getId())
                .child("conflict_medicines");
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() == null) {
                            addNewValueInList("0", value, rootRef, success);
                        } else {
                            ArrayList<String> currentList = (ArrayList<String>) dataSnapshot.getValue();
                            addNewValueInList(currentList.size()+"", value, rootRef, success);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        success.setValue(false);
                    }
                });
    }

    private void addNewValueInList(String index, String value, DatabaseReference rootRef, MutableLiveData<Boolean> success) {
        Map<String, Object> map = new HashMap<>();
        map.put(index, value);
        rootRef.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                success.setValue(task.isSuccessful());
            }
        });
    }

    @Override
    public void addContradictoryDiseasesForMedicine(Medicine medicine, String value, MutableLiveData<Boolean> success) {
        DatabaseReference rootRef = mDatabase.getReference(Medicine.class.getSimpleName())
                .child(medicine.getId())
                .child("contradictory_diseases");
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    addNewValueInList("0", value, rootRef, success);
                } else {
                    ArrayList<String> currentList = (ArrayList<String>) dataSnapshot.getValue();
                    addNewValueInList(currentList.size()+"", value, rootRef, success);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                success.setValue(false);
            }
        });
    }

    @Override
    public void retrieveConflictMedicinesForMedicine(Medicine medicine,MutableLiveData<List<String>> conflictMedicinesLiveData) {
        mDatabase.getReference(Medicine.class.getSimpleName())
                .child(medicine.getId())
                .child("conflict_medicines")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ArrayList<String> conflictMedicines = (ArrayList<String>) dataSnapshot.getValue();
                        conflictMedicinesLiveData.setValue(conflictMedicines);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        conflictMedicinesLiveData.setValue(null);
                    }
                });
    }

    @Override
    public void retrieveContradictoryDiseasesForMedicine(Medicine medicine, MutableLiveData<List<String>> ContradictoryDiseasesLiveData) {
        mDatabase.getReference(Medicine.class.getSimpleName())
                .child(medicine.getId())
                .child("contradictory_diseases")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ArrayList<String> ContradictoryDiseases = (ArrayList<String>) dataSnapshot.getValue();
                        ContradictoryDiseasesLiveData.setValue(ContradictoryDiseases);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        ContradictoryDiseasesLiveData.setValue(null);
                    }
                });
    }
}
