package com.example.ecommerce.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.ecommerce.Adapters.ordersAdapter;
import com.example.ecommerce.Models.adminOrders;
import com.example.ecommerce.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminCheckOrders extends AppCompatActivity {
    private RecyclerView ordersList;
    private DatabaseReference ordersRef;
    ArrayList<adminOrders> orders;
    ordersAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_check_orders);
        ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders");
        ordersList = findViewById(R.id.ordersList);
        ordersList.setLayoutManager(new LinearLayoutManager(this));
        orders = new ArrayList<>();
        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String name = (String) dataSnapshot1.child("Name").getValue();
                    String price = (String) dataSnapshot1.child("Total Amount").getValue();
                    String phone = (String) dataSnapshot1.child("Phone").getValue();
                    String address = (String) dataSnapshot1.child("Address").getValue();
                    String time = (String) dataSnapshot1.child("Time").getValue();
                    String date = (String) dataSnapshot1.child("Date").getValue();
                    String city = (String) dataSnapshot1.child("City").getValue();
                    adminOrders adminOrders = new adminOrders();

                    adminOrders.setName(name);
                    adminOrders.setTime(time);
                    adminOrders.setAddress(address);
                    adminOrders.setCity(city);
                    adminOrders.setTotalAmount(price);
                    adminOrders.setDate(date);
                    adminOrders.setPhone(phone);



                    orders.add(adminOrders);
                }
                adapter = new ordersAdapter(orders, AdminCheckOrders.this);
                ordersList.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });


    }


















/*
    @Override
    protected void onStart() {
        super.onStart();


        FirebaseRecyclerOptions<adminOrders> options =
                new FirebaseRecyclerOptions.Builder<adminOrders>()
                        .setQuery(ordersRef, adminOrders.class)
                        .build();

        FirebaseRecyclerAdapter<adminOrders, adminOrdersViewHolder> adapter =
                new FirebaseRecyclerAdapter<adminOrders, adminOrdersViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull final adminOrdersViewHolder adminOrdersViewHolder, int i, @NonNull adminOrders adminOrders) {


                        adminOrdersViewHolder.Oname.setText("Name: " + adminOrders.getName());
                        adminOrdersViewHolder.Ophone.setText("Phone Number: " + adminOrders.getPhone());
                        adminOrdersViewHolder.Oprice.setText("Total Price: " + adminOrders.getTotalAmount() + " $");
                        adminOrdersViewHolder.Otime.setText("Ordered at: " + adminOrders.getDate() + adminOrders.getTime());
                        adminOrdersViewHolder.OshippingAddress.setText("Shipping Address: " + adminOrders.getAddress() + " , " + adminOrders.getCity());

                    }

                    @NonNull
                    @Override
                    public adminOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_row, parent, false);
                        return new adminOrdersViewHolder(view);
                    }
                };
        ordersList.setAdapter(adapter);
        adapter.startListening();


    }

    public static class adminOrdersViewHolder extends RecyclerView.ViewHolder {
        public TextView Oname, Ophone, Oprice, Otime, OshippingAddress;
        public Button showAll;

        public adminOrdersViewHolder(@NonNull View itemView) {
            super(itemView);
            Oname = itemView.findViewById(R.id.order_username);
            Ophone = itemView.findViewById(R.id.order_phone);
            Oprice = itemView.findViewById(R.id.order_total_price);
            Otime = itemView.findViewById(R.id.order_date_time);
            OshippingAddress = itemView.findViewById(R.id.order_address_city);
            showAll = itemView.findViewById(R.id.show_all_products_btn);


        }
    }*/
}
