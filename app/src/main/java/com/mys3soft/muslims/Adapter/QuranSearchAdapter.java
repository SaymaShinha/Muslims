package com.mys3soft.muslims.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mys3soft.muslims.Models.Ayah;
import com.mys3soft.muslims.R;
import com.mys3soft.muslims.Tools.Tools;

import java.util.List;

public class QuranSearchAdapter extends RecyclerView.Adapter<QuranSearchAdapter.ViewHolder> {
    private Context context;
    private List<Ayah> ayahList;
    private View mMainView;
    private String ayah_text_en;

    public QuranSearchAdapter(Context context, List<Ayah> ayahList) {
        this.context = context;
        this.ayahList = ayahList;
    }


    @NonNull
    @Override
    public QuranSearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mMainView = LayoutInflater.from(context).inflate(R.layout.single_ayah_layout, parent, false);
        return new QuranSearchAdapter.ViewHolder(mMainView);
    }


    @Override
    public void onBindViewHolder(@NonNull QuranSearchAdapter.ViewHolder holder, final int position) {
        Ayah ayah = ayahList.get(position);
        int ayah_num = ayah.getAyah_number();
        int surah_num = ayah.getSurah_number();
        switch (surah_num) {
            case 2:
                ayah_text_en = ayah_num - 7 + ". " + ayah.getText();
                break;
            case 3:
                ayah_text_en = ayah_num - 293 + ". " + ayah.getText();
                break;
            case 4:
                ayah_text_en = ayah_num - 493 + ". " + ayah.getText();
                break;
            case 5:
                ayah_text_en = ayah_num - 669 + ". " + ayah.getText();
                break;
            case 6:
                ayah_text_en = ayah_num - 789 + ". " + ayah.getText();
                break;
            case 7:
                ayah_text_en = ayah_num - 954 + ". " + ayah.getText();
                break;
            case 8:
                ayah_text_en = ayah_num - 1160 + ". " + ayah.getText();
                break;
            case 9:
                ayah_text_en = ayah_num - 1235 + ". " + ayah.getText();
                break;
            case 10:
                ayah_text_en = ayah_num - 1364 + ". " + ayah.getText();
                break;
            case 11:
                ayah_text_en = ayah_num - 1473 + ". " + ayah.getText();
                break;
            case 12:
                ayah_text_en = ayah_num - 1596 + ". " + ayah.getText();
                break;
            case 13:
                ayah_text_en = ayah_num - 1707 + ". " + ayah.getText();
                break;
            case 14:
                ayah_text_en = ayah_num - 1750 + ". " + ayah.getText();
                break;
            case 15:
                ayah_text_en = ayah_num - 1802 + ". " + ayah.getText();
                break;
            case 16:
                ayah_text_en = ayah_num - 1901 + ". " + ayah.getText();
                break;
            case 17:
                ayah_text_en = ayah_num - 2029 + ". " + ayah.getText();
                break;
            case 18:
                ayah_text_en = ayah_num - 2140 + ". " + ayah.getText();
                break;
            case 19:
                ayah_text_en = ayah_num - 2250 + ". " + ayah.getText();
                break;
            case 20:
                ayah_text_en = ayah_num - 2348 + ". " + ayah.getText();
                break;
            case 21:
                ayah_text_en = ayah_num - 2483 + ". " + ayah.getText();
                break;
            case 22:
                ayah_text_en = ayah_num - 2595 + ". " + ayah.getText();
                break;
            case 23:
                ayah_text_en = ayah_num - 2673 + ". " + ayah.getText();
                break;
            case 24:
                ayah_text_en = ayah_num - 2791 + ". " + ayah.getText();
                break;
            case 25:
                ayah_text_en = ayah_num - 2855 + ". " + ayah.getText();
                break;
            case 26:
                ayah_text_en = ayah_num - 2932 + ". " + ayah.getText();
                break;
            case 27:
                ayah_text_en = ayah_num - 3159 + ". " + ayah.getText();
                break;
            case 28:
                ayah_text_en = ayah_num - 3252 + ". " + ayah.getText();
                break;
            case 29:
                ayah_text_en = ayah_num - 3340 + ". " + ayah.getText();
                break;
            case 30:
                ayah_text_en = ayah_num - 3409 + ". " + ayah.getText();
                break;
            case 31:
                ayah_text_en = ayah_num - 3469 + ". " + ayah.getText();
                break;
            case 32:
                ayah_text_en = ayah_num - 3503 + ". " + ayah.getText();
                break;
            case 33:
                ayah_text_en = ayah_num - 3533 + ". " + ayah.getText();
                break;
            case 34:
                ayah_text_en = ayah_num - 3606 + ". " + ayah.getText();
                break;
            case 35:
                ayah_text_en = ayah_num - 3660 + ". " + ayah.getText();
                break;
            case 36:
                ayah_text_en = ayah_num - 3705 + ". " + ayah.getText();
                break;
            case 37:
                ayah_text_en = ayah_num - 3788 + ". " + ayah.getText();
                break;
            case 38:
                ayah_text_en = ayah_num - 3970 + ". " + ayah.getText();
                break;
            case 39:
                ayah_text_en = ayah_num - 4058 + ". " + ayah.getText();
                break;
            case 40:
                ayah_text_en = ayah_num - 4133 + ". " + ayah.getText();
                break;
            case 41:
                ayah_text_en = ayah_num - 4218 + ". " + ayah.getText();
                break;
            case 42:
                ayah_text_en = ayah_num - 4272 + ". " + ayah.getText();
                break;
            case 43:
                ayah_text_en = ayah_num - 4325 + ". " + ayah.getText();
                break;
            case 44:
                ayah_text_en = ayah_num - 4414 + ". " + ayah.getText();
                break;
            case 45:
                ayah_text_en = ayah_num - 4473 + ". " + ayah.getText();
                break;
            case 46:
                ayah_text_en = ayah_num - 4510 + ". " + ayah.getText();
                break;
            case 47:
                ayah_text_en = ayah_num - 4545 + ". " + ayah.getText();
                break;
            case 48:
                ayah_text_en = ayah_num - 4583 + ". " + ayah.getText();
                break;
            case 49:
                ayah_text_en = ayah_num - 4612 + ". " + ayah.getText();
                break;
            case 50:
                ayah_text_en = ayah_num - 4630 + ". " + ayah.getText();
                break;
            case 51:
                ayah_text_en = ayah_num - 4675 + ". " + ayah.getText();
                break;
            case 52:
                ayah_text_en = ayah_num - 4735 + ". " + ayah.getText();
                break;
            case 53:
                ayah_text_en = ayah_num - 4784 + ". " + ayah.getText();
                break;
            case 54:
                ayah_text_en = ayah_num - 4846 + ". " + ayah.getText();
                break;
            case 55:
                ayah_text_en = ayah_num - 4901 + ". " + ayah.getText();
                break;
            case 56:
                ayah_text_en = ayah_num - 4979 + ". " + ayah.getText();
                break;
            case 57:
                ayah_text_en = ayah_num - 5075 + ". " + ayah.getText();
                break;
            case 58:
                ayah_text_en = ayah_num - 5104 + ". " + ayah.getText();
                break;
            case 59:
                ayah_text_en = ayah_num - 5126 + ". " + ayah.getText();
                break;
            case 60:
                ayah_text_en = ayah_num - 5150 + ". " + ayah.getText();
                break;
            case 61:
                ayah_text_en = ayah_num - 5163 + ". " + ayah.getText();
                break;
            case 62:
                ayah_text_en = ayah_num - 5177 + ". " + ayah.getText();
                break;
            case 63:
                ayah_text_en = ayah_num - 5188 + ". " + ayah.getText();
                break;
            case 64:
                ayah_text_en = ayah_num - 5199 + ". " + ayah.getText();
                break;
            case 65:
                ayah_text_en = ayah_num - 5217 + ". " + ayah.getText();
                break;
            case 66:
                ayah_text_en = ayah_num - 5229 + ". " + ayah.getText();
                break;
            case 67:
                ayah_text_en = ayah_num - 5241 + ". " + ayah.getText();
                break;
            case 68:
                ayah_text_en = ayah_num - 5271 + ". " + ayah.getText();
                break;
            case 69:
                ayah_text_en = ayah_num - 5323 + ". " + ayah.getText();
                break;
            case 70:
                ayah_text_en = ayah_num - 5375 + ". " + ayah.getText();
                break;
            case 71:
                ayah_text_en = ayah_num - 5419 + ". " + ayah.getText();
                break;
            case 72:
                ayah_text_en = ayah_num - 5447 + ". " + ayah.getText();
                break;
            case 73:
                ayah_text_en = ayah_num - 5475 + ". " + ayah.getText();
                break;
            case 74:
                ayah_text_en = ayah_num - 5495 + ". " + ayah.getText();
                break;
            case 75:
                ayah_text_en = ayah_num - 5551 + ". " + ayah.getText();
                break;
            case 76:
                ayah_text_en = ayah_num - 5591 + ". " + ayah.getText();
                break;
            case 77:
                ayah_text_en = ayah_num - 5622 + ". " + ayah.getText();
                break;
            case 78:
                ayah_text_en = ayah_num - 5672 + ". " + ayah.getText();
                break;
            case 79:
                ayah_text_en = ayah_num - 5712 + ". " + ayah.getText();
                break;
            case 80:
                ayah_text_en = ayah_num - 5758 + ". " + ayah.getText();
                break;
            case 81:
                ayah_text_en = ayah_num - 5800 + ". " + ayah.getText();
                break;
            case 82:
                ayah_text_en = ayah_num - 5829 + ". " + ayah.getText();
                break;
            case 83:
                ayah_text_en = ayah_num - 5848 + ". " + ayah.getText();
                break;
            case 84:
                ayah_text_en = ayah_num - 5884 + ". " + ayah.getText();
                break;
            case 85:
                ayah_text_en = ayah_num - 5909 + ". " + ayah.getText();
                break;
            case 86:
                ayah_text_en = ayah_num - 5931 + ". " + ayah.getText();
                break;
            case 87:
                ayah_text_en = ayah_num - 5948 + ". " + ayah.getText();
                break;
            case 88:
                ayah_text_en = ayah_num - 5967 + ". " + ayah.getText();
                break;
            case 89:
                ayah_text_en = ayah_num - 5993 + ". " + ayah.getText();
                break;
            case 90:
                ayah_text_en = ayah_num - 6023 + ". " + ayah.getText();
                break;
            case 91:
                ayah_text_en = ayah_num - 6043 + ". " + ayah.getText();
                break;
            case 92:
                ayah_text_en = ayah_num - 6058 + ". " + ayah.getText();
                break;
            case 93:
                ayah_text_en = ayah_num - 6079 + ". " + ayah.getText();
                break;
            case 94:
                ayah_text_en = ayah_num - 6090 + ". " + ayah.getText();
                break;
            case 95:
                ayah_text_en = ayah_num - 6098 + ". " + ayah.getText();
                break;
            case 96:
                ayah_text_en = ayah_num - 6106 + ". " + ayah.getText();
                break;
            case 97:
                ayah_text_en = ayah_num - 6125 + ". " + ayah.getText();
                break;
            case 98:
                ayah_text_en = ayah_num - 6130 + ". " + ayah.getText();
                break;
            case 99:
                ayah_text_en = ayah_num - 6138 + ". " + ayah.getText();
                break;
            case 100:
                ayah_text_en = ayah_num - 6146 + ". " + ayah.getText();
                break;
            case 101:
                ayah_text_en = ayah_num - 6157 + ". " + ayah.getText();
                break;
            case 102:
                ayah_text_en = ayah_num - 6168 + ". " + ayah.getText();
                break;
            case 103:
                ayah_text_en = ayah_num - 6176 + ". " + ayah.getText();
                break;
            case 104:
                ayah_text_en = ayah_num - 6179 + ". " + ayah.getText();
                break;
            case 105:
                ayah_text_en = ayah_num - 6188 + ". " + ayah.getText();
                break;
            case 106:
                ayah_text_en = ayah_num - 6193 + ". " + ayah.getText();
                break;
            case 107:
                ayah_text_en = ayah_num - 6197 + ". " + ayah.getText();
                break;
            case 108:
                ayah_text_en = ayah_num - 6204 + ". " + ayah.getText();
                break;
            case 109:
                ayah_text_en = ayah_num - 6207 + ". " + ayah.getText();
                break;
            case 110:
                ayah_text_en = ayah_num - 6213 + ". " + ayah.getText();
                break;
            case 111:
                ayah_text_en = ayah_num - 6216 + ". " + ayah.getText();
                break;
            case 112:
                ayah_text_en = ayah_num - 6221 + ". " + ayah.getText();
                break;
            case 113:
                ayah_text_en = ayah_num - 6225 + ". " + ayah.getText();
                break;
            case 114:
                ayah_text_en = ayah_num - 6230 + ". " + ayah.getText();
                break;
            default:
                ayah_text_en = ayah_num +". "+ayah.getText();
        }

        holder.mAyahTrasTV_1.setText(ayah_text_en);

        holder.mAyahTV.setText(ayah.getSurah_en_name());
    }

    @Override
    public int getItemCount() {
        return ayahList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mAyahTV, mEnTransAyah, mAyahTrasTV_1;
        private TableLayout mTableLayout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mAyahTV = itemView.findViewById(R.id.ayah_id);
            mEnTransAyah = itemView.findViewById(R.id.ayah_en_translation);
            mAyahTrasTV_1 = itemView.findViewById(R.id.ayah_translation_1_id);

            mTableLayout = itemView.findViewById(R.id.single_ayah_table_layout);
        }
    }
}

