package com.example.oderapp.Fragment.SanPhamFragment;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.oderapp.Adapter.Product_Dashboard_sanPham_Adapter;
import com.example.oderapp.Adapter.Product_SanPham_Dashboard_Adapter;
import com.example.oderapp.Adapter.Product_oders_Adapter;
import com.example.oderapp.Model.DashboardSanPham;
import com.example.oderapp.Model.Product_oders;
import com.example.oderapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AllSanPhamFragment extends Fragment {

    RecyclerView recyclerViewProductAllSanPham;
    Product_Dashboard_sanPham_Adapter product_sanPham_dashboard_adapter;
    List<DashboardSanPham> list;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_all_san_pham, container, false);
        list = new ArrayList<>();
        recyclerViewProductAllSanPham = view.findViewById(R.id.recyclerViewProductAllSanPham);
        product_sanPham_dashboard_adapter = new Product_Dashboard_sanPham_Adapter(this.getContext(),R.layout.line_sanpham_dashboardsanpham,list);
        recyclerViewProductAllSanPham.setHasFixedSize(true);
        recyclerViewProductAllSanPham.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewProductAllSanPham.setAdapter(product_sanPham_dashboard_adapter);

        product_sanPham_dashboard_adapter.notifyDataSetChanged();
        for (int i = 0 ;i < 10 ; i ++){
            list.add(new DashboardSanPham(1,"Banh Ngon gì digf hâhha",2500,"https://image.flaticon.com/icons/png/128/2971/2971975.png","Bài văn về thầy giáo cũ gây xúc động\n" +
                    "\n" +
                    "Ngày 16/10, Vũ Phương Thảo (lớp 10A1, THPT Định Hóa) được biết đến với bài văn điểm 10 về người thầy có những cảm xúc trong sáng, chân thành.\n" +
                    "\n" +
                    "Trong bài văn, Thảo viết: “Máy quay dường như đang chậm lại, từng cảnh từng nét hiện lên rõ ràng. Tôi thấy thầy đang lụi hụi trồng rau, chăm sóc con chó lông trắng đen già khụ, thấy cả chúng tôi ngày đó, trong những ngày vất vả nhưng yên bình. Tôi nghĩ, có lẽ đó là những ngày hạnh phúc và vui vẻ nhất tôi từng có. Sau này, khi bước đi trên đường đời chông gai, có thể sẽ chẳng còn ai chỉ bảo, dạy dỗ tôi tận tình như thầy đã từng, có thể sẽ chẳng có ai lo tôi liệu có ngủ đủ giấc, liệu có stress khi nhồi nhét quá nhiều. Nhưng, cố nhân từng nói, cuộc đời chỉ cần một người khiến ta ngưỡng mộ, để cả đời noi gương, cả đời thương mến. Vậy là quá đủ rồi”.",1));
        }




        return view;
    }
}