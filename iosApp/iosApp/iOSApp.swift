import SwiftUI
import ComposableArchitecture

@main
struct iOSApp: App {
    
    init () {
        Koin.start()
    }
    
    var body: some Scene {
        WindowGroup {
            MainMenuScreen(store: Store(initialState: MainMenuFeature.State()){
                MainMenuFeature()
            })
        }
    }
}
