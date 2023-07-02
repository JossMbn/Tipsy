package com.jmabilon.tipsy.ui.home

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.jmabilon.tipsy.R
import com.jmabilon.tipsy.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private var binding: ActivityHomeBinding? = null
    private var controller: NavController? = null

    private var interstitialAd: InterstitialAd? = null
    private var _adRequest: AdRequest? = null

    companion object {
        const val AD_UNIT_ID = "ca-app-pub-2132066617984288/9567106784"
    }

    val adRequest: AdRequest?
        get() = _adRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        MobileAds.initialize(this) { /* do nothing */ }

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        val navHostController =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        controller = navHostController.navController

        _adRequest = AdRequest.Builder().build()
        loadInterstitialAd()
    }

    private fun loadInterstitialAd() {
        _adRequest?.let {
            InterstitialAd.load(
                this,
                AD_UNIT_ID,
                it,
                object : InterstitialAdLoadCallback() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        interstitialAd = null
                    }

                    override fun onAdLoaded(interstitialAd: InterstitialAd) {
                        this@HomeActivity.interstitialAd = interstitialAd
                    }
                })
        }
    }

    fun showInterstitialAd() {
        if (interstitialAd != null) {
            interstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    interstitialAd = null
                    loadInterstitialAd()
                }
            }
            interstitialAd?.show(this)
        } else {
            Log.d("INTERSTITIAL_AD_LOG", "Ad wasn't loaded.")
        }
    }
}