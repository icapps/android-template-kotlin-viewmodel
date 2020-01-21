# icapps Kotlin project template with LiveData, ViewModels, Dagger2 and more

### Used libraries:
- AppCompatv7
- CardView
- RecyclerView
- Architecture components: ViewModel, LiveData, Extensions, Testing
- OkHttp
- Retrofit
- Dagger2, DaggerAndroid, DaggerAndroidSupport
- Moshi + Moshi codegen
- Picasso
- Niddler

### Others
- Kotlin
- Databinding
- Custom ObservableFuture implementation (see https://github.com/icapps/androidarchitecture)
- Dependency update plugin

### ViewModel architecture

ViewModels can be injected in `BaseActivity`'s and `BaseFragment`s by using the `getOrCreateViewModel<T>()` method. See `MainActivity` for an example.
To share a viewmodel between fragments, inject a viewmodel in the underlying activity, and use `getOrCreateActivityViewModel()` in the fragments.

You will also need to register each viewModel in `ViewModelModule.kt` so Dagger can provide the viewModel with its dependencies.