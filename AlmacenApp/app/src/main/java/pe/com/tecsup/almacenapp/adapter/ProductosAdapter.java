package pe.com.tecsup.almacenapp.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import pe.com.tecsup.almacenapp.R;
import pe.com.tecsup.almacenapp.activities.DetailActivity;
import pe.com.tecsup.almacenapp.model.Producto;
import pe.com.tecsup.almacenapp.services.ApiService;

public class ProductosAdapter extends RecyclerView.Adapter<ProductosAdapter.ViewHolder>{

    private List<Producto> productos;

    public ProductosAdapter(){
        this.productos=new ArrayList<>();
    }

    public void setProductos(List<Producto> productos){
        this.productos=productos;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView fotoImage;
        public TextView nombreText;
        public TextView precioText;

        public ViewHolder(View itemView){
            super(itemView);;
            fotoImage=itemView.findViewById(R.id.foto_image);
            nombreText=itemView.findViewById(R.id.nombre_text);
            precioText=itemView.findViewById(R.id.precio_text);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType){
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto,parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public  void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position){
        final Producto producto=this.productos.get(position);

        viewHolder.nombreText.setText(producto.getNombre());
        viewHolder.precioText.setText("S/."+producto.getPrecio());

        String url = ApiService.API_BASE_URL+"/productos/images/"+producto.getImagen();
        Picasso.with(viewHolder.itemView.getContext()).load(url).into(viewHolder.fotoImage);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(viewHolder.itemView.getContext(), DetailActivity.class);
                intent.putExtra("ID", producto.getId());
                viewHolder.itemView.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount(){
            return this.productos.size();
    }


}
