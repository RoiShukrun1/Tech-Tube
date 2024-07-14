//package com.example.tech_titans_app.ui.api;
//
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//public class VideosAPI {
//
//    Retrofit retrofit;
//    WebServiceAPI webServiceAPI;
//
//    public PostAPI() {
//        retrofit = new Retrofit.Builder()
//                .baseUrl("http://10.0.2.2:80/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        webServiceAPI = retrofit.create(WebServiceAPI.class);
//    }
//    public void get() {
//
//        Call<List<Post>> call = webServiceAPI.getPosts();
//        call.enqueue(new Callback<List<Post>>() {
//            @Override
//            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
//                List<Post> posts = response.body();
//                // Handle the response, e.g., update the UI
//            }
//
//            @Override
//            public void onFailure(Call<List<Post>> call, Throwable t) {
//                // Handle the error
//            }
//        });
//    }
//}
//
//public class UsersAPI {
//
//    private Retrofit retrofit;
//    private WebServiceAPI webServiceAPI;
//
//    public UsersAPI() {
//        retrofit = new Retrofit.Builder()
//                .baseUrl(R.)
//                .baseUrl("http://10.0.2.2/") // Use the correct port your server is running on
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        webServiceAPI = retrofit.create(WebServiceAPI.class);
//    }
//
//    public void registerUser(UserData user, Callback<Void> callback) {
//        Call<Void> call = webServiceAPI.registerUser(user);
//        call.enqueue(callback);
//    }
//}
//
