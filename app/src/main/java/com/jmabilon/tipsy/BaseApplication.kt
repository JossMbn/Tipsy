package com.jmabilon.tipsy

import android.app.Application
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application() {

    private var _interstitialAd: InterstitialAd? = null
    private var _adRequest: AdRequest? = null

    val interstitialAd: InterstitialAd?
        get() = _interstitialAd

    val adRequest: AdRequest?
        get() = _adRequest

    override fun onCreate() {
        super.onCreate()
        _adRequest = AdRequest.Builder().build()
        setupInterstitialAds()
    }

    private fun setupInterstitialAds() {
        _adRequest?.let {
            InterstitialAd.load(
                this,
                "ca-app-pub-2132066617984288/9567106784",
                it,
                object : InterstitialAdLoadCallback() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        _interstitialAd = null
                    }

                    override fun onAdLoaded(interstitialAd: InterstitialAd) {
                        _interstitialAd = interstitialAd
                    }
                })

            _interstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    _interstitialAd = null
                }
            }
        }
    }
}