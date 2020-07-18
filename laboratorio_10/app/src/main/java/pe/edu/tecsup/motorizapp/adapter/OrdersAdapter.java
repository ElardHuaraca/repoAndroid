package pe.edu.tecsup.motorizapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pe.edu.tecsup.motorizapp.R;
import pe.edu.tecsup.motorizapp.model.Order;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder> {

    private List<Order> orders;
    private Context context;

    // Definimos un objeto viewholder que será recyclado por cada elemento de nuestra vista
    public static class OrdersViewHolder extends RecyclerView.ViewHolder {

        private TextView txtOrder;

        public OrdersViewHolder(View itemView) {
            super(itemView);

            txtOrder = (TextView) itemView.findViewById(R.id.txtOrder);
        }

        public void bindOrder(Order order) {
            String productOrder = String.format("%s - %s", order.getQuantity(), order.getProductName());
            txtOrder.setText(productOrder);
        }

    }

    public OrdersAdapter(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public OrdersViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_order, viewGroup, false);

        OrdersViewHolder ordersViewHolder = new OrdersViewHolder(itemView);

        return ordersViewHolder;
    }

    @Override
    public void onBindViewHolder(OrdersViewHolder viewHolder, int pos) {
        Order item = orders.get(pos);

        viewHolder.bindOrder(item);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

}
