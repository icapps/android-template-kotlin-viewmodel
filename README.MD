# icapps Kotlin project template with LiveData, ViewModels, Dagger2 and more

### Used libraries:
- AppCompatv7
- CardView
- RecyclerView
- Architecture components: ViewModel, LiveData, Extensions, Testing
- OkHttp
- Retrofit
- Dagger2, DaggerAndroid, DaggerAndroidSupport
- Moshi
- Picasso
- Niddler

### Others
- Kotlin
- Databinding
- Custom ObservableFuture implementation
- Dependency update plugin

### ViewModel architecture

ViewModels can be injected in `BaseActivity`'s and `BaseFragment`s by using the `getOrCreateViewModel<T>()` method. You can provide an optional `savedInstanceState` bundle. The instance state will automatically be saved in `onSaveInstanceState` if you are using the Base activity or fragment classes. See `MainActivity` for an example.
You will also need to register your viewModel in `ViewModelModule.kt` so Dagger can provide the viewModel with its dependencies.
