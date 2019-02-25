package com.example.testnetwork;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;

public class NetworkType {

  private ConnectivityManager.NetworkCallback mWifiNetworkCallback, mMobileNetworkCallback;
  private Network mWifiNetwork, mMobileNetwork;
  private final ConnectivityManager connectivityManager;

  public NetworkType(Context context) {
    connectivityManager =
        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
  }

  public Network get(String networkType) {

    if (networkType.equals(NetworkConstants.NETWORK_WIFI)) {
      if (mWifiNetworkCallback == null) {
        //Init only once
        mWifiNetworkCallback = new ConnectivityManager.NetworkCallback() {
          @Override
          public void onAvailable(final Network network) {
            try {
              //mWifiNetwork = network;
              ConnectivityManager.setProcessDefaultNetwork(network);
            } catch (NullPointerException npe) {
              npe.printStackTrace();
            }
          }
        };
      }

      NetworkRequest.Builder wifiBuilder;
      wifiBuilder = new NetworkRequest.Builder();
      wifiBuilder.addTransportType(NetworkCapabilities.TRANSPORT_WIFI);
      connectivityManager.requestNetwork(wifiBuilder.build(), mWifiNetworkCallback);

      return mWifiNetwork;
    }

    if (networkType.equals(NetworkConstants.NETWORK_CELLULAR)) {
      if (mMobileNetworkCallback == null) {
        //Init only once
        mMobileNetworkCallback = new ConnectivityManager.NetworkCallback() {
          @Override
          public void onAvailable(final Network network) {
            try {
              mMobileNetwork = network;
            } catch (NullPointerException npe) {
              npe.printStackTrace();
            }
          }
        };
      }

      NetworkRequest.Builder mobileNwBuilder;
      mobileNwBuilder = new NetworkRequest.Builder();
      mobileNwBuilder.addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR);
      connectivityManager.requestNetwork(mobileNwBuilder.build(), mMobileNetworkCallback);

      return mMobileNetwork;
    }

    return null;
  }
}
