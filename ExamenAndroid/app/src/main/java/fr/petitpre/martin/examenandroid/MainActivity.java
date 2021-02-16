package fr.petitpre.martin.examenandroid;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import fr.petitpre.martin.examenandroid.FirstFragment;
import fr.petitpre.martin.examenandroid.SecondFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import fr.petitpre.martin.examenandroid.RetrofitService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Implémentation du viewPager (Pour afficher les fragments first et second)
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(new SectionsPagerAdapter(this, getSupportFragmentManager()));

        //Implémentation du tabeLayout pour afficher le titre des tabs
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        //je nai pas reissui a faire fonctionner lappel à l'api
        RetrofitService retrofitService = new Retrofit.Builder()
                .baseUrl(RetrofitService.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RetrofitService.class);

        System.out.println("test");
        retrofitService.getListeJoueurs().enqueue(new Callback<List<Joueur>>() {
            @Override
            public void onResponse(Call<List<Joueur>> call, Response<List<Joueur>> response) {
                afficherJoueurs(response.body());
                System.out.println("succes");
            }

            @Override
            public void onFailure(Call<List<Joueur>> call, Throwable t) {
                System.out.println("echec");
            }
        });

    }

    public void afficherJoueurs(List<Joueur> joueurs) {
        Toast.makeText(this,"nombre de joueurs : "+joueurs.size(), Toast.LENGTH_SHORT).show();
        System.out.println("toto"+joueurs.size());
    }

    //implémentation du PagerAdapter qui s'occupe d'afficher les différents fragments (first ou second ici) par swipe et par click sur le tab
    public static class SectionsPagerAdapter extends FragmentPagerAdapter {

        @StringRes
        private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
        private final Context mContext;

        public SectionsPagerAdapter(Context context, FragmentManager fm) {
            super(fm);
            mContext = context;
        }

        @NotNull
        @Override
        public Fragment getItem(int position) {
            return position == 0 ? new FirstFragment() : new SecondFragment();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mContext.getResources().getString(TAB_TITLES[position]);
        }

        @Override
        public int getCount() {
            return TAB_TITLES.length;
        }
    }
}