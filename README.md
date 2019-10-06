# AndroidNews
An Android App using AndroidX + MVVM + Dagger2 + Room + Retrofit + LiveData + Rx


An Android application that shows a articles related to "TransferWise"
- The content is from a free to use API from newsapi.org (listed in https://github.com/public-apis/public-apis)
- I cannot guarentee the quality of the content. There might be broken image URL or malformated content text for some article
- Persists the contents of the feed locally, so if the app is used without Internet connection it will show previously downloaded content

In this app, I attempted to use
- MVVM architecture (provided by AndroidX)
- Dagger2 for dependency injection
- OkHttp + Retrofit + GSON for API service integration
- Glide for image loading
- Room for data persistent
- LiveData for linking various layers and observing data changes
- Rx for working outside the main thread
- Timber for logging
