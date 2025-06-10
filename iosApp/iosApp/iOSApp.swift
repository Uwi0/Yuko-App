import SwiftUI
import ComposableArchitecture

@main
struct iOSApp: App {
	
	static let store = Store(initialState: RootFeature.State(), reducer: { RootFeature()})
	
	init () {
		Koin.start()
	}
	
	var body: some Scene {
		WindowGroup {
			RootView(store: iOSApp.store)
		}
	}
}
