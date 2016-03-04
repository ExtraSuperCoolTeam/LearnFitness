package com.codepath.apps.learnfitness;

import android.content.Context;

/*
 * This is the Android application itself and is used to configure various settings
 * including the image cache in memory and on disk. This also adds a singleton
 * for accessing the relevant rest client.
 *
 *     FacebookClient client = FitnessApplication.getRestClient();
 *     // use client to send requests to API
 *
 */
public class FitnessApplication extends com.activeandroid.app.Application {
	private static Context context;

	@Override
	public void onCreate() {
		super.onCreate();
		FitnessApplication.context = this;
	}

	public static FacebookClient getRestClient() {
		return (FacebookClient) FacebookClient.getInstance(FacebookClient.class, FitnessApplication.context);
	}
}